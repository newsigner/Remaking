package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.android.youtube.player.YouTubePlayerFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    YouTubePlayerFragment youTube;
    RecyclerView list;
    String key = "35392ba4b1a9bcacbcea93803f31d8c0";
    String baseUrl = "http://www.kobis.or.kr";
    RecyclerView.Adapter adapter;
    Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = findViewById(R.id.list);
        youTube = (YouTubePlayerFragment) getFragmentManager().findFragmentById(R.id.youTube);

        String day = Day();
        RetrofitMaking(day);
        list.setAdapter(adapter);
    }

    private String Day() {
        Date date = new Date();
        date.setTime(date.getTime()-(1000*60*60*24));
        String day = new SimpleDateFormat("yyyyMMdd").format(date);
        return day;
    }//현재 시간 얻는 메소드.

    private void RetrofitMaking(String day) {
        retrofit = new Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        BoxService boxService = retrofit.create(BoxService.class);

        boxService.getBoxOffice(key, day).enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(Call<Map<String, Object>> call, Response<Map<String, Object>> response) {
                Map<String, Object> boxOfficeResult = (Map<String, Object>) response.body().get("boxOfficeResult");
                ArrayList<Map<String, Object>> jsonList = (ArrayList) boxOfficeResult.get("dailyBoxOfficeList");
                adapter = new BoxAdapter(jsonList);
            }

            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {

            }
        });
    }
}