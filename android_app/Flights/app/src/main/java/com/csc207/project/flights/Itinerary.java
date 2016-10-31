package com.csc207.project.flights;

import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * A class of itineraries for keeping track of flights, total time and cost
 * it takes to complete the trip.
 */
public class Itinerary implements Serializable {

	private static final long serialVersionUID = 8641290541234831234L;
	
	// Every Itinerary is comprised of Flights, has a total travel time, and
	// a total cost
	private ArrayList<Flight> flights;
	private long time = 0;
	private double cost = 0.00;
	private String depDate;
	private String arrDate;
	private String origin;
	private String dest;
	
	/**
	 * Constructs a new Itinerary that is made up of Flights flights.
	 * 
	 * @param flights the Flights which this Itinerary is made up of
	 * @throws ParseException 
	 */
	public Itinerary (ArrayList<Flight> flights) throws ParseException {
		this.flights = flights;
		String lastFlightArrTime = flights.get(0).getDepDateTime();
		for (Flight myFlight : flights) {
			cost += myFlight.getCost();
			// Increment costs by flight cost
			time += myFlight.getTime();
			// Increment time by flight time
			// Add in layover time
			time += Flight.layoverTime(lastFlightArrTime, 
					myFlight.getDepDateTime());
			lastFlightArrTime = myFlight.getArrDateTime();
		}
		this.depDate = flights.get(0).getDepDate();
		this.arrDate = flights.get(flights.size() - 1).getArrDate();
		this.origin = flights.get(0).getOrigin();
		this.dest = flights.get(flights.size() - 1).getDestination();
	}

	/**
	 * Constructs an Itinerary given a String. Specifically created to
	 * create an Itinerary from a given Itinerary format which we display
	 * to Users.
	 *
	 * @param itinString the Itinerary as a String
	 */
	public Itinerary (String itinString) {
		try {
			String[] strings = itinString.split("\n");
			ArrayList<Flight> newList = new ArrayList<Flight>();
			for (int i=0; i < strings.length-2; i++) {
				try {
					int flightNo = Integer.parseInt(
						strings[i].split(",")[0]);
					Flight myFlight = LoginActivity.
						flightManager.getAllFlights()
						.getFlight(flightNo);
					newList.add(myFlight);
				} catch (NoSuchFlightException e) { }
			}
			Itinerary newItin = new Itinerary(newList);
			this.flights = newItin.getFlights();
			this.time = newItin.getTime();
			this.cost = newItin.getCost();
			this.depDate = newItin.getDepDate();
			this.arrDate = newItin.getArrDate();
			this.origin = newItin.getOrigin();
			this.dest = newItin.getDest();
		} catch (ParseException e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * Gets the total cost of this Itinerary.
	 * 
	 * @return this Itinerary's total cost
	 */
	public double getCost() {
		return this.cost;
	}
	
	/**
	 * Gets this Itinerary's total time.
	 * 
	 * @return the total time this Itinerary will take
	 */
	public long getTime() {
		return this.time;
	}

	public String getDepDate() { return this.depDate; }

	public String getArrDate() { return this.arrDate; }

	public String getOrigin() { return this.origin; }

	public String getDest() { return this.dest; }
	
	/**
	 * A comparator class for comparing the cost of two Itineraries.
	 */
	public static Comparator<Itinerary> costComparator = new 
			Comparator<Itinerary>(){
	
		/**
		 * Compares the cost of Itineraries it1 and it2.
		 * 
		 * @param it1 the first Itinerary to compare
		 * @param it2 the other Itinerary to compare
		 * @returns the value 0 if it1 and it2's costs are he same,
		 * 	a value less than 0 if it1 costs less than it2,
		 * 	and a value greater than 0 if it1 costs more than it2.
		 */
		public int compare(Itinerary it1, Itinerary it2){
			int it1Int = (int)(it1.getCost()*100);
			int it2Int = (int)(it2.getCost()*100);
			return (it1Int - it2Int);
		}
	};
	
	/**
	 * A comparator class for comparing the travel time of two Itineraries.
	 */
	public static Comparator<Itinerary> timeComparator = new
			Comparator<Itinerary>(){
		
		/**
		 * Compares the time of Itineraries it1 and it2.
		 * 
		 * @param it1 the first Itinerary to compare
		 * @param it2 the other Itinerary to compare
		 * @return the value 0 if it1 and it2's take the same amount 
		 * of time, a value less than 0 if it1 takes less time than 
		 * it2,	and a value greater than 0 if it1 takes longer than it2
		 */
		public int compare(Itinerary it1, Itinerary it2){
			return ((int)it1.getTime()) - ((int)it2.getTime());
		}
	};

	/**
	 * Returns a String representation of this Itinerary.
	 * Each itinerary contains one Flight per line, in the format:
	 * Number,DepartureDateTime,ArrivalDateTime,Airline,Origin,
	 * Destination followed by total cost (on its own line, exactly two
	 * decimal places), followed by total duration (on its own line, in
	 * format HH:MM).
	 * 
	 * @return itinerary String representing this Itinerary
	 */
	public String toString() {
		String itinerary = "";
		for (Flight f : flights) {
			 itinerary += f.getFlightNo() + "," + f.getDepDateTime() + ","
				+ f.getArrDateTime() + "," + f.getAirline() 
				+ "," + f.getOrigin() + "," + f.getDestination() + "\n";
		}
		itinerary += String.format("%.2f", this.cost) + "\n"
			+ String.format("%02d:%02d", this.time / 60, 
					this.time % 60) + "\n";
		return itinerary;
	}

	/**
	 * Returns a list of Flights that make up this Itinerary.
	 *
	 * @return flights the flights that make up this Itinerary
	 */
	public ArrayList<Flight> getFlights() {
		return this.flights;
	}
}
