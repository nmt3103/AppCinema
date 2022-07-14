package com.example.appcinema.viewmodel;


import android.content.Context;
import android.content.Intent;
import android.util.Patterns;
import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.example.appcinema.utilities.Constants;
import com.example.appcinema.utilities.PreferenceManager;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignInVM extends ViewModel {
    public MutableLiveData<String> email;
    public MutableLiveData<String> password;
//    public MutableLiveData<Void> showLoading;
//    public MutableLiveData<Void> hideLoading;
    public MutableLiveData<Boolean> isLoading;
    public MutableLiveData<String> showErrorMessage;
    public MutableLiveData<Boolean> isValid;

    public SignInVM() {
        email = new MutableLiveData<>();
        password = new MutableLiveData<>() ;
        isLoading = new MutableLiveData<>();
//        showLoading = new MutableLiveData<>();
//        hideLoading = new MutableLiveData<>();
        showErrorMessage = new MutableLiveData<>();
        isValid = new MutableLiveData<>();
    }


    public void checkFireBase(String emailFire,String passFire,Context context){
        isLoading.postValue(true);
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection(Constants.KEY_COLLECTION_USERS)
                .whereEqualTo(Constants.KEY_EMAIL,emailFire)
                .whereEqualTo(Constants.KEY_PASSWORD,passFire)
                .get()
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful() && task.getResult() != null
                            && task.getResult().getDocuments().size() > 0){
                        DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                        PreferenceManager preferenceManager = new PreferenceManager(context);
                        preferenceManager.putBoolean(Constants.KEY_IS_SIGNED_IN,true);
                        preferenceManager.putString(Constants.KEY_USER_ID, documentSnapshot.getId());
                        preferenceManager.putString(Constants.KEY_NAME,documentSnapshot.getString(Constants.KEY_NAME));
                        preferenceManager.putString(Constants.KEY_IMAGE,documentSnapshot.getString(Constants.KEY_IMAGE));
//                        userSuccess.setImage(documentSnapshot.getString(Constants.KEY_IMAGE));
//                        userSuccess.setEmail(emailFire);
//                        userSuccess.setPassword(passFire);
//                        userSuccess.setName(documentSnapshot.getString(Constants.KEY_NAME));
                        isValid.postValue(true);
                    } else {
                        isValid.postValue(false);
                        showErrorMessage.setValue("Wrong email or password");
                    }
                    isLoading.postValue(false);
                });
    }


    public void checkValidSignInDetails(String emailIn, String passIn, Context context) {

        if (emailIn.trim().isEmpty()){
            showErrorMessage.postValue("Enter email");
            isValid.postValue(false);
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailIn).matches()){
            showErrorMessage.postValue("Enter valid email");
            isValid.postValue(false);
        } else if (passIn.trim().isEmpty()){
            showErrorMessage.setValue("Enter password");
            isValid.postValue(false);
        } else{
            checkFireBase(emailIn,passIn,context);
        }
    }

//    private void setIsLoading(boolean loading){
//        if (loading){
//            showLoading.postValue(null);
//        } else {
//            hideLoading.postValue(null);
//        }
//    }

    public MutableLiveData<String> getShowErrorMessage() {
        return showErrorMessage;
    }

//    public MutableLiveData<Void> getShowLoading() {
//        return showLoading;
//    }
//
//    public MutableLiveData<Void> getHideLoading() {
//        return hideLoading;
//    }


    public MutableLiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public MutableLiveData<Boolean> getIsValid() {
        return isValid;
    }
}
