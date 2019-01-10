package com.tanvir.tanvirmahmudkhan.myapplication;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.tanvir.tanvirmahmudkhan.model.Flower;
import com.tanvir.tanvirmahmudkhan.parser.FlowerJSONParser;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView tv1;
    List<Flower> flowers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv1 = findViewById(R.id.tv1);
        //flowers = new ArrayList<>();
        MyTask myTask = new MyTask();

        myTask.execute("http://services.hanselandpetal.com/feeds/flowers.json");





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
                for (int i=0; i<flowers.size(); i++) {
                    String str = tv1.getText().toString();

                    tv1.setText( str + "\n" + flowers.get(i).getName());
                }
            }
            else{
                tv1.setText("Joy Bangla");
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
