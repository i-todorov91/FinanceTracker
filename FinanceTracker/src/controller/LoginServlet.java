package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.JsonObject;

import model.DAO.UserDAO;
import model.util.Validator;
/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		JsonObject result = new JsonObject();
		if((session.getAttribute("logged") != null && (Boolean) session.getAttribute("logged") && session.getAttribute("IP") != null && session.getAttribute("IP") != request.getRemoteAddr())){
			session.setAttribute("logged", false);
			session.removeAttribute("username");
			session.removeAttribute("IP");
			session.invalidate();
			response.setStatus(200);
			result.addProperty("login", "redirect");
		}
		else{
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			if(Validator.isValidEmailAddress(email) && Validator.validPassword(password)){
				if(UserDAO.getInstance().validLogin(email, password)){
					response.setStatus(200);
					result.addProperty("login", true);
					session.setAttribute("logged", true);
					session.setAttribute("username", email);
					session.setAttribute("IP", request.getRemoteAddr());
					session.setMaxInactiveInterval(60);
				}
				else
				{
					response.setStatus(200);
					result.addProperty("login", false);
					session.setAttribute("logged", false);
				}
			}
			else{
				response.setStatus(200);
				result.addProperty("login", false);
				session.setAttribute("logged", false);
			}
		}
		response.getWriter().append(result.toString());
	}

}
