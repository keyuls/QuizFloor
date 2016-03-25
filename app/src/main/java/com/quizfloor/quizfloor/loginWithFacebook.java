package com.quizfloor.quizfloor;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.LoggingBehavior;

import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import com.parse.ParseObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


public class loginWithFacebook extends FragmentActivity implements Serializable {

    private static String app_id="835011139946449";
    private List<JSONObject> invitableFriendsList;
    private List<JSONObject> challengableFriendsList;
    LoginButton login_button;
    CallbackManager callbackManager = CallbackManager.Factory.create();
    AccessToken accessToken;
    private  AccessTokenTracker accessTokenTracker;
    JSONObject jObj = new JSONObject();
    String name;
    String id;

    HashMap<String,Object> findChallenge = new HashMap<String, Object>();
    public List<JSONObject> getChallengableFriendsList() {
        return challengableFriendsList;
    }

    public void setChallengableFriendsList(List<JSONObject> challengableFriendsList) {
        this.challengableFriendsList = challengableFriendsList;
    }

    public List<JSONObject> getInvitableFriendsList() {
        return invitableFriendsList;
    }

    public void setInvitableFriendsList(List<JSONObject> invitableFriendsList) {
        this.invitableFriendsList = invitableFriendsList;
    }


    //@Override
    public View onCreateView (
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {
                    View view = inflater.inflate(R.layout.activity_login_with_facebook, container, false);
                    login_button = (LoginButton) view.findViewById(R.id.login_button);
                    login_button.setReadPermissions("public_profile");
                    login_button.setReadPermissions("user_friends");
                    login_button.setReadPermissions("email");
                    login_button.setReadPermissions("user_location");
                    login_button.setReadPermissions("publish_actions");
                    return view;

            }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (BuildConfig.DEBUG) {
            FacebookSdk.setIsDebugEnabled(true);
            FacebookSdk.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS) ;
        }
        FacebookSdk.sdkInitialize(getApplicationContext());
        accessToken =  AccessToken.getCurrentAccessToken();
        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken newAccessToken) {
                updateWithToken(newAccessToken);
            }
        };

        setContentView(R.layout.activity_login_with_facebook);

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        callOnSucess();
                        //  getUserScore();
                    }

                    @Override
                    public void onCancel() {
                    }

                    @Override
                    public void onError(FacebookException e) {

                    }
                });

   /* hash key code*/
       try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.quizfloor.quizfloor",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("YourKeyHash :", Base64.encodeToString(md.digest(), Base64.DEFAULT));
                Log.d("YourKeyHash: ", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }

    }



    private void callOnSucess() {

        if (AccessToken.getCurrentAccessToken()!=null) {
            LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email"));
            LoginManager.getInstance().logInWithPublishPermissions(this, Arrays.asList("publish_actions"));
            LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("user_friends"));
           // AccessToken.refreshCurrentAccessTokenAsync();
            GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken() ,
                    new GraphRequest.GraphJSONObjectCallback() {

                        @Override
                        public void onCompleted(JSONObject user, GraphResponse response) {

                            Log.e("userKeyul", user.optString("id"));
                            ((quizFloorApplication)getApplicationContext()).setUserId(user.optString("id"));
                            ((quizFloorApplication)getApplicationContext()).setUserName(user.optString("first_name"));

                            try {
                                retriveCurrentScore(user);
                                friendScoereList();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            Log.e("userLocation", user.optString("location"));
                            Log.e("userGen", user.optString("gender"));
                            Log.e("userEmail", user.optString("email"));
                            Log.e("Userscores", user.optString("scores"));

                            getUserDetails(user);
                            checkTheChallenge();

                        }
                    });

            Bundle parameters = new Bundle();
            parameters.putString("fields", "id,first_name,picture,email,location,gender,scores");
            request.setParameters(parameters);
            request.executeAsync();
           // friendsToInvite();
            final Handler myHandler = new Handler();
            myHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //Do something after 100ms
                    //friendsToChallenge();
                }
            }, 1000);
        }
    }

    public void retriveCurrentScore(JSONObject user) throws JSONException {
        JSONObject jsonScoreObj = user.optJSONObject("scores");
        if(jsonScoreObj!=null){
        JSONArray dataArray = jsonScoreObj.getJSONArray("data");
        JSONObject objectIn = dataArray.getJSONObject(0);
        String getScore = objectIn.optString("score");
        Log.e("Userscore", getScore);
        ((quizFloorApplication) getApplicationContext()).setFbScore(Integer.parseInt(getScore));
        }
    }



    public void friendScoereList(){
        GraphRequest request = GraphRequest.newGraphPathRequest(
                AccessToken.getCurrentAccessToken(),
                "/835011139946449/scores",
                new GraphRequest.Callback() {
                    @Override
                    public void onCompleted(GraphResponse response) {
                        // Insert your code here
                        JSONObject graphObject = response.getJSONObject();
                        try {
                            JSONArray dataArray = graphObject.getJSONArray("data");
                            if (dataArray.length() > 0) {

                                List<JSONObject> sbList = new ArrayList<JSONObject>();
                                // Ensure the user has at least one friend ...
                                for (int i = 0; i < dataArray.length(); i++) {
                                    sbList.add(dataArray.optJSONObject(i));
                                }
                                ((quizFloorApplication) getApplicationContext()).setFriendScoreBoardList(sbList);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });

        request.executeAsync();

    }

    public void checkTheChallenge()
    {
        findChallenge.put("finderId", ((quizFloorApplication) getApplicationContext()).getUserId());
        ParseCloud.callFunctionInBackground("findChallenges", findChallenge, new FunctionCallback<List<ParseObject>>() {
            @Override
            public void done(List<ParseObject> challengeObj, com.parse.ParseException e) {
                if (e == null) {
                    Log.d("challenge size", String.valueOf(challengeObj.size()));
                    if (challengeObj.size() > 0) {
                        ((quizFloorApplication) getApplicationContext()).setChallengeObj(challengeObj);
                        ((quizFloorApplication) getApplicationContext()).setChallengeMode(true);
                        showChallengeList();
                    } else {
                        ((quizFloorApplication) getApplicationContext()).setChallengeMode(false);
                    }
                }
            }

        });
    }

    private void showChallengeList() {
        Intent qintent = new Intent(this, showChallenges.class);
        startActivity(qintent);
    }

    public void getUserDetails(JSONObject user){
                     name =  user.optString("first_name");
                     id = user.optString("id");
                     startNew();
         }

    public void startNew()
    {
        if ((isNetworkStatusAvialable(getApplicationContext()))) {

            Intent intent = new Intent(this, selectCatagory.class);
            startActivity(intent);
        }
    }


 public void friendsToInvite()
 {
     GraphRequest requestFriend = GraphRequest.newGraphPathRequest(AccessToken.getCurrentAccessToken(), "/me/invitable_friends",
             new GraphRequest.Callback() {


                 @Override
                 public void onCompleted(GraphResponse response) {
                     JSONObject graphObject = response.getJSONObject();
                     List<JSONObject> inviteFriendList = new ArrayList<JSONObject>();
                     try {
                         JSONArray dataArray = graphObject.getJSONArray("data");
                         if (dataArray.length() > 0) {
                             // Ensure the user has at least one friend ...
                             for (int i = 0; i < dataArray.length(); i++) {
                                 inviteFriendList.add(dataArray.optJSONObject(i));
                             }
                         }
                         setInvitableFriendsList(inviteFriendList);
                         ((quizFloorApplication)getApplicationContext()).setInvitableFriends(inviteFriendList);
                         int size = ((quizFloorApplication)getApplication()).getInvitableFriends().size();
                         String s =Integer.toString(size);
                         Log.e("newSizeAfter", s);

                     } catch (JSONException e) {
                         e.printStackTrace();
                     }
                 }
             });

     Bundle inviteparameters = new Bundle();
     inviteparameters.putString("fields","id,first_name,picture");
     requestFriend.setParameters(inviteparameters);
     requestFriend.executeAsync();
 }

  /*  public void friendsToChallenge()
    {
        GraphRequest requestFriend = GraphRequest.newGraphPathRequest(AccessToken.getCurrentAccessToken(),
                "/me/friends",
                new GraphRequest.Callback(){


                    @Override
                    public void onCompleted(  GraphResponse response) {
                        JSONObject graphObject = response.getJSONObject();
                        List<JSONObject> ChallenegeFriendList = new ArrayList<JSONObject>();
                        try {
                            JSONArray dataArray = graphObject.getJSONArray("data");

                            if (dataArray.length() > 0) {
                                // Ensure the user has at least one friend ...

                                for (int i = 0; i < dataArray.length(); i++) {
                                    ChallenegeFriendList.add(dataArray.optJSONObject(i));
                                }
                            }
                            setChallengableFriendsList(ChallenegeFriendList);
                            ((quizFloorApplication)getApplicationContext()).setChallengbleFriends(ChallenegeFriendList);
                            int size = ((quizFloorApplication)getApplication()).getChallengbleFriends().size();
                            String s =Integer.toString(size);
                            Log.e("newSizeAfter", s);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
        Bundle inviteparameters = new Bundle();
        inviteparameters.putString("fields","id,first_name,picture");
        requestFriend.setParameters(inviteparameters);
        requestFriend.executeAsync();
    }

*/


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login_with_facebook, menu);
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

    private void updateWithToken(AccessToken currentAccessToken) {

        if (currentAccessToken != null) {
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                   // callTogetLogin();
                    startNew();
                    finish();
                }
            }, 1000);
        }
        /*else {
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    Intent i = new Intent(this, loginWithFacebook.class);
                    startActivity(i);

                    finish();
                }
            }, 1000);
        }*/
    }


    @Override
    public void onResume() {
        super.onResume();
     //  callOnSucess();

    }

    @Override
    public void onStart() {
        super.onStart();
        callOnSucess();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        accessTokenTracker.stopTracking();
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

