package com.quizfloor.quizfloor;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import com.facebook.share.model.GameRequestContent;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import com.parse.ParseObject;

import java.util.HashMap;
import java.util.List;


public class ShowSubCatagory extends ActionBarActivity {
    ListView subCatListView;
    List<ParseObject> subCatObj;
    HashMap<String,Object> getSubCat = new HashMap<String, Object>();
    ActionBar actionBar;


    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_show_sub_catagory);

        setTitle(((quizFloorApplication) getApplicationContext()).getSelectedCatagory());
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        subCatListView = (ListView)findViewById(R.id.subCatListView);

        subCatObj=((quizFloorApplication)getApplicationContext()).getSubCatagoryObj();
        getSubCat();
      /*  if((subCatObj.size())<1) {
            getSubCat();
        }*/
     //   showSubCatList();


    }




    private void showSubCatList() {

        subCatObj= ((quizFloorApplication) getApplicationContext()).getSubCatagoryObj();

    /*    int currentSize=subCatObj.size();
        DisplayMetrics metrics = new DisplayMetrics ();
        this.getWindowManager().getDefaultDisplay().getMetrics (metrics);
// Set layout based on screen size and qty of items
        subCatListView.getLayoutParams().height = (metrics.heightPixels ) / currentSize ;
*/
        final subCatagoryListAdapter cAdapter = new subCatagoryListAdapter(this,subCatObj);
        subCatListView.setAdapter(cAdapter);


        subCatListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                ParseObject clickedUser = subCatObj.get(position);
                String selectedSubCatagory = (String) clickedUser.get("DbName");
                ((quizFloorApplication) getApplicationContext()).setSelectedSubCatagory(selectedSubCatagory);
                showNotice();
            }
        });
    }

    private void showNotice() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set title
        alertDialogBuilder.setTitle("Rules");

        // set dialog message
        alertDialogBuilder
                .setMessage("You are going into quiz mode now. Answer five questions to score well.")
                .setCancelable(false)
                .setPositiveButton("Play", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        goToShowQuestion();
                    }
                })
                .setNegativeButton("Not Now", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();

                    }
                });
        // create alert dialog

        AlertDialog dialog = alertDialogBuilder.create();
        dialog.show();
    }

    private void goToShowQuestion() {

        ((quizFloorApplication) getApplicationContext()).setChallengeMode(false);
        Intent qintent = new Intent(this, showQuestion.class);
        startActivity(qintent);
    }

    private void getSubCat() {

        getSubCat.put("catName", ((quizFloorApplication) getApplicationContext()).getSelectedCatagory());

        ParseCloud.callFunctionInBackground("getSubCatagory", getSubCat, new FunctionCallback<List<ParseObject>>() {
            @Override
            public void done(List<ParseObject> getSubCatObj, com.parse.ParseException e) {
                if (e == null) {

                    Log.d("subcatsize", String.valueOf(getSubCatObj.size()));
                    ((quizFloorApplication) getApplicationContext()).setSubCatagoryObj(getSubCatObj);
                    showSubCatList();

                }
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
     //   getMenuInflater().inflate(R.menu.menu_show_sub_catagory, menu);
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
}
