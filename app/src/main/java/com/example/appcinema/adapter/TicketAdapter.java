package com.example.appcinema.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appcinema.R;
import com.example.appcinema.model.Order;

import java.util.List;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.TicketHolder>{
    private List<Order> list;
    private TicketListener ticketListener;

    public TicketAdapter(List<Order> list, TicketListener ticketListener) {
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
        holder.imgTicket.setImageResource(order.getMovie().getImgPoster());
    }

    @Override
    public int getItemCount() {
        if (list != null)
            return list.size();

        return 0;
    }

    class TicketHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tvName,tvDate,tvLocation;
        ImageView imgTicket;

        public TicketHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvLocation = itemView.findViewById(R.id.tvLocation);
            imgTicket = itemView.findViewById(R.id.imgTicket);
        }

        @Override
        public void onClick(View v) {

        }
    }
    public interface TicketListener{
        public void onTicketClick(View view, int position);
    }
}
