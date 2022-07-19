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
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.List;

public class VideoPagerAdapter extends RecyclerView.Adapter<VideoPagerAdapter.VideoPagerHolder> {
    List<String> list;
    ViewPager2 viewPager2;

    public VideoPagerAdapter(List<String> list, ViewPager2 viewPager2) {
        this.list = list;
        this.viewPager2 = viewPager2;
    }

    @NonNull
    @Override
    public VideoPagerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VideoPagerHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pager_video,parent,false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull VideoPagerHolder holder, int position) {
        String link = list.get(position);
        holder.view.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                youTubePlayer.loadVideo(link,0);
            }
        });
    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    class VideoPagerHolder extends RecyclerView.ViewHolder{

        private YouTubePlayerView view;
        public VideoPagerHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView.findViewById(R.id.video);
        }

    }

}
