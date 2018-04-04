package com.example.sara.tripplanner;


import android.annotation.SuppressLint;
import android.graphics.Paint;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class pastTripsAdapter extends RecyclerView.Adapter <pastTripsAdapter.PastTripsHolder>{

    private List pastTrips;
    private TextView pastName;
    private TextView pastDate;


    pastTripsAdapter(List pastTrips) {
        this.pastTrips = pastTrips;
    }


    @Override
    public PastTripsHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.past_trip_item, viewGroup, false);
        return new PastTripsHolder(view);
    }

    void updateTrips(List<Trip> Trips){
        this.pastTrips = Trips;
        notifyDataSetChanged();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(PastTripsHolder pastTripsHolder,final int position) {
        Trip pastTrip= (Trip) pastTrips.get(position);
        pastDate = pastTripsHolder.itemView.findViewById(R.id.past_date);
        pastDate.setText(pastTrip.getDate());
        pastName = pastTripsHolder.itemView.findViewById(R.id.past_name);
        pastName.setText(pastTrip.getName());
    }

    @Override
    public int getItemCount() {
        return pastTrips!= null? pastTrips.size():0;
    }

    class PastTripsHolder extends RecyclerView.ViewHolder {

        PastTripsHolder(View itemView) {
            super(itemView);
            CardView cv = itemView.findViewById(R.id.pastcv);
            pastName = cv.findViewById(R.id.past_name);
            pastName.setPaintFlags(pastName.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
            pastDate = cv.findViewById(R.id.past_date);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(view.getContext(), "Trip Details", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
