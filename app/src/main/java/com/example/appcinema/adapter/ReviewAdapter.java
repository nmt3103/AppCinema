package com.example.appcinema.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.appcinema.R;
import com.example.appcinema.model.Review;

import java.util.List;

public class ReviewAdapter extends PagerAdapter {
    private Context context;
    private List<Review> listReview;

    public ReviewAdapter(Context context, List<Review> listReview) {
        this.context = context;
        this.listReview = listReview;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.item_review,container,false);
        ImageView imgReview = view.findViewById(R.id.img_review);

        Review review = listReview.get(position);
        if (review != null){
            Glide.with(context).load(review.getResource()).into(imgReview);
        }
        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        if (listReview != null){
            return listReview.size();
        }
        return 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
