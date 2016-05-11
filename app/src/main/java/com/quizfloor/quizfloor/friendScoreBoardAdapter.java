package com.quizfloor.quizfloor;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.facebook.login.widget.ProfilePictureView;
import com.parse.ParseObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by keyul on 3/25/2016.
 */
public class friendScoreBoardAdapter extends ArrayAdapter<JSONObject> {
    private final Context context;
    private final List<JSONObject> catListObj;


    public friendScoreBoardAdapter(Context context, List<JSONObject> catListObj) {
        super(context, R.layout.score_board_adapter, catListObj);
        this.context = context;
        this.catListObj = catListObj;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){


        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(context.LAYOUT_INFLATER_SERVICE);

        View listItemView = inflater.inflate(R.layout.score_board_adapter, parent, false);
        TextView scoreView = (TextView) listItemView.findViewById(R.id.txtFriendScore);
        TextView nameView = (TextView) listItemView.findViewById(R.id.txtFriendName);
        TextView posView = (TextView) listItemView.findViewById(R.id.txtPos);
        ProfilePictureView profilePictureView = (ProfilePictureView)listItemView.findViewById(R.id.friendImage);

        JSONObject currentUser = catListObj.get(position);
        try {
            scoreView.setText(String.valueOf(currentUser.optString("score")));
            JSONObject objectIn = currentUser.getJSONObject("user");
            String name= objectIn.optString("name");
            nameView.setText(name);
            posView.setText(String.valueOf(position + 1) + ".");
            String id =objectIn.optString("id");
            Log.e("new id",id);
            profilePictureView.setPresetSize(ProfilePictureView.SMALL);
            profilePictureView.setProfileId(id) ;
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return listItemView;
    }
}
