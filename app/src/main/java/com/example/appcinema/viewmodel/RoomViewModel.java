package com.example.appcinema.viewmodel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.appcinema.model.Room;
import com.example.appcinema.model.Slot;
import com.example.appcinema.utilities.Constants;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.HashMap;
import java.util.List;

public class RoomViewModel extends ViewModel {

    private MutableLiveData<Boolean> isSuccess;
    private MutableLiveData<Double> totalPrice;
    private MutableLiveData<String> stringMutableLiveData;
    private MutableLiveData<String> seatSelect;
    private MutableLiveData<String> priceLiveData;

    public RoomViewModel() {
        isSuccess = new MutableLiveData<>();
        totalPrice = new MutableLiveData<>();
        stringMutableLiveData = new MutableLiveData<>();
        seatSelect = new MutableLiveData<>();
        priceLiveData = new MutableLiveData<>();
    }


    public void changeStatus(List<Slot> list,Room room,String location){

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(location + "/" + Constants.KEY_COLLECTION_ROOMS);
        for (int i = 0;i<list.size();i++){
            HashMap slot = new HashMap();
            slot.put(Constants.KEY_SLOT_ID,list.get(i).getId());
            slot.put(Constants.KEY_SLOT_NAME, list.get(i).getName());
            slot.put(Constants.KEY_SLOT_SELECTED, list.get(i).getSelect());
            slot.put(Constants.KEY_SLOT_STATUS, true);
            myRef.child("/Room"+room.getId()+"/" + Constants.KEY_ROOMS_LIST_SLOT + "/Slot"+list.get(i).getId()).updateChildren(slot).addOnFailureListener(new OnFailureListener() {
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


    public MutableLiveData<String> getSeatSelect() {
        return seatSelect;
    }

    public MutableLiveData<String> getPriceLiveData() {
        return priceLiveData;
    }
}
