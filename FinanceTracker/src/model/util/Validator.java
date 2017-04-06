package model.util;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public class Validator {
	private Validator(){
		
	}
	
	public static boolean isValidEmailAddress(String email) {
	   boolean result = true;
	   try {
	      InternetAddress emailAddr = new InternetAddress(email);
	      emailAddr.validate();
	   } catch (AddressException ex) {
	      result = false;
	   }
	   return result;
	}
}
