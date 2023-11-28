package com.allcodingtutorials.softwaredev;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class wallet extends AppCompatActivity {

    private TextView balanceTextView;
    private Button topUpButton, payButton;
    private EditText userInputEditText, inputPayment;
    private String lastTopUpCoupon = ""; // Store the last used coupon

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);

        Button btn4 = findViewById(R.id.backButton);
        btn4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(), CartActivity.class);
                startActivity(intent);
                finish();
            }
        });

        balanceTextView = findViewById(R.id.balanceTextView);
        topUpButton = findViewById(R.id.topUpButton);
        payButton = findViewById(R.id.payNowButton);
        userInputEditText = findViewById(R.id.userInputEditText);
        inputPayment = findViewById(R.id.etpayment);

        topUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the current balance text
                String currentBalanceText = balanceTextView.getText().toString();

                // Parse the balance string and convert it to a numerical value
                double currentBalance = parseBalanceString(currentBalanceText);

                // Obtain the user-inputted text
                String userInputText = getUserInputText();

                // Check if the user input is correct (6 characters, first 3 alphabets, last 3 numbers)
                if (isTopUpCouponValid(userInputText)) {
                    // Check if the same coupon is used twice
                    if (userInputText.equals(lastTopUpCoupon)) {
                        // Display a toast message indicating that the coupon has been used
                        Toast.makeText(wallet.this, "Coupon has been used", Toast.LENGTH_SHORT).show();
                    } else {
                        // Store the last used coupon
                        lastTopUpCoupon = userInputText;

                        // Perform the top-up action
                        performTopUpAction();
                    }
                } else {
                    // Display a toast message indicating incorrect user input
                    Toast.makeText(wallet.this, "Invalid top-up coupon. Please enter a valid coupon.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the entered payment amount
                String paymentAmountText = inputPayment.getText().toString();

                // Parse the payment amount and check if it's greater than or equal to 2
                try {
                    double paymentAmount = Double.parseDouble(paymentAmountText);

                    // Check if the balance is greater than 0 before processing the payment
                    if (getCurrentBalance() <= 0) {
                        // Display a toast message asking the user to top up the balance
                        Toast.makeText(wallet.this, "Balance is 0. Please top up your balance.", Toast.LENGTH_SHORT).show();
                    } else if (paymentAmount < 2) {
                        // Display a toast message indicating that the amount must be at least 2
                        Toast.makeText(wallet.this, "Incorrect Amount", Toast.LENGTH_SHORT).show();
                    } else {
                        // Deduct the payment amount from the balance
                        double currentBalance = getCurrentBalance();
                        double newBalance = currentBalance - paymentAmount;

                        // Check if the new balance is greater than or equal to 0 after deduction
                        if (newBalance >= 0) {
                            updateBalance(newBalance);

                            // Generate a unique Order ID
                            String orderID = generateOrderID();

                            // Display a pop-up dialog indicating successful payment
                            showOrderSuccessfulDialog(orderID);
                        } else {
                            // Display a toast message indicating insufficient funds
                            Toast.makeText(wallet.this, "Insufficient funds. Please top up your balance.", Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (NumberFormatException e) {
                    // Handle the case where the entered amount is not a valid number
                    Toast.makeText(wallet.this, "Invalid payment amount", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    // Method to generate a unique Order ID
    private String generateOrderID() {
        // Use a combination of random alphabets and numbers
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder orderIDBuilder = new StringBuilder();

        for (int i = 0; i < 6; i++) {
            int index = (int) (Math.random() * characters.length());
            orderIDBuilder.append(characters.charAt(index));
        }

        return orderIDBuilder.toString();
    }

    // Method to show a pop-up dialog indicating successful payment
    private void showOrderSuccessfulDialog(String orderID) {
        AlertDialog.Builder builder = new AlertDialog.Builder(wallet.this);
        builder.setTitle("Order Successful")
                .setMessage("Order ID: " + orderID + "\nPlease screenshot your Order ID.")
                .setPositiveButton("OK", null)
                .show();
    }

    // Method to perform the top-up action
    private void performTopUpAction() {
        // Get the current balance text
        String currentBalanceText = balanceTextView.getText().toString();

        // Parse the balance string and convert it to a numerical value
        double currentBalance = parseBalanceString(currentBalanceText);

        // Define the top-up amount
        double topUpAmount = 20.0; // Replace with your desired top-up amount

        // Add the top-up amount to the current balance
        double newBalance = currentBalance + topUpAmount;

        // Update the balanceTextView with the new balance
        balanceTextView.setText(formatBalanceString(newBalance));

        // Perform the specific action (insert specific line of text)
        insertSpecificLineOfText(userInputEditText.getText().toString());

        // Display a toast message indicating successful top-up
        Toast.makeText(wallet.this, "Balance topped up successfully!", Toast.LENGTH_SHORT).show();
    }

    // Helper method to check if the top-up coupon is valid
    private boolean isTopUpCouponValid(String userInput) {
        // Check if the input is 6 characters and follows the specified pattern
        return userInput.matches("[A-Za-z]{3}[0-9]{3}");
    }

    // Helper method to get the current balance
    private double getCurrentBalance() {
        String currentBalanceText = balanceTextView.getText().toString();
        return parseBalanceString(currentBalanceText);
    }

    // Helper method to update the balance TextView
    private void updateBalance(double newBalance) {
        balanceTextView.setText(formatBalanceString(newBalance));
    }

    // Method to get user input
    private String getUserInputText() {
        return userInputEditText.getText().toString();
    }

    // Helper method to parse the balance string and convert it to a numerical value
    private double parseBalanceString(String balanceString) {
        // Remove non-numeric characters and parse the double value
        return Double.parseDouble(balanceString.replaceAll("[^\\d.]+", ""));
    }

    // Helper method to format a double value as a currency string
    private String formatBalanceString(double balance) {
        return String.format(" RM%.2f", balance);
    }

    // Placeholder method for inserting a specific line of text
    private void insertSpecificLineOfText(String userText) {
        // Implement your logic for inserting a specific line of text here
        // You can use the 'userText' parameter for the specific line of text
    }
}
