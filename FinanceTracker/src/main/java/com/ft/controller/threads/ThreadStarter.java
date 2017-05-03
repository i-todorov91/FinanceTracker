package com.ft.controller.threads;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ThreadStarter {
	
	private final EmailDaemonThread edt = new EmailDaemonThread();

	private final PdfCleanerThread pct = new PdfCleanerThread();
	
	@PostConstruct
	public void initialize(){
		edt.setDaemon(true);
		edt.start();
		pct.setDaemon(true);
		pct.start();
	}
	
}
