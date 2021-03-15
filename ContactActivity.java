package com.example.joshuatabar_json_parser;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class ContactActivity extends AppCompatActivity {

    private TextView mDisplayAboutTextView;
    private Button mOpenWebpageButton;
    private Button mOpenMapButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        // connect with UI elements
        mDisplayAboutTextView = (TextView) findViewById(R.id.tv_about_text);
        mOpenWebpageButton = (Button) findViewById(R.id.button_open_webpage);

        // grabbing the data that the originating intent sends us
        Intent intentThatStartedThisActivity = getIntent();
        String message = "news";
        //check is extra data
        if(intentThatStartedThisActivity.hasExtra(Intent.EXTRA_TEXT)){
            message = intentThatStartedThisActivity.getStringExtra(Intent.EXTRA_TEXT);
            mDisplayAboutTextView.append("\n\n\n" + message);
        } // end if

        final String urlString = "https://roadtolarissa.com/meteors/"; // url string

        // open webpage button
        mOpenWebpageButton.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v){

                        openWebPage(urlString);

                    } // end of onClick method

                } // end of View
        ); // end of setOnClickListener


    } // end of onCreate

    public void openWebPage(String urlString){
        Uri webpage = Uri.parse(urlString);

        Intent openWebPageIntent = new Intent(Intent.ACTION_VIEW, webpage);

        startActivity(openWebPageIntent);

    } // end of open web page

} // end of ContactActivity class
