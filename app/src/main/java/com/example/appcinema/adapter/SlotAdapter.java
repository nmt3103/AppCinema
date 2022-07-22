package com.example.appcinema.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appcinema.R;
import com.example.appcinema.inteface.SlotListener;
import com.example.appcinema.model.Slot;

import java.util.ArrayList;
import java.util.List;

public class SlotAdapter extends RecyclerView.Adapter<SlotAdapter.SlotHolder> {
    private List<Slot> list;
    private SlotListener slotListener;

    public SlotAdapter(List<Slot> list, SlotListener slotListener) {
        this.list = list;
        this.slotListener = slotListener;
    }

    @NonNull
    @Override
    public SlotHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_slot,parent,false);

        return new SlotHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SlotHolder holder, int position) {
        Slot slot = list.get(position);
        if (slot == null)
            return;
        if (slot.getStatus()){
            holder.img.setImageResource(R.drawable.ic_baseline_chair_24);
        } else {
            holder.bindSlot(slot);
        }


    }

    public List<Slot> selectedList(){
        List<Slot> seleclist = new ArrayList<>();
        for (Slot slot : list){
            if (slot.getSelect()){
                seleclist.add(slot);
            }
        }
        return seleclist;
    }

    @Override
    public int getItemCount() {
        if (list!=null)
            return list.size();
        return 0;
    }

    class SlotHolder extends RecyclerView.ViewHolder{
        private CardView cv;
        private ImageView img;

        public SlotHolder(@NonNull View itemView) {
            super(itemView);
            cv = itemView.findViewById(R.id.cvSlot);
            img =itemView.findViewById(R.id.imgSlot);
        }
        void bindSlot(final Slot slot){
            if (slot.getSelect()){
                img.setImageResource(R.drawable.ic_baseline_chair_24_blue);
            } else{
                img.setImageResource(R.drawable.ic_baseline_chair_24_white);
            }
            cv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (slot.getSelect()){
                        img.setImageResource(R.drawable.ic_baseline_chair_24_white);
                        slot.setSelect(false);
                        if (selectedList().size() == 0){
                            slotListener.onSlotShowAction(false);
                        }
                    } else{
                        img.setImageResource(R.drawable.ic_baseline_chair_24_blue);
                        slot.setSelect(true);
                        slotListener.onSlotShowAction(true);
                    }
                }
            });

        }
    }
}
