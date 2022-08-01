package com.example.appcinema.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appcinema.R;
import com.example.appcinema.activities.MovieDetailActivity;
import com.example.appcinema.model.Movie;
import com.squareup.picasso.Picasso;


import java.util.List;

public class MovieTrendAdapter extends RecyclerView.Adapter<MovieTrendAdapter.MovieTrendHolder>{
    private List<Movie> list;
    private MovieTrendListener movieTrendListener;
    private Context context;


    public MovieTrendAdapter(Context context,List<Movie> list) {
        this.context = context;
        this.list = list;

    }

    public void setMovieTrendListener(MovieTrendListener movieTrendListener) {
        this.movieTrendListener = movieTrendListener;
    }

    @NonNull
    @Override
    public MovieTrendHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie_trend,parent,false);


        return new MovieTrendHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieTrendHolder holder, int position) {
        Movie movie = list.get(position);
        if (movie == null)
            return;
        holder.tvName.setText(movie.getName());
        holder.tvCate.setText(movie.getCate());
        Picasso.get().load(movie.getImgPoster()).into(holder.imgMovie);
//        holder.imgMovie.setImageResource(movie.getImgPoster());
        holder.ratingBar.setRating(movie.getRate());
        holder.cvTrend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MovieDetailActivity.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (list!=null)
            return list.size();
        return 0;
    }

    class MovieTrendHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tvName,tvCate;
        ImageView imgMovie;
        AppCompatRatingBar ratingBar;
        CardView cvTrend;


        public MovieTrendHolder(@NonNull View itemView) {
            super(itemView);
            cvTrend = itemView.findViewById(R.id.cv_trend);
            tvName = itemView.findViewById(R.id.tvName);
            tvCate = itemView.findViewById(R.id.tvCategory);
            imgMovie = itemView.findViewById(R.id.imgMovie);
            ratingBar = itemView.findViewById(R.id.rateBarMovie);
        }

        @Override
        public void onClick(View v) {
//            if (movieTrendListener!=null){
//                movieTrendListener.onMovieTrendClick(v,getAdapterPosition());
//            }

        }
    }
    public interface MovieTrendListener{
        public void onMovieTrendClick(View view, int position);
    }
}
