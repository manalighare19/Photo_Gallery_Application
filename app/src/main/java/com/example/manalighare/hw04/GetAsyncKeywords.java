/*
 ************************ Assignment #HOMEWORK 04 *******************************************
 *********************** File Name- GetAsyncKeywords.java *************************************
 ************************ Full Name- 1. Manali Ghare 2. Anup Deshpande (Group 19) ***********

 */

package com.example.manalighare.hw04;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class GetAsyncKeywords extends AsyncTask<String, Void, String[]> {


    HttpURLConnection Connection = null;
    URL url;
    String[] get_list;
    String result;
    Handler handler;
    Keyword_interface keyword_interface;


    public GetAsyncKeywords(Keyword_interface keyword_interface) {
        this.keyword_interface=keyword_interface;
    }

    @Override
    protected String[] doInBackground(String... urls) {

        try{
            url = new URL(urls[0]);
            Connection = (HttpURLConnection) url.openConnection();
            Connection.connect();


            if (Connection.getResponseCode() == HttpURLConnection.HTTP_OK) {

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(Connection.getInputStream()));
                String keywords = "";
                if ((keywords = bufferedReader.readLine()) != null) {
                    get_list = keywords.split(";");

                }

            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return get_list;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String[] strings) {
        keyword_interface.handlekeyworddata(strings);
    }


    public static interface Keyword_interface{
        public void handlekeyworddata(final String[] strings);
    }
}
