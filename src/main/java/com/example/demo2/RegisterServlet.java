package com.example.demo2;


import entity.FamilyDetails;
import entity.FinanceDetails;
import entity.Logins;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.mindrot.jbcrypt.BCrypt;
import utility.HibernateUtil;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

@WebServlet(name = "registerServlet", urlPatterns = "/register-servlet")
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext sc = getServletContext();
        Connection con = (Connection) sc.getAttribute("con");
        String un = req.getParameter("username");
        String p = req.getParameter("password");
        String phoneNumber = req.getParameter("phone");
        String email = req.getParameter("email");
        String name = req.getParameter("name");
        String flat = req.getParameter("flat");
        boolean status = registerHibernate(un,p,phoneNumber,email,flat);
        if(!status) resp.sendRedirect("adminpage.jsp");
        else resp.sendRedirect("index.jsp");

    }

    private boolean registerHibernate(String uname, String pass, String phone, String email, String flat){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction tx = null;

        try{
            tx = session.beginTransaction();
            Logins l = new Logins();
            l.setPassword(hashPassword(pass));
            l.setUname(uname);
            session.persist(l);

            FamilyDetails f = new FamilyDetails();
            f.setLogins(l);
            f.setFlat(flat);
            f.setEmail(email);
            f.setPhoneNumber(phone);
            session.persist(f);
            FinanceDetails fin = new FinanceDetails();
            fin.setLogins(l);
            session.persist(fin);

            tx.commit();
        }
        catch (Exception e){
            assert tx != null;
            if(tx.isActive()) tx.rollback();
            System.out.println("Error: "+e.getMessage());
            return false;
        }
        finally{
            System.out.println("finally");
        }
        session.close();
        return tx.isActive();
    }

    // for encrypting password
    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public static boolean checkPassword(String password, String hashed) {
        // Verify the password using bcrypt
        return BCrypt.checkpw(password, hashed);
    }
}

/*        try {
            PrintWriter out = resp.getWriter();
            PreparedStatement loginPS = con.prepareStatement("insert into logins values(?,?)");
            PreparedStatement familyPs = con.prepareStatement("insert into familytrail values (?,?,?,?,?)");
            PreparedStatement finPs = con.prepareStatement("insert into financetrail values (0,0,0,0,?,null,0,'0',null,null)");
            finPs.setString(1,un);
            loginPS.setString(1,un);
            loginPS.setString(2,p);
            familyPs.setString(1,phoneNumber);
            familyPs.setString(2,email);
            familyPs.setString(3,un);
            familyPs.setString(4,flat);
            familyPs.setString(5,name);
            finPs.setString(1,un);
            int i = loginPS.executeUpdate();
            int j = familyPs.executeUpdate();
            int k = finPs.executeUpdate();
            if (i<=0 || j<=0 || k<=0) {
                out.println("Registration Unsuccessful. Try again!!");
            }
            else{
                resp.sendRedirect("adminpage.jsp");
            }
        }
        catch (Exception e){
            System.out.println(e);
        }*/
