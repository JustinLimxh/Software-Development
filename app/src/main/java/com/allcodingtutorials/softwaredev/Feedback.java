package com.allcodingtutorials.softwaredev;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Feedback extends AppCompatActivity {

    private Button submitFeedbackButton;
    private EditText feedbackEditText;
    private DatabaseReference feedbacksRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        try {
            // Initialize Firebase
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        } catch (Exception e) {
            // Handle exception if setPersistenceEnabled is called after other operations
            // Log.e("FirebasePersistence", "Unable to setPersistenceEnabled", e);
        }

        // Update the database URL to match the correct region
        String databaseUrl = "https://software-development-95b9e-default-rtdb.firebaseio.com/";
        FirebaseDatabase.getInstance().getReference().child("Feedbacks").keepSynced(true);
        feedbacksRef = FirebaseDatabase.getInstance(databaseUrl).getReference().child("Feedbacks");

        // Now you can perform other Firebase Database operations
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("your_reference_path");
        Button btn4 = findViewById(R.id.backButton);
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), userMenu.class);
                startActivity(intent);
                finish();
            }
        });

        submitFeedbackButton = findViewById(R.id.submitButton);
        feedbackEditText = findViewById(R.id.feedbackEditText);

        submitFeedbackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String feedback = feedbackEditText.getText().toString().trim();

                if (!TextUtils.isEmpty(feedback)) {
                    // Create a new map to hold the data of the feedback
                    Map<String, Object> newFeedbackMap = new HashMap<>();
                    newFeedbackMap.put("feedbackText", feedback); // Use a meaningful key, like "feedbackText"

                    // Create a new key for the new feedback
                    String newFeedbackKey = feedbacksRef.push().getKey();

                    // Use the new key to update the data in the Realtime Database
                    feedbacksRef.child(newFeedbackKey).setValue(newFeedbackMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(Feedback.this, "Feedback Submitted Successfully", Toast.LENGTH_SHORT).show();
                                        feedbackEditText.setText(""); // Clear the EditText
                                    } else {
                                        Toast.makeText(Feedback.this, "Error in Submitting Feedback: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                } else {
                    Toast.makeText(Feedback.this, "Please enter your feedback", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
