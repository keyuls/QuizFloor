package com.quizfloor.quizfloor;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.parse.ParseObject;

import java.util.List;


public class subCatagoryListAdapter extends ArrayAdapter<ParseObject> {

    private final Context context;
    private final List<ParseObject> subCatListObj;


    public subCatagoryListAdapter(Context context, List<ParseObject> subCatListObj) {
        super(context, R.layout.subcatagorylist_adapter, subCatListObj);
        this.context = context;
        this.subCatListObj = subCatListObj;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){


        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(context.LAYOUT_INFLATER_SERVICE);

        View listItemView = inflater.inflate(R.layout.subcatagorylist_adapter, parent, false);
        TextView nameView = (TextView) listItemView.findViewById(R.id.txtSubCatName);


        ParseObject currentUser = subCatListObj.get(position);
        nameView.setText((String)currentUser.get("DisplayName"));



        return listItemView;
    }
}
