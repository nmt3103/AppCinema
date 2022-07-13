package com.example.appcinema.viewmodel;

import android.content.Intent;
import android.util.Patterns;
import android.widget.Toast;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.Observable;
import androidx.databinding.ObservableField;

import com.example.appcinema.BR;
import com.example.appcinema.activities.FirstActivity;
import com.example.appcinema.utilities.Constants;
import com.example.appcinema.utilities.PreferenceManager;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignInVM extends BaseObservable {
    private String email;
    private String password;
    private PreferenceManager preferenceManager;

    public ObservableField<Boolean> loading = new ObservableField<>();

    @Bindable
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        notifyPropertyChanged(BR.email);
    }

    @Bindable
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        notifyPropertyChanged(BR.password);
    }

    public void onClickButtonLogin(){
//        loading.set(true);
//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//
//        db.collection(Constants.KEY_COLLECTION_USERS)
//                .whereEqualTo(Constants.KEY_EMAIL,email)
//                .whereEqualTo(Constants.KEY_PASSWORD,password)
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
//                        loading.set(false);
//                        showToast("Unable to sign in");
//                    }
//                });

    }

//    private boolean isValidSignInDetails() {
//        if (email.trim().isEmpty()){
//            showToast("Enter email");
//            return false;
//        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
//            showToast("Enter valid email");
//            return false;
//        } else if (email.trim().isEmpty()){
//            showToast("Enter password");
//            return false;
//        } else {
//            return true;
//        }
//    }
//    private void showToast(String message){
//        Toast.makeText(get, message, Toast.LENGTH_SHORT).show();
//    }

    public void onClickTextRegister(){

    }
}
