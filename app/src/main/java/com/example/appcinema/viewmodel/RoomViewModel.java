package com.example.appcinema.viewmodel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.appcinema.model.Room;
import com.example.appcinema.model.Slot;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RoomViewModel extends ViewModel {
    private MutableLiveData<Room> roomSelect;
    private MutableLiveData<Boolean> isSuccess;
    private MutableLiveData<Double> totalPrice;
    private MutableLiveData<String> stringMutableLiveData;
//    private MutableLiveData<Double> seatSelect;

    public RoomViewModel() {
        isSuccess = new MutableLiveData<>();
        totalPrice = new MutableLiveData<>();
        stringMutableLiveData = new MutableLiveData<>();
//        seatSelect = new MutableLiveData<>();
    }

    public void obeRoom(Room room){
        roomSelect.postValue(room);
    }

    public void changeStatus(List<Slot> list,Room room){

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("listRoom");
        for (int i = 0;i<list.size();i++){
            HashMap slot = new HashMap();
            slot.put("id",list.get(i).getId());
            slot.put("name", list.get(i).getName());
            slot.put("selected", list.get(i).getSelect());
            slot.put("status", true);
            myRef.child("/Room"+room.getId()+"/listSlot/Slot"+list.get(i).getId()).updateChildren(slot).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    isSuccess.postValue(false);
                }
            });

        }
    }

    public MutableLiveData<Boolean> getIsSuccess() {
        return isSuccess;
    }

    public MutableLiveData<Double> getTotalPrice() {
        return totalPrice;
    }

    public MutableLiveData<String> getStringMutableLiveData() {
        return stringMutableLiveData;
    }

    public MutableLiveData<Room> getRoomSelect() {
        return roomSelect;
    }
}
