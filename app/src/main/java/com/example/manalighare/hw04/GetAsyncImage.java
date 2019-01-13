/*
 ************************ Assignment #HOMEWORK 04 *******************************************
 *********************** File Name- GetAsyncImage.java *************************************
 ************************ Full Name- 1. Manali Ghare 2. Anup Deshpande (Group 19) ***********

 */

package com.example.manalighare.hw04;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class GetAsyncImage extends AsyncTask<String, Void, Bitmap> {

    loadimage loadimage;

    public GetAsyncImage(loadimage loadimage) {
        this.loadimage=loadimage;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        loadimage.Load_Image(bitmap);


    }

    @Override
    protected Bitmap doInBackground(String... strings) {


        HttpURLConnection connection = null;
        Bitmap bitmap = null;

        try {

            URL url= new URL(strings[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                bitmap = BitmapFactory.decodeStream(connection.getInputStream());
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }


        return bitmap;

    }

    public static interface loadimage{
        public void Load_Image(Bitmap bitmap);
    }
}
