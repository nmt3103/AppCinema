package com.example.appcinema.viewmodel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.appcinema.model.Room;
import com.example.appcinema.model.Slot;
import com.example.appcinema.utilities.Constants;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChooseViewModel extends ViewModel {
    private MutableLiveData<Room> listRoomLiveDate;
    private MutableLiveData<Boolean> isLoading;

    public ChooseViewModel() {
        listRoomLiveDate = new MutableLiveData<>();
        isLoading = new MutableLiveData<>();

    }

    public void readListRoomFromFirebase(String location,String dateIn,String timeIn,int idIn) {
        isLoading.postValue(true);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        listRoomLiveDate = new MutableLiveData<>();

        DatabaseReference myRef = database.getReference(location + "/" + Constants.KEY_COLLECTION_ROOMS);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshotRoom : snapshot.getChildren()){
                    Long movieId = dataSnapshotRoom.child(Constants.KEY_ROOMS_MOVIE_ID).getValue(Long.class);

                    String time = dataSnapshotRoom.child(Constants.KEY_ROOMS_TIME).getValue(String.class);
                    String date = dataSnapshotRoom.child(Constants.KEY_ROOMS_DATE).getValue(String.class);
                    if (time.equals(timeIn) && date.equals(dateIn) && Long.valueOf(idIn) == movieId){
                        Long id = dataSnapshotRoom.child(Constants.KEY_ROOMS_ID).getValue(Long.class);
                        List<Slot> listSlotRead = new ArrayList<>();
                        for (DataSnapshot dataSnapshot : dataSnapshotRoom.child(Constants.KEY_ROOMS_LIST_SLOT).getChildren()){
                            Slot slot = dataSnapshot.getValue(Slot.class);
                            listSlotRead.add(slot);
                        }
                        Room room = new Room(id,date,listSlotRead,time,movieId);
                        listRoomLiveDate.postValue(room);
                        break;
                    }
                }
                isLoading.postValue(false);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


    public MutableLiveData<Room> getListRoomLiveDate() {
        return listRoomLiveDate;
    }

    public MutableLiveData<Boolean> getIsLoading() {
        return isLoading;
    }

//    isLoading.postValue(true);
//    FirebaseDatabase database = FirebaseDatabase.getInstance();
//
//    DatabaseReference myRef = database.getReference(location + "/" + Constants.KEY_COLLECTION_ROOMS);
//        myRef.addValueEventListener(new ValueEventListener() {
//        @Override
//        public void onDataChange(@NonNull DataSnapshot snapshot) {
//            List<Room> listRead = new ArrayList<>();
//            for (DataSnapshot dataSnapshotRoom : snapshot.getChildren()){
//                Long id = dataSnapshotRoom.child(Constants.KEY_ROOMS_ID).getValue(Long.class);
//                String time = dataSnapshotRoom.child(Constants.KEY_ROOMS_TIME).getValue(String.class);
//                String date = dataSnapshotRoom.child(Constants.KEY_ROOMS_DATE).getValue(String.class);
//                Long movieId = dataSnapshotRoom.child(Constants.KEY_ROOMS_MOVIE_ID).getValue(Long.class);
//                List<Slot> listSlotRead = new ArrayList<>();
//                for (DataSnapshot dataSnapshot : dataSnapshotRoom.child(Constants.KEY_ROOMS_LIST_SLOT).getChildren()){
//                    Slot slot = dataSnapshot.getValue(Slot.class);
//                    listSlotRead.add(slot);
//                }
//                Room room = new Room(id,date,listSlotRead,time,movieId);
//                listRead.add(room);
//            }
//            listRoomLiveDate.postValue(listRead);
//            isLoading.postValue(false);
//        }
//
//        @Override
//        public void onCancelled(@NonNull DatabaseError error) {
//
//        }
//    });




//     .addChildEventListener(new ChildEventListener() {
//        @Override
//        public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//            for (DataSnapshot dataSnapshotRoom : snapshot.getChildren()){
//                String time = dataSnapshotRoom.child(Constants.KEY_ROOMS_TIME).getValue(String.class);
//                String date = dataSnapshotRoom.child(Constants.KEY_ROOMS_DATE).getValue(String.class);
//                if (time.trim().equals(timeIn) && date.trim().equals(dateIn)  ){
//                    Long id = dataSnapshotRoom.child(Constants.KEY_ROOMS_ID).getValue(Long.class);
//                    Long movieId = dataSnapshotRoom.child(Constants.KEY_ROOMS_MOVIE_ID).getValue(Long.class);
//                    List<Slot> listSlotRead = new ArrayList<>();
//                    for (DataSnapshot dataSnapshot : dataSnapshotRoom.child(Constants.KEY_ROOMS_LIST_SLOT).getChildren()){
//                        Slot slot = dataSnapshot.getValue(Slot.class);
//                        listSlotRead.add(slot);
//                    }
//                    Room room = new Room(id,date,listSlotRead,time,movieId);
//                    listRoomLiveDate.postValue(room);
//                    isLoading.postValue(false);
//                    break;
//                }
//            }
//
//
//        }
//
//        @Override
//        public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//
//        }
//
//        @Override
//        public void onChildRemoved(@NonNull DataSnapshot snapshot) {
//
//        }
//
//        @Override
//        public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//
//        }
//
//        @Override
//        public void onCancelled(@NonNull DatabaseError error) {
//
//        }
//    });
}
