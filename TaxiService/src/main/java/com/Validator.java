package com;

import java.util.Map;

public interface Validator {
	
	<T> Map<String, String> validator(T t);
}
