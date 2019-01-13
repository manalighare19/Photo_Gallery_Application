/*
 ************************ Assignment #HOMEWORK 04 *******************************************
 *********************** File Name- RequestParam.java *************************************
 ************************ Full Name- 1. Manali Ghare 2. Anup Deshpande (Group 19) ***********

 */

package com.example.manalighare.hw04;

import java.util.HashMap;

public class RequestParam {
    HashMap<String,String> params;
    public StringBuilder stringBuilder;

    public RequestParam() {
        params=new HashMap<>();
        stringBuilder=new StringBuilder();
    }


    public RequestParam addpara(String key, String value){
        params.put(key,value);
        return this;
    }


    public String getEncodedParameters(){
        for(String key:params.keySet()){
            stringBuilder.append(key+"="+params.get(key));
        }

        return stringBuilder.toString();
    }

    public String getEncodedURl(String url){
        return url+"?"+getEncodedParameters();
    }
}
