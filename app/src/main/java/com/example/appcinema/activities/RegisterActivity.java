package com.example.appcinema.activities;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appcinema.R;
import com.example.appcinema.utilities.Constants;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.makeramen.roundedimageview.RoundedImageView;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
    FrameLayout frameLayout;
    RoundedImageView imgProfile;
    TextView tvAddImage,tvSignIn;
    EditText edName,edEmail,edPassword,edConfirm;
    MaterialButton btnSignUp;
    ProgressBar progressBar;

    private String encodedImage;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        frameLayout = findViewById(R.id.layoutImage);
        imgProfile = findViewById(R.id.imageProfile);
        tvAddImage = findViewById(R.id.textAddImage);
        tvSignIn = findViewById(R.id.textSignIn);
        edName = findViewById(R.id.inputName);
        edEmail = findViewById(R.id.inputEmail);
        edPassword = findViewById(R.id.inputPassword);
        edConfirm = findViewById(R.id.inputConfirmPassword);
        btnSignUp = findViewById(R.id.buttonSignUp);
        progressBar = findViewById(R.id.progressBar);

        setListeners();

    }

    private void setListeners() {
        tvSignIn.setOnClickListener(v -> onBackPressed());
        btnSignUp.setOnClickListener(v -> {
            if (isValidSignUpDetails()){
                signUp();
            }
        });
        frameLayout.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            pickImage.launch(intent);
        });
    }
    private void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void signUp() {
        loading(true);
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        HashMap<String, Object> user = new HashMap<>();
        user.put(Constants.KEY_NAME, edName.getText().toString());
        user.put(Constants.KEY_EMAIL, edEmail.getText().toString());
        user.put(Constants.KEY_PASSWORD, edPassword.getText().toString());
        user.put(Constants.KEY_IMAGE,encodedImage);
        database.collection(Constants.KEY_COLLECTION_USERS)
                .add(user)
                .addOnSuccessListener(documentReference -> {
                   loading(false);
                   Intent intent = new Intent(getApplicationContext(), FirstActivity.class);
                   intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                   startActivity(intent);
                })
                .addOnFailureListener(exception -> {
                   loading(false);
                   showToast(exception.getMessage());
                });
    }

    private String encodeImage(Bitmap bitmap){
        int previewWidth = 150;
        int previewHeight = bitmap.getHeight() * previewWidth / bitmap.getWidth();
        Bitmap previewBitmap = Bitmap.createScaledBitmap(bitmap, previewWidth,previewHeight, false);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        previewBitmap.compress(Bitmap.CompressFormat.JPEG,50,byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(bytes,Base64.DEFAULT);
    }

    private final ActivityResultLauncher<Intent> pickImage = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK){
                    if (result.getData() != null){
                        Uri imageUri = result.getData().getData();
                        try {
                            InputStream inputStream = getContentResolver().openInputStream(imageUri);
                            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                            imgProfile.setImageBitmap(bitmap);
                            tvAddImage.setVisibility(View.GONE);
                            encodedImage = encodeImage(bitmap);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
    );

    private boolean isValidSignUpDetails() {
        if (encodedImage == null){
            showToast("Select profile image");
            return false;
        } else if (edName.getText().toString().trim().isEmpty()){
            showToast("Enter Name");
            return false;
        } else if (edEmail.getText().toString().trim().isEmpty()){
            showToast("Enter Email");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(edEmail.getText().toString()).matches()){
            showToast("Enter valid Email");
            return false;
        } else if (edPassword.getText().toString().trim().isEmpty()){
            showToast("Enter Password");
            return false;
        } else if (edConfirm.getText().toString().trim().isEmpty()){
            showToast("Confirm your password");
            return false;
        } else if (!edPassword.getText().toString().equals(edConfirm.getText().toString())){
            showToast("Password & confirm password must be the same ");
            return false;
        } else {
            return true;
        }
    }

    private void loading(Boolean isLoading){
        if (isLoading){
            btnSignUp.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.VISIBLE);
        } else {
            btnSignUp.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.INVISIBLE);
        }
    }
}