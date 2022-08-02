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
    public void genQrImg(){
//        QRGEncoder qrgEncoder = new QRGEncoder(inputValue, null, QRGContents.Type.TEXT, smallerDimension);
//        qrgEncoder.setColorBlack(Color.RED);
//        qrgEncoder.setColorWhite(Color.BLUE);
//        try {
//            // Getting QR-Code as Bitmap
//            bitmap = qrgEncoder.getBitmap();
//            // Setting Bitmap to ImageView
//            qrImage.setImageBitmap(bitmap);
//        } catch (WriterException e) {
//            Log.v(TAG, e.toString());
//        }
    }
    public void addOrder(Order order){

        Map<String, Object> orderAdd = new HashMap<>();
        orderAdd.put("id", order.getId());
        orderAdd.put("movieId", order.getMovie().getId());
        orderAdd.put("dateCrate", order.getDate());
        orderAdd.put("Location",order.getLocation());
        orderAdd.put("price",order.getPrice());
        orderAdd.put("Slot",order.getSlot());
        orderAdd.put("imgQr",order.getImgQr());
        FirebaseFirestore db = FirebaseFirestore.getInstance();
// Add a new document with a generated ID
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
