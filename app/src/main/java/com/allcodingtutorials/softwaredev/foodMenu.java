package com.allcodingtutorials.softwaredev;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class foodMenu extends AppCompatActivity {

    private GridView gridView;
    private ArrayList<DataClass> dataList;
    private MyAdapter adapter;
    final private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Foods");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_menu);

        gridView = findViewById(R.id.gridView);
        dataList = new ArrayList<>();
        adapter = new MyAdapter(this, dataList);
        gridView.setAdapter(adapter);

        Button btn4 = findViewById(R.id.backButton);
        btn4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(), menuSelect.class);
                startActivity(intent);
                finish();
            }
        });

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    DataClass dataClass = dataSnapshot.getValue(DataClass.class);
                    dataList.add(dataClass);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle onCancelled event if needed
            }
        });

        // Registering the onItemClick event
        gridView.setOnItemClickListener((parent, view, position, id) -> {
            showQuantityDialog(dataList.get(position));
        });
    }

    private void showQuantityDialog(DataClass dataClass) {
        // Open a dialog to allow the user to input the quantity
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Quantity");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        builder.setView(input);

        builder.setPositiveButton("Add to Cart", (dialog, which) -> {
            try {
                int quantity = Integer.parseInt(input.getText().toString());
                addToCart(dataClass, quantity);
            } catch (NumberFormatException e) {
                // Handle if the input is not a valid number
                Toast.makeText(this, "Invalid quantity format", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    private void addToCart(DataClass dataClass, int quantity) {
        if (quantity > 0) {
            // Display the caption and price
            String caption = dataClass.getCaption();
            double price = dataClass.getPrice();
            Toast.makeText(this, "Caption: " + caption + ", Price: $" + price, Toast.LENGTH_SHORT).show();

            // Add to the cart
            CartItem cartItem = new CartItem(dataClass, quantity);
            CartManager.addToCart(this, cartItem);
            Toast.makeText(this, "Added to Cart", Toast.LENGTH_SHORT).show();

            // Link to CartActivity
            Intent intent = new Intent(this, CartActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Invalid quantity", Toast.LENGTH_SHORT).show();
        }
    }

}
