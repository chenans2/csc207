package com.csc207.project.flights;

import java.util.Date;

/**
 * An Administrator class.
 */
public class Administrator extends User {

	/**
	 * Constructor for a new Administrator instance.
	 *
	 * @param email this Administrator's email
	 */
	public Administrator(String email) {
		super(email);
	}

	/**
	 * Sets Client cl's address to address.
	 *
	 * @param cl the Client whose  to change
	 * @param address the new address for Client
	 */
	public void setAddress(Client cl, String address) {
		cl.setAddress(address);
	}

	/**
	 * Sets Client cl's credit card expiry date to expDate.
	 *
	 * @param cl the Client whose  to change
	 * @param expDate the new credit card expiry date for Client
	 */
	public void setExpDate(Client cl, String expDate) {

		cl.setExpDate(expDate);
	}

	/**
	 * Sets Client cl's email to email.
	 *
	 * @param cl the Client whose  to change
	 * @param email the new email for Client
	 */
	public void setEmail(Client cl, String email) {

		cl.setEmail(email);
	}

	/**
	 * Sets Client cl's credit card number to creditCardNo.
	 *
	 * @param cl the Client whose credit card number to change
	 * @param creditCardNo the new  for Client
	 */
	public void setCreditCardNo(Client cl, Long creditCardNo) {

		cl.setCreditCardNo(creditCardNo);
	}

	/**
	 * Sets Client cl's first name to lastName.
	 *
	 * @param cl the Client whose first name to change
	 * @param firstName the new first name for Client
	 */
	public void setFirstNames(Client cl, String firstName) {

		cl.setFirstNames(firstName);
	}

	/**
	 * Sets Client cl's last name to lastName.
	 *
	 * @param cl the Client whose last name to change
	 * @param lastName the new last name for Client
	 */
	public void setLastName(Client cl, String lastName) {

		cl.setLastName(lastName);
	}

	/**
	 * Attempts to book Itinerary itin for Client cl.
	 * Returns a String based on whether or not there were enough seats availible.
	 *
	 * @param itin the Itinerary to book
	 * @param cl the client to book itin for
	 * @return a String based on whether or not the booking succeeded
	 */
	public String bookItinerary(Itinerary itin, Client cl) {
		return cl.bookItinerary(itin);
	}

	/**
	 * Sets Flight fl's airline to airline.
	 *
	 * @param fl the Flight whose airline to set
	 * @param airline the airline to set to
	 */
	public void setAirline(Flight fl, String airline) {
		fl.setAirline(airline);
	}

	/**
	 * Sets Flight fl's origin to origin.
	 *
	 * @param fl the Flight whose origin to set
	 * @param origin the origin to set to
	 */
	public void setOrigin(Flight fl, String origin) {
		fl.setOrigin(origin);
	}

	/**
	 * Sets Flight fl's destination to destination.
	 *
	 * @param fl the Flight whose destination to set
	 * @param destination the destination to set to
	 */
	public void setDestination(Flight fl, String destination) {
		setDestination(fl, destination);
	}

	/**
	 * Sets Flight fl's cost to cost.
	 *
	 * @param fl the Flight whose cost to set
	 * @param cost the cost to set to
	 */
	public void setCost(Flight fl, double cost) {
		fl.setCost(cost);
	}
	
	/**
	 * Sets Flight fl's departure date to depDate.
	 *
	 * @param fl the Flight whose departure date and time to set
	 * @param depDateTime the departure date to set to
	 */
	public void setDepDateTime(Flight fl, Date depDateTime) {
		fl.setDepDateTime(depDateTime);
	}

	/**
	 * Sets Flight fl's arrival date and time to arrDate.
	 *
	 * @param fl the Flight whose arrival date and time to set
	 * @param arrDateTime the departure date to set to
	 */
	public void setArrDateTime(Flight fl, Date arrDateTime) {
		fl.setArrDateTime(arrDateTime);
	}
	
	/**
	 * Sets Flight fl's number of availible seats to seats.
	 *
	 * @param fl the Flight whose seats to set
	 * @param seats the number of seats to set to
	 */
	public void setSeats(Flight fl, int seats) {
		fl.setNumSeats(seats);
	}
}
