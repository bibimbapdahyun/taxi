package com.taxi.function;

import java.security.NoSuchAlgorithmException;

/**
 * The class is used to generate password hashes to the database.
 * @param args
 */
public class HashMaker {
	public static void main(String[] args) {
		Hash hash = new ShaHash();
		try {
			System.out.println(hash.hash("380000000002"));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}
	
}
