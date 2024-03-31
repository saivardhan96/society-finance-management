package User;

import com.example.demo2.LoginServlet;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

@WebServlet(name = "trailRequestServlet", urlPatterns = "/TrailRequest-Servlet")
public class TrailRequestServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            PrintWriter out = resp.getWriter();
            ServletContext servletContext = getServletContext();
            Connection con = (Connection) servletContext.getAttribute("con");
            PreparedStatement ps1 =con.prepareStatement("update financetrail set request = 'sent' , reqAmount=?, due_amount=due_amount- ?where uname=?");
            String un = (String) servletContext.getAttribute("userName");
            System.out.println("this is called...");
            String requestedAmount = req.getParameter("totalAmountHidden");
            ps1.setString(1,requestedAmount);
            ps1.setString(2,requestedAmount);
            ps1.setString(3,un);
            System.out.println(requestedAmount);
//            PreparedStatement ps = con.prepareStatement("update financetrail set ");
//            ps.setString(1,requestedAmount);
//            int j=ps.executeUpdate();
            int i = ps1.executeUpdate();
            if(i>0 ){
                out.println("Request made successfully!!!");
               req.getRequestDispatcher("welcome.jsp").forward(req,resp);
            }
            else{
                out.println("Request unsuccessful");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
}
