package com.ft.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MainController {
	
	@RequestMapping(value="/index.html", method=RequestMethod.GET)
	public String getIndex(HttpSession session){

		if(session.getAttribute("logged") != null){
			session.invalidate();
		}
		return "index";
	}
	
}
