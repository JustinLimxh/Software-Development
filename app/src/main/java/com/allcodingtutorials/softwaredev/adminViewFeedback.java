package com.allcodingtutorials.softwaredev;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class adminViewFeedback extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FeedbackAdapter adapter;
    private List<FeedbackModel> feedbackList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_feedback);

        Button btn4 = findViewById(R.id.backButton);
        btn4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(), adminhomepage.class);
                startActivity(intent);
                finish();
            }
        });
        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize feedback list and adapter
        feedbackList = new ArrayList<>();
        adapter = new FeedbackAdapter(feedbackList);
        recyclerView.setAdapter(adapter);

        // Retrieve feedback data from Firebase and update the list
        retrieveFeedbackData();
    }

    private void retrieveFeedbackData() {
        DatabaseReference feedbacksRef = FirebaseDatabase.getInstance().getReference().child("Feedbacks");

        feedbacksRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Clear the existing list
                feedbackList.clear();

                // Iterate through the dataSnapshot to get feedback data
                for (DataSnapshot feedbackSnapshot : dataSnapshot.getChildren()) {
                    FeedbackModel feedback = feedbackSnapshot.getValue(FeedbackModel.class);
                    if (feedback != null) {
                        feedbackList.add(feedback);
                    }
                }

                // Notify the adapter that data has changed
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
            }
        });
    }
}
