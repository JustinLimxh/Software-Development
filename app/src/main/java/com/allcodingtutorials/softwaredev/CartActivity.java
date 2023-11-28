package com.allcodingtutorials.softwaredev;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {

    private ArrayList<CartItem> cartItems;
    private CartAdapter cartAdapter;
    private TextView totalCostTextView;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        Button btn1 = findViewById(R.id.payButton);
        btn1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(), wallet.class);
                startActivity(intent);
                finish();
            }
        });
        // Initialize views
        totalCostTextView = findViewById(R.id.totalCostTextView);
        ListView cartListView = findViewById(R.id.cartListView);

        // Initialize adapter
        cartItems = new ArrayList<>();
        cartAdapter = new CartAdapter(this, cartItems);
        cartListView.setAdapter(cartAdapter);

        // Set up Firebase Realtime Database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("cartItems");

        // Add a ValueEventListener to update the local cart when the database changes
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Clear the local cart
                cartItems.clear();

                // Iterate through the database snapshot and add items to the local cart
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    CartItem cartItem = snapshot.getValue(CartItem.class);
                    cartItems.add(cartItem);
                }

                // Notify the adapter that the data has changed
                cartAdapter.notifyDataSetChanged();

                // Update total cost
                updateTotalCost();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Handle onCancelled event if needed
            }
        });
    }

    private void updateTotalCost() {
        double totalCost = calculateTotalCost();
        totalCostTextView.setText("Total Cost: $" + totalCost);
    }

    private double calculateTotalCost() {
        double totalCost = 0;
        for (CartItem item : cartItems) {
            totalCost += item.getQuantity() * item.getDataClass().getPrice();
        }
        return totalCost;
    }
}
