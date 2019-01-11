package com.tanvir.tanvirmahmudkhan.myapplication;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.tanvir.tanvirmahmudkhan.model.Flower;
import com.tanvir.tanvirmahmudkhan.parser.FlowerJSONParser;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //TextView tv1;
    List<Flower> flowers;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        //flowers = new ArrayList<>();

        String url = "http://services.hanselandpetal.com/feeds/flowers.json";

        if(isOnline()){
            MyTask myTask = new MyTask();
            myTask.execute(url);
        }
        else {
            Toast.makeText(MainActivity.this,"No internet connection...",Toast.LENGTH_LONG).show();
        }
    }

    private boolean isOnline() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;
        if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
            isAvailable = true;
        }
        return isAvailable;
    }

    private class MyTask extends AsyncTask<String,String,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {

            flowers= FlowerJSONParser.parseFeed(s);

            if(flowers != null){
                FlowerAdapter flowerAdapter = new FlowerAdapter(MainActivity.this,android.R.layout.simple_list_item_1,flowers);
                listView.setAdapter(flowerAdapter);
            }


        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected String doInBackground(String... ulrs) {

            HttpManager httpManager = new HttpManager();

            return httpManager.getData(ulrs[0]);
        }
    }
}
