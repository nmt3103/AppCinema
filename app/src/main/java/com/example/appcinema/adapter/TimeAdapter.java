package com.example.appcinema.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appcinema.R;

import java.util.ArrayList;
import java.util.List;

public class TimeAdapter extends RecyclerView.Adapter<TimeAdapter.TimeHolder>{
    private List<String> list;
    private int selectedTime;

    public TimeAdapter() {
        list = new ArrayList<>();
        list.add("9 : 30");
        list.add("12 : 20");
        list.add("14 : 30");
        list.add("16 : 40");
        list.add("19 : 30");
        list.add("21 : 30");
        list.add("23 : 30");
        selectedTime = 0;
    }

    public String getSelectedTimeString() {
        return list.get(selectedTime);
    }

    @NonNull
    @Override
    public TimeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_time,parent,false);

        return new TimeHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TimeHolder holder, int position) {
        String time = list.get(position);
        if (time == null);
        holder.tvTime.setBackgroundColor(Color.parseColor("#151D3B"));
        if (selectedTime == position){
            holder.tvTime.setBackgroundColor(Color.parseColor("#3E60F9"));
        }
        holder.tvTime.setText(time);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int previousTime = selectedTime;
                selectedTime = holder.getPosition();
                notifyItemChanged(previousTime);
                notifyItemChanged(holder.getPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class TimeHolder extends RecyclerView.ViewHolder{
        private TextView tvTime;

        public TimeHolder(@NonNull View itemView) {
            super(itemView);
            tvTime = itemView.findViewById(R.id.tvTime);
        }
    }
}
