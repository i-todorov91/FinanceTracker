package com.ft.model.util;

import java.io.IOException;
import java.util.Date;
import java.util.Random;

import com.ft.model.util.exceptions.InvalidEncryptionException;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class StringUtil {
	
	private static StringUtil instance = null;
	
	private StringUtil(){}
	
	public static StringUtil getInstance(){
		if(instance == null){
			instance = new StringUtil();
		}
		return instance;
	}
	
	private static Random random = new Random((new Date()).getTime());

	/**
	 * Encrypts the string along with salt
	 * 
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public String encrypt(String password) {
		// TODO
		// add key to the password for more secure
		BASE64Encoder encoder = new BASE64Encoder();

		// generate the salt
		byte[] salt = new byte[32];
		random.nextBytes(salt);
		return encoder.encode(salt) + encoder.encode(password.getBytes());
	}

	/**
	 * Decrypts the string and removes the salt
	 * 
	 * @param encryptKey
	 * @return
	 * @throws InvalidEncryptionException
	 * @throws Exception
	 */
	public String decrypt(String encryptKey) throws InvalidEncryptionException {
		// we ignore he salt here
		if (encryptKey.length() > 44) {
			String cipher = encryptKey.substring(44);
			BASE64Decoder decoder = new BASE64Decoder();
			try {
				return new String(decoder.decodeBuffer(cipher));
			} catch (IOException e) {
				throw new InvalidEncryptionException();
			}
		}
		return null;
	}

}
