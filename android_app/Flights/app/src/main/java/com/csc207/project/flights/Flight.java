package com.csc207.project.flights;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.text.*;

/**
 * Flight class for keeping track of flight details.
 * Every flight has a number, departure and arrival date and time,
 * airline, origin, destination and cost.
 * Only Admins may edit Flight info
 */
public class Flight implements Comparable<Flight>, Serializable {

	// The information of this particular Flight
	private String flightNo;
    	private Long time;
	private Date depDate;
	private Date arrDate;
    	private String depDateTime;
    	private String arrDateTime;
	private String airline;
	private String origin;
	private String destination;
	private double cost;
    	private int layover = 6;
	private int numSeats;	// Total number of seats available

	// Format for all dates
	private DateFormat dateFormat = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm");

	/**
	 * Constructs a Flight object based on information given in flightStr
	 *
	 * @param flightStr the Flight's information, in the format
	 * 	"Number,DepartureDateTime,ArrivalDateTime,Airline,Origin,
	 * 	Destination,Price,NumSeats"
	 * @throws ParseException
	 */
	public Flight (String flightStr) throws ParseException {

		String[] flightInfo = flightStr.split(",");

		this.flightNo = flightInfo[0];
		this.depDate = new SimpleDateFormat("yyyy-MM-dd HH:mm")
				.parse(flightInfo[1]);
		this.arrDate = new SimpleDateFormat("yyyy-MM-dd HH:mm")
				.parse(flightInfo[2]);
		this.airline = flightInfo[3];
		this.origin = flightInfo[4];
		this.destination = flightInfo[5];
		this.cost = Double.parseDouble(flightInfo[6]);
		this.numSeats = Integer.parseInt(flightInfo[7]);
	}

	/**
	 * Returns the cost of this Flight.
	 *
	 * @return the cost of this Flight
	 */
	public double getCost() {
		return this.cost;
	}

	/**
	 * Sets the cost of this Flight to cost.
	 * Admin only
	 *
	 * @param cost
	 */
	public void setCost(double cost) {
		this.cost = cost;
	}

	/**
	 * Return the difference (in minutes) between departure and arrival
	 * of this Flight.
	 *
	 * @return the number of minutes this Flight will take
	 */
	public long getTime() {
		return (arrDate.getTime() - depDate.getTime())/60000;
	}

	/**
	 * Sets the number of seats of the Flight
	 * Admin onl
	 *
	 * @param flightNo
	 */
	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}

	/**
	 * Gets this Flight's number.
	 *
	 * @return this Flight's flight number
	 */
	public String getFlightNo() {
		return this.flightNo;
	}

	/**
	 * Gets this Flight's departure date.
	 *
	 * @return this Flight's departure date
	 */
	public String getDepDate() {
		String[] dateOnly = getDepDateTime().split(" ");
		return dateOnly[0];
	}

	/**
	 * Gets this Flight's departure date and time.
	 *
	 * @return this Flight's departure date
	 */
	public String getDepDateTime() {
		return dateFormat.format(depDate);
	}

	/**
	 * Sets the departure date and time of this Flight to date.
	 * Admin only
	 *
	 * @param date
	 */
	public void setDepDateTime(Date date) {
		this.depDate = date;
	}


	/**
	 * Gets this Flight's arrival date.
	 *
	 * @return this Flight's arrival date
	 */
	public String getArrDate() {
		String[] dateOnly = getArrDateTime().split(" ");
		return dateOnly[0];
	}

	/**
	 * Sets the arrival date and time of this Flight to date.
	 * Admin only
	 *
	 * @param date the arrival date and time to set
	 */
	public void setArrDateTime(Date date) { this.arrDate = date; }

	/**
	 * Gets this Flight's arrival date and time.
	 *
	 * @return this Flight's arrival date
	 */
	public String getArrDateTime() {
		return dateFormat.format(arrDate);
	}

	public Long getDepTime() {
		return depDate.getTime()/60000;
	}

	public Long getArrTime() {
		return arrDate.getTime()/60000;
	}
	/**
	 * Gets this Flight's airline.
	 *
	 * @return this Flight's airline
	 */
	public String getAirline() {
		return this.airline;
	}

	/**
	 * Sets the airline of this Flight to airline.
	 * Admin only
	 *
	 * @param airline
	 */
	public void setAirline(String airline) {
		this.airline = airline;
	}

	/**
	 * Gets this Flight's origin.
	 *
	 * @return this Flight's origin
	 */
	public String getOrigin() {
		return this.origin;
	}

	/**
	 * Sets the origin of this Flight to origin.
	 * Admin only
	 *
	 * @param origin
	 */
	public void setOrigin(String origin) {
		this.origin = origin;
	}

	/**
	 * Gets this Flight's destination.
	 *
	 * @return this Flight's destination
	 */
	public String getDestination() {
		return this.destination;
	}

	/**
	 * Sets the destination of this Flight to dest.
	 * Admin only
	 *
	 * @param dest
	 */
	public void setDestination(String dest) {
		this.destination = dest;
	}

	/**
	 * Gets this Flight's number of available seats.
	 *
	 * @return this Flight's numSeats
	 */
	public int getNumSeats() {
		return this.numSeats;
	}

	/**
	 * Sets the number of available seats of this Flight to seats.
	 * Admin only
	 *
	 * @param seats
	 */
	public void setNumSeats(int seats) {
		this.numSeats = seats;
	}

	/**
	 * Calculates the difference between date1 and date2 in minutes. 
	 * Calculated in minutes for greater accuracy.
	 *
	 * @param date1	the first Flight's arrival date and time
	 * @param date2 the second Flight's departure date and time
	 * @return the difference in minutes between the two Dates
	 * @throws ParseException
	 */
	public static long layoverTime(String date1, String date2)
			throws ParseException {
		Date arrDate = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(
				date1);
		Date depDate = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(
				date2);
		return (depDate.getTime() - arrDate.getTime())/60000;
		
	}

	/**
	 * Returns a String representation of this Flight in this format:
	 * Number,DepartureDateTime,ArrivalDateTime,Airline,Origin,
	 * Destination,Price,NumSeats (the dates are in the format YYYY-MM-DD;
	 * the price has exactly two decimal places)
	 *
	 * @return String representing this Flight 
	 */
	public String toString() {

		return flightNo + "," + getDepDateTime() + "," 
			+ getArrDateTime() + "," + airline + "," + origin
		       	+ "," + destination + "," + String.format("%.2f", cost)
				+ "," + String.valueOf(numSeats);
	}

    /**
     * Compares this Flight to Flight other. Comparison is based on comparison
     * of Flight numbers.
     *
     * @return an int < 0, if flightNo of this Node is less than ID of other
     *                 0, if ID of this Node is equal to ID of other
     *                 an int > 0, otherwise
     */
	@Override
	public int compareTo(Flight other) {
		return Integer.parseInt(this.flightNo)
				- Integer.parseInt(other.flightNo);
	}

	/**
	 * Return true if this Flight is equal to other Flight by comparing 
	 * their strings.
	 *
	 * @param other the Flight to compare with
	 * @return true if and only if this Flight is the same as other Flight.
	 */
	public boolean equals(Flight other) {
		return this.toString().equals(other.toString());
	}
}
