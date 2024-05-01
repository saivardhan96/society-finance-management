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

@WebServlet(name = "adminpaymentrequestservlet",urlPatterns = "/PaymentRequests-Admin")
public class AdminPaymentRequestServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext sc = getServletContext();
        Connection  con = (Connection) sc.getAttribute("con");
        PreparedStatement reqItems;
        String[] acceptedUsernames = req.getParameter("acceptedUsernames").split("\\s+");
        System.out.println(req.getParameter("acceptedUsernames"));
        System.out.println(Arrays.toString(acceptedUsernames));

        try {
            int updates = 0;
            for (String acceptedUsername: acceptedUsernames) {
                reqItems = con.prepareStatement("select reqItems,reqStatus from financetrail where uname = ?;");
                reqItems.setString(1, acceptedUsername);
                ResultSet rs = reqItems.executeQuery();
                String requestedItems = "";
                String requestStatus = "";
                while (rs.next()) {
                    requestedItems = rs.getString(1);
                    requestStatus = rs.getString(2);
                }
                String paymentsQuery = queryStr(requestedItems);
                System.out.println("Query String: "+paymentsQuery);
                StringBuilder str = new StringBuilder(requestStatus);
                for (int l = 0; l < requestedItems.length(); l++)
                    str.setCharAt(Integer.parseInt(String.valueOf(requestedItems.charAt(l))), '0');
                PreparedStatement financeUpdate =
                        con.prepareStatement("update financetrail set request='approved', reqAmount='0', reqStatus = ?, reqItems = null where uname=?;");
                financeUpdate.setString(1, str.toString());
                financeUpdate.setString(2, acceptedUsername);
                PreparedStatement paymentsUpdate = con.prepareStatement(paymentsQuery);
                int j = financeUpdate.executeUpdate();
                int k = paymentsUpdate.executeUpdate();
                if (j>0 && k>0) updates++;
            }

            if(updates>0) {
                System.out.println("This is checked!!!!");
                resp.sendRedirect("adminpage.jsp");
            }
            else System.out.println("Not checked");
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    private String queryStr(String reqItems){
        int l = reqItems.length();
        ServletContext sc = getServletContext();
        ArrayList<String> services = (ArrayList<String>) sc.getAttribute("services");
        StringBuilder str = new StringBuilder("update payments set usersPaid = usersPaid+1 where service in (");
        for(int i=0;i<l;i++){
            str.append("'");
            str.append(services.get(Integer.parseInt(String.valueOf(reqItems.charAt(i)))));
            str.append("'");
            if(i!=l-1) str.append(',');
        }
        str.append(");");
        return str.toString();

    }


}
