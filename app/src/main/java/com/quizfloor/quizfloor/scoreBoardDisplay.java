package com.quizfloor.quizfloor;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.parse.ParseObject;

import java.util.List;

public class scoreBoardDisplay extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_board_display);
      // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
      //  setSupportActionBar(toolbar);
      // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      //  getSupportActionBar().setTitle("Score board");
        ListView scoreListView = (ListView)findViewById(R.id.scoreListView);
        final friendScoreBoardAdapter cAdapter = new friendScoreBoardAdapter(this,((quizFloorApplication)getApplicationContext()).getFriendScoreBoardList());
        scoreListView.setAdapter((ListAdapter) cAdapter);

      FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
       // fab.setVisibility(View.GONE);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, "Can you beat me on Quizfloor?. http://bit.ly/quizfloor");
                startActivity(Intent.createChooser(intent, "Share"));
                Snackbar.make(view, "Invite friends to play on Quizfloor", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
            }
        });
    }

}