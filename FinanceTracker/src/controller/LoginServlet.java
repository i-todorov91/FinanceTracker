package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;

import model.DAO.UserDAO;
import model.util.Validator;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		JsonObject result = new JsonObject();
		if(Validator.isValidEmailAddress(email) && Validator.validPassword(password)){
			if(UserDAO.getInstance().validLogin(email, password)){
				response.setStatus(200);
				result.addProperty("login", "successful");
			}
			else
			{
				// status code for bad request
				// the server cannot or will not process the request due to something that is perceived to be a client error
				response.setStatus(400);
				result.addProperty("login", "failed");
			}
		}
		else{
			// status code for bad request
			// the server cannot or will not process the request due to something that is perceived to be a client error
			response.setStatus(400);
			result.addProperty("login", "failed");
		}
		response.getWriter().append(result.toString());
	}

}
