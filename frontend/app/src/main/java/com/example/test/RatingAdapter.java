package com.example.test;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RatingAdapter extends RecyclerView.Adapter<RatingAdapter.ViewHolder> {

    private final LayoutInflater inflater;
    private final List<RatingItem> ratingItems;

    RatingAdapter(Context context, List<RatingItem> ratingItems) {
        this.ratingItems = ratingItems;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public RatingAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.row_rating, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(RatingAdapter.ViewHolder holder, int position) {
        RatingItem ratingItem = ratingItems.get(position);
        holder.positionView.setText(Integer.toString(position));
        holder.nameView.setText(ratingItem.getFirstName() + " " + ratingItem.getSecondName());
        holder.scoreView.setText(Integer.toString(ratingItem.getScore()));
    }

    @Override
    public int getItemCount() {
        return ratingItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView positionView, nameView, scoreView;

        ViewHolder(View view) {
            super(view);
            positionView = view.findViewById(R.id.txtPosition);
            nameView = view.findViewById(R.id.txtName);
            scoreView = view.findViewById(R.id.txtScore);
        }
    }
}
