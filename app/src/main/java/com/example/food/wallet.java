package com.example.food;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class wallet extends AppCompatActivity {

    private TextView balanceTextView;
    private Button topUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);

        balanceTextView = findViewById(R.id.balanceTextView);
        topUpButton = findViewById(R.id.topUpButton);

        topUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the current balance text
                String currentBalanceText = balanceTextView.getText().toString();

                // Parse the balance string and convert it to a numerical value
                double currentBalance = parseBalanceString(currentBalanceText);

                // Define the top-up amount
                double topUpAmount = 50.0; // Replace with your desired top-up amount

                // Add the top-up amount to the current balance
                double newBalance = currentBalance + topUpAmount;

                // Update the balanceTextView with the new balance
                balanceTextView.setText(formatBalanceString(newBalance));

                // Insert a specific line of text or perform any other actions here
                // (This depends on your specific requirements)
                insertSpecificLineOfText();

                // Display a toast message indicating successful top-up
                Toast.makeText(wallet.this, "Balance topped up successfully!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Helper method to parse the balance string and convert it to a numerical value
    private double parseBalanceString(String balanceString) {
        // Remove non-numeric characters and parse the double value
        return Double.parseDouble(balanceString.replaceAll("[^\\d.]+", ""));
    }

    // Helper method to format a double value as a currency string
    private String formatBalanceString(double balance) {
        return String.format(" $%.2f", balance);
    }

    // Placeholder method for inserting a specific line of text
    private void insertSpecificLineOfText() {
        // Implement your logic for inserting a specific line of text here
    }
}