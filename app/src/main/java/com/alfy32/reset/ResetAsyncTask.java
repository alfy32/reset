package com.alfy32.reset;

import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class ResetAsyncTask extends AsyncTask<String, String, String> {
    private static final String DEFAULT_HOST = "192.168.0.1";

    HttpClient httpclient = new DefaultHttpClient();
    String host = DEFAULT_HOST;

    @Override
    protected String doInBackground(String... strings) {

        if (strings.length >= 1) {
            host = strings[0];
        }

        HttpPost httpPost = new HttpPost("http://" + host + "/rebootinfo.cgi");
        httpPost.addHeader("Accept", "application/json");

        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
        nameValuePairs.add(new BasicNameValuePair("Reboot", "1"));

        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        } catch (UnsupportedEncodingException e) {
            publishProgress("Request failed to build");
        }

        publishProgress("Sending request to the router...");

        try {
            httpclient.execute(httpPost);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "The router is restarting...";
    }
}
