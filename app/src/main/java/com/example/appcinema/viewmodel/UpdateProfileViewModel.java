package com.example.appcinema.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.util.Patterns;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.appcinema.model.User;
import com.example.appcinema.utilities.Constants;
import com.example.appcinema.utilities.PreferenceManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class UpdateProfileViewModel extends ViewModel {
    private MutableLiveData<User> userLiveData;
    private MutableLiveData<Boolean> isLoading;
    private MutableLiveData<String> showErrorMessage;
    private MutableLiveData<Boolean> isValid;

    public UpdateProfileViewModel() {
        userLiveData = new MutableLiveData<>();
        isLoading = new MutableLiveData<>();
        showErrorMessage = new MutableLiveData<>();
        isValid = new MutableLiveData<>();
    }

    public void getDataUser(Context context){
        PreferenceManager preferenceManager = new PreferenceManager(context);
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection(Constants.KEY_COLLECTION_USERS).document(
                        preferenceManager.getString(Constants.KEY_USER_ID)
                ).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful() && task.getResult() != null){
                    DocumentSnapshot documentSnapshot = task.getResult();
                    User user = new User();
                    user.setName(documentSnapshot.getString(Constants.KEY_NAME));
                    user.setImage(documentSnapshot.getString(Constants.KEY_IMAGE));
                    user.setEmail(documentSnapshot.getString(Constants.KEY_EMAIL));
                    user.setPassword(documentSnapshot.getString(Constants.KEY_PASSWORD));
                    userLiveData.postValue(user);
                }
            }
        });

    }
    public void updateUser(Context context,User user){
        isLoading.postValue(true);
        PreferenceManager preferenceManager = new PreferenceManager(context);
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        DocumentReference documentReference =
                database.collection(Constants.KEY_COLLECTION_USERS).document(
                        preferenceManager.getString(Constants.KEY_USER_ID)
                );
        HashMap<String, Object> userUpdate = new HashMap<>();
        userUpdate.put(Constants.KEY_NAME,user.getName());
        userUpdate.put(Constants.KEY_EMAIL, user.getEmail());
        userUpdate.put(Constants.KEY_PASSWORD, user.getPassword());
        userUpdate.put(Constants.KEY_IMAGE,user.getImage());
        documentReference.update(userUpdate)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        isLoading.postValue(false);
                        isValid.postValue(true);
                        PreferenceManager preferenceManager1 = new PreferenceManager(context);
                        preferenceManager1.putString(Constants.KEY_NAME, user.getName());
                        preferenceManager1.putString(Constants.KEY_IMAGE, user.getImage());
                        preferenceManager1.putString(Constants.KEY_EMAIL, user.getEmail());
                        userLiveData.postValue(user);
                    }
                })
                .addOnFailureListener(exception ->  {
                    isValid.postValue(false);
                    showErrorMessage.postValue(exception.getMessage());
                });


    }

    public void checkIsValid(String imgIn, String nameIn, String emailIn, String passIn, String passConfirm, Context context){
        if (imgIn == null){
            showErrorMessage.postValue("Select profile image");
            isValid.postValue(false);
        } else if (nameIn.trim().isEmpty()){
            showErrorMessage.postValue("Enter Name");
            isValid.postValue(false);
        } else if (emailIn.trim().isEmpty()){
            showErrorMessage.postValue("Enter Email");
            isValid.postValue(false);
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailIn).matches()){
            showErrorMessage.postValue("Enter valid Email");
            isValid.postValue(false);
        } else if (passIn.trim().isEmpty()){
            showErrorMessage.postValue("Enter Password");
            isValid.postValue(false);
        } else if (passConfirm.trim().isEmpty()){
            showErrorMessage.postValue("Confirm your password");
            isValid.postValue(false);
        } else if (!passIn.equals(passConfirm)){
            showErrorMessage.postValue("Password & confirm password must be the same ");
            isValid.postValue(false);
        } else {
            User user = new User(nameIn,imgIn,emailIn,passIn);
            updateUser(context,user);
        }
    }

    public MutableLiveData<User> getUserLiveData() {
        return userLiveData;
    }

    public MutableLiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public MutableLiveData<String> getShowErrorMessage() {
        return showErrorMessage;
    }

    public MutableLiveData<Boolean> getIsValid() {
        return isValid;
    }
}
