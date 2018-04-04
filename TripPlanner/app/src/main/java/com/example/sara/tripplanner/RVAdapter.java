package com.example.sara.tripplanner;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.renderscript.Sampler;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.HashMap;
import java.util.List;

import static android.support.v4.content.ContextCompat.startActivities;
import static android.support.v4.content.ContextCompat.startActivity;

/**
 * Created by sara on 01/04/18.
 */




public class RVAdapter extends RecyclerView.Adapter<RVAdapter.PersonViewHolder>{
    private FirebaseDatabase mDatabase;
    public Context mycont;
    List<Trip> Trips;

    public RVAdapter(Context mycont, List<Trip> trips) {
        this.mycont = mycont;
        Trips = trips;
    }

    RVAdapter(List<Trip> Trips){    this.Trips = Trips;}

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }


    public void updateTrips(List<Trip> Trips){
        this.Trips = Trips;
        notifyDataSetChanged();

    }
    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);
        PersonViewHolder pvh = new PersonViewHolder(v);
        return pvh;
    }

    Intent goToDetails ;
    private Context myContext;
    @Override
    public void onBindViewHolder(PersonViewHolder personViewHolder,  final int i) {


//
        final Trip trip = Trips.get(i);
        personViewHolder.personName.setText(trip.getName());
        personViewHolder.personAge.setText(trip.getDate());
        personViewHolder.personPhoto.setImageResource(R.mipmap.ic_launcher);
       // final String Trip_key=
        personViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              // myContext=view.getContext();

                Toast.makeText(view.getContext(), Trips.get(i).getName(), Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(view.getContext(),details_activity.class);
                intent.putExtra("Name",Trips.get(i).getName());
                intent.putExtra("Date",Trips.get(i).getDate());
                intent.putExtra("Time",Trips.get(i).getTime());
                intent.putExtra("Placefrom",Trips.get(i).getPlaceFrom());
                intent.putExtra("Placeto",Trips.get(i).getPlaceTo());


                view.getContext().startActivity(intent);


            }

        });

        personViewHolder.start.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                String startPoint=String.valueOf(trip.getPlaceFrom());
                String endPont=String.valueOf(trip.getPlaceTo());
//
                String ur="";
                final  String uri ="https://www.google.com/maps/dir/?api=1&map_action=pano&origin=";
                ur=uri.concat(startPoint+"&destination="+endPont);
                final  Intent mapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(ur));
                mapIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mapIntent.setClassName("com.google.android.apps.maps","com.google.android.maps.MapsActivity");
                mapIntent.setPackage("com.google.android.apps.maps");
                view.getContext().startActivity(mapIntent);
                myContext = view.getContext();
            }


        });


            personViewHolder.done.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (((CheckBox) view).isChecked()){
                        FirebaseAuth firebaseAuth= FirebaseAuth.getInstance();
                        FirebaseUser user=firebaseAuth.getCurrentUser();
                        mDatabase=FirebaseDatabase.getInstance();
                        

//                        mDatabase.getReference(user.getUid()+"/Trips/").child(Trips.get(i).getKey()).removeValue();
//                        Trips.remove(i);


                         mDatabase.getReference(user.getUid()+"/Trips/")
                                 .child(Trips.get(i).getKey()).child("status").setValue("done");





//                      notifyItemRemoved(i);
//                      notifyItemRangeChanged(i,Trips.size());
//                      notifyDataSetChanged();

                    }

                }
            });
    }


    @Override
    public int getItemCount() {

        return Trips != null? Trips.size():0;
    }
    public static class PersonViewHolder extends RecyclerView.ViewHolder {


        ImageView personPhoto;
        Button start;
        CheckBox done;

        CardView cv;
        TextView personName;
        TextView personAge;
        PersonViewHolder(final View itemView) {
            super(itemView);
            cv = itemView.findViewById(R.id.cv);
            personName = itemView.findViewById(R.id.person_name);
            personAge = itemView.findViewById(R.id.person_age);
            personPhoto = itemView.findViewById(R.id.person_photo);
            start =itemView.findViewById(R.id.startNavbtn);
            done=itemView.findViewById(R.id.done);

           // itemView.getContext();




        }



    }
}