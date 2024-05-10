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
        PreparedStatement paidItemsPs = con.prepareStatement("select reqStatus from financetrail where uname=?");
        paidItemsPs.setString(1,un);
        ResultSet reqResultSet = paidItemsPs.executeQuery();
        String paidStatus = "";
        if(reqResultSet.next()) paidStatus+= reqResultSet.getString(1);
        ServletContext sc = getServletContext();
        sc.setAttribute("reqList",paidStatus); // items requested to pay

        PreparedStatement reqStatusPs = con.prepareStatement("select request,reqItems from financetrail where uname=?");
        reqStatusPs.setString(1,un);
        ResultSet reqStatusResultSet = reqStatusPs.executeQuery();
        String reqStat = "";
        String reqItems = "";
        while(reqStatusResultSet.next()) {
            reqStat+= reqStatusResultSet.getString(1);
            reqItems+=reqStatusResultSet.getString(2);
        }
        System.out.println("Request Items: "+reqItems);
        List<Character> reqItemsList = reqItems.chars().mapToObj(c -> (char) c).collect(Collectors.toList());
        sc.setAttribute("requestedItems",reqItems);
        sc.setAttribute("requestStatus",reqStat); // request status



    }
}
