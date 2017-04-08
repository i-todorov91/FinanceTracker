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
		if(session.isNew() || (session.getAttribute("logged") != null && (Boolean) session.getAttribute("logged") && session.getAttribute("IP") != request.getRemoteAddr())){
			session.invalidate();
			// TODO
			// redirect to home page
		}
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		JsonObject result = new JsonObject();
		if(Validator.isValidEmailAddress(email) && Validator.validPassword(password)){
			if(UserDAO.getInstance().validLogin(email, password)){
				response.setStatus(200);
				result.addProperty("login", "successful");
				session.setAttribute("logged", true);
				session.setAttribute("username", email);
				session.setAttribute("IP", request.getRemoteAddr());
			}
			else
			{
				// status code for bad request
				// the server cannot or will not process the request due to something that is perceived to be a client error
				response.setStatus(400);
				result.addProperty("login", "failed");
				session.setAttribute("logged", false);
			}
		}
		else{
			// status code for bad request
			// the server cannot or will not process the request due to something that is perceived to be a client error
			response.setStatus(400);
			result.addProperty("login", "failed");
			session.setAttribute("logged", false);
		}
		response.getWriter().append(result.toString());
	}

}
