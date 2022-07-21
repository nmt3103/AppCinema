package com.example.appcinema.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appcinema.R;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateAdapter extends RecyclerView.Adapter<DateAdapter.DateHolder>{
    private List<Date> list;
    private int selectedDate;

    public DateAdapter() {
        list = new ArrayList<>();
        initDay();
        selectedDate = 0;
    }

    private void initDay() {
        Calendar cal = Calendar.getInstance();
        list.add(new Date());
        for (int i = 1;i<7;i++){
            cal.add(Calendar.DATE,1);
            list.add(cal.getTime());
        }
    }

    @NonNull
    @Override
    public DateHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_date,parent,false);
        return new DateHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DateHolder holder, int position) {
        Date date = list.get(position);
        if (date == null)
            return;
        Format f = new SimpleDateFormat("EEE");
        Format d = new SimpleDateFormat("d");
        holder.tvDay.setText(f.format(date).toString());
        holder.tvNum.setText(d.format(date).toString());
        holder.ln.setBackgroundColor(Color.parseColor("#151D3B"));
        if (selectedDate == position){
            holder.ln.setBackgroundColor(Color.parseColor("#3E60F9"));
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int previousDate = selectedDate;
                selectedDate = holder.getPosition();
                notifyItemChanged(previousDate);
                notifyItemChanged(holder.getPosition());
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class DateHolder extends RecyclerView.ViewHolder {
        private TextView tvDay;
        private TextView tvNum;
        private LinearLayout ln;

        public DateHolder(@NonNull View itemView) {
            super(itemView);
            tvDay = itemView.findViewById(R.id.tvDay);
            tvNum = itemView.findViewById(R.id.tvNum);
            ln = itemView.findViewById(R.id.lnDate);

        }


    }
}
