package com.example.appcinema.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.appcinema.R;
import com.example.appcinema.model.Movie;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MoviePagerAdapter extends RecyclerView.Adapter<MoviePagerAdapter.MoviePagerHolder> {
    List<Movie> list;
    ViewPager2 viewPager2;
    MoviePagerListener moviePagerListener;

    public MoviePagerAdapter(List<Movie> list, ViewPager2 viewPager2,MoviePagerListener moviePagerListener) {
        this.list = list;
        this.viewPager2 = viewPager2;
        this.moviePagerListener = moviePagerListener;
    }

    @NonNull
    @Override
    public MoviePagerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MoviePagerHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_pager_item,parent,false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull MoviePagerHolder holder, int position) {
        holder.setImg(list.get(position));
        holder.setTextView(list.get(position));
        holder.setRatingBar(list.get(position));
        if (position == list.size() - 2){
            viewPager2.post(runnable);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MoviePagerHolder extends RecyclerView.ViewHolder{

        private RoundedImageView img;
        private AppCompatRatingBar ratingBar;
        private TextView textView;

        public MoviePagerHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.imgPager);
            ratingBar = itemView.findViewById(R.id.rateBarPager);
            textView = itemView.findViewById(R.id.tvName_pager);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    moviePagerListener.onClickMovie(list.get(getAdapterPosition()));
                }
            });
        }


        public void setImg(Movie movie) {
            Picasso.get().load(movie.getImgBig()).into(img);
        }

        public void setRatingBar(Movie movie) {
            ratingBar.setRating(movie.getRate());
        }


        public void setTextView(Movie movie) {
            textView.setText(movie.getName());
        }
    }
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            list.addAll(list);
            notifyDataSetChanged();
        }
    };

    public interface MoviePagerListener{
        public void onClickMovie(Movie movie);
    }

}
