package com.example.sara.tripplanner;

/**
 * Created by sara on 27/03/18.
 */


import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class Route {
    public Distance distance;
    public Duration duration;
    public String endAddress;
    public LatLng endLocation;
    public String startAddress;
    public LatLng startLocation;

    public List<LatLng> points;
}
