package com.example.demo2;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@WebServlet(name = "adminpaymentrequestservlet",urlPatterns = "/PaymentRequests-Admin")
public class AdminPaymentRequestServlet extends HttpServlet {
    private String paidItems = "";
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext sc = getServletContext();
        Connection  con = (Connection) sc.getAttribute("con");
        PreparedStatement reqPs;
        String[] acceptedUsernames = req.getParameter("acceptedUsernames").split("\\s+");
        System.out.println(req.getParameter("acceptedUsernames"));
        System.out.println(Arrays.toString(acceptedUsernames));

        try {
            int updates = 0;
            for (String acceptedUsername: acceptedUsernames) { // try if there's any optimal way
                reqPs = con.prepareStatement("select reqItems,reqStatus from financetrail where uname = ?;");
                reqPs.setString(1, acceptedUsername);
                ResultSet rs = reqPs.executeQuery();
                String requestedItems = "";
                String requestStatus = "";
                while (rs.next()) {
                    requestedItems = rs.getString(1); // items they wanted to pay for
                    requestStatus = rs.getString(2);
                }
                ArrayList<String> reqItemsList = new ArrayList<>(List.of(requestedItems.split(",+")));
                String paymentsQuery = queryStr(reqItemsList);
                System.out.println("Query String: "+paymentsQuery);
                StringBuilder str = new StringBuilder(requestStatus);
                for (int l = 0; l < reqItemsList.size(); l++)
                    str.setCharAt(Integer.parseInt(String.valueOf(reqItemsList.get(l))), '0');
                PreparedStatement histUpdate = con.prepareStatement("insert into history values (?,CURRENT_TIMESTAMP,?,(select reqAmount from financetrail where uname = ?))");
                histUpdate.setString(1,acceptedUsername);
                histUpdate.setString(2,paidItems); // directly add services instead of numbers
                histUpdate.setString(3,acceptedUsername);
                int f = histUpdate.executeUpdate();
                PreparedStatement financeUpdate =
                        con.prepareStatement("update financetrail set request='approved', reqAmount='0', reqStatus = ?, reqItems = null where uname=?;");
                financeUpdate.setString(1, str.toString());
                financeUpdate.setString(2, acceptedUsername);
                PreparedStatement paymentsUpdate = con.prepareStatement(paymentsQuery); // updates number of users paid
                int j = financeUpdate.executeUpdate();
                int k = paymentsUpdate.executeUpdate();
                if (j>0 && k>0 && f>0) updates++;
            }

            if(updates>0)  resp.sendRedirect("adminpage.jsp");
            else System.out.println("Not checked");
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String queryStr(ArrayList<String> reqItemsList){
        paidItems = "";
        int l = reqItemsList.size();
        ServletContext sc = getServletContext();
        ArrayList<String> services = (ArrayList<String>) sc.getAttribute("services");
        StringBuilder str = new StringBuilder("update payments set usersPaid = usersPaid+1 where service in (");
        for(int i=0;i<l;i++){
            str.append("'");
            String s = services.get(Integer.parseInt(String.valueOf(reqItemsList.get(i))));
            str.append(s);
            paidItems+=s;
            str.append("'");
            if(i!=l-1){
                paidItems+=", ";
                str.append(',');
            }
        }
        str.append(");");
        System.out.println(str);
        return str.toString();
    }


}
