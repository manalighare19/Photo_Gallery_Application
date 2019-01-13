/*
 ************************ Assignment #HOMEWORK 04 *******************************************
 *********************** File Name- MainActivity.java *************************************
 ************************ Full Name- 1. Manali Ghare 2. Anup Deshpande (Group 19) ***********

 */

package com.example.manalighare.hw04;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.security.NetworkSecurityPolicy;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.TooManyListenersException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,GetAsyncKeywords.Keyword_interface,GetKeywordParamGetAsync.image_interface,GetAsyncImage.loadimage {

    private TextView search_keywords;
    private TextView progress_label;
    private Button go_btn;
    private ImageView display_image;
    private ImageView prev_image;
    private ImageView next_image;


    private ConstraintLayout progressBarLayout;
    AlertDialog.Builder alertDialog;
    String result;
    int total_urls;
    int counter=0;


    ArrayList<String> image_urls=new ArrayList<String>();

    ExecutorService executorService = Executors.newFixedThreadPool(2);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Main Activity");
        if(!isConnected()){
            Toast.makeText(this, "No Internet connection", Toast.LENGTH_SHORT).show();
        }




        alertDialog = new AlertDialog.Builder(this);

        search_keywords = (TextView) findViewById(R.id.keyword);
        go_btn = (Button) findViewById(R.id.go_btn);
        display_image = (ImageView) findViewById(R.id.display_image);
        prev_image = (ImageView) findViewById(R.id.prev_image);
        next_image = (ImageView) findViewById(R.id.next_image);
        progress_label=(TextView)findViewById(R.id.progressBar_Label);
        progressBarLayout=(ConstraintLayout)findViewById(R.id.progressBar_Layout);

        progressBarLayout.setVisibility(View.INVISIBLE);


        go_btn.setOnClickListener(this);
        next_image.setOnClickListener(this);
        prev_image.setOnClickListener(this);

        display_image.setImageResource(android.R.color.transparent);

        next_image.setEnabled(false);
        prev_image.setEnabled(false);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.go_btn:
                if (isConnected()){
                    new GetAsyncKeywords(this).execute("http://dev.theappsdr.com/apis/photos/keywords.php");
                    progressBarLayout.setVisibility(View.VISIBLE);
                    progress_label.setText(R.string.load_dict);
                }else{
                    Toast.makeText(this, "No Internet connection", Toast.LENGTH_SHORT).show();
                }
                break;


            case R.id.next_image:
                if (isConnected()){
                    counter++;
                    progressBarLayout.setVisibility(View.VISIBLE);
                    progress_label.setText(R.string.load_image);
                    if(counter==image_urls.size()){
                        counter=0;
                    }
                    Log.d("counter value is : ",""+counter);
                    new GetAsyncImage(MainActivity.this).execute(image_urls.get(counter));
                }else{
                    Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                }

                break;


            case R.id.prev_image:
                if (isConnected()){
                    counter--;
                    progressBarLayout.setVisibility(View.VISIBLE);
                    progress_label.setText(R.string.load_image);
                    if(counter<0){
                        counter=image_urls.size()-1;
                    }
                    Log.d("counter value is : ",""+counter);
                    new GetAsyncImage(MainActivity.this).execute(image_urls.get(counter));
                }
                else{
                    Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                }

                break;

        }

    }

    @Override
    public void handlekeyworddata(final String[] strings) {

        alertDialog.setTitle("Choose a keyword");
        alertDialog.setItems(strings, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                result=strings[which];
                search_keywords.setText(result);

                RequestParam param=new RequestParam();
                param.addpara("keyword",""+result);

                new GetKeywordParamGetAsync(param,MainActivity.this).execute(result);
                progress_label.setText(R.string.load_image);
            }
        }).show();


    }

    @Override
    public void handleurlsdata(ArrayList<String> image_urls_async) {
        image_urls.clear();

        image_urls=image_urls_async;

        if(image_urls.size()==0){
            progressBarLayout.setVisibility(View.INVISIBLE);
            display_image.setImageResource(android.R.color.transparent);
            Toast.makeText(this,"No Images Found",Toast.LENGTH_LONG).show();
            next_image.setEnabled(false);
            prev_image.setEnabled(false);
        }else{
            next_image.setEnabled(true);
            prev_image.setEnabled(true);
            new GetAsyncImage(MainActivity.this).execute(image_urls.get(0));
            counter=0;
            total_urls=image_urls.size();
        }


    }


    @Override
    public void Load_Image(Bitmap bitmap) {
        display_image.setImageBitmap(bitmap);
        progressBarLayout.setVisibility(View.INVISIBLE);
    }

    private Boolean isConnected(){

        ConnectivityManager connectivityManager=(ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();

        if(networkInfo== null ||!networkInfo.isConnected()){
            return false;
        }

        return true;
    }
}
