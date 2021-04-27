package com.customer.exceptions;

public class IncorrectPaswordException extends Exception {

	private static final long serialVersionUID = 1L;
	public IncorrectPaswordException()
	{
		super("Incorrect Password");
	}
}
