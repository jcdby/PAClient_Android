package com.example.ethan.paclient_android;

/**
 * Created by Ethan on 04/08/2015.
 */

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.google.gson.Gson;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;


public class MainActivity extends ActionBarActivity {

    private static final String MAIN_ACTIVITY = "MainActivity";
    private static final String ASYNC_SCAN_TASK = "AsyncScanTask";

    private static final String SCAN_PATH =
            "http://210.107.197.150:8080/PAmanager/webresources/services";

    //Two button
    private Button scanServiceB;
    private Button myService;

    //service catalog data from server
    private SPCatalogWrap spcata;

    private AsyncScanTask ast;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Single PAUser created and get instance
        PAUser user = PAUser.getInstance();


        //Get contents from the server
        ast = new AsyncScanTask();
        ast.execute();
        final Intent scanServiceIntent =
                new Intent(this, ResultOfScanServiceActivity.class);
        final Intent reservedServices =
                new Intent(this, ReservedServices.class);

        scanServiceB = (Button)findViewById(R.id.ScanServiceB);
        myService = (Button)findViewById(R.id.MyService);

        scanServiceB.setOnClickListener(new OnClickListener() {
            //In this section, the scan service function will be implemented.

            //After clicking scan service button, client will send a GET message to Server
            //and get a provider arraylist. In the list, There are providers who provide service to PA user.
            public void onClick(View v) {
                Log.i(MAIN_ACTIVITY, "scan service button is clicked");


                if (ast.getStatus() == AsyncTask.Status.FINISHED) {
                    if (spcata == null) {
                        Log.i(MAIN_ACTIVITY, "I didn`t get anything from server");

                    } else {

                        //put the service catalog into the extra
                        //and send it to the next activity
                        scanServiceIntent.putExtra("catalog", spcata);
                        startActivity(scanServiceIntent);
                    }
                }
            }
        });


        myService.setOnClickListener(new OnClickListener() {
            //in this section, the my service function will be implemented.
            //that is, when we click this button, it will be turn to the
            // other activity which gives us the result of
            //the services we have already had.


            public void onClick(View v) {
                Log.i(MAIN_ACTIVITY, "My service button is clicked");

                PAUser user = PAUser.getInstance();

                if (user.getId().equals("")) {
                    // force move to login activity
                    Intent intentUserLoginActivity = new Intent(MainActivity.this, UserLoginActivity.class);
                    startActivity(intentUserLoginActivity);
                } else {
                    //get contents from the server
                    startActivity(reservedServices);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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


    // This AsyncTask retrieves a service catalog from the web service (GET)
    private class AsyncScanTask extends AsyncTask<Void, Void, SPCatalogWrap>{


        public AsyncScanTask() {
            super();
        }

        @Override
        protected SPCatalogWrap doInBackground(Void... params) {
            SPCatalogWrap result = null;

            HttpClient client = new DefaultHttpClient();
            HttpGet get = new HttpGet(SCAN_PATH);

            get.setHeader("Accept", "application/json");

            HttpEntity entity;

            try {
                HttpResponse response = client.execute(get);

                if (response.getStatusLine().getStatusCode() != 200) {
                    return null;
                }

                entity = response.getEntity();

                String data = EntityUtils.toString(entity);

                result = JSONToSPCatalogWrap(data);

            } catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


            return result;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.i(ASYNC_SCAN_TASK,"Start to scan service catalog");

        }

        @Override
        protected void onPostExecute(SPCatalogWrap result) {
            super.onPostExecute(result);

            if(result != null)
            {
                spcata = result;
                Log.i(MAIN_ACTIVITY, "Getting content from server is done!");
            }else{
                Log.i(ASYNC_SCAN_TASK,"Getting the JSON is failed!");
            }




        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }


    }

        /* Conversion methods between JSON and SPCatalogWrap. */

    private SPCatalogWrap JSONToSPCatalogWrap(String json) {
        Gson gson = new Gson();
        SPCatalogWrap p = gson.fromJson(json, SPCatalogWrap.class);
        return p;
    }



}
