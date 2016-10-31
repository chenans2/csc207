package com.csc207.project.flights;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Login activity, the first screen that loads when the app is started.
 * Prompts the user for a email and password to login.
 */
public class LoginActivity extends AppCompatActivity 
	implements View.OnClickListener {

    // Directory where all files are stored
    public static final String DATADIR = "userdata";
    public static final String PASSWORDS = "passwords.txt";

    // For reading and storing client data
    public static final String CLIENTS_FILE = "clients.ser";

    // For reading and storing flight data
    public static final String FLIGHTS_FILE = "flights.ser";

    private Button bLogin;
    private EditText etEmail, etPassword;
    private TextView tvWrongLogin;
    private HashMap<String, String> credentials = new HashMap<>();

    // Protected variables allow us to access these from anywhere within the
    // package, saving us the trouble of continuously passing these through
    // intents as they are used in multiple activities.
    protected static ClientManager clientManager;
    protected static FlightManager flightManager;

    /**
     * On creation, load up credentials from file and create text boxes
     * for a User to input their credentials.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        tvWrongLogin = (TextView) findViewById(R.id.incorrect_login);
        tvWrongLogin.setVisibility(View.INVISIBLE);
        bLogin = (Button) findViewById(R.id.bLogin);
        bLogin.setOnClickListener(this);

        // Find out where this application stores files, and get the
        // directory called DATADIR, or if it doesn't exist, create it.
        File data = this.getApplicationContext().getDir(DATADIR, MODE_PRIVATE);

        // Make a file object that stores this path to a file called FILENAME.
        File credentialsFile = new File(data, PASSWORDS);

        // Load data from credentialsFile
        try {
            readCredentials(credentialsFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Listen for button clicks, check if their credentials are correct and
     * take the User to the main menu if they are.
     */
    @Override
    public void onClick(View v) {

        // Get user input
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();

        // Check if valid email and password
        if (authenticate(email, password)) {

            // Remove the error message
            tvWrongLogin.setVisibility(View.INVISIBLE);

            // Find out where this application stores files, and get the
            // directory called DATADIR, or if it doesn't exist, create it.
            File userdata = this.getApplicationContext().getDir(DATADIR, 
			    MODE_PRIVATE);
            File clientsFile = new File(userdata, CLIENTS_FILE);
            File flightsFile = new File(userdata, FLIGHTS_FILE);

            // Initialize both Managers. It will load data from serialized
            // file if it exists, and if not, will create a new empty file
            // for it to be saved in later
            try {
                clientManager = new ClientManager(clientsFile);
                flightManager = new FlightManager(flightsFile);
            } catch (IOException | ClassNotFoundException e) { }

            // Pass the information to Menu Activity so we don't have to
            // reread from the file
            Intent intent = new Intent(this, MenuActivity.class);
            intent.putExtra("email", email);
            startActivity(intent);

        } else {

            // Display error message and clear both EditText boxes
            tvWrongLogin.setVisibility(View.VISIBLE);
            etEmail.setText("");
            etPassword.setText("");
        }
    }

    /**
     * Checks if the entered email and password match with one from the
     * credentials file.
     *
     * @param email the email the user inputted
     * @param password the password the user inputted
     * @return true if and only if the entered email and password match
     */
    private boolean authenticate(String email, String password) {
        for (String credential : credentials.keySet()) {

            // Check if there is a matching email
            if (credential.equals(email)) {

                // Email exists, return true if the password matches
                return credentials.get(credential).equals(password);
            }
        }
        return false;
    }
    
    /**
     * Reads the credentials (username and password) from the File file
     * and populates this LoginActivity's map of credentials.
     * @param file the file to be read
     */
    public void readCredentials(File file) throws FileNotFoundException {
        Scanner sc = new Scanner(file);
        while (sc.hasNextLine()){
            String[] loginInfo = sc.nextLine().split(",");
            credentials.put(loginInfo[0], loginInfo[1]);
        }
        sc.close();
    }
}
