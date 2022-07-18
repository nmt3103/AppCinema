package com.example.appcinema.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.widget.TextView;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.appcinema.MainActivity;
import com.example.appcinema.R;
import com.example.appcinema.fragment.DashBoardFragment;
import com.example.appcinema.fragment.HomeFragment;
import com.example.appcinema.fragment.MyTicketFragment;
import com.example.appcinema.utilities.Constants;
import com.example.appcinema.utilities.PreferenceManager;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.HashMap;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class FirstActivity extends AppCompatActivity {
//    TextView tvLogout;
    private PreferenceManager preferenceManager;
    private MeowBottomNavigation bnv_Main;
//    RoundedImageView imgProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
//        tvLogout = findViewById(R.id.tvLogout);
//        imgProfile = findViewById(R.id.imageProfile);



        bnv_Main = findViewById(R.id.bnv_Main);
        bnv_Main.add(new MeowBottomNavigation.Model(1,R.drawable.ic_baseline_home_24));
        bnv_Main.add(new MeowBottomNavigation.Model(2,R.drawable.ic_baseline_dashboard_24));
        bnv_Main.add(new MeowBottomNavigation.Model(3,R.drawable.ic_baseline_face_24));

        preferenceManager = new PreferenceManager(getApplicationContext());


        bnv_Main.show(1,true);
        replace(new HomeFragment());
        bnv_Main.setOnClickMenuListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {
                switch (model.getId()){
                    case 1:
                        replace(new HomeFragment());
                        break;

                    case 2:
                        replace(new DashBoardFragment());
                        break;

                    case 3:
                        replace(new MyTicketFragment());
                        break;
                }
                return null;
            }
        });

//        loadUserDetails();
//        setListeners();

    }



//    private void loadUserDetails() {
//        byte[] bytes = Base64.decode(preferenceManager.getString(Constants.KEY_IMAGE),Base64.DEFAULT);
//        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
//        imgProfile.setImageBitmap(bitmap);
//    }

//    private void setListeners() {
//        tvLogout.setOnClickListener(v -> {
//            signOut();
//        });
//    }

//    private void showToast(String message){
//        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
//    }
//
//    private void signOut() {
//        showToast("Signing out ...");
//        FirebaseFirestore database = FirebaseFirestore.getInstance();
//        DocumentReference documentReference =
//                database.collection(Constants.KEY_COLLECTION_USERS).document(
//                    preferenceManager.getString(Constants.KEY_USER_ID)
//                );
//        HashMap<String, Object> updates = new HashMap<>();
//        documentReference.update(updates)
//                .addOnSuccessListener(unused -> {
//                    preferenceManager.clear();
//                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
//                    finish();
//                })
//                .addOnFailureListener(e -> showToast("Unable to sign out"));
//    }

    private void replace(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame,fragment);
        transaction.commit();
    }
}