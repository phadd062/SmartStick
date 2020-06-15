/*
package com.google.maps.android;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;


public class LocationsData {
    public LatLng location;
    public BitmapDescriptor bitmapDescriptor;
    public String title;

    */
/**
     * getting hard coded data, data source can be anything ie. network, db etc.
     *
     * @return
     *//*




    public ArrayList<LocationsData> getData() {

        //hard coded data, can be change dynamically
       // LatLng first = MapsActivity.getLocation();
        */
/**LatLng second = new LatLng(45.4215, -75.6972);
        LatLng third = new LatLng(45.4215, -75.6972);
        LatLng fourth = new LatLng(45.4215, -75.6972);*//*



        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.mipmap.marker);
        BitmapDescriptor bitmapDescriptor2 = BitmapDescriptorFactory.fromResource(R.mipmap.marker2);

        String title = "Current location";
        */
/**String title2 = "The Great India Place Mall, Noida, Uttar Pradesh, India";
        String title3 = "Pari Chowk, NRI City, Greater Noida, Uttar Pradesh 201310, India";
        String title4 = "Arun Vihar, Sector 37, Noida, Uttar Pradesh 201303, India";*//*


        ArrayList<LocationsData> datas = new ArrayList<>();

        LocationsData data = new LocationsData();
        data.location =  MapsActivity.mlocation;
        data.bitmapDescriptor = bitmapDescriptor;
        data.title = title;
        datas.add(data);



        return datas;
    }
}
*/
