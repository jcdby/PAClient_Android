package com.example.ethan.paclient_android;


/**
 * Created by Ethan on 04/08/2015.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class ResultOfScanServiceActivity extends ActionBarActivity {



    private static final String TAG = "result of scan services";
    private static final String PATH = "http://192.168.0.23:8080/RandomPersonApp/webresources/person/random";


    private ListView catalogListview;
    private SPCatalogWrap preSpcata;
    private List<ServiceProviderForPA> splist = new ArrayList<ServiceProviderForPA>();
    private List<String> spNamelist = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_of_scan_service);
        catalogListview =
                (ListView)findViewById(R.id.listView_scan_resuilts);
        final Intent serviceListIntent =
                new Intent(this, ServiceListActivity.class);


        //Extract the content of the intent from previous main activity
        //And store it to the pascl
        Intent i = getIntent();
        preSpcata =
                (SPCatalogWrap) i.getSerializableExtra("catalog");
        Log.i(TAG, "i got the catalog from main activity!");

        //show these service catalog onto listview in this activity
        showServiceCatalog(preSpcata);

        catalogListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              
                TextView tv = (TextView)view;
                String contentInItem = new String();

                //get the name of provider from clicked item
                contentInItem = tv.getText().toString();

                //pass sp id to the next activity
                serviceListIntent.putExtra("data", preSpcata);
                serviceListIntent.putExtra("owner_id", findSpId(contentInItem));
                startActivity(serviceListIntent);

            }
        });


    }

    private Long findSpId(String spName)
    {
        for(int i = 0 ; i < preSpcata.getSps().size(); i++)
        {
            if(preSpcata.getSps().get(i).getSpName() == spName)
                return preSpcata.getSps().get(i).getId();
        }
        return null;
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_result_of_scan, menu);
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



    //show the name of each service providers
    private void showServiceCatalog(SPCatalogWrap preextra){
        //Show these sercive catalog onto listview.
        //To show the name of Service Provider, extract the list of sp
        splist = preextra.getSps();
        for(int i =0; i < splist.size(); i++)
        {
            //get the name of sp.
            spNamelist.add(splist.get(i).getSpName());
        }

        ArrayAdapter<String> cataAdapter =
                new ArrayAdapter<String>
                        (this, android.R.layout.simple_list_item_1,spNamelist);

        catalogListview
                .setAdapter(cataAdapter);
    }
}
