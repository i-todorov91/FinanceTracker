package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;

import model.DAO.UserDAO;
import model.user.User;
import model.util.Validator;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String firstName = request.getParameter("firstName");
		String secondName = request.getParameter("secondName");
		JsonObject result = new JsonObject();
		if(!(Validator.validateString(email) && Validator.validateString(password) && Validator.validateString(firstName) && Validator.validateString(secondName) && Validator.isValidEmailAddress(email) && Validator.validPassword(password))){
			response.setStatus(400); 
			// status code for bad request
			// the server cannot or will not process the request due to something that is perceived to be a client error
			result.addProperty("success", false);
		}
		else{
			User newUser = new User(email, password);
			newUser.setFirstName(firstName);
			newUser.setLastName(secondName);
			if(UserDAO.getInstance().addUser(newUser)){
				response.setStatus(200);
				result.addProperty("success", true);
			}
			else
			{
				response.setStatus(400); 
				// status code for bad request
				// the server cannot or will not process the request due to something that is perceived to be a client error
				result.addProperty("success", false);
			}
		}
		response.getWriter().append(result.toString());
	}
}


