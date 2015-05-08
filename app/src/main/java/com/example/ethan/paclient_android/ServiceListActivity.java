package com.example.ethan.paclient_android;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
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


public class ServiceListActivity extends ActionBarActivity {

    private ListView serviceListview;
    private SPCatalogWrap preSpcata = new SPCatalogWrap();
    private List<ServiceForPA> listService = new ArrayList<ServiceForPA>();
    private List<String> serviceNamelist = new ArrayList<String>();
    private ServiceForPA selectedService = new ServiceForPA();
    private long sp_ID = new Long(0);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_list);
        serviceListview = (ListView) findViewById(R.id.service_listView);
        final Intent serviceIntent =
                new Intent(this, ServiceIntroActivity.class);

        //extract datas
        Intent i = getIntent();
        preSpcata =
                (SPCatalogWrap) i.getSerializableExtra("data");
        sp_ID =
                i.getLongExtra("owner_id", 0);
        //show list of service
        showServiceList(sp_ID);

        serviceListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TextView tv = (TextView)view;
                String contentInItem = new String();

                //get the name of provider from clicked item
                contentInItem = tv.getText().toString();

                selectedService = findServiceByServiceName(contentInItem);

                serviceIntent.putExtra("service", selectedService);
                startActivity(serviceIntent);

            }
        });


    }

    private ServiceForPA findServiceByServiceName(String contentInItem) {

        for(int i = 0; i < listService.size(); i++)
        {
            if (listService.get(i).getName().equals(contentInItem))
            {

                return listService.get(i);
            }
        }
        return null;

    }

    private void showServiceList(long sp_id) {

        listService = getRelatedListofService(sp_id);

        Log.i("after", "showed the list");
        for(int i = 0; i < listService.size(); i++)
        {
            serviceNamelist.add(listService.get(i).getName());
            Log.i("serviceList" , serviceNamelist.get(i));
        }

        ArrayAdapter<String> servicesAdapter =
                new ArrayAdapter<String>
                        (this, android.R.layout.simple_list_item_1,serviceNamelist);

        serviceListview
                .setAdapter(servicesAdapter);



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_service_list, menu);
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


    private List<ServiceForPA> getRelatedListofService(Long spID)
    {

        List<ServiceForPA> temp = new ArrayList<ServiceForPA>();
        for(int i = 0 ; i < preSpcata.getServices().size(); i++)
        {
             if (spID.equals(preSpcata.getServices().get(i).getOwnerid())) {

                temp.add(preSpcata.getServices().get(i));

            }
        }

        return temp;
    }




}
