package com.zfuller.task91_zacharyfuller;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.zfuller.task91_zacharyfuller.data.DatabaseHelper;
import com.zfuller.task91_zacharyfuller.model.Item;


public class AdvertActivity extends AppCompatActivity {
    TextView txtDesc, txtDate, txtName, txtLocation, txtPhone;
    Button btnRemove;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advert);
        DatabaseHelper db = new DatabaseHelper(AdvertActivity.this);
        int id = getIntent().getExtras().getInt("itemId") + 1;
        Item item = db.fetchItem(id);

        txtDesc = (findViewById(R.id.txtDesc));
        txtDate = (findViewById(R.id.txtDate));
        txtName = (findViewById(R.id.txtName));
        txtLocation = (findViewById(R.id.txtLocation));
        txtPhone = (findViewById(R.id.txtPhone));
        btnRemove = (findViewById(R.id.btnRemove));

        txtDesc.setText(item.getDesc());
        txtDate.setText(item.getDate());
        txtName.setText(item.getName());
        txtLocation.setText(item.getLocation());
        txtPhone.setText(item.getPhone());

        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.deleteItem(id);
                Toast.makeText(AdvertActivity.this, "Submission has been deleted...", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
