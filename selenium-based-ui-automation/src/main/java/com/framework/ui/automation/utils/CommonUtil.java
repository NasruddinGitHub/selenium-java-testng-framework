package com.framework.ui.automation.utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CommonUtil {

	private CommonUtil() {

	}

	public static <T> List<T> convertDelimitedValueToList(String value, String delimiter, Function<String, T> mapper) {

		if (value == null || value.isBlank()) {
			return Collections.emptyList();
		}

		return Arrays.stream(value.split(delimiter)).map(String::trim).map(mapper).collect(Collectors.toList());
	}

}
