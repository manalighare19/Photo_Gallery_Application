/*
 ************************ Assignment #HOMEWORK 04 *******************************************
 *********************** File Name- GetKeywordParamGetAsync.java *************************************
 ************************ Full Name- 1. Manali Ghare 2. Anup Deshpande (Group 19) ***********

 */

package com.example.manalighare.hw04;

import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class GetKeywordParamGetAsync extends AsyncTask<String,Void,ArrayList<String>> {

    RequestParam requestParam;
    HttpURLConnection Connection = null;
    URL url;
    ArrayList<String> image_urls_async=new ArrayList<String>();
    image_interface image_interface;

    public GetKeywordParamGetAsync(RequestParam requestParam,image_interface image_interface) {
        this.requestParam = requestParam;
        this.image_interface=image_interface;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();



    }

    @Override
    protected void onPostExecute(ArrayList<String> strings) {
            image_interface.handleurlsdata(strings);
    }

    @Override
    protected ArrayList<String> doInBackground(String... strings) {

        try{
            url = new URL(requestParam.getEncodedURl("http://dev.theappsdr.com/apis/photos/index.php"));
            Connection = (HttpURLConnection) url.openConnection();
            Connection.connect();

            image_urls_async.clear();

            if (Connection.getResponseCode() == HttpURLConnection.HTTP_OK) {

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(Connection.getInputStream()));
                String urls = "";
                while ((urls = bufferedReader.readLine()) != null) {
                    Log.d("line is : ",""+urls);
                    image_urls_async.add(urls);

                }
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return image_urls_async;
    }


    public static interface image_interface{
        public void handleurlsdata(final ArrayList<String> image_urls_async);
    }
}
