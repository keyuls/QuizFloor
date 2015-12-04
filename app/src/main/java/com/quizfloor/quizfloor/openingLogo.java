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
import com.parse.ParseCrashReporting;
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

        if(isNetworkStatusAvialable (getApplicationContext())) {
            Toast.makeText(getApplicationContext(), "Internet detected", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Please check your Internet Connection", Toast.LENGTH_SHORT).show();

        }

        // Enable Crash Reporting
        ParseCrashReporting.enable(this);

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "64ywb2cyyKZvFCkv1nSIYosbbPQTnM2EfU3AWPWi", "6MCsFJw6TpaN3EoD3nYgdshBD7Jfn9u7P6mM5KBw");

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

  /*  private void generateCatJson() {

        JSONObject subcatName1 = new JSONObject();
        try {
        subcatName1.put("Name","Maths");
        }
        catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        JSONObject subcatName2 = new JSONObject();

        try {
            subcatName2.put("Name","English");
        }
        catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        JSONArray subcat1 = new JSONArray();
        subcat1.put(subcatName2);
        subcat1.put(subcatName1);

        JSONObject cat1 = new JSONObject();
        try {
            cat1.put("title", "GRE");
            cat1.put("subcatlist",subcat1);
        }
        catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        JSONArray jsonArray = new JSONArray();
        jsonArray.put(cat1);
       // jsonArray.put(student2);
        JSONObject studentsObj = new JSONObject();
        try {
            studentsObj.put("data", jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("jsonCreate",studentsObj.toString());


        try{
            JSONArray jsonArray1=new JSONObject(parseStr).getJSONArray("data");

            for(int i=0;i<jsonArray1.length();i++)
            {

               JSONObject jbba= jsonArray1.getJSONObject(i);
                String title =jbba.optString("title");
                Log.d("title", title);

              JSONArray jgga= jbba.optJSONArray("subcatlist");
                String gagag = jgga.optJSONObject(i).optString("Name");
                Log.d("gagag",gagag);
            }
          /*  String name= jsonObject.getString("name");
            int salary=jsonObject.getInt("salary");
            String status= jsonObject.getString("married");
            String str="Employee Name:"+name+"\nEmployee:"+salary+"\nstatus:"+status;
*/
     /*       Log.d("check",jsonArray1.toString());
        }
        catch(JSONException e)
        {
    }
        }
*/
    @Override
    public void onStart(){
        super.onStart();
        final Handler myHandler = new Handler();
        myHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 100ms
                Intent FbIntent = new Intent(openingLogo.this, loginWithFacebook.class);
                startActivity(FbIntent);
            }
        }, 2000);



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
}
