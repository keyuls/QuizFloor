package com.quizfloor.quizfloor;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;


public class openingLogo extends ActionBarActivity {

    static  int count =0;

  //  String parseStr ="{\"data\":[{\"title\":\"GRE\",\"subcatlist\":[{\"Name\":\"English\"},{\"Name\":\"Maths\"}]},{\"title\":\"MobileProgramming\",\"subcatlist\":[{\"Name\":\"Android\"},{\"Name\":\"IOS\"},{\"Name\":\"Swift\"}]}]}";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opening_logo);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();


/*        Thread t = new Thread(){

                public  void run(){
        try {
            //check if connected!
            while (!isNetworkStatusAvialable(getApplicationContext()))
            {
                //Wait to connect
                Toast.makeText(getApplicationContext(), "Please check your Internet Connection", Toast.LENGTH_SHORT).show();
                Thread.sleep(1000);
            };

        } catch (Exception e) {
        }
    }
        };
    t.start(); */

        // Enable Crash Reporting
     //   ParseCrashReporting.enable(this);

        // Enable Local Datastore.

    }



    @Override
    public void onStart(){
        super.onStart();
        final Handler myHandler = new Handler();
        myHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 100ms

                if ((isNetworkStatusAvialable(getApplicationContext()))) {
                    Intent FbIntent = new Intent(openingLogo.this, loginWithFacebook.class);
                    startActivity(FbIntent);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Please check your Internet Connection", Toast.LENGTH_SHORT).show();


                }

            }
        }, 3000);



    }

    /*public void OnResume(){

    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_opening_logo, menu);
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

    @Override
    public void onBackPressed() {
        //Display alert message when back button has been pressed
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
        return;
    }


    //check internet connection
    public static boolean isNetworkStatusAvialable (Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null)
        {
            NetworkInfo netInfos = connectivityManager.getActiveNetworkInfo();
            if(netInfos != null)
            {
                return netInfos.isConnected();
            }
        }
        return false;
    }

}
