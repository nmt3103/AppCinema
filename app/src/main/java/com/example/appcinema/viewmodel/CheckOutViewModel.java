package com.example.appcinema.viewmodel;

import android.graphics.Color;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.appcinema.model.Order;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import androidmads.library.qrgenearator.QRGEncoder;

public class CheckOutViewModel extends ViewModel {
    public MutableLiveData<Boolean> isSuccess;
    public CheckOutViewModel() {
        isSuccess = new MutableLiveData<>();
    }

    public void addOrder(Order order){

        Map<String, Object> orderAdd = new HashMap<>();
        orderAdd.put("movieId", order.getMovie().getId());
        orderAdd.put("roomId", order.getRoom().getId());
        orderAdd.put("customerId", order.getCustomerId());
        orderAdd.put("dateCrate", order.getDate());
        orderAdd.put("Location",order.getLocation());
        orderAdd.put("price",order.getPrice());
        orderAdd.put("Slot",order.getSlot());
        orderAdd.put("imgQr",order.getImgQr());
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("orders")
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
