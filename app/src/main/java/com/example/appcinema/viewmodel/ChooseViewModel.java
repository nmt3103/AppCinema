package com.example.appcinema.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.appcinema.model.Room;
import com.example.appcinema.model.Slot;
import com.example.appcinema.utilities.Constants;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChooseViewModel extends ViewModel {
    private MutableLiveData<List<Room>> listRoomLiveDate;
    private MutableLiveData<Boolean> isLoading;

    public ChooseViewModel() {
        listRoomLiveDate = new MutableLiveData<>();
        isLoading = new MutableLiveData<>();

    }

    public void readListRoomFromFirebase(String location) {
        isLoading.postValue(true);
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference myRef = database.getReference(Constants.KEY_COLLECTION_ROOMS);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Room> listRead = new ArrayList<>();
                for (DataSnapshot dataSnapshotRoom : snapshot.getChildren()){
                    int id = dataSnapshotRoom.child(Constants.KEY_ROOMS_ID).getValue(Integer.class);
                    String time = dataSnapshotRoom.child(Constants.KEY_ROOMS_TIME).getValue(String.class);
                    String date = dataSnapshotRoom.child(Constants.KEY_ROOMS_DATE).getValue(String.class);
                    int movieId = dataSnapshotRoom.child(Constants.KEY_ROOMS_MOVIE_ID).getValue(Integer.class);
                    List<Slot> listSlotRead = new ArrayList<>();
                    for (DataSnapshot dataSnapshot : dataSnapshotRoom.child(Constants.KEY_ROOMS_LIST_SLOT).getChildren()){
                        Slot slot = dataSnapshot.getValue(Slot.class);
                        listSlotRead.add(slot);
                    }
                    Room room = new Room(id,date,listSlotRead,time,movieId);
                    listRead.add(room);
                }
                listRoomLiveDate.postValue(listRead);
                isLoading.postValue(false);
            }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
        });

    }


    public MutableLiveData<List<Room>> getListRoomLiveDate() {
        return listRoomLiveDate;
    }

    public MutableLiveData<Boolean> getIsLoading() {
        return isLoading;
    }

}
