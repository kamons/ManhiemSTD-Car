package manheim.kamolstd.manheimcar;

import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

public class ListService extends AppCompatActivity {

    //Explicit
    private ListView listView;
    private String [] nameStrings, latStrings, lngStrings, imageStrings;
    private LocationManager locationManager;
    private Criteria criteria;
    private double latADouble, lngADouble;
    private String idLoginUserString;



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
        idLoginUserString = getIntent().getStringExtra("id");

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

                //check Lat, Lng ===> double
                boolean bolLat = true;
                boolean bolLng = true;

                try {
                    double douLat = Double.parseDouble(latStrings[i]);

                } catch (Exception e) {
                    Log.d("30octV2", "e ==> " + e.toString());
                    bolLat = false;
                }

                try {
                    double douLng = Double.parseDouble(lngStrings[i]);

                } catch (Exception e) {
                    Log.d("30octV2", "e ==> " + e.toString());
                    bolLng = false;
                }

                Log.d("30octV2", "bolLat ==> " + bolLat);
                Log.d("30octV2", "bolLng ==> " + bolLng);


                if (bolLat && bolLng) {
                    Intent intent = new Intent(ListService.this, DetailActivity.class);

                    intent.putExtra("Name", nameStrings[i]);
                    intent.putExtra("Lat", latStrings[i]);
                    intent.putExtra("Lng", lngStrings[i]);
                    startActivity(intent);

                } else {
                    MyAlert myAlert = new MyAlert(ListService.this,
                            R.drawable.bird48, "No Location",
                            "No Lat/Lng on This Person");
                    myAlert.myDialog();
                }

                // onItemClick
            } // onItemClick
        });


        // Setup For Get Location
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);

    } //Main Method

    private class EditUserLocation extends AsyncTask<String, Void, String> {

        //Explicit
        private Context context;

        public EditUserLocation(Context context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(String... strings) {

            try {

                OkHttpClient okHttpClient = new OkHttpClient();
                RequestBody requestBody = new FormEncodingBuilder()
                        .add("isAdd", "true")
                        .add("id", idLoginUserString)
                        .add("Lat", Double.toString(latADouble))
                        .add("Lng", Double.toString(lngADouble))
                        .build();
                Request.Builder builder = new Request.Builder();
                Request request = builder.url(strings[0]).post(requestBody).build();
                Response response = okHttpClient.newCall(request).execute();
                return response.body().string();

            } catch (Exception e) {
                Log.d("30octV1", "e doIn ==> " + e.toString());
                return null;
            }

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Log.d("30octV1", "idUserLogin ==> " + idLoginUserString);
            Log.d("30octV1", "Result ===> " +s );
        }

    } // Edit Class



    @Override
    protected void onResume() {
        super.onResume();

        locationManager.removeUpdates(locationListener);
        latADouble = 13.719687;
        lngADouble = 100.703694;  // Location of Manheim Office

        //Find by Network
        Location netWorkLocation = myFindLocation(LocationManager.NETWORK_PROVIDER);
        if (netWorkLocation != null) {
            latADouble = netWorkLocation.getLatitude();
            lngADouble = netWorkLocation.getLongitude();

        }
        // Find by GPS Card
        Location gpsLocation = myFindLocation(LocationManager.GPS_PROVIDER);
        if (gpsLocation != null) {
            latADouble = gpsLocation.getLatitude();
            lngADouble = gpsLocation.getLongitude();
        }

        Log.d("30octV1", "lat ==> " + latADouble);
        Log.d("30octV1", "lng ==> " + lngADouble);

        // Edit Lat, Lng On Server
        EditUserLocation editUserLocation = new EditUserLocation(ListService.this);
        MyConstant myConstant = new MyConstant();
        editUserLocation.execute(myConstant.getUrlEditLocation());


    }  // onResume

    @Override
    protected void onStop() {
        super.onStop();
        locationManager.removeUpdates(locationListener);

    }

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
