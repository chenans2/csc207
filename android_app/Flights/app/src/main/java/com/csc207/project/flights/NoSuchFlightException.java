package com.csc207.project.flights;

/*
 * A class to take care of not being able to find a Flight.
 */
public class NoSuchFlightException extends Exception {
	
	public NoSuchFlightException() {
		
	}
	
	public NoSuchFlightException(String s) {
		super(s);
	}
}