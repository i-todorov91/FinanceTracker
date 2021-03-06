package com.ft.controller.threads;

import java.io.File;
import java.util.Date;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class PdfCleanerThread extends Thread {

	private static String sep = File.separator;
	private static final String DESTINATION = System.getProperty("os.name").startsWith("Linux") ? 
			sep + "home"+sep+"streetzaki"+sep+"Programming"+sep+"github"+sep+"FinanceTracker"+sep+"FinanceTracker"+sep+"src"+sep+"main"+sep+"webapp"+sep+"static"+sep+"pdf"
				: "D:"+sep+"Programming"+sep+"ITTalents_s7"+sep+"ITtalents_finalProject"+sep+"FinanceTracker"+sep+"src"+sep+"main"+sep+"webapp"+sep+"static"+sep+"pdf";
	private static final long SLEEP_TIME = 60*60*1000;//1 hour
	private static final long DELETE_TIME = 60*60*1000;//1 hour
	
	@Async
	@Override
	public void run() {
		while(true){
			File dir = new File(DESTINATION);
			long now = new Date().getTime();
			File[] pdfs = dir.listFiles();
			if (pdfs != null) {
				for(int i = 0; i < pdfs.length; i++){
					File file = pdfs[i];
					long lastModified = (now - file.lastModified());
					if (lastModified >= DELETE_TIME) {
						try {
							file.delete();
						} catch (Exception e) {
							System.out.println("PdfCleanerThread: deletion of " + file.getName() + " failed.");
							continue;
						}
						System.out.println(file.getName() + " deleted!");
					}
				}
			}
			try {
				Thread.sleep(SLEEP_TIME);
			} catch (InterruptedException e) {
				System.out.println("PdfCleanerThread: interupted.");
				break;
			}
		}
	}
	
}
