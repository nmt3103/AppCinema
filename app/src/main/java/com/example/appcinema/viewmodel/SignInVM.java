package com.example.appcinema.viewmodel;


import android.content.Context;
import android.util.Patterns;


import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.example.appcinema.utilities.Constants;
import com.example.appcinema.utilities.PreferenceManager;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignInVM extends ViewModel {
    public MutableLiveData<String> email;
    public MutableLiveData<String> password;
    public MutableLiveData<Boolean> isLoading;
    public MutableLiveData<String> showErrorMessage;
    public MutableLiveData<Boolean> isValid;

    public SignInVM() {
        email = new MutableLiveData<>();
        password = new MutableLiveData<>() ;
        isLoading = new MutableLiveData<>();
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
                        preferenceManager.putString(Constants.KEY_EMAIL,documentSnapshot.getString(Constants.KEY_EMAIL));
                        isValid.postValue(true);
                    } else {

                        isValid.postValue(false);
                        isLoading.postValue(false);
                        showErrorMessage.setValue("Wrong email or password");
                    }
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

    public MutableLiveData<String> getShowErrorMessage() {
        return showErrorMessage;
    }

    public MutableLiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public MutableLiveData<Boolean> getIsValid() {
        return isValid;
    }
}
