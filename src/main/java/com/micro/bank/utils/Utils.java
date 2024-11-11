package com.micro.bank.utils;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;
import com.micro.bank.exceptions.BadParametersException;

import io.micrometer.common.util.StringUtils;

public class Utils {
	
	public static void validateNonEmptyParams(String parameter, String parameterName) throws BadParametersException {
		
		if(StringUtils.isEmpty(parameter)) {
			throw new BadParametersException("The parameter " + parameterName + " is empty.");
		}
	}

	public static void validatePhoneNumber(String phoneNumber) throws BadParametersException {
		PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
		PhoneNumber number;
		try {
		  number = phoneUtil.parse(phoneNumber, "ES");
		} catch (NumberParseException e) {
			throw new BadParametersException("Error verifying number");
		}
		
		if(phoneUtil.isValidNumber(number)==false) {
			throw new BadParametersException("the number is invalidate");
		}
	}
}