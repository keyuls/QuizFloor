package com.quizfloor.quizfloor;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.share.model.GameRequestContent;
import com.facebook.share.widget.GameRequestDialog;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class showInviteFriend extends ActionBarActivity {

    public List<JSONObject> getInviteFriendListObj() {
        return inviteFriendListObj;
    }

    public void setInviteFriendListObj(List<JSONObject> inviteFriendListObj) {
        this.inviteFriendListObj = inviteFriendListObj;
    }

    List<JSONObject> inviteFriendListObj = new ArrayList<JSONObject>();
    private List<String> idsToInvite = new ArrayList<String>();
    private GridView invitesGridView;
    private Button sendInviteButton;
    GameRequestDialog requestDialog;
    CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_invite_friend);
        //  View view = inflater.inflate(R.layout.activity_show_invite_friend, container, false);
        setTitle("Invite Friends");
        invitesGridView = (GridView) findViewById(R.id.invitesGridView);
        sendInviteButton = (Button) findViewById(R.id.sendInviteButton);

        inviteFriendListObj = ((quizFloorApplication) getApplication()).getInvitableFriends();


        FacebookSdk.sdkInitialize(this.getApplicationContext());

        callbackManager = CallbackManager.Factory.create();
        requestDialog = new GameRequestDialog(this);
        requestDialog.registerCallback(callbackManager, new FacebookCallback<GameRequestDialog.Result>() {
            public void onSuccess(GameRequestDialog.Result result) {
                List<String> id = result.getRequestRecipients();
                Log.d("inviteSize", String.valueOf(id.size()));
            }

            public void onCancel() {}

            public void onError(FacebookException error) {}
        });

        showInviteList();

    }

    public void showInviteList() {

        final inviteListAdapter adapter = new inviteListAdapter(this,
                inviteFriendListObj);

        invitesGridView.setAdapter(adapter);
        //   adapter.notifyDataSetChanged();


        invitesGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {


                JSONObject clickedUser = inviteFriendListObj.get(position);
                String invitableToken = clickedUser.optString("id");


                // items act as toggles. so check to see if item exists. if it does
                // then remove. otherwise, add it.

                if (idsToInvite.contains(invitableToken)) {
                    idsToInvite.remove(invitableToken);
                } else {
                    idsToInvite.add(invitableToken);
                }


            }
        });


    }


    private void sendDirectedInvite(List<String> invitableTokens) {

       // GameRequestContent content = new GameRequestContent.Builder().setMessage("play with me").build();
        GameRequestContent content = new GameRequestContent.Builder().setMessage("play with me").setTo((TextUtils.join(",", invitableTokens))).build();
        requestDialog.show(content);

        /*.setTo(TextUtils.join(",", invitableTokens))*/
        //.setMessage("play with me").setActionType(GameRequestContent.ActionType.ASKFOR).setObjectId("987240521326046")

    }

    public void sendInvitation(View view) {


        sendDirectedInvite(idsToInvite);


      /*  Intent intent = new Intent(this, lastPage.class);
        int size = idsToInvite.size();
        String s = Integer.toString(size);
        intent.putExtra("inviteSize", s);
        startActivity(intent);
        Log.e("newSizeNow", s); */
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_show_invite_friend, menu);
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

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}