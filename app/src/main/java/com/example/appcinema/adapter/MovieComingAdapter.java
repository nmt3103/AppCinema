package com.example.appcinema.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appcinema.R;
import com.example.appcinema.model.Movie;


import java.util.List;

public class MovieComingAdapter extends RecyclerView.Adapter<MovieComingAdapter.MovieComingHolder> {
    private List<Movie> list;
    private MovieComingListener movieComingListener;

    public MovieComingAdapter(List<Movie> list, MovieComingListener movieComingListener) {
        this.list = list;
        this.movieComingListener = movieComingListener;
    }

    public void setMovieComingListener(MovieComingListener movieComingListener) {
        this.movieComingListener = movieComingListener;
    }

    @NonNull
    @Override
    public MovieComingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie_coming,parent,false);

        return new MovieComingHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieComingHolder holder, int position) {
        Movie movie = list.get(position);
        if (movie == null)
            return;
        holder.img.setImageResource(movie.getImgPoster());
    }

    @Override
    public int getItemCount() {
        if (list!=null)
            return list.size();
        return 0;
    }

    class MovieComingHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView img;

        public MovieComingHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.imgPoster);
        }

        @Override
        public void onClick(View v) {

        }
    }
    public interface MovieComingListener{
        public void onMovieComingClick(View view, int position);
    }
}
