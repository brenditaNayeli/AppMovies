package com.example.appmovies.ui;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.appmovies.R;
import com.example.appmovies.data.local.entity.MovieEntity;
import com.example.appmovies.data.remote.ApiConstants;
import com.squareup.picasso.Picasso;

import java.util.List;


public class MyMovieRecyclerViewAdapter extends RecyclerView.Adapter<MyMovieRecyclerViewAdapter.ViewHolder> {

    private List<MovieEntity> mValues;
    private Context context;

    public MyMovieRecyclerViewAdapter(Context context, List<MovieEntity> items) {
        mValues = items;
       this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_movie, parent, false);
        return new ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        Glide.with(context)
                .load(ApiConstants.IMAGE_API_PREFIX + holder.mItem.getPosterPath())
                .into(holder.imageViewCover);
    }
    public void setData(List<MovieEntity> movies) {
        this.mValues = movies;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        if (mValues != null)
            return mValues.size();
        else return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView imageViewCover;
        public MovieEntity mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            imageViewCover = view.findViewById(R.id.imageViewCover);
        }

        @Override
        public String toString() {
            return super.toString();
        }
    }
}
