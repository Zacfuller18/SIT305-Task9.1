package com.zfuller.task91_zacharyfuller;

import android.os.Bundle;
import android.widget.ArrayAdapter;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.zfuller.task91_zacharyfuller.data.DatabaseHelper;
import com.zfuller.task91_zacharyfuller.databinding.ActivityMapsBinding;
import com.zfuller.task91_zacharyfuller.model.Item;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    ArrayList<String> advertArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        advertArrayList = new ArrayList<>();
        DatabaseHelper db = new DatabaseHelper(MapsActivity.this);

        List<Item> itemList = db.fetchAllItem();
        for (Item item :itemList)
        {
            LatLng itemLocation = new LatLng(item.getLatitude(),item.getLongitude());
            mMap.addMarker(new MarkerOptions().position(itemLocation).title(item.getType() +" "+ item.getName()));
        }
    }
}