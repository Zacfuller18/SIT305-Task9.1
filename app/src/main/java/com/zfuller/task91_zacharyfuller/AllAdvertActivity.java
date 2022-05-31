package com.zfuller.task91_zacharyfuller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.zfuller.task91_zacharyfuller.data.DatabaseHelper;
import com.zfuller.task91_zacharyfuller.model.Item;

import java.util.ArrayList;
import java.util.List;

public class AllAdvertActivity extends AppCompatActivity {
    ListView itemListView;
    ArrayAdapter<String> adapter;
    ArrayList<String> itemArrayList;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alladvert);
        itemListView = (findViewById(R.id.itemListView));
        itemArrayList = new ArrayList<>();
        db = new DatabaseHelper(AllAdvertActivity.this);

        List<Item> itemList = db.fetchAllItem();
        for (Item item : itemList) {
            itemArrayList.add(item.getType() + " " + item.getName());

        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, itemArrayList);
        itemListView.setAdapter((adapter));

        itemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int itemId = i;
                Intent removeAdIntent = new Intent(AllAdvertActivity.this, AdvertActivity.class);
                removeAdIntent.putExtra("itemId", itemId);
                startActivity(removeAdIntent);
            }
        });
    }
}
