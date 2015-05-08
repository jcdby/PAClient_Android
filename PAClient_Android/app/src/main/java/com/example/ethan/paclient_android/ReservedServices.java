package com.example.ethan.paclient_android;

/**
 * Created by Ethan on 04/08/2015.
 */

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;
import retrofit.http.GET;
import retrofit.http.Path;


public class ReservedServices extends ActionBarActivity {

    private ListView reservedService;
    private List<String> reservations;
    private ArrayAdapter<String> adapter;

    public interface ReservationService{

        public static final String LIST_RESERVATION_URL = "http://210.107.197.150:8080/PAmanager/webresources/reservations";

        @GET("/{uid}")
        public void getReservations(@Path("uid") int uid, Callback<List<JsonObject>> callback);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserved_services);
        reservedService = (ListView)findViewById(R.id.listView_reserved_service);

        reservations = new ArrayList<>();

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, reservations);
        reservedService.setAdapter(adapter);

    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.i("ReservedServices", "onStart");

        this.inflateReservations();

    }

    public void inflateReservations(){
        Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
        Log.i("inflateReservations", "inflateReservation started.");


        RestAdapter restAdapter;
        restAdapter = new RestAdapter.Builder().setLogLevel(RestAdapter.LogLevel.BASIC).setEndpoint(ReservationService.LIST_RESERVATION_URL).setConverter(new GsonConverter(gson)).build();

        int uid = 1;
        restAdapter.create(ReservationService.class).getReservations(uid, new Callback<List<JsonObject>>(){
            @Override
            public void success(List<JsonObject> jsonObjectList, Response response) {
                for(JsonObject item:jsonObjectList){

                    String rsv =("id: " + item.get("id")) + ", uid: " + item.get("uid") + ", name: " + item.get("name") + ", starts: " + item.get("starts") + ", ends: " + item.get("ends");
                    Log.i("inflateReservations", rsv);
                    reservations.add(rsv);
                }
                adapter.notifyDataSetChanged();
                Log.i("inflateReservations", "inflateReservation completed.");
            }

            @Override
            public void failure(RetrofitError error) {

                Log.i("inflateReservations", "inflateReservation failed.");
                error.printStackTrace();
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_reserved_services, menu);
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
