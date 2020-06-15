package com.google.maps.android;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        LocationListener,GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener{

    private GoogleMap mMap;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    GoogleApiClient mGoogleApiClient;
    LocationRequest mLocationRequest;
    PopupInfo dangerVar;
    FirebaseDatabase db= FirebaseDatabase.getInstance();
    DatabaseReference Locations = db.getReference("Location");
    DatabaseReference Count = Locations.child("datacount");
    DatabaseReference Labels = Count.child("Label");
    DatabaseReference Lattitude = Count.child("Lattitude");
    DatabaseReference Longitude = Count.child("longitude");
    int datacount;
    DataSnapshot dbs;
    int time=0;
    private MediaPlayer salamis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        salamis = MediaPlayer.create(this, R.raw.salamisound);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        //String danger;
       // String dangerous;
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener()
        {
            @Override
            public void onMapClick(LatLng latt)
            {

                String dangerous="test";
                Intent i = new Intent(MapsActivity.this, Pop.class);
              //  i.putExtra("message",dangerous);

                startActivity(i);

                // Write a message to the database
                writeLocationdb("test",latt.latitude,latt.longitude, datacount);
                datacount++;

                Map<String, Object> docData = new HashMap<>();
            }
        });



        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            }
        }
        else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }

        ///////////////read from db
        // Read from the database
        Locations.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                dbs= dataSnapshot;
                MarkerOptions markerOptions = new MarkerOptions();
                String lat1=dataSnapshot.child(Integer.toString(datacount)).child("latitude").getKey();
                long len = dataSnapshot.getChildrenCount();

                android.util.Log.i("test",lat1);
                android.util.Log.i("test","keyyyyyy");
                android.util.Log.i("test","keykey");
                for (int i=0; i<=len-1;i++) {


                    //String lab=dataSnapshot.child(Integer.toString(datacount)).child("label").getValue().toString();
                    String latitud = dataSnapshot.child(Integer.toString(i)).child("lat").getValue().toString();
                    String longitud = dataSnapshot.child(Integer.toString(i)).child("lon").getValue().toString();

                    double latitude = Double.parseDouble(latitud);
                    double longitude = Double.parseDouble(longitud);

                    LatLng latt = new LatLng(latitude, longitude);

                    markerOptions.position(latt);

                    markerOptions.title("label");
                    mMap.addMarker(markerOptions);
                    time=1;
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });

        /////CHECK PROXIMITY

        (new Timer()).scheduleAtFixedRate(new TimerTask() {
            public void run() {
           if(time==1){

                long len = dbs.getChildrenCount();

                for (int i=0; i<=len-1;i++) {

                String latitud = dbs.child(Integer.toString(i)).child("lat").getValue().toString();
                String longitud = dbs.child(Integer.toString(i)).child("lon").getValue().toString();

                double latitude = Double.parseDouble(latitud);
                double longitude = Double.parseDouble(longitud);
                double minLat=latitude-0.003;
                double maxLat=latitude+0.003;
                double minLon=longitude-0.003;
                double maxLon=longitude+0.003;
                //code

                double currentLat=mLastLocation.getLatitude();
                double currentLon=mLastLocation.getLongitude();

                    if ((currentLat < maxLat && currentLat > minLat)&&(currentLon < maxLon && currentLon > minLon)){
                    Log.w("TAG", "Prox alert");
                    salamis.start();


                }}


            }}
        },  0L, 1000L);
    }


    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle) {

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }

    }
    public void writeLocationdb(String label, double lat, double lon, int datacount){
        Loc loc = new Loc(label, lat,lon);
        //long datacount= Locations.getC
        Locations.child(Integer.toString(datacount)).setValue(loc);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {

        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }
        //Place current location marker
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Position");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        mCurrLocationMarker = mMap.addMarker(markerOptions);

        //move map camera
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(11));

        //stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

}