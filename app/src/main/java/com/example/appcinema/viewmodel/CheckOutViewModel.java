package com.example.appcinema.viewmodel;

import android.graphics.Color;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.appcinema.model.Order;
import com.example.appcinema.utilities.Constants;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class CheckOutViewModel extends ViewModel {
    public MutableLiveData<Boolean> isSuccess;
    public CheckOutViewModel() {
        isSuccess = new MutableLiveData<>();
    }

    public void addOrder(Order order){

        Map<String, Object> orderAdd = new HashMap<>();
        orderAdd.put(Constants.KEY_ORDERS_MOVIE_ID, order.getMovie().getId());
        orderAdd.put(Constants.KEY_ORDERS_ROOM_ID, order.getRoom().getId());
        orderAdd.put(Constants.KEY_ORDERS_CUSTOMER_ID, order.getCustomerId());
        orderAdd.put(Constants.KEY_ORDERS_DATE_CREATE, order.getDate());
        orderAdd.put(Constants.KEY_ORDERS_LOCATION,order.getLocation());
        orderAdd.put(Constants.KEY_ORDERS_PRICE,order.getPrice());
        orderAdd.put(Constants.KEY_ORDERS_SLOT,order.getSlot());
        orderAdd.put(Constants.KEY_ORDERS_IMGQR,order.getImgQr());
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection(Constants.KEY_COLLECTION_ORDERS)
                .add(orderAdd)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        isSuccess.postValue(true);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        isSuccess.postValue(false);
                    }
                });
    }

    public MutableLiveData<Boolean> getIsSuccess() {
        return isSuccess;
    }
}
