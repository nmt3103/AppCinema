package com.example.appcinema.viewmodel;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.google.firebase.database.ChildEventListener;
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
    private MutableLiveData<List<Order>> listOrderLiveData;


    private MutableLiveData<List<Movie>> listMovieLive;
    private MutableLiveData<List<Room>> listRoomLive;

    private MutableLiveData<List<Order>> listOrderLiveDateMain;

    public int size = 0;



    public TicketViewModel() {
        listOrderLiveDateMain = new MutableLiveData<>();

        listOrderLiveData = new MutableLiveData<>();

        listRoomLive = new MutableLiveData<>();
        listMovieLive = new MutableLiveData<>();

    }

    public void initData(String idCustomer) {


        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection(Constants.KEY_COLLECTION_MOVIES)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            List<Movie> movies = new ArrayList<>();
                            QuerySnapshot snapshot = task.getResult();
                            for (DocumentSnapshot doc : snapshot){
                                Movie movie = new Movie();
                                movie.setId(Integer.parseInt(doc.get(Constants.KEY_MOVIES_ID).toString()));
                                movie.setImgBig(doc.get(Constants.KEY_MOVIES_IMGPAGER).toString());
                                movie.setImgPoster(doc.get(Constants.KEY_MOVIES_IMGPOSTER).toString());
                                movie.setImgTeaster(doc.get(Constants.KEY_MOVIES_IMGTRAILER).toString());
                                movie.setCate(doc.get(Constants.KEY_MOVIES_CATEGORY).toString());
                                movie.setLinkMusic(doc.get(Constants.KEY_MOVIES_LINKSONG).toString());
                                movie.setLinkTrailer(doc.get(Constants.KEY_MOVIES_LINKTRAILER).toString());
                                movie.setReview(doc.get(Constants.KEY_MOVIES_REVIEW).toString());
                                movie.setTime(doc.get(Constants.KEY_MOVIES_TIME).toString());
                                movie.setName(doc.get(Constants.KEY_MOVIES_NAME).toString());
                                movie.setRate(Float.parseFloat(doc.get(Constants.KEY_MOVIES_RATE).toString()));
                                List<Long> listActor = (List<Long>) doc.get(Constants.KEY_MOVIES_LISTACTOR);
                                movie.setListIdActor(listActor);
                                movies.add(movie);
                            }
                            listMovieLive.postValue(movies);
                        }
                    }
                });

        listMovieLive.observeForever(new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                database.collection(Constants.KEY_COLLECTION_ORDERS)
                        .whereEqualTo(Constants.KEY_ORDERS_CUSTOMER_ID, idCustomer)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()){
                                    QuerySnapshot snapshot = task.getResult();
                                    List<Order> listAll = new ArrayList<>();

                                    for (QueryDocumentSnapshot doc : snapshot){
                                        Order order = new Order();
                                        for (Movie movie : movies){
                                            if (Integer.parseInt(doc.get(Constants.KEY_ORDERS_MOVIE_ID).toString()) == movie.getId()){
                                                order.setMovie(movie);
                                                break;
                                            }
                                        }
                                        Room room = new Room();
                                        room.setId(Long.parseLong(doc.get(Constants.KEY_ORDERS_ROOM_ID).toString()));
                                        order.setRoom(room);
                                        order.setPrice(Integer.parseInt(doc.get(Constants.KEY_ORDERS_PRICE).toString()));
                                        order.setId(doc.getId());
                                        order.setImgQr(doc.get(Constants.KEY_ORDERS_IMGQR).toString());
                                        order.setLocation(doc.get(Constants.KEY_ORDERS_LOCATION).toString());
                                        order.setSlot(doc.get(Constants.KEY_ORDERS_SLOT).toString());
                                        order.setCustomerId(doc.get(Constants.KEY_ORDERS_CUSTOMER_ID).toString());
                                        order.setDate(doc.get(Constants.KEY_ORDERS_DATE_CREATE).toString());
                                        listAll.add(order);
                                    }
                                    size = listAll.size();
                                    listOrderLiveData.postValue(listAll);
                                }
                            }
                        });
            }
        });



        listOrderLiveData.observeForever(new Observer<List<Order>>() {
            @Override
            public void onChanged(List<Order> orders) {
                List<Order> orders1 = new ArrayList<>();
                for (Order order : orders){

                    FirebaseDatabase dataReal = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = dataReal.getReference(order.getLocation().trim() + "/" + Constants.KEY_COLLECTION_ROOMS);
                    myRef.orderByChild(Constants.KEY_ROOMS_ID).equalTo(order.getRoom().getId()).addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                            Room room = new Room();
                            room.setId((Long) snapshot.child(Constants.KEY_ROOMS_ID).getValue());
                            room.setDate(snapshot.child(Constants.KEY_ROOMS_DATE).getValue().toString());
                            room.setTime(snapshot.child(Constants.KEY_ROOMS_TIME).getValue().toString());
                            room.setMovieId((Long) snapshot.child(Constants.KEY_ROOMS_MOVIE_ID).getValue());
                            List<Slot> listSlotRead = new ArrayList<>();
                            for (DataSnapshot dataSnapshot : snapshot.child(Constants.KEY_ROOMS_LIST_SLOT).getChildren()){
                                Slot slot = dataSnapshot.getValue(Slot.class);
                                listSlotRead.add(slot);
                            }
                            room.setListSlot(listSlotRead);
                            order.setRoom(room);
                            orders1.add(order);
                            Log.d("onChildAdded: ","1");
                            if(orders1.size() == size){
                                Log.d("onChildAdded", "2");
                                listOrderLiveDateMain.postValue(orders1);
                            }
                        }

                        @Override
                        public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                        }

                        @Override
                        public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                        }

                        @Override
                        public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });//search success
                }


                //

            }
        });


