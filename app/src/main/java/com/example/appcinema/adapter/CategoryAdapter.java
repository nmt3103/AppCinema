package com.example.appcinema.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appcinema.R;
import com.example.appcinema.model.Category;


import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>{
    private List<Category> list;
    private  int selectedCate;

    public CategoryAdapter(List<Category> list) {
        this.list = list;
        selectedCate = 0;
    }


    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category,parent,false);

        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category item = list.get(position);
        if (item == null)
            return;
        holder.tv.setBackgroundColor(Color.parseColor("#151D3B"));
        if (selectedCate == position){
             holder.tv.setBackgroundColor(Color.parseColor("#3E60F9"));
        }
        holder.tv.setText(item.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int previousCate = selectedCate;
                selectedCate = holder.getPosition();
                notifyItemChanged(previousCate);
                notifyItemChanged(holder.getPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        if (list!=null)
            return list.size();
        return 0;
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView tv;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tvCateName);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {


        }
    }

}
