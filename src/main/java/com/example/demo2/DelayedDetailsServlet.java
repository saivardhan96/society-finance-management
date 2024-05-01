package com.example.demo2;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static java.lang.System.out;

@WebServlet(name = "delayedDetailsServlet", urlPatterns = "/DelayedDetails-Servlet")
public class DelayedDetailsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            PrintWriter out = resp.getWriter();
            ServletContext servletContext = getServletContext();
            Connection con = (Connection) servletContext.getAttribute("con");
            PreparedStatement ps = con.prepareStatement("select email_id,phone_number from familytrail where uname in (select uname from financetrail where due_times>1)");
            ResultSet resultSet = ps.executeQuery();
            resp.setContentType("text/html");

            out.println("<table>\n" +
                    "  <tr>\n" +
                    "    <th>Phone</th>\n" +
                    "    <th>email</th>\n" +
                    "    </tr>\n");
            while (resultSet.next()){
                out.println("  <tr>\n" +
                        "    <td>"+ resultSet.getString("phone_number")+"</td>\n" +"\t"+
                        "    <td>"+ resultSet.getString("email_id")+"</td>\n" +
                        "     </tr>");
            }
            out.println("</table>");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
