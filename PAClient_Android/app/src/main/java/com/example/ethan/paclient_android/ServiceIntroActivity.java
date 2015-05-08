package com.example.ethan.paclient_android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class ServiceIntroActivity extends ActionBarActivity {

    private ServiceForPA selectedService = new ServiceForPA();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_intro);

        TextView servicename = (TextView) findViewById(R.id.service_name);
        TextView serviceIntro = (TextView) findViewById(R.id.service_intro);
        TextView serviceAddress = (TextView) findViewById(R.id.service_address);

        Intent i  = getIntent();
        selectedService = (ServiceForPA) i.getSerializableExtra("service");

        Log.i("name", selectedService.getName());
        Log.i("Intro", selectedService.getServiceIntroduction());
        Log.i("Address", selectedService.getRedirectionAddress());

        servicename.setText(selectedService.getName());
        serviceIntro.setText(selectedService.getServiceIntroduction());
        serviceAddress.setText(selectedService.getRedirectionAddress());



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_service_intro, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
