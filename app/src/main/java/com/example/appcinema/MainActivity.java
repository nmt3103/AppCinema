package com.example.appcinema;


import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;


import android.content.Intent;
import android.os.Bundle;


import android.view.View;
import android.widget.Toast;


import com.example.appcinema.activities.FirstActivity;
import com.example.appcinema.activities.RegisterActivity;

import com.example.appcinema.databinding.ActivityMainBinding;
import com.example.appcinema.utilities.Constants;
import com.example.appcinema.utilities.PreferenceManager;
import com.example.appcinema.viewmodel.SignInVM;




public class MainActivity extends AppCompatActivity {
    private SignInVM signInVM;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getSupportActionBar().hide();

        binding = DataBindingUtil.setContentView(MainActivity.this,R.layout.activity_main);
        binding.setLifecycleOwner(this);
        signInVM = new ViewModelProvider(this).get(SignInVM.class);


        checkSharePreferrence();
        setListeners();
        observerViewModel();
    }
    private void checkSharePreferrence(){
        PreferenceManager preferenceManager = new PreferenceManager(getApplicationContext());
        if (preferenceManager.getBoolean(Constants.KEY_IS_SIGNED_IN)){
            Intent intent = new Intent(getApplicationContext(), FirstActivity.class);
            startActivity(intent);
            finish();
        }


    }

    private void observerViewModel() {
        signInVM.getShowErrorMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                showToast(s);
            }
        });
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
                        if (isValid){
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
}