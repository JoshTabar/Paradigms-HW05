package com.example.joshuatabar_json_parser;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;


/**
 * Created by Joshua Tabar.
 */

public class customJSONParser {

    // this method is specific to the meteor URL
    public static URL buildMeteorUrl(){
        // get string url
        String meteorUrlString = "https://data.nasa.gov/resource/gh4g-9sfh.json";
        URL meteorUrl = null;
        try{
            meteorUrl = new URL(meteorUrlString);
        }catch(MalformedURLException e){
            e.printStackTrace();
        }
        return meteorUrl;
    } // end of build

    // this method can be used with any URL object
    public static String getResponseFromUrl(URL url) throws IOException{
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection(); // getting the connection open
        try{
            InputStream in = urlConnection.getInputStream();
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A"); // delimiter for end of message
            boolean hasInput = scanner.hasNext();
            if(hasInput) return scanner.next(); // success
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            urlConnection.disconnect();
        }
        return null;
    } // end of get Resp

    public static ArrayList<String> parseMeteorJson(String meteorResponseString){


        ArrayList<String> meteorList = new ArrayList<String>();

        try {

            JSONArray meteorJSONArray = new JSONArray(meteorResponseString);

            for(int i =0; i< meteorJSONArray.length(); i++) {
                JSONObject childJSON = (JSONObject) meteorJSONArray.get(i);

                String meteorName = (String) childJSON.get("name");
                meteorList.add(meteorName);
            }
        }catch (Exception e){
            System.out.println(e);
            e.printStackTrace();
        }

        return meteorList;
    } // end of parse




    public static URL buildUrl(String urlString){
        //urlString = "https://www3.nd.edu/~skumar5/teaching/pp-files/mini-movies.json";
        //URL url;
        URL url = null;
        try{
            url = new URL(urlString);
        }catch(MalformedURLException e){
            e.printStackTrace();
        }
        return url;

    } // end of buildUrl

    public static ArrayList<String> parseMoviesJson(String responseText){
        ArrayList<String> movies = new ArrayList<String>();

        try {
            Log.d("debugging", "ResponseText from url: " + responseText);
            JSONArray jsonArr = new JSONArray(responseText);
            Log.d("debugging", "JSONArray: " + jsonArr);
            for(int i =0; i< jsonArr.length(); i++) {
                JSONObject jsonMovie = (JSONObject) jsonArr.get(i);
                Log.d("debugging", "JSONObject: " + jsonMovie);
                String movieName = (String) jsonMovie.get("title");
                Log.d("debugging", "Movie name: " + movieName);
                movies.add(movieName);
            }
        }catch (Exception e){
            System.out.println(e);
            e.printStackTrace();
        }

        return movies;
    } // end parseMoviesJson

} // end of class








