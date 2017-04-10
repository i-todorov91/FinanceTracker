package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.JsonObject;

/**
 * Servlet implementation class LoadServlet
 */
@WebServlet("/filter")
public class LoadServlet extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println(req.getPathTranslated());
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		JsonObject result = new JsonObject();
		if(session.getAttribute("logged") != null && (Boolean) session.getAttribute("logged")){
			session.invalidate();
			result.addProperty("success", false);
		}
		else{
			result.addProperty("success", true);
		}
		response.setStatus(200);
		response.getWriter().append(result.toString());
	}

}
