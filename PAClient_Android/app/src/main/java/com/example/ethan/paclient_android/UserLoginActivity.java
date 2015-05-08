package com.example.ethan.paclient_android;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;


public class UserLoginActivity extends ActionBarActivity {

    private static final String LOGIN_PATH = "http://210.107.197.150:8080/PAmanager/webresources/login";

    private EditText editTextLoginID;
    private EditText editTextLoginPwd;
    private Button buttonLoginLogin;
    private Button buttonLoginRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        editTextLoginID = (EditText)findViewById(R.id.editTextLoginID);
        editTextLoginPwd = (EditText)findViewById(R.id.editTextLoginPwd);
        buttonLoginLogin = (Button)findViewById(R.id.buttonLoginLogin);
        buttonLoginRegister = (Button)findViewById(R.id.buttonLoginRegister);

        buttonLoginRegister.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // force move to UserRegisterActivity
                Intent intentUserRegisterActivity = new Intent(UserLoginActivity.this, UserRegisterActivity.class);
                startActivity(intentUserRegisterActivity);
            }
        });
    }

    public void loginTask(View view) {
        Log.i("UserLogin", "logintask called!");
        // empty EditText check
        if(editTextLoginID.getText().toString().equals("") || editTextLoginPwd.getText().toString().equals("")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(UserLoginActivity.this);
            builder.setMessage("Fill the form please!")
                    .setPositiveButton("OK", null);
            AlertDialog dialog = builder.show();
            TextView messageText = (TextView)dialog.findViewById(android.R.id.message);
            messageText.setGravity(Gravity.CENTER);
            dialog.show();

            return;
        }

        // call RESTful login service on server
        AsyncCreate t = new AsyncCreate();
        t.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_login, menu);
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

            String id = editTextLoginID.getText().toString();
            String pwd = editTextLoginPwd.getText().toString();

            AuthenticationUserData u = new AuthenticationUserData(id, "", pwd);

            String data = AuthenticationUserDataToJSON(u);

            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(LOGIN_PATH);

            post.addHeader("Content-Type", "application/json");

            try {
                StringEntity se = new StringEntity(data);
                post.setEntity(se);

                HttpResponse response = client.execute(post);

                String responseData = EntityUtils.toString(response.getEntity());

                if(responseData.equals("true")) {
                    result = true;
                }

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
                Log.i("UserLogin", "Login succeed");
                Toast.makeText(getApplicationContext(), "Login succeed", Toast.LENGTH_LONG).show();
                PAUser user = PAUser.getInstance(editTextLoginID.getText().toString(), "", "");
                finish();
            } else {
                //fail
                Log.i("UserLogin", "Login failed");
                Toast.makeText(getApplicationContext(), "Login failed", Toast.LENGTH_LONG).show();
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
