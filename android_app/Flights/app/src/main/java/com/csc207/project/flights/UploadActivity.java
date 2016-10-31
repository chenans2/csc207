package com.csc207.project.flights;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;

/**
 * An Activity for Admins to upload Client and Flight files.
 */
public class UploadActivity extends AppCompatActivity 
	implements View.OnClickListener {

    private EditText etClientFile, etFlightFile;
    private TextView tvUploadClientsMessage, tvUploadFlightsMessage;
    private Button bUploadClientFile, bUploadFlightFile, bBack;

    /**
     * On creation, create editable text fields for admins to enter their
     * file names, and a submit button.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        etClientFile = (EditText) findViewById(R.id.etClientFile);
        tvUploadClientsMessage = (TextView) findViewById(R.id.tvUploadClientsMessage);
        bUploadClientFile = (Button) findViewById(R.id.bUploadClientFile);
        bUploadClientFile.setOnClickListener(this);

        etFlightFile = (EditText) findViewById(R.id.etFlightFile);
        tvUploadFlightsMessage = (TextView) findViewById(
			R.id.tvUploadFlightsMessage);
        bUploadFlightFile = (Button) findViewById(R.id.bUploadFlightFile);
        bUploadFlightFile.setOnClickListener(this);

        bBack = (Button) findViewById(R.id.bBack);
        bBack.setOnClickListener(this);
    }

    /**
     * When submit is clicked, check if the file is valid and upload that data
     * to the existing serialized client and flight files, or create if they
     * don't exist. Otherwise, if a filename is invalid, display an error 
     * message.
     */
    @Override
    public void onClick(View v) {
        File userdata = this.getApplicationContext()
                .getDir(LoginActivity.DATADIR, MODE_PRIVATE);
        switch (v.getId()) {
            case R.id.bUploadClientFile:
                try {
                    String clientFileName = etClientFile.getText().toString();
                    File clientFile = new File(userdata, clientFileName);
                    LoginActivity.clientManager.uploadClientInfo(
				    clientFile.getPath());
                    LoginActivity.clientManager.saveToFile();
                    tvUploadClientsMessage.setText(
				    "File uploaded successfully");
                    tvUploadClientsMessage.setTextColor(Color.parseColor(
					    "#4CAF50"));
                    etClientFile.setText("");
                } catch (FileNotFoundException e) {
                    tvUploadClientsMessage.setText("File not found");
                    tvUploadClientsMessage.setTextColor(Color.parseColor(
					    "#FF4081"));
                }
                break;

            case R.id.bUploadFlightFile:
                try {
                    String flightFileName = etFlightFile.getText().toString();
                    File flightFile = new File(userdata,flightFileName);
                    LoginActivity.flightManager.uploadFlightInfo(
				    flightFile.getPath());
                    LoginActivity.flightManager.saveToFile();
                    tvUploadFlightsMessage.setText(
				    "File uploaded successfully");
                    tvUploadFlightsMessage.setTextColor(Color.parseColor(
					    "#4CAF50"));
                    etFlightFile.setText("");
                    System.out.println(LoginActivity.flightManager
				    .getAllFlights().toString());
                } catch (IOException | ParseException | NoSuchFlightException e) 		{
                    tvUploadFlightsMessage.setText("File not found");
                    tvUploadFlightsMessage.setTextColor(Color.parseColor(
					    "#FF4081"));
                }
                break;

            case R.id.bBack:
                finish();
                break;

        }
    }
}
