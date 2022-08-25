package com.example.appcinema.loginPage;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;


import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.appcinema.R;
import com.example.appcinema.databinding.ActivityMainBinding;
import com.example.appcinema.homePage.HomeActivity;
import com.example.appcinema.activities.RegisterActivity;

import com.example.appcinema.utilities.Constants;
import com.example.appcinema.utilities.PreferenceManager;
import com.example.appcinema.viewmodel.SignInVM;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;


public class LoginActivity extends AppCompatActivity {
    private SignInVM signInVM;
    private ActivityMainBinding binding;
    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;
    private CallbackManager mCallbackManager;

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseAuth.getInstance().signOut();
        LoginManager.getInstance().logOut();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(LoginActivity.this, R.layout.activity_main);
        binding.setLifecycleOwner(this);
        signInVM = new ViewModelProvider(this).get(SignInVM.class);
        mAuth = FirebaseAuth.getInstance();

        checkSharePreferrence();
        setListeners();
        observerViewModel();
        googleListener();
        facebookListener();
    }
    private void checkSharePreferrence(){
        PreferenceManager preferenceManager = new PreferenceManager(getApplicationContext());
        preferenceManager.clear();


        if (preferenceManager.getBoolean(Constants.KEY_IS_SIGNED_IN)){
            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
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
                    binding.inputEmail.setEnabled(false);
                    binding.inputPassword.setEnabled(false);
                    binding.progressBar.setVisibility(View.VISIBLE);
                    binding.buttonSignIn.setVisibility(View.INVISIBLE);
                } else {
                    binding.inputEmail.setEnabled(true);
                    binding.inputPassword.setEnabled(true);
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
                signInVM.getIsValid().observe(LoginActivity.this, new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean isValid) {
                        if (isValid){
                            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }
                    }
                });

            }
        });


    }

    private void facebookListener() {
        mCallbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("TAG", "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d("TAG", "facebook:onCancel");
                // ...
            }

            @Override
            public void onError(FacebookException error) {
                Log.d("TAG", "facebook:onError", error);
                // ...
            }
        });


        binding.imgFacebook.setOnClickListener(v -> {
            LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, Arrays.asList("public_profile"));
        });


    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d("TAG", "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInWithCredential:success");
                            updateUI();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "signInWithCredential:failure", task.getException());
                            showToast("Authentication failed.");
                        }
                    }
                });
    }

    private void updateUI() {
        signInVM.loginFacebook(getApplicationContext());
        signInVM.getLoginFacebookValid().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isFacebookValid) {
                if (isFacebookValid){
                    signInVM.updateUser(getApplicationContext());
                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }
            }
        });


    }

    private void googleListener() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this,gso);
        binding.imgGoogle.setOnClickListener(v -> {
            signIn();
        });

    }

    private void signIn(){
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent,1234);
    }

    private void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1234){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(),null);
                mAuth.signInWithCredential(credential)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    signInVM.loginGoogle(getApplicationContext());
                                    signInVM.getLoginGoogleValid().observe(LoginActivity.this, new Observer<Boolean>() {
                                        @Override
                                        public void onChanged(Boolean isGoogleValid) {
                                            if (isGoogleValid){
                                                signInVM.updateUser(getApplicationContext());
                                                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                startActivity(intent);
                                                finish();
                                            }
                                        }
                                    });
                                } else {
                                    showToast("Failure Auth Login");
                                }
                            }
                        });
            } catch (ApiException e) {
                e.printStackTrace();
                showToast("Bug in ApiException");

            }
        }else {
            mCallbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }
}