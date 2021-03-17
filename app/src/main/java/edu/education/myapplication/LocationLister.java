package edu.education.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class LocationLister extends AppCompatActivity {

    private DatabaseHandler databaseHandler;
    private LinearLayout Successful;
    private LinearLayout lister;
    private RecyclerView recyclerView;

    private ArrayList<LocationDetails> details;
    private LocationListerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_lister);

        Successful = findViewById(R.id.successful);
        lister = findViewById(R.id.lister);
        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        databaseHandler = new DatabaseHandler(this);

        if (databaseHandler.getLocationCount() > 0) {
            Successful.setVisibility(View.GONE);
            lister.setVisibility(View.VISIBLE);

            Cursor cursor = databaseHandler.getLocation();

            details = new ArrayList<>();
            adapter = new LocationListerAdapter(this,details);
            recyclerView.setAdapter(adapter);

            while (cursor.moveToNext()) {
                String dateTimeStamp = cursor.getString(0);
                double latitude = Double.parseDouble(cursor.getString(1));
                double longitude = Double.parseDouble(cursor.getString(2));
                String status = cursor.getString(3);
                details.add(new LocationDetails(dateTimeStamp, latitude, longitude, status));
                adapter.notifyDataSetChanged();
            }

        } else {
            Successful.setVisibility(View.VISIBLE);
            lister.setVisibility(View.GONE);
        }

    }
}
