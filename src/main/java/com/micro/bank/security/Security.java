package com.micro.bank.security;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.HexFormat;
import java.util.UUID;

public class Security {

	public static String calculateHash(String algorithm, String content) throws Exception {
        MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
        messageDigest.update(content.getBytes(StandardCharsets.UTF_8));
        byte[] hash = messageDigest.digest();
        return HexFormat.of().formatHex(hash);
	}
	
	public static String calculateUUID(){
		UUID uuid = UUID.randomUUID();
		
		return uuid.toString();
	}
	
	
}
