package com.taxi.function;

import java.security.NoSuchAlgorithmException;

public interface Hash {
	String hash(String input) throws NoSuchAlgorithmException;
}
