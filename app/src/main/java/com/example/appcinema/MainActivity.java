package com.example.appcinema;


import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appcinema.activities.FirstActivity;
import com.example.appcinema.activities.RegisterActivity;

import com.example.appcinema.databinding.ActivityMainBinding;
import com.example.appcinema.model.User;
import com.example.appcinema.utilities.Constants;
import com.example.appcinema.utilities.PreferenceManager;
import com.example.appcinema.viewmodel.SignInVM;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {
    private SignInVM signInVM;
    private PreferenceManager preferenceManager;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        signInVM = new ViewModelProvider(this).get(SignInVM.class);
        binding = DataBindingUtil.setContentView(MainActivity.this,R.layout.activity_main);
        preferenceManager = new PreferenceManager(getApplicationContext());
        if (preferenceManager.getBoolean(Constants.KEY_IS_SIGNED_IN)){
            Intent intent = new Intent(getApplicationContext(), FirstActivity.class);
            startActivity(intent);
            finish();
        }

        binding.setLifecycleOwner(this);
        setListeners();

        observerViewModel();
    }

    private void observerViewModel() {
        signInVM.getShowErrorMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                showToast(s);
            }
        });
//        signInVM.getShowLoading().observe(this, new Observer<Void>() {
//            @Override
//            public void onChanged(Void unused) {
//                binding.progressBar.setVisibility(View.VISIBLE);
//                binding.buttonSignIn.setVisibility(View.INVISIBLE);
//            }
//        });
//
//        signInVM.getHideLoading().observe(this, new Observer<Void>() {
//            @Override
//            public void onChanged(Void unused) {
//                binding.progressBar.setVisibility(View.INVISIBLE);
//                binding.buttonSignIn.setVisibility(View.VISIBLE);
//            }
//        });

        signInVM.getIsLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean){
                    binding.progressBar.setVisibility(View.VISIBLE);
                    binding.buttonSignIn.setVisibility(View.INVISIBLE);
                } else {
                    binding.progressBar.setVisibility(View.INVISIBLE);
                    binding.buttonSignIn.setVisibility(View.VISIBLE);
                }
            }
        });


    }

    private void setListeners() {
        binding.textCreateNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
        });

        binding.buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInVM.checkValidSignInDetails(binding.inputEmail.getText().toString(),binding.inputPassword.getText().toString(), getApplicationContext());
                signInVM.getIsValid().observe(MainActivity.this, new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean isValid) {
                        if (isValid == true){
                            Intent intent = new Intent(getApplicationContext(), FirstActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }
                    }
                });

            }
        });
    }
    private void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

//    private void signIn() {
//        loading(true);
//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//
//        db.collection(Constants.KEY_COLLECTION_USERS)
//                .whereEqualTo(Constants.KEY_EMAIL,binding.inputEmail.getText().toString())
//                .whereEqualTo(Constants.KEY_PASSWORD,binding.inputPassword.getText().toString())
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
//
//
//
//    }
//
//    private boolean isValidSignInDetails() {
//        if (binding.inputEmail.getText().toString().trim().isEmpty()){
//            showToast("Enter email");
//            return false;
//        } else if (!Patterns.EMAIL_ADDRESS.matcher(binding.inputEmail.getText().toString()).matches()){
//            showToast("Enter valid email");
//            return false;
//        } else if (binding.inputPassword.getText().toString().trim().isEmpty()){
//            showToast("Enter password");
//            return false;
//        } else {
//            return true;
//        }
//    }
//
//    private void loading(Boolean isLoading){
//        if (isLoading){
//            binding.buttonSignIn.setVisibility(View.INVISIBLE);
//            binding.progressBar.setVisibility(View.VISIBLE);
//        } else {
//            binding.buttonSignIn.setVisibility(View.VISIBLE);
//            binding.progressBar.setVisibility(View.INVISIBLE);
//        }
//    }


}