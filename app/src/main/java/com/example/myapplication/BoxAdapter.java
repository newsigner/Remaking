package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;

import java.util.ArrayList;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BoxAdapter extends RecyclerView.Adapter<BoxAdapter.BoxVH> {

    private ArrayList<Map<String, Object>> items = new ArrayList<Map<String, Object>>();
    Context context;
    YouTubePlayerFragment youTube;

    public BoxAdapter(Context context, ArrayList<Map<String, Object>> items, YouTubePlayerFragment youTube) {
        this.context = context;
        this.items = items;
        this.youTube = youTube;
    }

    @NonNull
    @Override
    public BoxVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new BoxVH(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BoxVH holder, int position) {
        Map<String, Object> item = items.get(position);
        holder.setItem(item);
    }

    @Override
    public int getItemCount() { return items.size();}

    public class BoxVH extends RecyclerView.ViewHolder {

        public ImageView rank, audiImg;
        public TextView movieNm, audiChange;

        public BoxVH(@NonNull View itemView) {
            super(itemView);
            rank = itemView.findViewById(R.id.rank);
            movieNm = itemView.findViewById(R.id.movieNm);
            audiChange = itemView.findViewById(R.id.audiChange);
            audiImg = itemView.findViewById(R.id.audiImg);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Map<String, Object> item = items.get(getLayoutPosition());
                    setYouTube(item);
                }
            });
        }
        public void setItem(Map<String, Object> item) {
            String s = item.get("rank").toString();
            switch (s) {
                case "1" : rank.setImageResource(R.drawable.one);
                    break;
                case "2" : rank.setImageResource(R.drawable.two);
                    break;
                case "3" : rank.setImageResource(R.drawable.three);
                    break;
                case "4" : rank.setImageResource(R.drawable.four);
                    break;
                case "5" : rank.setImageResource(R.drawable.five);
                    break;
                case "6" : rank.setImageResource(R.drawable.six);
                    break;
                case "7" : rank.setImageResource(R.drawable.seven);
                    break;
                case "8" : rank.setImageResource(R.drawable.eight);
                    break;
                case "9" : rank.setImageResource(R.drawable.nine);
                    break;
                case "10" : rank.setImageResource(R.drawable.ten);
            }
            movieNm.setText(item.get("movieNm").toString());
            audiChange.setText(item.get("audiChange").toString());
            String s2 = item.get("audiChange").toString();
            if (s2.contains("-")) audiImg.setImageResource(R.drawable.down);
            else if (s2.equals("0")) audiImg.setImageResource(R.drawable.equals);
            else audiImg.setImageResource(R.drawable.up);
        }
    }

    public void setYouTube(Map<String, Object> item) {
        String key = "AIzaSyCPapv9Ng5jyW-QulsNqlebY-3CO7sNAyU";
        String part = "id";
        String query = item.get("movieNm").toString()+"trailer";
        int max = 1;
        String type = "video";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.googleapis.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        BoxService youTubeService = retrofit.create(BoxService.class);
        youTubeService.searchVideo(key, part, query, max, type).enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(Call<Map<String, Object>> call, Response<Map<String, Object>> response) {
                ArrayList<Map<String, Object>> items = (ArrayList) response.body().get("items");
                Map<String, Object> id = (Map<String, Object>) items.get(0).get("id");
                Toast.makeText(context, id.get("videoId").toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {
                Toast.makeText(context, "X", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
