/*
 * @(#)GoogleAuthenticatorUtils.java	${date}
 *
 * Copyright (c) ${year}. All Rights Reserved.
 *
 */

package ${packagePrefix}.service.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.github.javaclub.sword.core.Strings;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;

/**
 * GoogleAuthenticatorUtils
 *
 * @author <a href="mailto:gerald.chen.hz@gmail.com">Gerald Chen</a>
 * @version $Id: GoogleAuthenticatorUtils.java ${currentTime} Exp $
 */
public class GoogleAuthenticatorUtils {
	
	/**
     * The format string to generate the Google Chart HTTP API call.
     */
    private static final String TOTP_URI_FORMAT =
            "https://api.qrserver.com/v1/create-qr-code/?data=%s&size=200x200&ecc=M&margin=0";
    
    public static void main(String[] args) {
//    	String secretKey = "ASL5L472JGEYA5G3MHXHROJZ2YDWVEVV";
//		String url = formatQrcodeUrl("XINC-SSO-DEV", "chenzq", secretKey);
//		System.out.println(url);
    	String pwd = Strings.fixed(8);
    	System.out.println(pwd);
	}
    
    public static String generateSecretKey() {
        com.warrenstrange.googleauth.GoogleAuthenticator gAuth  = new com.warrenstrange.googleauth.GoogleAuthenticator();
        final GoogleAuthenticatorKey gakey = gAuth .createCredentials();
        String key = gakey.getKey();
        return key;
    }
	
	public static String formatQrcodeUrl(String issuer, String accountName, String secretKey) {
		String txt = getOtpAuthTotpURL(issuer, accountName, secretKey);
		String str = "";
		try {
			str = URLEncoder.encode(txt, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException("UTF-8 encoding is not supported by URLEncoder.", e);
        }
		
		return String.format(TOTP_URI_FORMAT,  str);
	}
	
	public static String getOtpAuthTotpURL(String issuer, String accountName, String secretKey) {
		String pattern = "otpauth://totp/{}:{}?secret={}&issuer={}&algorithm=SHA1&digits=6&period=30";
		String data = Strings.format(pattern, issuer, accountName, secretKey, issuer);
		return data;
	}

	public static boolean verify(String key, int password) {
		com.warrenstrange.googleauth.GoogleAuthenticator gAuth = new com.warrenstrange.googleauth.GoogleAuthenticator();
        boolean isPattern = gAuth.authorize(key,password);
        return isPattern;
    }

}
