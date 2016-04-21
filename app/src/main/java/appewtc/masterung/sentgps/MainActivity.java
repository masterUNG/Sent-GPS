package appewtc.masterung.sentgps;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    //Explicit
    private TextView latTextView, lngTextView;
    private EditText plateEditText;

    private LocationManager locationManager;
    private Criteria criteria;
    private boolean GPSABoolean, networkABoolean;
    private int timeAnInt = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Bind Widget
        bindWidget();

        //Setup Location
        setupLocation();

        //Auto Update
        autoUpdate();

    }   // Main Method

    public void clickSaveData(View view) {

        String strName = plateEditText.getText().toString().trim();

        if (strName.equals("")) {
            Toast.makeText(this, "กรุณากรอกชื่อ สถานที่ด้วย คะ",
                    Toast.LENGTH_SHORT).show();
        } else {

        }

    }   // clickSaveData


    private void autoUpdate() {

        timeAnInt += 1;



        //Change Policy
        StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy
                .Builder().permitAll().build();
        StrictMode.setThreadPolicy(threadPolicy);

        //Get Current Time
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        String getTimeDate = dateFormat.format(date);

        Log.d("Test", "Time ==> " + timeAnInt + " = " + getTimeDate);

        myLoop();


    }   // autoUpdate

    private void myLoop() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                autoUpdate();
            }
        }, 1000);   // Mil Second
    }

    @Override
    protected void onResume() {
        super.onResume();


        locationManager.removeUpdates(locationListener);

        String strLat = "Unknow";
        String strLng = "Unknow";

        Location networkLocation = requestLocation(LocationManager.NETWORK_PROVIDER, "No Internet");
        if (networkLocation != null) {
            strLat = String.format("%.7f", networkLocation.getLatitude());
            strLng = String.format("%.7f", networkLocation.getLongitude());
        }

        Location gpsLocation = requestLocation(LocationManager.GPS_PROVIDER, "No GPS card");
        if (gpsLocation != null) {
            strLat = String.format("%.7f", gpsLocation.getLatitude());
            strLng = String.format("%.7f", gpsLocation.getLongitude());
        }

        latTextView.setText(strLat);
        lngTextView.setText(strLng);

    }   // onResume

    @Override
    protected void onStart() {
        super.onStart();

        GPSABoolean = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if (!GPSABoolean) {
            networkABoolean = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!networkABoolean) {
                Log.d("GPS", "Cannot Find Location");
            }

        }


    }

    @Override
    protected void onStop() {
        super.onStop();


        locationManager.removeUpdates(locationListener);

    }

    public Location requestLocation(String strProvider, String strError) {

        Location location = null;

        if (locationManager.isProviderEnabled(strProvider)) {


            locationManager.requestLocationUpdates(strProvider, 1000, 10, locationListener);
            location = locationManager.getLastKnownLocation(strProvider);

        } else {
            Log.d("GPS", strError);
        }


        return location;
    }



    public final LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            latTextView.setText(String.format("%.7f", location.getLatitude()));
            lngTextView.setText(String.format("%.7f", location.getLongitude()));
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


    private void setupLocation() {

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);


    }   // setupLocation

    private void bindWidget() {
        latTextView = (TextView) findViewById(R.id.textView3);
        lngTextView = (TextView) findViewById(R.id.textView5);
        plateEditText = (EditText) findViewById(R.id.editText);
    }

}   // Main Class
