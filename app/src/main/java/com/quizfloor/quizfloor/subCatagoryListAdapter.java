package com.quizfloor.quizfloor;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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

        int currentSize=subCatListObj.size();
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics (metrics);
// Set layout based on screen size and qty of items
        int newheight = (metrics.heightPixels - 150) / currentSize ;

        nameView.setMinimumHeight(newheight);
        if((position+1)%3==1)
            nameView.setBackgroundColor(Color.parseColor("#FB8C00"));
        else if((position+1)%3==2)
            nameView.setBackgroundColor(Color.parseColor("#515E73"));
        else if((position+1)%3==0)
            nameView.setBackgroundColor(Color.parseColor("#EE454C"));
        ParseObject currentUser = subCatListObj.get(position);
        nameView.setText((String)currentUser.get("DisplayName"));
        nameView.setTextColor(Color.parseColor("#ffffff"));



        return listItemView;
    }

}
