package com.example.gpsopenweather;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Openweather extends AppCompatActivity {
    private RecyclerView rv;
    private ArrayList<Detail> Total_detail;
    String TAG = getClass().getSimpleName();
    private DataMeanger dataMeanger;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_openweather);
        rv = findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(Openweather.this));


        Total_detail = new ArrayList<>();
        new weatherpaser().execute();
        dataMeanger = DataMeanger.getInstance();
    }

    @Override
    protected void onResume() {
        super.onResume();
        dataMeanger.setDetails(Total_detail);
    }


    class weatherpaser extends AsyncTask<String,Void,String>{
        String data = "";

        @Override
        protected String doInBackground(String... params) {
            try{
                Intent intent2 = getIntent();
                String lat = intent2.getStringExtra("lat");
                Log.e(TAG,"intent lat =" + lat);

                String lon = intent2.getStringExtra("lon");
                Log.e(TAG,"intent lon =" + lon);

                URL url = new URL("http://api.weatherapi.com/v1/forecast.json?key=bb98c750c15845f3b1e201038200803&q="+lat+","+lon+"&lang=zh_tw&days=3");

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("GET");
                if(httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK)
                {
                    InputStream inputStreamReader = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStreamReader));
                    String line = "";

                    while (line != null)
                    {
                        line = bufferedReader.readLine();
                        data = data + line;
                        Log.e(TAG, "data =" + data);
                    }


                }else if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_MOVED_TEMP)
                {
                    String location = httpURLConnection.getHeaderField("location");
                }else {
                }


                JSONObject jsonObject = new JSONObject(data);
                Log.e(TAG,"data=" + data);
                JSONObject forecast = jsonObject.getJSONObject("forecast");
                JSONArray forecastday = forecast.getJSONArray("forecastday");

                for (int i = 0; i < forecastday.length(); i++) {
                    Detail detail = new Detail();
                    JSONObject jsonObject1 = forecastday.getJSONObject(i);


                    String date = jsonObject1.getString("date");//時間
                    Log.e(TAG, "date =" + jsonObject1.getString("date"));
                    detail.setDt(date);




                    JSONObject day = jsonObject1.getJSONObject("day");//溫度
                    String avgtemp_c = day.getString("avgtemp_c");
                    String avgtemp_F = day.getString("avgtemp_f") + " °F" ;
                    String avghumidity = day.getString("avghumidity") + " %";
                    String UV = day.getString("uv");
                    Log.e(TAG, "day =" + jsonObject1.getString("day"));
                    detail.setTemp(avgtemp_c);
                    detail.setTemp_F(avgtemp_F);
                    detail.setavghumidity(avghumidity);
                    detail.setUV(UV);




                    JSONObject condition = day.getJSONObject("condition");//天氣
                    String text = condition.getString("text");
                    String im = condition.getString("icon");
                    String a = "http:" + im;
                    detail.setIm(a);
                    Log.e(TAG, "condition =" + day.getString("condition"));
                    detail.setDescription(text);



                    Total_detail.add(detail);
                }


            }  catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            rv.setAdapter(new OpenweatherAdapter(Openweather.this, Total_detail, onItemClickListener));
        }
    }

    private OpenweatherAdapter.OnItemClickListener onItemClickListener = new OpenweatherAdapter.OnItemClickListener() {
        @Override
        public void onClick(int pos) {
            Intent intent = new Intent(Openweather.this,Main2Activity.class);
            intent.putExtra("detail", pos);
            startActivity(intent);

        }
    };




}
