package com.quizfloor.quizfloor;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.List;


public class submitQuestion extends ActionBarActivity {
    private Spinner spinner1, spinner2;
    Button  btnSubmit;
    EditText question;
    EditText option1;
    EditText option2;
    EditText option3;
    EditText option4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_question);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        addItemsOnSpinner1();
        addItemsOnSpinner2();
        question=(EditText)(findViewById(R.id.QuestionSubmitVal));
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        option1 =(EditText)(findViewById(R.id.Option1SubmitVal));
        option2=(EditText)(findViewById(R.id.Option2SubmitVal));
        option3=(EditText)(findViewById(R.id.Option3SubmitVal));
        option4=(EditText)(findViewById(R.id.Option4SubmitVal));


        btnSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                getData();
            }

        });

    }

    public void getData()
    {
        String cat=String.valueOf(spinner1.getSelectedItem());
        String que = String.valueOf(question.getText());

        ParseObject testObject = new ParseObject("SubmitQuestion");
        testObject.put("Catagory",String.valueOf(spinner1.getSelectedItem()) );
        testObject.put("question", String.valueOf(question.getText()));
        testObject.put("Option1", String.valueOf(option1.getText()));
        testObject.put("Option2", String.valueOf(option2.getText()));
        testObject.put("Option3", String.valueOf(option3.getText()));
        testObject.put("Option4", String.valueOf(option4.getText()));
        testObject.put("Answer",String.valueOf(spinner2.getSelectedItem()) );

        testObject.saveInBackground();

        question.setText("");
        option1.setText("");
        option2.setText("");
        option3.setText("");
        option4.setText("");

    }

    // add items into spinner dynamically
    public void addItemsOnSpinner2() {

        spinner2 = (Spinner) findViewById(R.id.AnswerSubmitVal);
        List<String> list = new ArrayList<String>();
        list.add("Option1");
        list.add("Option2");
        list.add("Option3");
        list.add("Option4");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(dataAdapter);
    }

    // add items into spinner dynamically
    public void addItemsOnSpinner1() {

        spinner1 = (Spinner) findViewById(R.id.CatagorySubmitVal);
        List<String> list = new ArrayList<String>();
        list.add("Java");
        list.add("GRE Verbal");
        list.add("GRE Maths");
        list.add("Java Script");
        list.add("DSA");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(dataAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_submit_question, menu);
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
    @Override
    public void onBackPressed() {
        //Display alert message when back button has been pressed
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
        return;
    }
}
