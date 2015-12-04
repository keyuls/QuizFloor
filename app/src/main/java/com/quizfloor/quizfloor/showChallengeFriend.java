package com.quizfloor.quizfloor;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class showChallengeFriend extends ActionBarActivity {

    public List<JSONObject> getChallengeFriendListObj() {
        return challengeFriendListObj;
    }

    public void setChallengeFriendListObj(List<JSONObject> inviteFriendListObj) {
        this.challengeFriendListObj = inviteFriendListObj;
    }

    List<JSONObject> challengeFriendListObj = new ArrayList<JSONObject>();
    private List<String> idsToChallenge = new ArrayList<String>();
    private GridView challengeGridView;
    private Button sendChallengeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_challenge_friend);
        //  View view = inflater.inflate(R.layout.activity_show_invite_friend, container, false);
        setTitle("Invite Friends");
        challengeGridView = (GridView)findViewById(R.id.invitesGridView);
        sendChallengeButton =(Button)findViewById(R.id.sendInviteButton);

        challengeFriendListObj =((quizFloorApplication)getApplication()).getChallengbleFriends();

        showChallengeList();


    }

    public void showChallengeList()
    {

        final inviteListAdapter adapter = new inviteListAdapter(this,
                challengeFriendListObj);

        challengeGridView.setAdapter(adapter);
        //   adapter.notifyDataSetChanged();


        challengeGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {


                JSONObject clickedUser = challengeFriendListObj.get(position);
                String invitableToken = clickedUser.optString("id");


                // items act as toggles. so check to see if item exists. if it does
                // then remove. otherwise, add it.

                if (idsToChallenge.contains(invitableToken)) {
                    idsToChallenge.remove(invitableToken);
                } else {
                    idsToChallenge.add(invitableToken);
                }


            }
        });


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_show_challenge_friend, menu);
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
