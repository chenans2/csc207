package com.csc207.project.flights;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;

/**
 * Activity to display and edit client information.
 */
public class ClientActivity extends AppCompatActivity implements 
	View.OnClickListener {

    private Button bUpdate, bToMenu;
    private EditText etFirstName, etLastName, etEmail, etAddress,
	    etCreditCardNo, etExpDate;
    private TextView tvClientSaved;
    private ClientManager clientManager = LoginActivity.clientManager;
    private Client myClient;
    private String myClientEmail;

    /**
     * On creation of this Activity, make the edit texts and fill them with
     * the Client's information.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        etLastName = (EditText) findViewById(R.id.etLastName);
        etFirstName = (EditText) findViewById(R.id.etFirstName);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etAddress = (EditText) findViewById(R.id.etAddress);
        etCreditCardNo = (EditText) findViewById(R.id.etCreditCardNo);
        etExpDate = (EditText) findViewById(R.id.etExpDate);
        tvClientSaved = (TextView) findViewById(R.id.tvClientSaved);
        tvClientSaved.setVisibility(View.INVISIBLE);

        Bundle bundle = getIntent().getExtras();
        myClientEmail = bundle.getString("email");
        HashMap<String, Client> clients = clientManager.getClients();
        myClient = clients.get(myClientEmail);

        etLastName.setText(myClient.getLastName());
        etFirstName.setText(myClient.getFirstNames());
        etEmail.setText(myClient.getEmail());
        etAddress.setText(myClient.getAddress());
        etCreditCardNo.setText(String.valueOf(myClient.getCreditCardNo()));
        etExpDate.setText(myClient.getExpDate());

        bUpdate = (Button) findViewById(R.id.bUpdate);
        bUpdate.setOnClickListener(this);
        bToMenu = (Button) findViewById(R.id.bToMenu);
        bToMenu.setOnClickListener(this);
    }

    /**
     * Listen for button clicks on the 'Update' and 'Back' button.
     */
    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.bUpdate:

                // Get the info in the text boxes and update the Client's info
                myClient.setLastName(etLastName.getText().toString());
                myClient.setFirstNames(etFirstName.getText().toString());
                myClient.setEmail(etEmail.getText().toString());
                myClient.setAddress(etAddress.getText().toString());
                myClient.setCreditCardNo(Long.parseLong(etCreditCardNo
                        .getText().toString()));
                myClient.setExpDate(etExpDate.getText().toString());

                // Save to serialized file
                clientManager.saveToFile();
                tvClientSaved.setVisibility(View.VISIBLE);
                break;

            case R.id.bToMenu:
                finish();
                break;
        }
    }

}