//        listOrderLiveData.observeForever(new Observer<List<Order>>() {
//            @Override
//            public void onChanged(List<Order> orders) {
//                for (Order order : orders){
//                    database.collection(Constants.KEY_COLLECTION_MOVIES)
//                        .whereEqualTo(Constants.KEY_MOVIES_ID,order.getMovie().getId())
//                        .get()
//                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                            @Override
//                            public void onComplete(@NonNull Task<QuerySnapshot> taskMovie) {
//                                if (taskMovie.isSuccessful() && taskMovie.getResult() != null
//                                        && taskMovie.getResult().getDocuments().size() > 0){
//                                    DocumentSnapshot documentSnapshot = taskMovie.getResult().getDocuments().get(0);
//                                    Movie movie = new Movie();
//
//                                    movie.setImgBig(documentSnapshot.getString(Constants.KEY_MOVIES_IMGPAGER));
//                                    movie.setImgPoster(documentSnapshot.getString(Constants.KEY_MOVIES_IMGPOSTER));
//                                    movie.setImgTeaster(documentSnapshot.getString(Constants.KEY_MOVIES_IMGTRAILER));
//                                    movie.setCate(documentSnapshot.getString(Constants.KEY_MOVIES_CATEGORY));
//                                    movie.setLinkMusic(documentSnapshot.getString(Constants.KEY_MOVIES_LINKSONG));
//                                    movie.setLinkTrailer(documentSnapshot.getString(Constants.KEY_MOVIES_LINKTRAILER));
//                                    movie.setReview(documentSnapshot.getString(Constants.KEY_MOVIES_REVIEW));
//                                    movie.setTime(documentSnapshot.getString(Constants.KEY_MOVIES_TIME));
//                                    movie.setName(documentSnapshot.getString(Constants.KEY_MOVIES_NAME));
//                                    movie.setRate(Float.parseFloat(documentSnapshot.get(Constants.KEY_MOVIES_RATE).toString()));
//                                    List<Long> listActor = (List<Long>) documentSnapshot.get(Constants.KEY_MOVIES_LISTACTOR);
//                                    movie.setListIdActor(listActor);
//                                    order.setMovie(movie);
//                                }
//                            }
//                        }); /// sea
//                }
//            }
//        });



