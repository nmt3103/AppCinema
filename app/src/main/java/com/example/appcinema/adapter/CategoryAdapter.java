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
    private CateListener cateListener;
    private  int selectedCate;

    public CategoryAdapter(List<Category> list,CateListener cateListener) {
        this.list = list;
        this.cateListener =cateListener;
        selectedCate = 0;
    }

    public void setCateListener(CateListener cateListener) {
        this.cateListener = cateListener;
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
        holder.tv.setBackgroundColor(Color.GRAY);
        if (selectedCate == position){
            holder.tv.setBackgroundColor(Color.BLUE);
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

    public interface CateListener{
        public void onCateClick(View view,int position);
    }
}
