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

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter out = resp.getWriter();
        ServletContext servletContext = getServletContext();
        ArrayList<String> serviceList = (ArrayList<String>) servletContext.getAttribute("serviceList");
        String reqList = (String) servletContext.getAttribute("reqList");
        int duePayments = 0; // Payments user needs to make
        if(reqList==null) duePayments=0;
        else for(int i=0;i<reqList.length();i++) if(reqList.charAt(i)=='1'){
            duePayments++;
            break;
        }
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
