package com.csc207.project.flights;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 * Class for managing all Flights, uploading and getting the Flights from a
 * file.
 */
public class FlightManager implements Serializable {

    private static final long serialVersionUID = 8641290543921831234L;

    // The maximum layover time (hours)
    public static final int LAYOVER_MAX = 6;

    private String filePath;

    // Graph to store all Flights and their connecting Flights
    private Graph flights = new Graph();

    /**
     * Constructs
     * @param file the serialized file containing the Graph object
     */
    public FlightManager(){

    }
    public FlightManager(File file) throws ClassNotFoundException, IOException {

        // Records the path of the serialized data file.
        this.filePath = file.getPath();

        // Populate the flights Graph using serialized data stored in file,
        // if it exists.  If not, create a new empty file for it to be saved in
        // later.
        if (file.exists()) {

            // Read from the serialized file flights.ser
            readFromFile(file.getPath());
        } else {

            // Create new file flights.ser
            file.createNewFile();
        }

    }

    public Graph getAllFlights() {
        return this.flights;
    }

    /**
     * Read serialized flight data from file at path and populate
     * this FlightManager's Graph.
     * @param path the path the CSV file is saved
     */
    private void readFromFile(String path) {
        try {
            InputStream file = new FileInputStream(path);
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream(buffer);

            // Deserialize the clients HashMap
            flights = (Graph) input.readObject();
            input.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Populates the Graph of Flights given the path containing the file
     * that contains all Flight info.
     *
     * @param filePath the file path containing the Flight info
     * @throws IOException
     * @throws NoSuchFlightException
     * @throws ParseException
     */
    public void uploadFlightInfo(String filePath)
            throws IOException, NoSuchFlightException, ParseException {
        Scanner sc = new Scanner(new File(filePath));
        while (sc.hasNextLine()) {

            // The new Flight to be added
            Flight newFlight = new Flight(sc.nextLine());

            // If there is already a Flight with the same flight number
            if (flights.containsFlight(newFlight)){

                // Removes the old flight from the graph
                flights.remove(flights.getFlight(Integer.parseInt(newFlight.
						getFlightNo())));
                // Adds Flight object to Graph
                flights.addFlight(newFlight);

            } else {
                flights.addFlight(newFlight);
            }

            // Links up myFlight to the Flights in Graph
            // Iterate through Set of Flights
            for (Flight existingFlight : flights.getFlights()) {
                long layover = Flight.layoverTime(existingFlight
                                .getArrDateTime(),
                        newFlight.getDepDateTime());
                if (existingFlight.getDestination().equals(
                        newFlight.getOrigin())
                        && (layover >= 0 && layover <= LAYOVER_MAX * 60)) {
                    flights.addPath(existingFlight, newFlight);
                }
            }
        }
        sc.close();
        // Save to the serialized file flights.ser

    }

    /**
     * Writes the flights to serialized data file.
     */
    public void saveToFile() {
        try {
            OutputStream file = new FileOutputStream(filePath);
            OutputStream buffer = new BufferedOutputStream(file);
            ObjectOutput output = new ObjectOutputStream(buffer);

            // Serialize the flights HashMap and write to file
            output.writeObject(flights);
            output.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns all Flights with matching departure date, origin and
     * destination as a String.
     *
     * @param date the Flight's departure date to match
     * @param origin the Flight's origin to match
     * @param destination the Flight's destination to match
     * @return all matching Flights as a String separated by lines
     */
    public String getFlights(String date, String origin, String destination) {

        String matchingFlights = "";

        // Iterate through Set of Flights of this Application
        for (Flight fl : flights.getFlights()) {
            if (fl.getDepDate().equals(date)
                    && fl.getOrigin().equals(origin)
                    && fl.getDestination().equals(destination))  {
                matchingFlights += fl.toString() + "\n";
            }
        }
        return matchingFlights;
    }
}
