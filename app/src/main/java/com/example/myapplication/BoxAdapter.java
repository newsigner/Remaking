package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Map;

public class BoxAdapter extends RecyclerView.Adapter<BoxAdapter.BoxVH> {

    private ArrayList<Map<String, Object>> items = new ArrayList<Map<String, Object>>();

    public BoxAdapter(ArrayList<Map<String, Object>> items) {
        this.items = items;
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

    public static class BoxVH extends RecyclerView.ViewHolder {

        public TextView rank, movieNm, audiChange;

        public BoxVH(@NonNull View itemView) {
            super(itemView);
            rank = itemView.findViewById(R.id.rank);
            movieNm = itemView.findViewById(R.id.movieNm);
            audiChange = itemView.findViewById(R.id.audiChange);
        }
        public void setItem(Map<String, Object> item) {
            rank.setText(item.get("rank").toString());
            movieNm.setText(item.get("movieNm").toString());
            audiChange.setText(item.get("audiChange").toString());
        }
    }
}
