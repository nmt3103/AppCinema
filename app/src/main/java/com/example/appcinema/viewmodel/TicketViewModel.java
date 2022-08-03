package com.example.appcinema.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.appcinema.R;
import com.example.appcinema.model.Movie;
import com.example.appcinema.model.Order;
import com.example.appcinema.model.Room;
import com.example.appcinema.model.Slot;
import com.example.appcinema.utilities.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TicketViewModel extends ViewModel {
    private MutableLiveData<List<Order>> listOrderLiveDate;
    private MutableLiveData<List<Order>> listNewOrder;
    private MutableLiveData<List<Order>> listExpireOrder;
    private MutableLiveData<List<Movie>> listMovieLiveData;
    private MutableLiveData<List<Integer>> listMovieId;
    private MutableLiveData<List<Room>> listRoomLiveData;
    private int idMovie;

    public TicketViewModel() {
        listOrderLiveDate = new MutableLiveData<>();
        listNewOrder = new MutableLiveData<>();
        listExpireOrder = new MutableLiveData<>();
        listMovieLiveData = new MutableLiveData<>();
        listMovieId = new MutableLiveData<>();
        listRoomLiveData = new MutableLiveData<>();
    }


    public void initData(String name) {


        List<Order> listAll = new ArrayList<>();
        List<Order> listNew = new ArrayList<>();
        List<Order> listExpire = new ArrayList<>();

        List<Room> listRoom = new ArrayList<>();
        FirebaseDatabase dataReal = FirebaseDatabase.getInstance();
        DatabaseReference myRef = dataReal.getReference("listRoom/rednotice");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshotRoom : snapshot.getChildren()){
                    int id = dataSnapshotRoom.child("id").getValue(Integer.class);
                    String time = dataSnapshotRoom.child("time").getValue(String.class);
                    String date = dataSnapshotRoom.child("date").getValue(String.class);
                    List<Slot> listSlotRead = new ArrayList<>();
                    for (DataSnapshot dataSnapshot : dataSnapshotRoom.child("listSlot").getChildren()){
                        Slot slot = dataSnapshot.getValue(Slot.class);
                        listSlotRead.add(slot);
                    }
                    Room room = new Room(id,date,listSlotRead,time);
                    listRoom.add(room);
                }
                listRoomLiveData.postValue(listRoom);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });







        FirebaseFirestore database = FirebaseFirestore.getInstance();
                    database.collection("movies")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> taskMovie) {
                            if (taskMovie.isSuccessful() && taskMovie.getResult() != null
                                    && taskMovie.getResult().getDocuments().size() > 0){
                                QuerySnapshot snapshot = taskMovie.getResult();

                                List<Movie> list = new ArrayList<>();
                                for (QueryDocumentSnapshot doc : snapshot) {
                                    Movie movie = new Movie();
                                    movie.setId(Integer.parseInt(doc.get("id").toString()));
                                    movie.setImgBig(doc.get("imgPager").toString());
                                    movie.setImgPoster(doc.get("imgPoster").toString());
                                    movie.setImgTeaster(doc.get("imgTrailer").toString());
                                    movie.setCate(doc.get("Category").toString());
                                    movie.setLinkMusic(doc.get("LinkSong").toString());
                                    movie.setLinkTrailer(doc.get("LinkTrailer").toString());
                                    movie.setReview(doc.get("Review").toString());
                                    movie.setTime(doc.get("Time").toString());
                                    movie.setName(doc.get("name").toString());
                                    movie.setRate(Float.parseFloat(doc.get("rate").toString()));
                                    List<Integer> listActor = (List<Integer>) doc.get("ListActor");
                                    movie.setListIdActor(listActor);
                                    list.add(movie);

                                }
                                listMovieLiveData.postValue(list);
                            }

                        }
                    });


        listRoomLiveData.observeForever(new Observer<List<Room>>() {
            @Override
            public void onChanged(List<Room> list) {
                listMovieLiveData.observeForever(new Observer<List<Movie>>() {
                    @Override
                    public void onChanged(List<Movie> movies) {
                        FirebaseFirestore database = FirebaseFirestore.getInstance();
                        database.collection("tests")
                                .whereEqualTo("customerId", name)
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()){
                                            QuerySnapshot snapshot = task.getResult();
                                            List<Order> listAll = new ArrayList<>();
                                            List<Order> listNew = new ArrayList<>();
                                            List<Order> listExpire = new ArrayList<>();
                                            for (QueryDocumentSnapshot doc : snapshot){
                                                Order order = new Order();
                                                for (int i = 0;i<movies.size();i++){
                                                    if (Integer.parseInt(doc.get("movieId").toString()) == movies.get(i).getId()){
                                                        order.setMovie(movies.get(i));
                                                        break;
                                                    }
                                                }
                                                for (int j = 0;j<list.size();j++){
                                                    if (Integer.parseInt(doc.get("roomId").toString()) == list.get(j).getId()){
                                                        order.setRoom(list.get(j));
                                                        break;
                                                    }
                                                }
//                                                    List<Slot> listSlot = new ArrayList<>();
//                                                Room room = new Room(Integer.parseInt(doc.get("roomId").toString()),"05-Aug-2022",listSlot,"9 : 30");
//                                                order.setRoom(room);
                                                order.setPrice(Integer.parseInt(doc.get("price").toString()));
                                                order.setId(doc.getId());
                                                order.setImgQr(doc.get("imgQr").toString());
                                                order.setLocation(doc.get("Location").toString());
                                                order.setSlot(doc.get("Slot").toString());
                                                order.setCustomerId(doc.get("customerId").toString());
                                                order.setDate(doc.get("dateCrate").toString());
                                                listAll.add(order);
                                            }
                                            listOrderLiveDate.setValue(listAll);
                                            listNewOrder.setValue(listAll);
                                            listExpireOrder.setValue(listAll);
                                        }
                                    }
                                });
                    }
                });

            }
        });








    }


    public MutableLiveData<List<Order>> getListOrderLiveDate() {
        return listOrderLiveDate;
    }

    public MutableLiveData<List<Order>> getListNewOrder() {
        return listNewOrder;
    }

    public MutableLiveData<List<Order>> getListExpireOrder() {
        return listExpireOrder;
    }
}
