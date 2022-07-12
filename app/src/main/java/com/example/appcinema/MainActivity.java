package com.example.appcinema;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appcinema.activities.FirstActivity;
import com.example.appcinema.activities.RegisterActivity;

import com.example.appcinema.utilities.Constants;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {
    EditText edEmail,edPassword;
    MaterialButton btnSignIn;
    ProgressBar progressBar;
    TextView tvNew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edEmail = findViewById(R.id.inputEmail);
        edPassword = findViewById(R.id.inputPassword);
        btnSignIn = findViewById(R.id.buttonSignIn);
        progressBar = findViewById(R.id.progressBar);
        tvNew = findViewById(R.id.textCreateNewAccount);


//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        Map<String, Object> user = new HashMap<>();
//        user.put("first", "Ada");
//        user.put("last", "Lovelace");
//        user.put("born", 1815);
//
//        db.collection("users")
//                .add(user)
//                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                    @Override
//                    public void onSuccess(DocumentReference documentReference) {
//                       showToast("Success");
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        showToast("Failure");
//                    }
//                });

        setListeners();
    }

    private void setListeners() {
        tvNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValidSignInDetails()){
                    signIn();
                }
            }
        });
    }

    private void signIn() {
        loading(true);
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection(Constants.KEY_COLLECTION_USERS)
                .whereEqualTo(Constants.KEY_EMAIL,edEmail.getText().toString())
                .whereEqualTo(Constants.KEY_PASSWORD,edPassword.getText().toString())
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null
                            && task.getResult().getDocuments().size() > 0){
                        DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                        Intent intent = new Intent(getApplicationContext(), FirstActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    } else{
                        loading(false);
                        showToast("Unable to sign in");
                    }
                });



    }

    private boolean isValidSignInDetails() {
        if (edEmail.getText().toString().trim().isEmpty()){
            showToast("Enter email");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(edEmail.getText().toString()).matches()){
            showToast("Enter valid email");
            return false;
        } else if (edPassword.getText().toString().trim().isEmpty()){
            showToast("Enter password");
            return false;
        } else {
            return true;
        }
    }

    private void loading(Boolean isLoading){
        if (isLoading){
            btnSignIn.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.VISIBLE);
        } else {
            btnSignIn.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    private void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}