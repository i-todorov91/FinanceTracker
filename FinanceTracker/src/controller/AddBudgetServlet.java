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
import model.budget.Budget;
import model.user.User;

/**
 * Servlet implementation class AddBudgetServlet
 */
@WebServlet("/addbudget")
public class AddBudgetServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO check if user is logged, session, etc
		HttpSession session = request.getSession();
		JsonObject result = new JsonObject();
		if((Boolean) session.getAttribute("logged") != null && (Boolean) session.getAttribute("logged")){
			String name = request.getParameter("name");
			double balance = Double.parseDouble(request.getParameter("balance"));
			Budget toAdd = new Budget(name, balance);
			String username = (String) session.getAttribute("username");
			User user = UserDAO.getInstance().getAllUsers().get(username);
			if(UserDAO.getInstance().addBudget(toAdd, user)){
				response.setStatus(200);
				result.addProperty("success", true);
			}
			else{
				// status code for bad request
				// the server cannot or will not process the request due to something that is perceived to be a client error
				response.setStatus(400);
				result.addProperty("success", false);
			}
		}
		else{
			// status code for bad request
			// the server cannot or will not process the request due to something that is perceived to be a client error
			response.setStatus(400);
			result.addProperty("success", false);
		}
		response.getWriter().append(result.toString());
	}

}
