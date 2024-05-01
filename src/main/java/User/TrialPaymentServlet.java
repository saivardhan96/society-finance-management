package User;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(name = "trialPaymentServlet",urlPatterns = "/TrialPayment-Servlet")
public class TrialPaymentServlet extends HttpServlet {
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        if(req.getSession().getAttribute("username")==null) resp.sendRedirect("login.jsp");
//        else{
//            doPost(req,resp);
//        }
//    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        ServletContext servletContext = getServletContext();
        Connection con = (Connection) servletContext.getAttribute("con");
        HttpSession session = req.getSession();
        String un = (String) session.getAttribute("userName");
        int dueAmount = 0;  // Amount user needs to pay...
        try {
            PreparedStatement ps = con.prepareStatement("select due_amount from financetrail where uname =?;");
            ps.setString(1,un);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) dueAmount=rs.getInt(1);
            System.out.println("Due amount: "+dueAmount);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ArrayList<String> serviceList = (ArrayList<String>) getServletContext().getAttribute("serviceList");
//        ArrayList<Integer> amountList = (ArrayList<Integer>) getServletContext().getAttribute("amountList");
        StringBuilder reqList = new StringBuilder( (String) getServletContext().getAttribute("reqList"));
        int duePayments = 0; // Payments user needs to make
        for(int i=0;i<reqList.length();i++) if(reqList.charAt(i)=='1') duePayments++;
//        String temp = (String) getServletContext().getAttribute("reqList");
//        System.out.println(serviceList);
        if(!serviceList.isEmpty() && duePayments>0){
            resp.sendRedirect("Trail.jsp");
        }
        else{
            resp.setContentType("text/html");
            out.println("<!DOCTYPE html>\n" +
                    "<html lang=\"en\">\n" +
                    "<head>\n" +
                    "    <meta charset=\"UTF-8\">\n" +
                    "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                    "    <title>No Pending Payments.</title>\n" +
                    "    <style>\n" +
                    "        body {\n" +
                    "            font-family: Arial, sans-serif;\n" +
                    "            text-align: center;\n" +
                    "            margin-top: 50px;\n" +
                    "        }\n" +
                    "\n" +
                    "        h1 {\n" +
                    "            color: #333;\n" +
                    "        }\n" +
                    "\n" +
                    "        a {\n" +
                    "            color: #007bff;\n" +
                    "            text-decoration: none;\n" +
                    "            font-size: 20px;\n" +
                    "            cursor: pointer;\n" +
                    "        }\n" +
                    "\n" +
                    "        a:hover {\n" +
                    "            text-decoration: underline;\n" +
                    "        }\n" +
                    "    </style>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "    <h1>No Pending Payments.</h1>\n" +
                    "    <p><a href = 'welcome.jsp' >Go Back</a></p>\n" +
                    "</body>\n" +
                    "</html>");
        }
    }
}
