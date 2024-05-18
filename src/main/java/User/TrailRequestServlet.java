package User;

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
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(name = "trailRequestServlet", urlPatterns = "/TrailRequest-Servlet")
public class TrailRequestServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            PrintWriter out = resp.getWriter();
            ServletContext servletContext = getServletContext();
            Connection con = (Connection) servletContext.getAttribute("con");
            PreparedStatement ps1 =con.prepareStatement("update financetrail set request = 'sent', last_paid = CURRENT_DATE, reqAmount= reqAmount+?,reqItems= if(reqItems is null ,?,concat(reqItems,?))  where uname=?");
            HttpSession session = req.getSession();
            String un = (String) session.getAttribute("userName");
            int requestedAmount = Integer.parseInt(req.getParameter("totalAmountHidden"));
            String paidList = req.getParameter("paidItems");//should append if there are any pre-existing req && we have to update the reqStatus column... when admin approves this.
            ps1.setInt(1,requestedAmount);
            ps1.setString(2,paidList);
            ps1.setString(3,paidList);
            System.out.println(paidList);
            ps1.setString(4,un); // uploaded in db so when admin approves then 1 -> 0
            System.out.println("Amount selected to pay: "+requestedAmount);
            System.out.println("Items selected to pay: "+paidList); // to do: set 1's to zeroes at these numbers in reqList.
            int i = ps1.executeUpdate();
            if(i>0){
                refreshAttributes(con,un);
                resp.sendRedirect("welcome.jsp");
            }
            else{
                out.println("Request unsuccessful");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void refreshAttributes(Connection con,String un) throws SQLException{
        ServletContext sc = getServletContext();
        PreparedStatement paidItemsPs = con.prepareStatement("select reqStatus,request,reqItems from financetrail where uname=?");
        paidItemsPs.setString(1,un);
        ResultSet reqResultSet = paidItemsPs.executeQuery();
        StringBuilder paidStatus = new StringBuilder();
        StringBuilder reqStat = new StringBuilder();
        StringBuilder reqItems = new StringBuilder();
        while(reqResultSet.next()){
            paidStatus.append(reqResultSet.getString("reqStatus"));
            reqStat.append(reqResultSet.getString("request"));
            reqItems.append(reqResultSet.getString("reqItems"));
        }
        sc.setAttribute("reqList", paidStatus.toString()); // items requested to pay
        ArrayList<String> reqItemsList = new ArrayList<>(List.of(reqItems.toString().split(",+")));
        System.out.println("Requested Items List: "+reqItemsList);
        sc.setAttribute("requestedItems",reqItemsList);
        sc.setAttribute("requestStatus", reqStat.toString()); // request status
    }
}
