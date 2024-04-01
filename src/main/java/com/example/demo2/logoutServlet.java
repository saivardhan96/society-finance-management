package com.example.demo2;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import javax.swing.text.html.HTMLEditorKit;
import java.io.IOException;

@WebServlet(name = "logoutservlet",urlPatterns = "/Logout-Servlet")
public class logoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        session.removeAttribute("username");
        session.invalidate();
        resp.sendRedirect("http://localhost:8080/index.jsp");
    }
}
