package com.taxi.function;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ShaHash implements Hash {

	@Override
	public String hash(String input) throws NoSuchAlgorithmException {
		MessageDigest digest = MessageDigest.getInstance("SHA-512");
		digest.update(input.getBytes());
		byte[] hash = digest.digest();
		StringBuilder sb = new StringBuilder();
		for (byte el : hash) {
			int[] digits = { (el >> 4) & 0b1111, el & 0b1111 };
			for (int d : digits) {
				if (d < 10) {
					sb.append(d);
				} else {
					sb.append((char) ('A' + d - 10));
				}
			}
		}
		return sb.toString();
	}

}
