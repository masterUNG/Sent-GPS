package appewtc.masterung.sentgps;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class FirstActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
    }   // Main Method

    public void clickAddPlate(View view) {
        startActivity(new Intent(FirstActivity.this, MainActivity.class));
    }

}   // Main Class
