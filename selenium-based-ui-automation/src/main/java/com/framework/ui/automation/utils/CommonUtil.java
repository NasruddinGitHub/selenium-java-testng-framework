package com.framework.ui.automation.utils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CommonUtil {

	private CommonUtil() {

	}

	public static List<String> converCommaSeperatedValueToList(String value) {
		return Arrays.stream(value.split(",")).map(String::trim).collect(Collectors.toList());
	}

}
