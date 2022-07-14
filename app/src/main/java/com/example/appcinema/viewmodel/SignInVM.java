package com.example.appcinema.viewmodel;


import android.content.Intent;
import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.appcinema.activities.FirstActivity;
import com.example.appcinema.model.User;
import com.example.appcinema.utilities.Constants;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignInVM extends ViewModel {
    public MutableLiveData<String> email = new MutableLiveData<>();
    public MutableLiveData<String> password = new MutableLiveData<>();

    private MutableLiveData<User> userLogin;

    public MutableLiveData<User> getUserLogin() {
        if (userLogin == null){
            userLogin = new MutableLiveData<>();
        }


        return userLogin;
    }
    public void onClickButtonLogin(View view){
        User user = new User();
//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//
//        db.collection(Constants.KEY_COLLECTION_USERS)
//                .whereEqualTo(Constants.KEY_EMAIL,edEmail.getText().toString())
//                .whereEqualTo(Constants.KEY_PASSWORD,edPassword.getText().toString())
//                .get()
//                .addOnCompleteListener(task -> {
//                    if (task.isSuccessful() && task.getResult() != null
//                            && task.getResult().getDocuments().size() > 0){
//                        DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
//                        preferenceManager.putBoolean(Constants.KEY_IS_SIGNED_IN,true);
//                        preferenceManager.putString(Constants.KEY_USER_ID, documentSnapshot.getId());
//                        preferenceManager.putString(Constants.KEY_NAME,documentSnapshot.getString(Constants.KEY_NAME));
//                        preferenceManager.putString(Constants.KEY_IMAGE,documentSnapshot.getString(Constants.KEY_IMAGE));
//                        Intent intent = new Intent(getApplicationContext(), FirstActivity.class);
//                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                        startActivity(intent);
//                    } else{
//                        loading(false);
//                        showToast("Unable to sign in");
//                    }
//                });

        userLogin.setValue(user);
    }

    public void onClickTextRegister(View view){

    }
}
