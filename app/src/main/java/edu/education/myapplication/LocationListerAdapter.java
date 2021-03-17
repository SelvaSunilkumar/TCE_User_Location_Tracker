package edu.education.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class LocationListerAdapter extends RecyclerView.Adapter<LocationListerAdapter.ViewHolder> {

    private ArrayList<LocationDetails> details;
    private Context context;

    public LocationListerAdapter(Context context, ArrayList<LocationDetails> details) {
        this.context = context;
        this.details = details;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.location_lister,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LocationDetails locationDetails = details.get(position);
        holder.dateTimeStamp.setText(locationDetails.getCurrentTime());
        holder.longitude.setText(String.valueOf(locationDetails.getLongitude()));
        holder.latitude.setText(String.valueOf(locationDetails.getLatitude()));
        holder.status.setText(locationDetails.getPosition());
    }

    @Override
    public int getItemCount() {
        return details.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView dateTimeStamp;
        public TextView latitude;
        public TextView longitude;
        public TextView status;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.dateTimeStamp = itemView.findViewById(R.id.dateTimeStamp);
            this.latitude = itemView.findViewById(R.id.latitude);
            this.longitude = itemView.findViewById(R.id.longitude);
            this.status = itemView.findViewById(R.id.status);

        }
    }

}
