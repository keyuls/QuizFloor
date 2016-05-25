package com.quizfloor.quizfloor;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.login.widget.ProfilePictureView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

public class selectCatagory extends ActionBarActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks {


    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private CharSequence mTitle;
    static String  name;
    static String id;
    static int CurrentScore=0;
//    GameRequestDialog requestDialog;
    CallbackManager callbackManager;
    InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_nav);
        //setContentView(R.layout.MyLayoutContainingBannerAd);
        setTitle("Welcome");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        MobileAds.initialize(getApplicationContext(), "ca-app-pub-1379428137301106~5434507071");
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-1379428137301106/2212156671");

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
                redirectToStore();
            }
        });

        requestNewInterstitial();


        Intent intent =  getIntent();
        name = ((quizFloorApplication)getApplicationContext()).getUserName();
        id =   ((quizFloorApplication)getApplicationContext()).getUserId();
        CurrentScore =((quizFloorApplication) getApplicationContext()).getFbScore();

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));


    }


  /*  public void sendChallenge(View view) {

        GameRequestContent content = new GameRequestContent.Builder().setMessage("Come play this level with me").build();
        requestDialog.show(content);

    }
*/
    @Override
    public void onNavigationDrawerItemSelected(int position) {


        switch (position) {
            case 0:

                break;
            case 1:

                break;
            case 2:

                break;
        }
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, selectCatagory.PlaceholderFragment.newInstance(position + 1))
                .commit();
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = "Hello";
               // Intent submit = new Intent(this,submitQuestion.class);
               // startActivity(submit);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
            case 4:
                mTitle=getString(R.string.title_section4);
                break;
            case 5:
                mTitle="Rate app";
                break;
            case 6:
                mTitle=getString(R.string.title_section5);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }



    public void startLoading(View view)
    {
        Intent qintent = new Intent(this, categoryListActivity.class);
        startActivity(qintent);
    }

    public void getScoreBoard(View view) {
        startScoreBoardAct();
    }


    private void startScoreBoardAct() {
        Intent intent = new Intent(this, scoreBoardDisplay.class);
        startActivity(intent);
    }

    public void goToVideo (View view)
    {
        ((quizFloorApplication)getApplicationContext()).setVideoCatMode(true);
        Intent vintent = new Intent(this, videoCatDisplayList.class);
        startActivity(vintent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.menu.menu_select_catagory, menu);
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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.activity_select_catagory, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.userName);
            ProfilePictureView userImage = (ProfilePictureView)rootView.findViewById(R.id.userImage);
            TextView scoreText = (TextView) rootView.findViewById(R.id.fbScoreVal);
            userImage.setProfileId(id);
            textView.setText(name);
            scoreText.setText(String.valueOf( CurrentScore ));

            AdView mAdView = (AdView) rootView.findViewById(R.id.adView);
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((selectCatagory) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

  /*  @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
*/
    @Override
    public void onBackPressed() {
        //Display alert message when back button has been pressed

        askForRating();

    }

    public void exitApp(){
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
        return;
    }

    public void askForRating(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set title
        alertDialogBuilder.setTitle("Exit");

        // set dialog message
        alertDialogBuilder
                .setMessage("Would you like to rate app ?")
                .setCancelable(false)
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                            exitApp();
                        dialog.cancel();
                    }
                })
                .setPositiveButton("Rate App", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        if (mInterstitialAd.isLoaded()) {
                            mInterstitialAd.show();
                        } else {

                                redirectToStore();
                        }
                    }
                });
        // create alert dialog

        AlertDialog dialog = alertDialogBuilder.create();
        dialog.show();
    }

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        mInterstitialAd.loadAd(adRequest);
    }

    public  void redirectToStore(){
        Uri uri = Uri.parse("market://details?id=" + getApplicationContext().getPackageName());
        Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
        try {
            startActivity(myAppLinkToMarket);
        } catch (ActivityNotFoundException e) {
            //  Toast.makeText(this, " unable to find market app", Toast.LENGTH_LONG).show();
            Log.e("sidemenu", "error");
        }
    }
}