//
//                    database.collection(Constants.KEY_COLLECTION_MOVIES)
//                            .whereEqualTo(Constants.KEY_MOVIES_ID,1)
//                            .get()
//                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                                @Override
//                                public void onComplete(@NonNull Task<QuerySnapshot> taskMovie) {
//                                    if (taskMovie.isSuccessful() && taskMovie.getResult() != null
//                                            && taskMovie.getResult().getDocuments().size() > 0){
//                                        DocumentSnapshot documentSnapshot = taskMovie.getResult().getDocuments().get(0);
//                                        Movie movie = new Movie();
//
//                                        movie.setImgBig(documentSnapshot.getString(Constants.KEY_MOVIES_IMGPAGER));
//                                        movie.setImgPoster(documentSnapshot.getString(Constants.KEY_MOVIES_IMGPOSTER));
//                                        movie.setImgTeaster(documentSnapshot.getString(Constants.KEY_MOVIES_IMGTRAILER));
//                                        movie.setCate(documentSnapshot.getString(Constants.KEY_MOVIES_CATEGORY));
//                                        movie.setLinkMusic(documentSnapshot.getString(Constants.KEY_MOVIES_LINKSONG));
//                                        movie.setLinkTrailer(documentSnapshot.getString(Constants.KEY_MOVIES_LINKTRAILER));
//                                        movie.setReview(documentSnapshot.getString(Constants.KEY_MOVIES_REVIEW));
//                                        movie.setTime(documentSnapshot.getString(Constants.KEY_MOVIES_TIME));
//                                        movie.setName(documentSnapshot.getString(Constants.KEY_MOVIES_NAME));
//                                        movie.setRate(Float.parseFloat(documentSnapshot.get(Constants.KEY_MOVIES_RATE).toString()));
//                                        List<Long> listActor = (List<Long>) documentSnapshot.get(Constants.KEY_MOVIES_LISTACTOR);
//                                        movie.setListIdActor(listActor);
//                                        movieMutableLiveData.postValue(movie);
//
//                                    }
//
//                                }
//                            }); /// search movie success
//
//            FirebaseDatabase dataReal = FirebaseDatabase.getInstance();
//            DatabaseReference myRef = dataReal.getReference("Location1" + "/" + Constants.KEY_COLLECTION_ROOMS);
//            myRef.orderByChild(Constants.KEY_ROOMS_ID).equalTo(1).addChildEventListener(new ChildEventListener() {
//                @Override
//                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//                    Room room = new Room();
//                    room.setId((Long) snapshot.child(Constants.KEY_ROOMS_ID).getValue());
//                    room.setDate(snapshot.child(Constants.KEY_ROOMS_DATE).getValue().toString());
//                    room.setTime(snapshot.child(Constants.KEY_ROOMS_TIME).getValue().toString());
//                    room.setMovieId((Long) snapshot.child(Constants.KEY_ROOMS_MOVIE_ID).getValue());
//                    List<Slot> listSlotRead = new ArrayList<>();
//                    for (DataSnapshot dataSnapshot : snapshot.child(Constants.KEY_ROOMS_LIST_SLOT).getChildren()){
//                        Slot slot = dataSnapshot.getValue(Slot.class);
//                        listSlotRead.add(slot);
//                    }
//                    room.setListSlot(listSlotRead);
//                    roomMutableLiveData.postValue(room);
//                }
//
//                @Override
//                public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//
//                }
//
//                @Override
//                public void onChildRemoved(@NonNull DataSnapshot snapshot) {
//
//                }
//
//                @Override
//                public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//
//                }
//            });//search success




    }




