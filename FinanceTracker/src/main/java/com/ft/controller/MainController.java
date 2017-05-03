package com.ft.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MainController {
	
	@RequestMapping(value="/index.html", method=RequestMethod.GET)
	public String getIndexGet(HttpSession session){
		
		if(session.getAttribute("logged") != null){
			session.invalidate();
		}
		return "index";
	}
	
	@RequestMapping(value="/index.html", method=RequestMethod.POST)
	public String getIndexPost(HttpSession session, HttpServletRequest req){

		if(session.getAttribute("logged") != null){
			session.invalidate();
		}
		
		// for the contact us form
		
		return "index";
	}
	
	@RequestMapping(value="*/error500", method=RequestMethod.GET)
	public String errorPage(HttpSession session){
		
		return "error500";
	}
	
	
	@RequestMapping(value="/404NotFound.html")
    public String handlePageNotFound() {
		
        return "error404";
    }
	
}
