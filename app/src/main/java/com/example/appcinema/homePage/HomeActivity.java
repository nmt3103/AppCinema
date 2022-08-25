package com.example.appcinema.homePage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


import android.content.Intent;
import android.os.Bundle;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;

import com.example.appcinema.R;
import com.example.appcinema.fragment.DashBoardFragment;
import com.example.appcinema.fragment.HomeFragment;
import com.example.appcinema.fragment.MyTicketFragment;


import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class HomeActivity extends AppCompatActivity {
    private MeowBottomNavigation bnv_Main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Intent intent = getIntent();
        int i = intent.getIntExtra("fragment",1);
        if (i == 1){
            replace(new HomeFragment(getApplicationContext()));
        } else{
            replace(new MyTicketFragment());
        }

        bnv_Main = findViewById(R.id.bnv_Main);
        bnv_Main.add(new MeowBottomNavigation.Model(1,R.drawable.ic_baseline_home_24));
        bnv_Main.add(new MeowBottomNavigation.Model(2,R.drawable.ic_baseline_dashboard_24));
        bnv_Main.add(new MeowBottomNavigation.Model(3,R.drawable.ic_baseline_face_24));
        bnv_Main.show(1,true);
        bnv_Main.setOnClickMenuListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {
                switch (model.getId()){
                    case 1:
                        replace(new HomeFragment(getApplicationContext()));
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
    }

    private void replace(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame,fragment);
        transaction.commit();
    }

}