//    public void initData(String idCustomer) {
//
//
//
//        FirebaseDatabase dataReal = FirebaseDatabase.getInstance();
////        for (int i =1;i<=3;i++){
//            DatabaseReference myRef = dataReal.getReference("Location1"+ "/" + Constants.KEY_COLLECTION_ROOMS);
//            myRef.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                    List<Room> listRoom = new ArrayList<>();
//                    for (DataSnapshot dataSnapshotRoom : snapshot.getChildren()){
//                        int id = dataSnapshotRoom.child(Constants.KEY_ROOMS_ID).getValue(Integer.class);
//                        String time = dataSnapshotRoom.child(Constants.KEY_ROOMS_TIME).getValue(String.class);
//                        String date = dataSnapshotRoom.child(Constants.KEY_ROOMS_DATE).getValue(String.class);
//                        int movieId = dataSnapshotRoom.child(Constants.KEY_ROOMS_MOVIE_ID).getValue(Integer.class);
//                        List<Slot> listSlotRead = new ArrayList<>();
//                        for (DataSnapshot dataSnapshot : dataSnapshotRoom.child(Constants.KEY_ROOMS_LIST_SLOT).getChildren()){
//                            Slot slot = dataSnapshot.getValue(Slot.class);
//                            listSlotRead.add(slot);
//                        }
//                        Room room = new Room(id,date,listSlotRead,time,movieId);
//                        listRoom.add(room);
//                    }
//                    listRoomLiveDate.postValue(listRoom);
//                }
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//
//                }
//            });
////        }
//
//
//
//        FirebaseFirestore database = FirebaseFirestore.getInstance();
//                    database.collection(Constants.KEY_COLLECTION_MOVIES)
//                    .get()
//                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                        @Override
//                        public void onComplete(@NonNull Task<QuerySnapshot> taskMovie) {
//                            if (taskMovie.isSuccessful() && taskMovie.getResult() != null
//                                    && taskMovie.getResult().getDocuments().size() > 0){
//                                QuerySnapshot snapshot = taskMovie.getResult();
//
//                                List<Movie> list = new ArrayList<>();
//                                for (QueryDocumentSnapshot doc : snapshot) {
//                                    Movie movie = new Movie();
//                                    movie.setId(Integer.parseInt(doc.get(Constants.KEY_MOVIES_ID).toString()));
//                                    movie.setImgBig(doc.get(Constants.KEY_MOVIES_IMGPAGER).toString());
//                                    movie.setImgPoster(doc.get(Constants.KEY_MOVIES_IMGPOSTER).toString());
//                                    movie.setImgTeaster(doc.get(Constants.KEY_MOVIES_IMGTRAILER).toString());
//                                    movie.setCate(doc.get(Constants.KEY_MOVIES_CATEGORY).toString());
//                                    movie.setLinkMusic(doc.get(Constants.KEY_MOVIES_LINKSONG).toString());
//                                    movie.setLinkTrailer(doc.get(Constants.KEY_MOVIES_LINKTRAILER).toString());
//                                    movie.setReview(doc.get(Constants.KEY_MOVIES_REVIEW).toString());
//                                    movie.setTime(doc.get(Constants.KEY_MOVIES_TIME).toString());
//                                    movie.setName(doc.get(Constants.KEY_MOVIES_NAME).toString());
//                                    movie.setRate(Float.parseFloat(doc.get(Constants.KEY_MOVIES_RATE).toString()));
//                                    List<Long> listActor = (List<Long>) doc.get(Constants.KEY_MOVIES_LISTACTOR);
//                                    movie.setListIdActor(listActor);
//                                    list.add(movie);
//
//                                }
//                                listMovieLiveDate.postValue(list);
//                            }
//
//                        }
//                    });
//
//
//        listMovieLiveDate.observeForever(new Observer<List<Movie>>() {
//            @Override
//            public void onChanged(List<Movie> movies) {
//                listRoomLiveDate.observeForever(new Observer<List<Room>>() {
//                    @Override
//                    public void onChanged(List<Room> list) {
//                        FirebaseFirestore database = FirebaseFirestore.getInstance();
//                        database.collection(Constants.KEY_COLLECTION_ORDERS)
//                                .whereEqualTo(Constants.KEY_ORDERS_CUSTOMER_ID, idCustomer)
//                                .get()
//                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                                    @Override
//                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                                        if (task.isSuccessful()){
//                                            QuerySnapshot snapshot = task.getResult();
//                                            List<Order> listAll = new ArrayList<>();
//                                            List<Order> listNew = new ArrayList<>();
//                                            List<Order> listExpire = new ArrayList<>();
//                                            for (QueryDocumentSnapshot doc : snapshot){
//                                                Order order = new Order();
//                                                for (int i = 0;i<movies.size();i++){
//                                                    if (Integer.parseInt(doc.get(Constants.KEY_ORDERS_MOVIE_ID).toString()) == movies.get(i).getId()){
//                                                        order.setMovie(movies.get(i));
//                                                        break;
//                                                    }
//                                                }
//                                                for (int j = 0;j<list.size();j++){
//                                                    if (Integer.parseInt(doc.get(Constants.KEY_ORDERS_ROOM_ID).toString()) == list.get(j).getId()){
//                                                        order.setRoom(list.get(j));
//                                                        break;
//                                                    }
//                                                }
//                                                order.setPrice(Integer.parseInt(doc.get(Constants.KEY_ORDERS_PRICE).toString()));
//                                                order.setId(doc.getId());
//                                                order.setImgQr(doc.get(Constants.KEY_ORDERS_IMGQR).toString());
//                                                order.setLocation(doc.get(Constants.KEY_ORDERS_LOCATION).toString());
//                                                order.setSlot(doc.get(Constants.KEY_ORDERS_SLOT).toString());
//                                                order.setCustomerId(doc.get(Constants.KEY_ORDERS_CUSTOMER_ID).toString());
//                                                order.setDate(doc.get(Constants.KEY_ORDERS_DATE_CREATE).toString());
//                                                SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy hh:mm",Locale.getDefault());
//                                                try {
//                                                    Date dateCheck = format.parse(order.getRoom().getDate()+" "+order.getRoom().getTime());
//                                                    if (dateCheck.before(new Date())){
//                                                        listExpire.add(order);
//                                                    } else {
//                                                        listNew.add(order);
//                                                    }
//                                                } catch (ParseException e) {
//                                                    e.printStackTrace();
//                                                }
//                                                listAll.add(order);
//                                            }
//                                            listOrderLiveDate.setValue(listAll);
//                                            listNewOrder.setValue(listNew);
//                                            listExpireOrder.setValue(listExpire);
//                                        }
//                                    }
//                                });
//
//                    }
//                });
//            }
//        });
//    }




    public MutableLiveData<List<Order>> getListOrderLiveDateMain() {
        return listOrderLiveDateMain;
    }

    public MutableLiveData<List<Order>> getListOrderLiveData() {
        return listOrderLiveData;
    }


}

