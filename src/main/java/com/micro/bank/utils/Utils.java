package com.micro.bank.utils;

import com.micro.bank.exceptions.BadParametersException;

import io.micrometer.common.util.StringUtils;

public class Utils {
	
	public static void validateNonEmptyParams(String parameter, String parameterName) throws BadParametersException {
		
		if(StringUtils.isEmpty(parameter)) {
			throw new BadParametersException("El parámetro " + parameterName + " está vacio.");
		}
	}
}
