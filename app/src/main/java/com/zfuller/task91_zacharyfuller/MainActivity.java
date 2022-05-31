package com.zfuller.task91_zacharyfuller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.libraries.places.api.Places;

public class MainActivity extends AppCompatActivity {
    Button btnCreateNew, btnShowAll, btnShowMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCreateNew = findViewById(R.id.btnCreateNew);
        btnShowAll = findViewById(R.id.btnShowAll);
        btnShowMap = findViewById(R.id.btnShowMap);

        Places.initialize(getApplicationContext(), "AIzaSyD-o5IZjaS-9-WBnT9-qlFDpQVdgws2XGE");

        btnCreateNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(MainActivity.this, NewAdvertActivity.class);
                startActivity(a);
            }
        });

        btnShowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, AllAdvertActivity.class);
                startActivity(i);
            }
        });

        btnShowMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent o = new Intent(MainActivity.this, MapsActivity.class);
                startActivity(o);
            }
        });


    }
}