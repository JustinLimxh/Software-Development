package com.allcodingtutorials.softwaredev;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class AdminFoodMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_food_menu);
    }

    public void goToUploadActivity(View view) {
        startActivity(new Intent(this, UploadActivity.class));
    }

    public void goToDrinkUploadActivity(View view) {
        startActivity(new Intent(this, DrinkUploadActivity.class));
    }

    public void goToSidesUploadActivity(View view) {
        startActivity(new Intent(this, SidesUploadActivity.class));
    }
}
