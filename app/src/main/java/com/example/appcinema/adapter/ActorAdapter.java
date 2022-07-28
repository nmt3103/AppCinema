package com.example.appcinema.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appcinema.R;
import com.example.appcinema.model.Actor;
import com.example.appcinema.model.Promo;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ActorAdapter extends RecyclerView.Adapter<ActorAdapter.ActorViewHolder>{
    private List<Actor> list;

    public ActorAdapter(List<Actor> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ActorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_actor,parent,false);
        return new ActorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ActorViewHolder holder, int position) {
        Actor actor = list.get(position);
        if (actor == null)
            return;
        holder.tv.setText(actor.getName());
        Picasso.get().load(actor.getImg()).into(holder.img);
    }

    @Override
    public int getItemCount() {
        if (list!=null)
            return list.size();
        return 0;
    }

    class ActorViewHolder extends RecyclerView.ViewHolder{
        private RoundedImageView img;
        private TextView tv;

        public ActorViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.imgActor);
            tv = itemView.findViewById(R.id.tvNameActor);
        }


    }
}
