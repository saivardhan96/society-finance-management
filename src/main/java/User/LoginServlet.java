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
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@WebServlet(name = "loginServlet", urlPatterns = "/login-servlet")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain");
        resp.getWriter().println("Hey!!");
        resp.getWriter().println("This is next line");
    }


    private void setContexts(String username, Connection con) throws SQLException {
        ArrayList<String> ans = getReqStatus(con,username);
        ArrayList<ArrayList<String>> userHistory = getHistory(con,username);
        ServletContext servletContext = getServletContext();
        servletContext.setAttribute("con",con); // Connection to db
        ArrayList<String> services = getServices(con);
        servletContext.setAttribute("serviceList",services); // Services provided by the society.
        servletContext.setAttribute("amountList",getAmounts(con));   // Cost of the services
        // better to set these in payments servlet so that they get updates when ever we visit rather than session wise...
        servletContext.setAttribute("requestStatus",ans.get(0)); // Request status of user (Sent/Approved/Not sent)
        servletContext.setAttribute("reqList",ans.get(1)); // items requested to pay (0->Paid 1->Pending)
        List<Character> reqItemsList = null;
        if(ans.get(2)!=null) reqItemsList = ans.get(2).chars().mapToObj(c -> (char) c).collect(Collectors.toList());
        servletContext.setAttribute("requestedItems",reqItemsList);
        servletContext.setAttribute("historyAmount",userHistory.get(1));
        servletContext.setAttribute("historyServices",userHistory.get(0));
        servletContext.setAttribute("historyPaidDate",userHistory.get(2));
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            PrintWriter out = resp.getWriter();
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sai","root","Kittu@96");
            String un = req.getParameter("username");
            String p = req.getParameter("password");
            setContexts(un,con);
            PreparedStatement ps = con.prepareStatement("select password from logins where uname = ?");
            ps.setString(1,un);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                String password = rs.getString(1);
                System.out.println("Password: "+password);
                System.out.println("P"+p);
                if(Objects.equals(password, p)){
                    HttpSession session = req.getSession(); // Establishing session and attributes.
                    session.setAttribute("userName",un);
                    resp.sendRedirect("welcome.jsp");
                }
                else{
                    req.setAttribute("status","wrong");
                    req.getRequestDispatcher("login.jsp").forward(req,resp);
                }
            }
            else{
                req.setAttribute("status","wrong");
                req.getRequestDispatcher("login.jsp").forward(req,resp);;
            }

        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private ArrayList<String> getServices(Connection con) throws SQLException {
        PreparedStatement servicesPs = con.prepareStatement("select service from payments");
        ResultSet rs = servicesPs.executeQuery();
        ArrayList<String> servicesList = new ArrayList<>();
        while (rs.next()){
            servicesList.add(rs.getString(1));
        }
        return servicesList;
    }

    private ArrayList<Integer> getAmounts(Connection con) throws SQLException {
        PreparedStatement amountPs = con.prepareStatement("select amount from payments");
        ResultSet amountRs = amountPs.executeQuery();
        ArrayList<Integer> amountList = new ArrayList<>();
        while (amountRs.next()){
            amountList.add(amountRs.getInt(1));
        }
        return amountList;
    }

    private ArrayList<String> getReqStatus (Connection con,String un) throws SQLException { // approved or sent...
        PreparedStatement reqPs = con.prepareStatement("select request,reqStatus,reqItems from financetrail where uname=?");
        reqPs.setString(1,un);
        ArrayList<String> ans = new ArrayList<>();
        ResultSet reqResultSet = reqPs.executeQuery();
        String reqStat = "";
        while (reqResultSet.next()) {
            ans.add(reqResultSet.getString(1));
            ans.add(reqResultSet.getString(2));
            ans.add(reqResultSet.getString(3));
        }
        return ans;
    }

    private ArrayList<ArrayList<String>> getHistory(Connection con, String un) throws SQLException{
        ArrayList<String> serviceHist= new ArrayList<>();
        ArrayList<String> amountHist= new ArrayList<>();
        ArrayList<String> dateHist= new ArrayList<>();
        PreparedStatement historyPs = con.prepareStatement("select services,amount,paidDate from history where uname = ?");
        historyPs.setString(1,un);
        ResultSet historySet = historyPs.executeQuery();
        while (historySet.next()){
            serviceHist.add(historySet.getString("services"));
            amountHist.add(String.valueOf(historySet.getInt("amount")));
            dateHist.add(historySet.getString("paidDate"));
        }

        return new ArrayList<>(){{
            add(serviceHist);
            add(amountHist);
            add(dateHist);
        }};
    }

}
