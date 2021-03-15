package com.example.joshuatabar_json_parser;

import com.example.joshuatabar_json_parser.customJSONParser;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.AsyncTask;
// import android.support.v7.app.AppCompatActivity; // this may be a different class for you
// import androidx.appcompat.app.AppCompatActivity; // in new Android studio version
import android.os.Bundle;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //  1: declare variables and import classes
    // Ctrl + space to get automatic import options
    private TextView mSearchResultsDisplay;
    private EditText mSearchTermEditText;
    private Button mSearchButton;
    private Button mResetButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //  2: connect variables to UI elements using R
        // connect with the visual elements in activity_main.xml
        mSearchResultsDisplay   = (TextView) findViewById(R.id.tv_display_text);
        mSearchTermEditText     = (EditText) findViewById(R.id.et_search_box);
        mSearchButton           = (Button) findViewById(R.id.search_button);
        mResetButton            = (Button) findViewById(R.id.reset_button);



        makeNetworkSearchQuery();


        // responding to search button
        mSearchButton.setOnClickListener(
                new View.OnClickListener(){ // a unnamed object
                    //inner method def
                    public void onClick(View v){
                        //get search string from user
                        String searchText = mSearchTermEditText.getText().toString();

                        // get text from mSearchResultsDisplayText
                        String meteors = mSearchResultsDisplay.getText().toString();
                        // convert to a list
                        String[] meteorsList = meteors.split("\n");

                        // search in that list to check if search string matches
                        mSearchResultsDisplay.setText("Matching Results:\n\n");
                        for(String name : meteorsList){
                            if(name.toLowerCase().equals(searchText.toLowerCase())){
                                mSearchResultsDisplay.setText(name);
                                break;
                            }else{
                                mSearchResultsDisplay.setText("No results match.\n");
                            }
                        }
                    } // end of onClick method

                } // end of View.OnClickListener
        ); // end of setOnClickListener

        // responding to reset button
        mResetButton.setOnClickListener(
                new View.OnClickListener(){ // a unnamed object
                    //inner method def
                    public void onClick(View v){
                        // reset the text
                        //mSearchResultsDisplay.setText(defaultDisplayText);
                        makeNetworkSearchQuery();

                    } // end of onClick method

                } // end of View.OnClickListener
        ); // end of setOnClickListener



    } // end of onCreate





    /* Networking related code begins */
    public void makeNetworkSearchQuery(){
        // get the search string
        String searchTerm = mSearchTermEditText.getText().toString();
        //reset the search results
        mSearchResultsDisplay.setText("Results : \n\n");
        // make the search - network
        new FetchNetworkData().execute(searchTerm);

    } // end of makeQuery

    // inner class
    public class FetchNetworkData extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params){
            //get the search term
            if(params.length == 0) return null;
            String searchTerm = params[0];
            // get the Url
            URL searchUrl = customJSONParser.buildMeteorUrl(); // write class and method

            // get the response from the URl
            String responseString = null;
            try{
                responseString = customJSONParser.getResponseFromUrl(searchUrl); //  write this method

            }catch(Exception e){
                e.printStackTrace();
            }
            return responseString;//
        } // end of doInBackground

        @Override
        protected void onPostExecute(String responseData){
            ArrayList<String> titles = customJSONParser.parseMeteorJson(responseData); //
            // display entries in GUI
            for(String title: titles){
                mSearchResultsDisplay.append("\n\n" + title);
            }
        } // end of onPost
    } // end of inner class

    /* Networking related code ends */



    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        //  inflate menu
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    } // end of onCreateOptionsMenu

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int menuItemSelected = item.getItemId();
        if(menuItemSelected == R.id.menu_contact){
            //  react to menu Contact click
            Class destinationActivity = ContactActivity.class;
            // create an Intent that will launch this
            Intent startContactActivityIntent = new Intent(MainActivity.this, destinationActivity);
            // create intent to go to next page
            String msg = mSearchTermEditText.getText().toString();
            startContactActivityIntent.putExtra(Intent.EXTRA_TEXT, msg);

            startActivity(startContactActivityIntent);
            Log.d("info", "Contact Activity launched");
        } // end if
        return true;

    } // end of onOptionsItemSe..


} // end of class