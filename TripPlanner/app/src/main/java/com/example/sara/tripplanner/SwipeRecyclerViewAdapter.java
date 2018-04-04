package com.example.sara.tripplanner;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class SwipeRecyclerViewAdapter extends RecyclerSwipeAdapter<SwipeRecyclerViewAdapter.SimpleViewHolder> {
    private FirebaseDatabase mDatabase;
    private Context mContext;
    private List<Trip> Trips;

    public SwipeRecyclerViewAdapter(Context context, List<Trip> objects) {
        this.mContext = context;
        this.Trips = objects;
    }

    public SwipeRecyclerViewAdapter(List<Trip> objects) {
        this.Trips = objects;
    }


    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.swipe_layout, parent, false);
        return new SimpleViewHolder(view);
    }
    Intent goToDetails ;
    private Context myContext;

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void updateTrips(List<Trip> Trips){
        this.Trips = Trips;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(final SimpleViewHolder viewHolder, final int position) {
        final Trip trip = Trips.get(position);

        FirebaseAuth firebaseAuth= FirebaseAuth.getInstance();
        final FirebaseUser user=firebaseAuth.getCurrentUser();
        final Trip item = Trips.get(position);

        viewHolder.Name.setText(item.getName());
        viewHolder.Time.setText(item.getDate());
        viewHolder.personPhoto.setImageResource(R.mipmap.ic_launcher);

        viewHolder.Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // myContext=view.getContext();

                Intent intent = new Intent(view.getContext(), details_activity.class);
                intent.putExtra("Name", Trips.get(position).getName());
                intent.putExtra("Date", Trips.get(position).getDate());
                intent.putExtra("Time", Trips.get(position).getTime());
                intent.putExtra("Placefrom", Trips.get(position).getPlaceFrom());
                intent.putExtra("Placeto", Trips.get(position).getPlaceTo());
                intent.putExtra("Status", Trips.get(position).getStatus());

                view.getContext().startActivity(intent);


            }

        });

        viewHolder.swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);

        //dari kiri
        viewHolder.swipeLayout.addDrag(SwipeLayout.DragEdge.Left, viewHolder.swipeLayout.findViewById(R.id.bottom_wrapper1));

        //dari kanan
        viewHolder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right, viewHolder.swipeLayout.findViewById(R.id.bottom_wraper));


        viewHolder.swipeLayout.addSwipeListener(new SwipeLayout.SwipeListener() {
            @Override
            public void onStartOpen(SwipeLayout layout) {

            }

            @Override
            public void onOpen(SwipeLayout layout) {

            }

            @Override
            public void onStartClose(SwipeLayout layout) {

            }

            @Override
            public void onClose(SwipeLayout layout) {

            }

            @Override
            public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {

            }

            @Override
            public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {

            }
        });

//        viewHolder.swipeLayout.getSurfaceView().setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(mContext, " Click : " + item.getName() + " \n" + item.getTime(), Toast.LENGTH_SHORT).show();
//            }
//        });


        viewHolder.Note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(view.getContext(),EditActvity.class);
                intent.putExtra("Name", Trips.get(position).getName());
                intent.putExtra("Date", Trips.get(position).getDate());
                intent.putExtra("Time", Trips.get(position).getTime());
                intent.putExtra("Placefrom", Trips.get(position).getPlaceFrom());
                intent.putExtra("Placeto", Trips.get(position).getPlaceTo());
                intent.putExtra("Status", Trips.get(position).getStatus());
                intent.putExtra("key",Trips.get(position).getKey());

                view.getContext().startActivity(intent);

            }
        });



        viewHolder.start.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                String startPoint = String.valueOf(trip.getPlaceFrom());
                String endPont = String.valueOf(trip.getPlaceTo());
//
                String ur = "";
                final String uri = "https://www.google.com/maps/dir/?api=1&map_action=pano&origin=";
                ur = uri.concat(startPoint + "&destination=" + endPont);
                final Intent mapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(ur));
                mapIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mapIntent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                mapIntent.setPackage("com.google.android.apps.maps");
                view.getContext().startActivity(mapIntent);
                myContext = view.getContext();
            }


        });


        viewHolder.Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mDatabase=FirebaseDatabase.getInstance();
                mItemManger.removeShownLayouts(viewHolder.swipeLayout);
              //  Trips.remove(position);

                mDatabase.getReference(user.getUid()+"/Trips/").child(Trips.get(position).getKey()).removeValue();
                Trips.remove(position);


                notifyItemRemoved(position);
                notifyItemRangeChanged(position, Trips.size());
                // mItemManger.closeAllItems();





            }
        });

        mItemManger.bindView(viewHolder.itemView, position);
//

        viewHolder.done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (((CheckBox) view).isChecked()) {
                    final Dialog dialog = new Dialog(view.getContext());
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.hamada);
                    Button buttonOK = dialog.findViewById(R.id.ok);
                    Button buttonCancel = dialog.findViewById(R.id.cancel);
                    dialog.show();
                    buttonOK.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            FirebaseAuth firebaseAuth= FirebaseAuth.getInstance();
                            FirebaseUser user=firebaseAuth.getCurrentUser();
                            mDatabase=FirebaseDatabase.getInstance();
                            assert user != null;

                            mDatabase.getReference(user.getUid()+"/Trips/").child(Trips.get(position).getKey())
                                    .child("status").setValue("done");
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position,Trips.size());
                           // updateTrips(Trips);
                            Trips.remove(position);

                            dialog.dismiss();
                        }
                    });
                    buttonCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            viewHolder.done.setChecked(false);
                            dialog.dismiss();
                        }
                    });
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return Trips != null? Trips.size():0;
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }

    public static class SimpleViewHolder extends RecyclerView.ViewHolder{
        public SwipeLayout swipeLayout;
        public TextView Name;
        public TextView Time;
        public CheckBox Check;
        public Button Start;
        public TextView Delete;
        public TextView Edit;
        public TextView Note;
        public ImageView personPhoto;
        public Button start;
        CheckBox done;

        public SimpleViewHolder( final View itemView) {
            super(itemView);

            swipeLayout = (SwipeLayout) itemView.findViewById(R.id.swipe);
            Name = (TextView) itemView.findViewById(R.id.Name);
            Time = (TextView) itemView.findViewById(R.id.Time);

            Start = (Button) itemView.findViewById(R.id.startNavbtn);
            Delete = (TextView) itemView.findViewById(R.id.Delete);
            Edit = (TextView) itemView.findViewById(R.id.Edit);
            start =itemView.findViewById(R.id.startNavbtn);
            done=itemView.findViewById(R.id.done);
            Note = (TextView) itemView.findViewById(R.id.Share);
            personPhoto = (ImageView) itemView.findViewById(R.id.person_photo);

        }
    }
}
