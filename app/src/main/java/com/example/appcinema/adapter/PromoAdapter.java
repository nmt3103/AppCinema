package com.example.appcinema.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appcinema.R;
import com.example.appcinema.model.Promo;


import java.util.List;

public class PromoAdapter extends RecyclerView.Adapter<PromoAdapter.PromoHolder> {
    private List<Promo> list;

    public PromoAdapter(List<Promo> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public PromoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.promo_item,parent,false);

        return new PromoHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PromoHolder holder, int position) {
        Promo promo = list.get(position);
        if (promo == null)
            return;
        holder.tvTitle.setText(promo.getTitle());
        holder.tvMota.setText(promo.getMota());
        holder.tvDiscount.setText("OFF "+ promo.getDiscount()+"%");
    }

    @Override
    public int getItemCount() {
        if (list!=null)
            return list.size();
        return 0;
    }

    class PromoHolder extends RecyclerView.ViewHolder{
        TextView tvTitle,tvMota,tvDiscount;


        public PromoHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvMota = itemView.findViewById(R.id.tvMota);
            tvDiscount = itemView.findViewById(R.id.tvDiscount);
        }
    }
}
