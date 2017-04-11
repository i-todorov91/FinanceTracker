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
import model.user.User;
import model.util.Validator;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		JsonObject result = new JsonObject();
		
		// if already logged invalidate the session
		if(session.getAttribute("logged") != null && (Boolean) session.getAttribute("logged") && session.getAttribute("IP") != request.getRemoteAddr()){
			session.invalidate();
			response.setStatus(200);
			result.addProperty("register", "redirect");
		}
		
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String confirmPassword = request.getParameter("confirmPassword");
		String firstName = request.getParameter("firstName");
		String secondName = request.getParameter("secondName");
		
		// check if all the input is correct
		boolean isValid = Validator.validateString(email) && 
								Validator.validateString(password) &&
										Validator.validateString(confirmPassword) &&
												Validator.validateString(firstName) &&
														Validator.validateString(secondName) &&
																Validator.isValidEmailAddress(email) &&
																		Validator.validPassword(password) &&
																				Validator.validPassword(confirmPassword);
		
		// if it is not correct add invalid property
		if(!isValid){
			response.setStatus(200); 
			result.addProperty("register", "invalid");
		}
		else{
			User newUser = new User(email, password);
			newUser.setFirstName(firstName);
			newUser.setLastName(secondName);
			if(UserDAO.getInstance().addUser(newUser)){
				response.setStatus(200);
				result.addProperty("register", true);
			}
			else
			{
				response.setStatus(200); 
				result.addProperty("register", false);
			}
		}
		System.out.println(result);
		response.getWriter().append(result.toString());
	}
}


