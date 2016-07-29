package com.quizfloor.quizfloor;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import com.parse.ParseObject;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;


public class categoryListActivity extends ActionBarActivity {

    GridView categoryGridView;
    List<ParseObject> catagoryObj;
    HashMap<String,Object> getCat = new HashMap<String, Object>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);
        setTitle("Exams");

        AdView mAdView = (AdView)findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        categoryGridView = (GridView)findViewById(R.id.CatgridView);
        catagoryObj=((quizFloorApplication)getApplicationContext()).getCatagoryObj();
        if((catagoryObj.size())<1) {
            getCatagory();
        }
        showCatagoryList();
    }



    private void getCatagory() {

        ParseCloud.callFunctionInBackground("getCatagories", getCat, new FunctionCallback<List<ParseObject>>() {
            @Override
            public void done(List<ParseObject> getCatObj, com.parse.ParseException e) {
                if (e == null) {

                    Log.d("catsize", String.valueOf(getCatObj.size()));
                    ((quizFloorApplication) getApplicationContext()).setCatagoryObj(getCatObj);
                    showCatagoryList();
                }
            }
        });
    }


    private void showCatagoryList() {
        catagoryObj= ((quizFloorApplication) getApplicationContext()).getCatagoryObj();
        final catagoryListAdapter cAdapter = new catagoryListAdapter(this,catagoryObj);
        categoryGridView.setAdapter( cAdapter);

        categoryGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {


                ParseObject clickedUser = catagoryObj.get(position);
                String selectedCatagory = (String) clickedUser.get("DisplayName");
                ((quizFloorApplication) getApplicationContext()).setSelectedCatagory(selectedCatagory);
                goToSubCat();
            }
        });
    }


    private void goToSubCat() {
      //  ((quizFloorApplication) getApplicationContext()).setChallengeMode(false);
        Intent qintent = new Intent(this, ShowSubCatagory.class);
        startActivity(qintent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
      //  getMenuInflater().inflate(R.menu.menu_category_list, menu);
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

    public void startNew()
    {
        Intent intent  = new Intent(this,selectCatagory.class);
       // intent.putExtra("fb", name);
        //intent.putExtra("id", id);
        startActivity(intent);
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
}
