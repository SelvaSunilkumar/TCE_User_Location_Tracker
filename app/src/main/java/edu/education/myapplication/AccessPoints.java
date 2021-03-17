package edu.education.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

public class AccessPoints extends AppCompatActivity {

    private DatabaseHandler databaseHandler;
    private Cursor cursor;

    private ArrayList<AccessPointsDetails> accessPointsDetails;
    private RecyclerView recyclerView;
    private AccessPointsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_access_points);

        databaseHandler = new DatabaseHandler(this);
        cursor = databaseHandler.getAccessPoints();

        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        accessPointsDetails = new ArrayList<>();
        adapter = new AccessPointsAdapter(this, accessPointsDetails);

        recyclerView.setAdapter(adapter);

        while (cursor.moveToNext()) {
            /*Toast.makeText(getApplicationContext(), cursor.getString(2),Toast.LENGTH_LONG).show();
            System.out.println(cursor.getString(2));*/
            accessPointsDetails.add(new AccessPointsDetails(cursor.getDouble(0), cursor.getDouble(1), cursor.getString(2), cursor.getDouble(3)));
            adapter.notifyDataSetChanged();
        }
    }
}
