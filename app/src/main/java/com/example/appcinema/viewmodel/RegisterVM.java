package com.example.appcinema.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.util.Patterns;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.appcinema.activities.FirstActivity;
import com.example.appcinema.model.User;
import com.example.appcinema.utilities.Constants;
import com.example.appcinema.utilities.PreferenceManager;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class RegisterVM extends ViewModel {
    public MutableLiveData<String> nameIn;
    public MutableLiveData<String> email;
    public MutableLiveData<String> password;
    public MutableLiveData<String> confirmPass;
    public MutableLiveData<Boolean> isLoading;
    public MutableLiveData<String> showErrorMessage;
    public MutableLiveData<Boolean> isValid;

    public RegisterVM() {
        nameIn = new MutableLiveData<>();
        email = new MutableLiveData<>();
        password = new MutableLiveData<>();
        confirmPass = new MutableLiveData<>();
        isLoading = new MutableLiveData<>();
        showErrorMessage = new MutableLiveData<>();
        isValid = new MutableLiveData<>();
    }

    private void signUp(User userIn,Context context){
        isLoading.postValue(true);
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        HashMap<String, Object> user = new HashMap<>();
        user.put(Constants.KEY_NAME,userIn.getName());
        user.put(Constants.KEY_EMAIL, userIn.getEmail());
        user.put(Constants.KEY_PASSWORD, userIn.getPassword());
        user.put(Constants.KEY_IMAGE,userIn.getImage());
        database.collection(Constants.KEY_COLLECTION_USERS)
                .add(user)
                .addOnSuccessListener(documentReference -> {
                    isLoading.postValue(false);
                    PreferenceManager preferenceManager = new PreferenceManager(context);
                    preferenceManager.putBoolean(Constants.KEY_IS_SIGNED_IN,true);
                    preferenceManager.putString(Constants.KEY_USER_ID,documentReference.getId());
                    preferenceManager.putString(Constants.KEY_NAME, userIn.getName());
                    preferenceManager.putString(Constants.KEY_IMAGE, userIn.getImage());
                    isValid.postValue(true);
                })
                .addOnFailureListener(exception -> {
                    isValid.postValue(false);
                    showErrorMessage.postValue(exception.getMessage());
                });
    };

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
            signUp(user,context);
        }
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
