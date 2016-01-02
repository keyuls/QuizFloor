package com.quizfloor.quizfloor;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import com.parse.ParseObject;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;


public class showChallenges extends ActionBarActivity {
    ListView challengesListView;
    List<ParseObject> challengeObj;
    HashMap<String,Object> getQue = new HashMap<String, Object>();
    TextView challengeMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_challenges);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Recent Challenges");
        challengesListView = (ListView)findViewById(R.id.challengesListView);

        challengeObj=((quizFloorApplication)getApplicationContext()).getChallengeObj();
        if(challengeObj.size()>0) {
            showChallengesList();
        }
        else{
            challengeMsg=(TextView)findViewById(R.id.txtChallengeMessage);
            challengesListView.setVisibility(View.GONE);
            challengeMsg.setVisibility(View.VISIBLE);

        }
    }

    private void showChallengesList() {

        final challengeListAdapter cAdapter = new challengeListAdapter(this,challengeObj);
        challengesListView.setAdapter((ListAdapter) cAdapter);

        challengesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {


                ParseObject clickedUser = challengeObj.get(position);
                String indexList = (String) clickedUser.get("IndexList");
                String topic = (String) clickedUser.get("Topic");
                String challengeObjId = (String) clickedUser.getObjectId();

                ((quizFloorApplication) getApplicationContext()).setChallengerName((String) clickedUser.get("SenderName"));
                ((quizFloorApplication) getApplicationContext()).setTopic(topic);
                ((quizFloorApplication) getApplicationContext()).setIndexList(indexList);
                ((quizFloorApplication) getApplicationContext()).setChallengeObjId(challengeObjId);
                ((quizFloorApplication) getApplicationContext()).setChallengerScore((String) clickedUser.get("SenderScore"));

                getQuestion();

            }
        });
    }

    /* get Question */
    public void getQuestion()
    {
        getQue.put("indexList",((quizFloorApplication)getApplicationContext()).getIndexList());
        getQue.put("topic", ((quizFloorApplication) getApplicationContext()).getTopic());
        ParseCloud.callFunctionInBackground("ChallengeQue", getQue, new FunctionCallback<List<ParseObject>>() {
            @Override
            public void done(List<ParseObject> getChallengeQueObj, com.parse.ParseException e) {
                if (e == null) {

                    Log.d("challenge Que size", String.valueOf(getChallengeQueObj.size()));
                    ((quizFloorApplication) getApplicationContext()).setQueList(getChallengeQueObj);
                    ((quizFloorApplication) getApplicationContext()).setChallengeMode(true);
                    startLoading();
                }
            }

        });


    }

    public void startLoading()
    {
        Intent qintent = new Intent(this, showQuestion.class);
        startActivity(qintent);
    }


    public void skipChallenges(View view)
    {
        Intent qintent = new Intent(this, categoryListActivity.class);
        startActivity(qintent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_show_challenges, menu);
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
