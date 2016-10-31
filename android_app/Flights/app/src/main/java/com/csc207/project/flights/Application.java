/*
package com.csc207.project.flights;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Scanner;

*//*

*/
/**
 * Aiming to move all methods here into Activities or Manager classes.
 *
 * A class for keeping track of the application's information, including
 * existing Flights and Clients.
 *//*
*/
/*

public class Application {
	
	// Constants, the maximum layover time
	public static final int LAYOVER_MAX = 6;

	// Map to store all Clients
	private HashMap<String, User> clients;

	// The path of the serialized data file.
	// It gets a value upon construction,  and that path value is used
	// whenever we save or lead this StudentManager.
	private  String filePath;
	
	// ArrayList to store most recently searched for Itineraries
	private ArrayList<Itinerary> searchedItins = new ArrayList<>();
	
	// Graph to store all Flights and their connecting Flights
	public Graph flights = new Graph();
	
	private ClientManager clientManager;
	private FlightManager flightManager;
	
	*//*

*/
/**
	 * Launches a new Application.
	 *
	 *//*


	public Application() {
	}


	
*/
/**
	 * Populates the HashMap of Clients given the path containing the file
	 * that contains all Client info.
	 * 
	 * @param path the directory location of the Client info
	 * @throws IOException
	 *//*


	public void uploadClientInfo(String path) throws IOException {
		clientManager.uploadClientInfo(path);
	}
	
	
*/
/**
	 * Populates the Graph of Flights given the path containing the file
	 * that contains all Flight info.
	 *
	 * @param path the directory location of the Flight info
	 * @throws IOException
	 * @throws NoSuchFlightException
	 * @throws ParseException
	 *//*


	public void uploadFlightInfo(String path)
			throws IOException, NoSuchFlightException, ParseException {
		flightManager.uploadFlightInfo(path);
	}

	
*/
/**
	 * Returns the Client with the given email as a String.
	 * 
	 * @param email the Client to get's email
	 * @return the Client with given email
	 *//*


	public String getClient(String email) {
		return clients.get(email).toString();
	}

	
*/
/**
	 * Returns all Flights with matching departure date, origin and
	 * destination as a String.
	 *
	 * @param date the Flight's departure date to match
	 * @param origin the Flight's origin to match
	 * @param destination the Flight's destination to match
	 * @return all matching Flights as a String separated by lines
	 *//*


	public String getFlights(String date, String origin, String destination) {
		return flightManager.getFlights(date, origin, destination);
	}
	
	
*/
/**
	 * Adds an Itinerary with myFlight as its first Flight
	 * and matches the appropriate date, origin, and destination to this
	 * Applications searchedItins variable.
	 * 
	 * @param 	flightList The list of Flights that will lead to the searched
	 * 			for destination
	 * @param 	date The departure date of the latest Flight
	 * @param 	origin The origin of the Flight to make an Itinerary starting
	 * 			from
	 * @param 	destination The destination goal for the Itinerary
	 * @throws ParseException 
	 *//*


	private void createItineraries(ArrayList<Flight> flightList, 
			Flight myFlight, String date, String origin, String destination) 
					throws ParseException {
		flightList.add(myFlight);
        if (myFlight.getDestination().equals(destination)) {
            searchedItins.add(new Itinerary(flightList));
        } else {
            try {
                for (Flight flight : flights.getLinkedFlights(myFlight)) {
                    ArrayList<Flight> newFlightList = new ArrayList<>();
                    newFlightList.addAll(flightList);
                    createItineraries(newFlightList, flight,
                            myFlight.getArrDate(),
                            myFlight.getDestination(), destination);
                }
            } catch (NullPointerException e) {
                //
            }
        }
	}
	
	
*/
/**
	 * Return all Itineraries matching the given criteria as a String, and sets
	 * the searchedItins variable to match the given criteria.
	 * 
	 * @param date the departure date of the first flight
	 * @param origin the origin of the Itineraries to find
	 * @param destination the destination of the Itineraries to find
	 * @return all Itineraries matching the criteria as a String
	 * @throws ParseException 
	 *//*


	public String getItineraries(String date, String origin, 
			String destination) throws ParseException {
		this.searchedItins.clear();

        for (Flight myFlight: flights.getFlights()) {
            if (myFlight.getDepDate().equals(date) && myFlight.getOrigin()
                    .equals(origin)) {
                createItineraries(new ArrayList<Flight>(), myFlight, date,
                        origin, destination);
            }
        }
        String itineraryStr = "";
        for (Itinerary itineraries : searchedItins) {
            itineraryStr += itineraries.toString();
        }
        return itineraryStr;
	}
	
	
*/
/**
	 * Sorts Itineraries searchedItins according to time, from least to 
	 * greatest.
	 * 
	 * @return the String representation of this Itinerary sorted by time
	 *//*


	public String sortByTime() {
		Collections.sort(searchedItins, Itinerary.timeComparator);

        String itineraryStr = "";
        for (Itinerary itineraries : searchedItins) {
            itineraryStr += itineraries.toString() + "~";
        }
        return itineraryStr;
	}
	
	
*/
/**
	 * Sorts Itineraries searchedItins according to cost, from least to 
	 * greatest, then returns the them as a String.
	 * 
	 * @return the String representation of this Itinerary sorted by cost
	 *//*


	public String sortByCost() {
		Collections.sort(searchedItins, Itinerary.costComparator);
        String itineraryStr = "";
        for (Itinerary itineraries : searchedItins) {
            itineraryStr += itineraries.toString() + "~";
        }
        return itineraryStr;
	}
}

*/
