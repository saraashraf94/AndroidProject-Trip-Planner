package com.example.sara.tripplanner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sara on 01/04/18.
 */

public class Tripcardview {



        String name;
        String age;
        int photoId;

    Tripcardview(String name, String age, int photoId) {
            this.name = name;
            this.age = age;
            this.photoId = photoId;
        }


    private List<Tripcardview> Trips;

    // This method creates an ArrayList that has three Person objects
// Checkout the project associated with this tutorial on Github if
// you want to use the same images.
    private void initializeData(){
        Trips = new ArrayList<>();
        Trips.add(new Tripcardview("Emma Wilson", "23 years old", R.drawable.ic_person));
        Trips.add(new Tripcardview("Lavery Maiss", "25 years old", R.drawable.ic_person));
        Trips.add(new Tripcardview("Lillie Watts", "35 years old", R.drawable.ic_person));
    }


}
