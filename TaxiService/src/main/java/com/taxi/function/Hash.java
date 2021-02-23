package com.taxi.function;

import java.security.NoSuchAlgorithmException;

public interface Hash {
	/**
	 * The method converts the input string (password) to a hex string of numbers
	 * and returns it.
	 * 
	 * @param input
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	String hash(String input) throws NoSuchAlgorithmException;
}
