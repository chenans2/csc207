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
import java.util.HashMap;
import java.util.Scanner;

/**
 * Manages the saving and loading of Clients.
 */
public class ClientManager implements Serializable {

    private static final long serialVersionUID = 8641290543921831486L;
    private String filePath;

    /** A mapping of client emails to Clients. */
    private HashMap<String, Client> clients;

    /**
     * Creates a new empty ClientManager.
     *
     * @param file the serialized file
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public ClientManager(){
        clients = new HashMap<>();
    }
    public ClientManager(File file) throws ClassNotFoundException,
            IOException {

        clients = new HashMap<>();

        // Records the path of the serialized data file.
        this.filePath = file.getPath();

        // Populate the clients HashMap using serialized data stored in file,
        // if it exists.  If not, create a new empty file for it to be saved in
        // later.
        if (file.exists()) {

            // Read from the serialized file clients.ser
            readFromFile(file.getPath());
        } else {

            // Create new file clients.ser
            file.createNewFile();

            // Read from clients.csv to get client info for first time
            //uploadClientInfo(seed.getPath());
        }
    }

    /**
     * Returns the current HashMap of Clients.
     * @return clients
     */
    public HashMap<String, Client> getClients() {
        return clients;
    }

    /**
     * Populates the records map from the file at path filePath.
     * @param filePath the path of the data file
     * @throws FileNotFoundException if filePath is not a valid path
     */
    public void uploadClientInfo(String filePath) throws FileNotFoundException {
        Scanner sc = new Scanner(new File(filePath));
        while (sc.hasNextLine()){
            String[] userInfo = sc.nextLine().split(",");
            Client myClient = new Client(userInfo[0], userInfo[1], userInfo[2],
                    userInfo[3], Long.parseLong(userInfo[4]), userInfo[5]);

            // If the Client already exists, delete the old one
            if (clients.keySet().contains(userInfo[2])) {
                clients.remove(userInfo[2]);
            }

            clients.put(myClient.getEmail(), myClient);
        }
        sc.close();
        // Save to the serialized file clients.ser
    }

    /**
     * Reads serialized client data from file at path and populates
     * this CLientManager's map with that data.
     * @param path the path of the file to read
     */
    private void readFromFile(String path) {
        try {
            InputStream file = new FileInputStream(path);
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream(buffer);

            // Deserialize the clients HashMap
            clients = (HashMap<String, Client>) input.readObject();
            input.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Writes the clients to serialized data file.
     */
    public void saveToFile() {
        try {
            OutputStream file = new FileOutputStream(filePath);
            OutputStream buffer = new BufferedOutputStream(file);
            ObjectOutput output = new ObjectOutputStream(buffer);

            // Serialize the clients HashMap and write to file
            output.writeObject(clients);
            output.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns a String representation of this ClientManager.
     * @return result the String representing this ClientManager
     */
    @Override
    public String toString() {
        String result = "";
        for (String client : clients.keySet()) {
            result += client + "\n";
        }
        return result;
    }

}
