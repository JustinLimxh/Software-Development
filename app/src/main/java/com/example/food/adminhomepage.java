package com.example.food;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class adminhomepage extends AppCompatActivity {

    Button feedbackbtn,notibtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminhomepage);


        feedbackbtn = findViewById(R.id.vfeed);
        notibtn = findViewById(R.id.pushnoti);


        feedbackbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(adminhomepage.this ,adminViewFeedback.class);
                startActivity(intent);
                finish();
            }
        });

        notibtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(adminhomepage.this ,adminNotification.class);
                startActivity(intent);
                finish();
            }
        });
    }
}