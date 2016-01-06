package com.quizfloor.quizfloor;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseObject;

import org.json.JSONObject;

import java.util.List;


public class challengeListAdapter extends ArrayAdapter<ParseObject> {

    private final Context context;
    private final List<ParseObject> challenegListObj;



    public challengeListAdapter(Context context, List<ParseObject> challenegListObj) {
        super(context, R.layout.challenge_adapter, challenegListObj);
        this.context = context;
        this.challenegListObj = challenegListObj;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent){


        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(context.LAYOUT_INFLATER_SERVICE);

        View listItemView = inflater.inflate(R.layout.challenge_adapter, parent, false);
        TextView nameView = (TextView) listItemView.findViewById(R.id.txtChallengeDetail);
        Button playButton = (Button) listItemView.findViewById(R.id.btnAccept);

        ParseObject currentUser = challenegListObj.get(position);
        nameView.setText(currentUser.get("SenderName")+" "+"Challenges" +" "+ currentUser.get("Topic")+" ");
        playButton.setText("Accept");


        return listItemView;
    }


}
