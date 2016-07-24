package com.quizfloor.quizfloor;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.parse.ParseObject;

import java.util.List;

/**
 * Created by keyul on 9/6/2015.
 */
public class catagoryListAdapter  extends ArrayAdapter<ParseObject> {

    private final Context context;
    private final List<ParseObject> catListObj;


    public catagoryListAdapter(Context context, List<ParseObject> catListObj) {
        super(context, R.layout.challenge_adapter, catListObj);
        this.context = context;
        this.catListObj = catListObj;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){


        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(context.LAYOUT_INFLATER_SERVICE);

        View listItemView = inflater.inflate(R.layout.catagory_adapter, parent, false);
        TextView nameView = (TextView) listItemView.findViewById(R.id.txtCatName);

        if((position+1)%3==1)
            nameView.setBackgroundColor(Color.parseColor("#FB8C00"));
        else if((position+1)%3==2)
            nameView.setBackgroundColor(Color.parseColor("#515E73"));
        else if((position+1)%3==0)
            nameView.setBackgroundColor(Color.parseColor("#EE454C"));
        ParseObject currentUser = catListObj.get(position);
        nameView.setText((String)currentUser.get("DisplayName"));
        nameView.setTextColor(Color.parseColor("#ffffff"));

        return listItemView;
    }

}
