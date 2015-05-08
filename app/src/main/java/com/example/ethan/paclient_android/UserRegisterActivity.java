package com.example.ethan.paclient_android;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;


public class UserRegisterActivity extends ActionBarActivity {

    private static final String REGISTER_PATH = "http://210.107.198.20:9998/PAmanager/webresources/register";

    private EditText editTextRegisterID;
    private EditText editTextRegisterName;
    private EditText editTextRegisterPwd;
    private Button buttonRegisterRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);

        editTextRegisterID = (EditText)findViewById(R.id.editTextRegisterID);
        editTextRegisterName = (EditText)findViewById(R.id.editTextRegisterName);
        editTextRegisterPwd = (EditText)findViewById(R.id.editTextRegisterPwd);
        buttonRegisterRegister = (Button)findViewById(R.id.buttonRegisterRegister);
    }

    /* RegisterTask
    * Instead of setOnCLickListener, xml file has onClick property which calls this method.
    */
    public void registerTask(View view) {
        Log.i("UserRegsiter", "registerTask method called.");

        // checking something

        //call register RESTful webservice on server
        AsyncCreate t = new AsyncCreate();
        t.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_register, menu);
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

    // This AsyncTask adds a new person on the web service (POST)
    private class AsyncCreate extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected void onPreExecute() {
            //loading
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            boolean result = false;

            String id = editTextRegisterID.getText().toString();
            String name = editTextRegisterName.getText().toString();
            String pwd = editTextRegisterPwd.getText().toString();

            AuthenticationUserData u = new AuthenticationUserData(id, name, pwd);

            String data = AuthenticationUserDataToJSON(u);

            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(REGISTER_PATH);

            post.addHeader("Content-Type", "application/json");

            try {
                StringEntity se = new StringEntity(data);
                post.setEntity(se);

                HttpResponse response = client.execute(post);

                result = true;
                /*
                if (response.getStatusLine().getStatusCode() == 204) {
                    result = true;
                }
                */

            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
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
        protected void onPostExecute(Boolean result) {

            if (result) {
                //success
                Log.i("UserRegister", "Registration succeed");
                Toast.makeText(getApplicationContext(), "Registration succeed", Toast.LENGTH_LONG).show();
                finish();
            } else {
                //fail
                Log.i("UserRegister", "Registration failed");
                Toast.makeText(getApplicationContext(), "Registration failed", Toast.LENGTH_LONG).show();
            }
        }
    }

    /* Conversion methods between JSON and Person. */

    private PAUser JSONToPAUser(String json) {
        Gson gson = new Gson();
        PAUser p = gson.fromJson(json, PAUser.class);
        return p;
    }

    private String AuthenticationUserDataToJSON(AuthenticationUserData p) {
        Log.i("UserLogin", p.getId() + p.getName() + p.getPwd());
        Gson gson = new Gson();
        String json = gson.toJson(p);
        return json;
    }
}

