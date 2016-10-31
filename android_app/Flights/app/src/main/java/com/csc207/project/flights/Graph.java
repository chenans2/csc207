package com.csc207.project.flights;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * A one-directional graph of Flights.
 * Two Flights flight1 and flight2 are considered "Linked" if the destination 
 * of flight1 is the origin of flight2.
 */
public class Graph implements Serializable {

	// Each key is a Flight, and each value is a Set of Flights leaving from 
	// the destination of the key Flight.
	private Map<Flight, Set<Flight>> myMap;

    /**
     * Creates a new empty Graph.
     */
	public Graph() {
		myMap = new HashMap<>();
	}
	
	public Map<Flight, Set<Flight>> getMyMap() {
		return this.myMap;
	}

    /**
     * Returns a Set of Flights in this Graph.
     * 
     * @return a Set of Flights in this Graph
     */
	public Set<Flight> getFlights() {
		return myMap.keySet();
	}

    /**
     * Returns the Flight from this Graph with the given flightNo.
     * 
     * @param flightNo the flight number of the Flight to return
     * @return the Flight from this Graph with the given flight number
     * @throws NoSuchFlightException if there is no Flight with flight 
     * 		number flightNo in this Graph
     */
	public Flight getFlight(int flightNo) throws NoSuchFlightException {
		for (Flight flight : getFlights()) {
			if (Integer.parseInt(flight.getFlightNo()) == flightNo) {
				return flight;
			}
		}
		throw new NoSuchFlightException();
	}

    /**
     * Returns a Set of Flights departing from the destination of 
     * the given Flight.
     * 
     * @param flight the Flight whose linked Flights are returned
     * @return a Set of linked Flights of Flight
     */
    public Set<Flight> getLinkedFlights(Flight flight) {
		return myMap.get(flight);
    }

    /**
     * Adds a new Flight with the given value to this Graph.
     * 
     * @param newFlight the new Flight object to be added
     * @throws NoSuchFlightException 
     */
	public void addFlight(Flight newFlight) throws NoSuchFlightException {
		if (!containsFlight(newFlight)) {
			myMap.put(newFlight, new TreeSet<Flight>());
		}
    }

    /**
     * Adds a path from flight1 to flight2 (one way) between the given flights in this Graph. 
     * If there is already a path from flight1 to flight2, or flight1 = flight2, does nothing.
     * 
     * @param flight1 the flight who's destination is the origin of flight2
     * @param flight2 the flight who's origin is the destination of flight1
     * @throws NoSuchFlightException if flight1 or flight2 is not in this Graph
     */
    public void addPath(Flight flight1, Flight flight2) throws NoSuchFlightException {
    	if (!containsFlight(flight1)|| !containsFlight(flight2)) {
            throw new NoSuchFlightException();
        // Check if there is already a path from flight1 to flight1, and no self flights
        } else if (!myMap.get(flight1).contains(flight2) && (!flight1.getFlightNo().equals(flight2.getFlightNo()))) {
        	myMap.get(flight1).add(flight2);
        } else {
        	// Do nothing
        }
    }
    /**
     * A helper method to check if a flight is in the Set of flights.
     * @param flight the flight to check
     * @return true if and only if flight is in the Set of flights
     * @throws NoSuchFlightException
     */
    protected boolean containsFlight(Flight flight) throws NoSuchFlightException {
		boolean alreadyExists = false;
		
		for (Flight existingFlight : getFlights()) {
			if (existingFlight.getFlightNo().equals(flight.getFlightNo())){
				alreadyExists = true;
			}
		}
		return alreadyExists;
    }
	/***
	 *
	 */
	protected void remove(Flight flight){
		myMap.remove(flight);
	}
	/**
	 * Returns a String representation of this Graph of Flights in this format:
	 * Number,DepartureDateTime,ArrivalDateTime,Airline,Origin,
	 * Destination,Price (the dates are in the format YYYY-MM-DD; 
	 * the price has exactly two decimal places)
	 * 
	 * @return String representing a Graph of Flights 
	 */
    @Override
    public String toString() {

        String result = "";

        for (Flight flight: getFlights()) {
            result += flight + " is linked to: ";
            for (Flight linked: getLinkedFlights(flight)) {
                result += linked + " ";
            }
            result += "\n";
        }
        return result;
    }
    
}
