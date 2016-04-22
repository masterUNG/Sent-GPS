package appewtc.masterung.sentgps;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;

public class FirstActivity extends AppCompatActivity {

    //Explicit
    private ListView listView;
    private String[] latStrings, lngStrings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        listView = (ListView) findViewById(R.id.listView);

        //Synchronize JSON
        synJSON();

        //Loop Check User
        loopCheckUser();

    }   // Main Method

    public class ConnectedLocalUser extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {

            try {

                OkHttpClient okHttpClient = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                Request request = builder.url("http://swiftcodingthai.com/watch/php_get_last.php").build();
                Response response = okHttpClient.newCall(request).execute();
                return response.body().string();

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }   // doInBack

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("22April", "JSON ==> " + s);

            try {

                JSONArray jsonArray = new JSONArray(s);
                JSONObject jsonObject = jsonArray.getJSONObject(0);
                String strLat = jsonObject.getString("Lat");
                String strLng = jsonObject.getString("Lng");

                for (int i=0;i<latStrings.length;i++) {

                    checkDistance(latStrings[i], lngStrings[i], strLat, strLng);

                }   // for


            } catch (Exception e) {
                e.printStackTrace();
            }

        }   // onPost

    }   // Connected Class

    private void checkDistance(String latString,
                               String lngString,
                               String strLat,
                               String strLng) {

        Log.i("22April", "Lat Plate ==> " + latString);
        Log.i("22April", "Lng Plate ==> " + lngString);
        Log.w("22April", "Lat User ==> " + strLat);
        Log.w("22April", "Lng User ==> " + strLng);

    }   // checkDistance


    private void loopCheckUser() {

        ConnectedLocalUser connectedLocalUser = new ConnectedLocalUser();
        connectedLocalUser.execute();

        //Delay
        Handler handler = new Handler();
        int intTime = 5000; // หน่วงเป็นเวลา 5 วินาที
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loopCheckUser();
            }
        }, intTime);

    }   // loopCheckUser

    @Override
    protected void onRestart() {
        super.onRestart();
        synJSON();
    }

    private void synJSON() {
        MySynJSON mySynJSON = new MySynJSON();
        mySynJSON.execute();
    }

    //Create Inner Class
    public class MySynJSON extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {

            try {

                OkHttpClient okHttpClient = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                Request request = builder.url("http://swiftcodingthai.com/watch/php_get_plate.php").build();
                Response response = okHttpClient.newCall(request).execute();
                return response.body().string();

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

        }   // doInBack

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Log.d("21April", "JSON ==> " + s);

            try {

                JSONArray jsonArray = new JSONArray(s);

                final String[] nameStrings = new String[jsonArray.length()];
                latStrings = new String[jsonArray.length()];
                lngStrings = new String[jsonArray.length()];

                for (int i=0;i<jsonArray.length();i++) {

                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    nameStrings[i] = jsonObject.getString("Name");
                    latStrings[i] = jsonObject.getString("Lat");
                    lngStrings[i] = jsonObject.getString("Lng");

                    Log.d("21April", "Name ==> " + i + " == " + nameStrings[i]);

                }   // for

                Log.d("21April", "Name length ==> " + nameStrings.length);


                PlateAdapter plateAdapter = new PlateAdapter(FirstActivity.this, nameStrings);
                listView.setAdapter(plateAdapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                        Intent intent = new Intent(FirstActivity.this, PlateMapsActivity.class);

                        intent.putExtra("Name", nameStrings[i]);
                        intent.putExtra("Lat", latStrings[i]);
                        intent.putExtra("Lng", lngStrings[i]);

                        startActivity(intent);

                    }   // onItem
                });


            } catch (Exception e) {
                e.printStackTrace();
            }

        }   // onPost

    }   // MySynJSON class

    public void clickAddPlate(View view) {
        startActivity(new Intent(FirstActivity.this, MainActivity.class));
    }

}   // Main Class
