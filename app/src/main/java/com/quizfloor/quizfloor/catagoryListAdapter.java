package com.quizfloor.quizfloor;

import android.content.Context;
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


        ParseObject currentUser = catListObj.get(position);
        nameView.setText((String)currentUser.get("DisplayName"));



        return listItemView;
    }

}
