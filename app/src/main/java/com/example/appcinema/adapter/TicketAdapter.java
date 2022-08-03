package com.example.appcinema.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appcinema.R;
import com.example.appcinema.activities.MovieDetailActivity;
import com.example.appcinema.activities.TicketDetailActivity;
import com.example.appcinema.model.Order;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.TicketHolder>{
    private List<Order> list;
    private TicketListener ticketListener;
//    private Context context;

    public TicketAdapter(List<Order> list ,TicketListener ticketListener) {
        this.list = list;
        this.ticketListener = ticketListener;
    }

    public void setTicketListener(TicketListener ticketListener) {
        this.ticketListener = ticketListener;
    }

    @NonNull
    @Override
    public TicketHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ticket,parent,false);

        return new TicketHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TicketHolder holder, int position) {
        Order order = list.get(position);
        if (order == null)
            return;

        holder.tvName.setText(order.getMovie().getName());
        holder.tvDate.setText(order.getDate().toString());
        holder.tvLocation.setText(order.getLocation());
        Picasso.get().load(order.getMovie().getImgPoster()).into(holder.imgTicket);
//        holder.imgTicket.setImageResource(order.getMovie().getImgPoster());
//        holder.cvTicket.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, TicketDetailActivity.class);
//                context.startActivity(intent);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        if (list != null)
            return list.size();

        return 0;
    }

    class TicketHolder extends RecyclerView.ViewHolder {
        TextView tvName,tvDate,tvLocation;
        ImageView imgTicket;

        public TicketHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvLocation = itemView.findViewById(R.id.tvLocation);
            imgTicket = itemView.findViewById(R.id.imgTicket);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ticketListener.onTicketClick(list.get(getAdapterPosition()));
                }
            });
        }


    }
    public interface TicketListener{
        public void onTicketClick(Order order);
    }
}
