package manheim.kamolstd.manheimcar;

import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class ListService extends AppCompatActivity {

    //Explicit
    private listView;
    private String [] nameStrings, latStrings, lngStrings, imageStrings;
    private LocationManager locationManager;
    private Criteria criteria;
    private double latADouble, lngADouble;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_service);

        //Bind Widget
        listView = (ListView) findViewById(R.id.livOfficer);

        //Get Value From Intent

        nameStrings = getIntent().getStringArrayExtra("Name");
        latStrings = getIntent().getStringArrayExtra("Lat");
        lngStrings = getIntent().getStringArrayExtra("Lng");
        imageStrings = getIntent().getStringArrayExtra("Image");

        //Check Array

        Log.d("24octV3", "จำนวน Record ที่อ่านได้  ==>" + nameStrings.length);

                for (int i=0; i<nameStrings.length; i++) {
                    Log.d("24octV3", "Name(" + i + ") ==> " + nameStrings);
                    Log.d("24octV3", "Image(" + i + ") ==> " + imageStrings);
                    Log.d("24octV3", "Lat(" + i + ") ==> " + latStrings);
                    Log.d("24octV3", "Lng(" + i + ") ==> " + lngStrings);

        }


        //Create ListView
        OfficerAdapter officerAdapter = new OfficerAdapter(ListService.this,
                nameStrings, latStrings, lngStrings, imageStrings);
        listView.setAdapter(officerAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(ListService.this, DetailActivity.class);
                startActivity(intent);


            } // onItemClick
        });


        // Setup For Get Location
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);

    } //Main Method

    public Location myFindLocation(String strProvider) {

        Location location = null;

        if (locationManager.isProviderEnabled(strProvider)) {

            locationManager.requestLocationUpdates(strProvider, 1000, 10, locationListener);
            location = locationManager.getLastKnownLocation(strProvider);


        } else {
            Log.d("30octV1", "Error Cannot Find Location");
        }

        return location;
    }

    public LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {

            latADouble = location.getLatitude();
            lngADouble = location.getLongitude();

        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };

} //Main Class
