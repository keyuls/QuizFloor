package com.quizfloor.quizfloor;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestAsyncTask;
import com.facebook.GraphRequestBatch;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.LoggingBehavior;
import com.facebook.ProfileTracker;

import com.facebook.internal.ImageDownloader;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.login.widget.ProfilePictureView;
import com.parse.FunctionCallback;
import com.parse.Parse;
import com.parse.ParseCloud;
import com.parse.ParseObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Console;
import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import android.view.Window;
import android.widget.Toast;


public class loginWithFacebook extends FragmentActivity implements Serializable {

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
                    return view;

            }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (BuildConfig.DEBUG) {
            FacebookSdk.setIsDebugEnabled(true);
            FacebookSdk.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);
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
        Parse.enableLocalDatastore(this);
        Parse.initialize(this);
        LoginManager.getInstance().registerCallback(callbackManager,
                    new FacebookCallback<LoginResult>() {
                        @Override
                        public void onSuccess(LoginResult loginResult) {
                            callOnSucess();
                        }
                        @Override
                        public void onCancel() {
                        }
                        @Override
                        public void onError(FacebookException e) {

                        }
                    });

   /* hash key code*/
  /*      try {
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
*/
    }



    private void callOnSucess() {

         if (AccessToken.getCurrentAccessToken()!=null) {
            GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(),
                    new GraphRequest.GraphJSONObjectCallback() {

                        @Override
                        public void onCompleted(JSONObject user, GraphResponse response) {

                            Log.e("userKeyul", user.optString("id"));
                            ((quizFloorApplication)getApplicationContext()).setUserId(user.optString("id"));
                            ((quizFloorApplication)getApplicationContext()).setUserName(user.optString("first_name"));
                            getUserDetails(user);
                            checkTheChallenge();

                        }
                    });

            Bundle parameters = new Bundle();
            parameters.putString("fields", "id,first_name,picture,email");
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

