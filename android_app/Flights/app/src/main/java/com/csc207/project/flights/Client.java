package com.csc207.project.flights;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * A serializable Client class
 */
public class Client extends User implements Serializable {

    private static final long serialVersionUID = -6345321639192656431L;

    private String lastName;
    private String firstNames;
    private String address;
    private long creditCardNo;
    private String expDate;
    private ArrayList<Itinerary> bookedItins = new ArrayList<>();

    /**
     * Constructs a new Client object.
     *
     * @param 	lastName this Client's lastName
     * @param   firstNames this Client's firstNames
     * @param   email this Client's email
     * @param   address this Client's address
     * @param   creditCardNo this Clients credit card number
     * @param   expDate this Client's card expiry date
     */
    public Client(String lastName, String firstNames, String email,
                  String address, Long creditCardNo, String expDate) {
        super(email);
        this.lastName = lastName;
        this.firstNames = firstNames;
        this.address = address;
        this.creditCardNo = creditCardNo;
        this.expDate = expDate;
    }


    /**
     * Returns an ArrayList of this Client's booked Itineraries.
     *
     * @return an ArrayList of this Client's booked Itineraries
     */
    public ArrayList<Itinerary> viewBookedItins() {
        return bookedItins;
    }

    /**
     * Gets the last name of this Client.
     *
     * @return the last name of this Client
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name of this Client to lastName
     *
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets the first names of this Client.
     *
     * @return this Client's first names
     */
    public String getFirstNames() {
        return firstNames;
    }

    /**
     * Sets the first names of this Client to firstNames.
     *
     * @param firstNames the firstNames to set
     */
    public void setFirstNames(String firstNames) {
        this.firstNames = firstNames;
    }

    /**
     * Gets the email of this Client.
     *
     * @return this Client's email
     */
    public String getEmail() {
        return super.getEmail();
    }

    /**
     * Sets the email of this Client to email.
     *
     * @param email the email to set
     */
    public void setEmail(String email) {
        super.setEmail(email);
    }

    /**
     * Gets the address of this Client.
     *
     * @return address the address of this Client
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the address of this Client to address.
     *
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Gets the credit card number of this Client.
     *
     * @return this Client's credit card number
     */
    public long getCreditCardNo() {
        return creditCardNo;
    }

    /**
     * Sets the credit card number of this Client to creditCardNo.
     *
     * @param creditCardNo the creditCardNo to set
     */
    public void setCreditCardNo(long creditCardNo) {
        this.creditCardNo = creditCardNo;
    }

    /**
     * Gets this Client's credit card's expiry date.
     *
     * @return this Client's credit card's expiry date
     */
    public String getExpDate() {
        return expDate;
    }

    /**
     * Sets this Client's credit card's expiry date.
     *
     * @param expDate the expDate to set
     */
    public void setExpDate(String expDate) {
        this.expDate = expDate;
    }

    /**
     * Returns a String representation of this Client.
     *
     * @return String representing this Client
     */
    public String toString () {
        return lastName + "," +
                firstNames + "," + super.getEmail() + ","
                + address + "," + creditCardNo + "," + expDate;
    }
    public ArrayList<Itinerary> getBookedItineraries(){
        return bookedItins;
    }
    /**
     * Books itin for this Client if all flights in the Itinerary have at least one available seat.
     * Returns a String based on whether or not there were enough seats available.
     *
     * @param itin the Itinerary to book for this Client
     * @return a String based on whether or not the booking succeeded
     */
    public String bookItinerary(Itinerary itin) {
        boolean seatsAvailable = true;
        for (Flight fl : itin.getFlights()) {
            if (fl.getNumSeats() < 1) {
                seatsAvailable = false;
            } else {
                fl.setNumSeats(fl.getNumSeats() - 1);
            }
        }
        if (seatsAvailable) {
            bookedItins.add(itin);
            return "Itinerary booked";
        }
        else {
            return "Not enough seats on a flight; booking failed";
        }
    }
}
