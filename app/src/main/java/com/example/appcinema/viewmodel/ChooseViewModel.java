package com.example.appcinema.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.appcinema.model.Room;
import com.example.appcinema.model.Slot;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChooseViewModel extends ViewModel {
    private MutableLiveData<List<Room>> listRoomLiveDate;
    private MutableLiveData<List<Slot>> listSlot;
    private MutableLiveData<Slot> slotTest;
    private MutableLiveData<String> testFire;
    private MutableLiveData<List<String>> listTest;




    public ChooseViewModel() {
        listRoomLiveDate = new MutableLiveData<>();
        listSlot = new MutableLiveData<>();
        slotTest = new MutableLiveData<>();
        testFire = new MutableLiveData<>();
        listTest = new MutableLiveData<>();
        readListRoomFromFirebase();
    }

    private void readListRoomFromFirebase() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        List<List<Slot>> list = new ArrayList<>();

            DatabaseReference myRef = database.getReference("listRoom");
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    List<Room> listRead = new ArrayList<>();
                    for (DataSnapshot dataSnapshotRoom : snapshot.getChildren()){
                        String time = dataSnapshotRoom.child("time").getValue(String.class);
                        String date = dataSnapshotRoom.child("date").getValue(String.class);
                        List<Slot> listSlotRead = new ArrayList<>();
                        for (DataSnapshot dataSnapshot : dataSnapshotRoom.child("listSlot").getChildren()){
                           Slot slot = dataSnapshot.getValue(Slot.class);
                           listSlotRead.add(slot);
                        }
                        Room room = new Room(date,listSlotRead,time);
                        listRead.add(room);
                    }
                    listRoomLiveDate.postValue(listRead);



//                    List<Slot> list1 = new ArrayList<>();//"listRoom/Room1/listSlot"
//                    for (DataSnapshot dataSnapshot : snapshot.getChildren()){
//                        Slot test = dataSnapshot.getValue(Slot.class);
//                        list1.add(test);
//                    }
//
//                    listSlot.postValue(list1);


//                        String test = snapshot.child("name").getValue(String.class); "listRoom/Room1/listSlot/Slot1"
//                        testFire.postValue(test);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

    }


    public MutableLiveData<List<Room>> getListRoomLiveDate() {
        return listRoomLiveDate;
    }


    public MutableLiveData<List<Slot>> getListSlot() {
        return listSlot;
    }

    public MutableLiveData<Slot> getSlotTest() {
        return slotTest;
    }

    public MutableLiveData<String> getTestFire() {
        return testFire;
    }

    public MutableLiveData<List<String>> getListTest() {
        return listTest;
    }
}
