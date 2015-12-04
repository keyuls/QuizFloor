package com.quizfloor.quizfloor;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.share.model.GameRequestContent;
import com.facebook.share.widget.GameRequestDialog;
import com.parse.FindCallback;
import com.parse.FunctionCallback;
import com.parse.Parse;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import org.json.JSONArray;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class showQuestion extends Activity {
    int i = 0;
    TextView  questionText;
    Button op1;
    Button op2;
    Button op3;
    Button op4;
    String answer;
    int correct=0;
    int incorrect =0;
    TextView challengerScoreVal;
    TextView challengerScoreTxt;
    TextView decesionVal;
    String percentValue;
    GameRequestDialog requestDialog;
    CallbackManager callbackManager;
    HashMap<String, Object> params = new HashMap<String, Object>();
    int percent;

    public String getPercentValue() {
        return percentValue;
    }
    public void setPercentValue(String percentValue) {
        this.percentValue = percentValue;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_question);
        questionText =  (TextView)findViewById(R.id.questionText);
        op1 = (Button)findViewById(R.id.op1Button);
        op2 = (Button)findViewById(R.id.op2Button);
        op3 = (Button)findViewById(R.id.op3Button);
        op4 = (Button)findViewById(R.id.op4Button);



        FacebookSdk.sdkInitialize(this.getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        requestDialog = new GameRequestDialog(this);
        requestDialog.registerCallback(callbackManager, new FacebookCallback<GameRequestDialog.Result>() {
            public void onSuccess(GameRequestDialog.Result result) {
                ((quizFloorApplication) getApplicationContext()).setReciverId(result.getRequestRecipients().get(0));
                updateDb();
            }

            public void onCancel() {
            }

            public void onError(FacebookException error) {
            }
        });

        if(((quizFloorApplication)getApplicationContext()).isChallengeMode())
        {
            DisplayQue(((quizFloorApplication)getApplicationContext()).getQueList());

        }
        else {


            params.put("topic", ((quizFloorApplication)getApplicationContext()).getSelectedSubCatagory());
            params.put("senderId", ((quizFloorApplication) getApplicationContext()).getUserId());
            params.put("senderName", ((quizFloorApplication) getApplicationContext()).getUserName());
            ParseCloud.callFunctionInBackground("getRandom", params, new FunctionCallback<List<ParseObject>>() {
                @Override
                public void done(List<ParseObject> queLsit, ParseException e) {
                    if (e==null) {
                        ((quizFloorApplication) getApplicationContext()).setQueList(queLsit);
                        DisplayQue(((quizFloorApplication) getApplicationContext()).getQueList());
                    }
                }
            });
        }
    }

    public void updateDb(){

        params.put("recieverId", ((quizFloorApplication) getApplicationContext()).getReciverId());
        params.put("score", getPercentValue());
        ParseCloud.callFunctionInBackground("updateChallenge", params, new FunctionCallback<String>() {
            @Override
            public void done(String updation, ParseException e) {

                if (e == null) {

                    Log.d("Challenge Sent", updation);
                }


            }
        });
    }

    private void DisplayQue(List<ParseObject> scoreList) {

        if (i < scoreList.size()) {
            String question =(String)  scoreList.get(i).getString("question");
            String option1 = (String) scoreList.get(i).getString("Option1");
            String option2 = (String) scoreList.get(i).getString("Option2");
            String option3 = (String) scoreList.get(i).getString("Option3");
            String option4 = (String) scoreList.get(i).getString("Option4");
                    answer =(String)  scoreList.get(i).getString("Answer");

            questionText.setText(question);
            op1.setText(option1);
            op2.setText(option2);

            if(option3.equals("-"))
            {
                op3.setVisibility(View.GONE);
            }
            else {
                op3.setVisibility(View.VISIBLE);

                op3.setText(option3);
            }

            if(option4.equals("-"))
            {
                op4.setVisibility(View.GONE);
            }
            else {
                op4.setVisibility(View.VISIBLE);
                op4.setText(option4);
            }


            op1.setBackgroundColor(Color.parseColor("#FF8A80"));
            op2.setBackgroundColor(Color.parseColor("#FF8A80"));
            op3.setBackgroundColor(Color.parseColor("#FF8A80"));
            op4.setBackgroundColor(Color.parseColor("#FF8A80"));

          /*  Log.d("que" + i, question);
            Log.d("que1", option1);
            Log.d("que2", option2);
            Log.d("que3", option3);
            Log.d("que4", option4);
            Log.d("ans", answer);
            */
            i++;
        }
        else
        {
            showResult(correct,incorrect);
        }
    }

    public void buttonPress(View v) {
        switch (v.getId()) {
            case R.id.op1Button:
                String Option1 = "Option1";
                verifyAnswer(Option1 , v);
                break;
            case R.id.op2Button:
                String Option2 = "Option2";
                verifyAnswer(Option2,v);
                break;
            case R.id.op3Button:
                String Option3 = "Option3";
                verifyAnswer(Option3,v);
                break;
            case R.id.op4Button:
                String Option4 = "Option4";
                verifyAnswer(Option4,v);
                break;
        }
    }

    public void verifyAnswer(String s, View v)
    {
        if (s.equals(answer))
        {
            v.setBackgroundColor(Color.parseColor("#4CAF50"));
            Toast.makeText(getApplicationContext(),
                    "Correct Answer", Toast.LENGTH_SHORT).show();
            correct++;
        }
        else
        {
            v.setBackgroundColor(Color.parseColor("#B71C1C"));
            Toast.makeText(getApplicationContext(),
                    "Incorrect Answer"+"\n"+"Correct Answer:" +answer , Toast.LENGTH_LONG).show();

            String buttonVal = "op" + answer.substring(answer.length() - 1)+"Button";
            int id = getResources().getIdentifier(buttonVal, "id", getPackageName());
            Button btOP = (Button)findViewById(id);
            btOP.setBackgroundColor(Color.parseColor("#4CAF50"));
            incorrect++;
        }



        final Handler myHandler = new Handler();
        myHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                DisplayQue(((quizFloorApplication)getApplicationContext()).getQueList());
            }
        }, 2000);

    }


    public void showResult(int cor,int incor)
    {
        setContentView(R.layout.result);
         percent = (cor*100/i);
        setPercentValue(String.valueOf(percent));
        Log.d("percent", String.valueOf(percent));

        TextView correctVal =(TextView)findViewById(R.id.correctVal);
        TextView incorrectVal=(TextView)findViewById(R.id.incorrectVal);
        TextView percentVal=(TextView)findViewById(R.id.percentVal);

        correctVal.setText(Integer.toString (cor));
        incorrectVal.setText(Integer.toString(incor));
        percentVal.setText(Integer.toString(percent));


        if(((quizFloorApplication)getApplicationContext()).isChallengeMode()) {

            decesionVal= (TextView)findViewById(R.id.txtDecesion);
            challengerScoreVal= (TextView)findViewById(R.id.challengeScoreVal);
            challengerScoreTxt=(TextView)findViewById(R.id.txtChalScore);

            challengerScoreVal.setText(((quizFloorApplication)getApplicationContext()).getChallengerScore());
            challengerScoreTxt.setText(((quizFloorApplication)getApplicationContext()).getChallengerName());

            compareScore();
        }
    }

    private void compareScore() {


       int challengerScore = Integer.parseInt(((quizFloorApplication)getApplicationContext()).getChallengerScore());
        if(percent<challengerScore)
        {
            decesionVal.setText("You Lose");

        }
        else
        {
            decesionVal.setText("You Win");
        }
        decesionVal.setVisibility(View.VISIBLE);
        challengerScoreVal .setVisibility(View.VISIBLE);
        challengerScoreTxt.setVisibility(View.VISIBLE);

        deleteChallenge();
        // sendNotification()
    }

    private void deleteChallenge() {

        String challengeObjId = ((quizFloorApplication)getApplicationContext()).getChallengeObjId();
        ParseObject.createWithoutData("Challenge", challengeObjId).deleteEventually();
    }

    public void playAgain(View view)
    {
        deleteCurrentUser();
        Intent qintent = new Intent(this, categoryListActivity.class);
        startActivity(qintent);

    }

    private void deleteCurrentUser() {
        ParseCloud.callFunctionInBackground("deleteNotUsedChallenge", params, new FunctionCallback<String>() {
            @Override
            public void done(String updation, ParseException e) {

                if (e == null) {

                    Log.d("user deleted", updation);
                }


            }
        });

    }

    public void sendChallenge(View view) {
        GameRequestContent content = new GameRequestContent.Builder().setMessage("play with me").setActionType(GameRequestContent.ActionType.TURN).build();
        requestDialog.show(content);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_show_question, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
