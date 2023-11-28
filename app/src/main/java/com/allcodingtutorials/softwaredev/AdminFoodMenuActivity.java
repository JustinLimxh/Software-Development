package com.allcodingtutorials.softwaredev;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class AdminFoodMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_food_menu);

        Button btn4 = findViewById(R.id.backButton);
        btn4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(), adminhomepage.class);
                startActivity(intent);
                finish();
            }
        });
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
