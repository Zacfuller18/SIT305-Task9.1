package com.zfuller.task91_zacharyfuller;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.zfuller.task91_zacharyfuller.data.DatabaseHelper;
import com.zfuller.task91_zacharyfuller.model.Item;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class NewAdvertActivity extends AppCompatActivity {
    DatabaseHelper db;
    double latitude, longitude;
    String userLocation;
    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newadvert);

        EditText editTextName = (findViewById(R.id.editTextName));
        EditText editTextPhone = (findViewById(R.id.editTextPhone));
        EditText editTextDesc = (findViewById(R.id.editTextDesc));
        EditText editTextDate = (findViewById(R.id.editTextDate));
        Button btnSave = (findViewById(R.id.btnSave));
        Button btnCurrentLocation = (findViewById(R.id.btnCurrentLocation));
        RadioGroup radioGroup = (findViewById(R.id.radioGroup));
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);
        db = new DatabaseHelper(this);


        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG));

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                userLocation = place.getName();
                latitude = place.getLatLng().latitude;
                longitude = place.getLatLng().longitude;
            }


            @Override
            public void onError(@NonNull Status status) {
                Toast.makeText(NewAdvertActivity.this, "An error has occurred...", Toast.LENGTH_SHORT).show();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RadioButton radioSelected;
                int radioId = radioGroup.getCheckedRadioButtonId();
                radioSelected = findViewById(radioId);
                String type = radioSelected.getText().toString();
                String name = editTextName.getText().toString();
                String phone = editTextPhone.getText().toString();
                String desc = editTextDesc.getText().toString();
                String date = editTextDate.getText().toString();

                if (!name.isEmpty() && !phone.isEmpty() && !desc.isEmpty() && !date.isEmpty()) {
                    long check = db.insertItem(new Item(type, name, phone, desc, date, userLocation, latitude, longitude));
                    Log.d("abc", String.valueOf(check));
                    if (check > 0) {
                        Toast.makeText(NewAdvertActivity.this, "Item submitted successfully!", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(NewAdvertActivity.this, "Submission error...", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(NewAdvertActivity.this, "Please fill in all entries before saving", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnCurrentLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(NewAdvertActivity.this, "Please wait...", Toast.LENGTH_SHORT).show();

                locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
                if (ContextCompat.checkSelfPermission(NewAdvertActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                        ContextCompat.checkSelfPermission(NewAdvertActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(NewAdvertActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                }

                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10, 1, new LocationListener() {
                    @Override
                    public void onLocationChanged(@NonNull Location location) {
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                        userLocation = getAddress(latitude, longitude);
                        autocompleteFragment.setText(userLocation);
                        Log.d("test1", String.valueOf(longitude));
                        Log.d("test2", String.valueOf(latitude));
                        Log.d("test4", userLocation);
                    }
                });

            }
        });
    }

    private String getAddress(double latitude, double longitude) {
        StringBuilder result = new StringBuilder();
        try {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses.size() > 0) {
                Address address = addresses.get(0);
                result.append(address.getLocality()).append("\n");
                result.append(address.getCountryName());
            }
        } catch (IOException e) {
            Log.e("tag", e.getMessage());
        }
        return result.toString();
    }
}