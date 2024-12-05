package com.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.database.UserDao;
import com.pojo.User;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        
        try {
            User rs = UserDao.loginUser(email, password);
            if (rs != null) {
                HttpSession session = request.getSession();
                session.setAttribute("userId", rs.getUserId());
                response.sendRedirect("userdashboard.jsp");
            }
            else {
                out.println("<script type=\"text/javascript\">");
                out.println("alert('Invalid User!!!');");
                out.println("window.location.href = 'login.jsp';");
                out.println("</script>");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

}
