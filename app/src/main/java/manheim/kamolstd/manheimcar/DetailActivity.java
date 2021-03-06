package manheim.kamolstd.manheimcar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class DetailActivity extends FragmentActivity implements OnMapReadyCallback {

    //Explicit

    private GoogleMap mMap;
    private String nameString, latString, lngString;
    private Double latADouble, lngADouble;
    private LatLng latLng;
    private Button backButton, qrCodeButton;
    private String modeString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.my_detail_layout);

        // Bind Widget
        backButton = (Button) findViewById(R.id.button4);
        qrCodeButton = (Button) findViewById(R.id.button5);

        // Back Controler
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //qrCode Controller
        qrCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(DetailActivity.this);
                builder.setCancelable(false);
                builder.setIcon(R.drawable.doremon48);
                builder.setTitle("โปรดเลือกโหมดการทำงาน");
                builder.setMessage("คุณต้องการอ่าน Bar Code หรือ QR Code");
                builder.setNegativeButton("Bar Code", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        readCode("BAR_CODE_MODE");
                        dialogInterface.dismiss();
                    }
                });
                builder.setPositiveButton("QR Code", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        readCode("QR_CODE_MODE");
                        dialogInterface.dismiss();
                    }
                });
                builder.show();


            } // onClick
        });


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Get Value From Intent
        nameString = getIntent().getStringExtra("Name");
        latString = getIntent().getStringExtra("Lat");
        lngString = getIntent().getStringExtra("Lng");

        Log.d("30octV3", "latSting ===> " + latString);
        Log.d("30octV3", "lngSting ===> " + lngString);



    } //Main Method

    private void readCode(String strMode) {

        Log.d("30octV4", "Mode ==> " + strMode);

        try {

            Intent intent = new Intent("com.google.zxing.client.android.SCAN");
            intent.putExtra("SCAN_MODE", strMode);
            startActivityForResult(intent, 0);

        } catch (Exception e) {
            Toast.makeText(DetailActivity.this, " Please Install Barcode Scanner",
                    Toast.LENGTH_SHORT).show();
        }
    } // ReadCode

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ((requestCode == 0)&&(resultCode == RESULT_OK)) {

            String strResult = data.getStringExtra("SCAN_RESULT");
            Log.d("30octV4", "strResult ==> " + strResult);

        } //if

    }// On Activity Result

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        latADouble = Double.parseDouble(latString);
        lngADouble = Double.parseDouble(lngString);
        latLng = new LatLng(latADouble, lngADouble);

        // Set up Center MAP

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));

        //Create Marker
        mMap.addMarker(new MarkerOptions()
        .position(latLng)
        .title(nameString)
        .snippet("นี่คือ snippet"));

        //Create Other Marker

        LatLng connerLatLng = new LatLng(13.721482, 100.702967);
        mMap.addMarker(new MarkerOptions()
                .position(connerLatLng)
                .title("ป้อมตำรวจ")
                .snippet("ให้เลี้ยวซ้าย")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.build4)));

    } //onMapReady
}
