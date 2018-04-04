package com.example.sara.tripplanner;

/**
 * Created by sara on 27/03/18.
 */
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;



public interface DirectionFinderListener {
    void onDirectionFinderStart();
    void onDirectionFinderSuccess(List<Route> route);
}
