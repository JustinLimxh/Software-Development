package com.example.food;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class adminNotification extends AppCompatActivity {

    private EditText orderIdEditText, orderDescEditText;
    private Button sendNotiBtn, backBtn;

    // Reference to Firebase Database
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_notification);

        // Initialize Firebase Database
        databaseReference = FirebaseDatabase.getInstance().getReference("Notifications");

        orderIdEditText = findViewById(R.id.etorderid);
        orderDescEditText = findViewById(R.id.etorderdesc);
        sendNotiBtn = findViewById(R.id.sendnotibtn);
        backBtn = findViewById(R.id.backbtn);

        sendNotiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendNotification();
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(adminNotification.this ,adminhomepage.class);
                startActivity(intent); // Close the activity
            }
        });
    }

    private void sendNotification() {
        String orderId = orderIdEditText.getText().toString().trim();
        String orderDesc = orderDescEditText.getText().toString().trim();

        if (!orderId.isEmpty() && !orderDesc.isEmpty()) {
            // Create a Notification object
            NotificationsClass notification = new NotificationsClass(orderId, orderDesc);

            // Push the notification to Firebase Database
            String notificationId = databaseReference.push().getKey();
            databaseReference.child(notificationId).setValue(notification);

            // Clear the input fields
            orderIdEditText.setText("");
            orderDescEditText.setText("");

            // Inform the user
            // You can display a Toast or update UI as needed
            // Inform the user about successful notification
            Toast.makeText(adminNotification.this, "Notification sent successfully", Toast.LENGTH_SHORT).show();
        } else {
            // Handle empty fields
            // You can display a Toast or update UI as needed
            Toast.makeText(adminNotification.this, "Please enter both Order ID and Order Description", Toast.LENGTH_SHORT).show();

        }
    }
}
