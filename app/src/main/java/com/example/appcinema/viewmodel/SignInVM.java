package com.example.appcinema.viewmodel;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Base64;
import android.util.Patterns;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.appcinema.homePage.HomeActivity;
import com.example.appcinema.utilities.Constants;
import com.example.appcinema.utilities.PreferenceManager;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

public class SignInVM extends ViewModel {
    public MutableLiveData<String> email;
    public MutableLiveData<String> password;
    public MutableLiveData<Boolean> isLoading;
    public MutableLiveData<String> showErrorMessage;
    public MutableLiveData<Boolean> isValid;
    public MutableLiveData<Boolean> loginGoogleValid;
    public MutableLiveData<Boolean> loginFacebookValid;

    public SignInVM() {
        email = new MutableLiveData<>();
        password = new MutableLiveData<>() ;
        isLoading = new MutableLiveData<>();
        showErrorMessage = new MutableLiveData<>();
        isValid = new MutableLiveData<>();
        loginGoogleValid = new MutableLiveData<>();
        loginFacebookValid = new MutableLiveData<>();
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

    public void loginGoogle(Context context){
        isLoading.postValue(true);
        loginGoogleValid.postValue(false);
        PreferenceManager preferenceManager = new PreferenceManager(context);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(context);
        String image = signInAccount.getPhotoUrl().toString().replace("s96-c", "s492-c");

        preferenceManager.putBoolean(Constants.KEY_IS_SIGNED_IN,true);
        preferenceManager.putString(Constants.KEY_USER_ID, user.getUid());
        preferenceManager.putString(Constants.KEY_LOGIN_PROVIDER,"Google");
        preferenceManager.putString(Constants.KEY_EMAIL,signInAccount.getEmail());
        preferenceManager.putString(Constants.KEY_NAME,signInAccount.getDisplayName());
        Glide.with(context)
                .asBitmap()
                .load(image)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        preferenceManager.putString(Constants.KEY_IMAGE,encodeImage(resource));
                        loginGoogleValid.postValue(true);
                        isLoading.postValue(false);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                        loginGoogleValid.postValue(false);

                    }
                });
    }

    public void loginFacebook(Context context){
        isLoading.postValue(true);
        loginFacebookValid.postValue(false);

        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        GraphRequest request = GraphRequest.newMeRequest(
                accessToken,
                new GraphRequest.GraphJSONObjectCallback(){
                    @Override
                    public void onCompleted(@Nullable JSONObject jsonObject, @Nullable GraphResponse graphResponse) {
                        try {
                            PreferenceManager preferenceManager = new PreferenceManager(context);
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            String fullName = jsonObject.getString("name");
                            String url = jsonObject.getJSONObject("picture").getJSONObject("data").getString("url");
                            String gmail = jsonObject.getString("email");

                            preferenceManager.putBoolean(Constants.KEY_IS_SIGNED_IN,true);
                            preferenceManager.putString(Constants.KEY_USER_ID, user.getUid());
                            preferenceManager.putString(Constants.KEY_LOGIN_PROVIDER,"Facebook");
                            preferenceManager.putString(Constants.KEY_EMAIL,gmail);
                            preferenceManager.putString(Constants.KEY_NAME,fullName);
                            Glide.with(context)
                                    .asBitmap()
                                    .load(url)
                                    .into(new CustomTarget<Bitmap>() {
                                        @Override
                                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                            preferenceManager.putString(Constants.KEY_IMAGE,encodeImage(resource));
                                            loginFacebookValid.postValue(true);
                                            isLoading.postValue(false);
                                        }

                                        @Override
                                        public void onLoadCleared(@Nullable Drawable placeholder) {
                                            loginFacebookValid.postValue(false);
                                        }
                                    });

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "name,picture.type(large),email");
        request.setParameters(parameters);
        request.executeAsync();
    }

    public void updateUser(Context context){
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        PreferenceManager preferenceManager = new PreferenceManager(context);
        HashMap<String, Object> user = new HashMap<>();
        user.put(Constants.KEY_NAME,preferenceManager.getString(Constants.KEY_NAME));
        user.put(Constants.KEY_EMAIL,preferenceManager.getString(Constants.KEY_EMAIL));
        user.put(Constants.KEY_PASSWORD,"123456");
        user.put(Constants.KEY_IMAGE,preferenceManager.getString(Constants.KEY_IMAGE));
        database.collection(Constants.KEY_COLLECTION_USERS).document(preferenceManager.getString(Constants.KEY_USER_ID))
                .set(user);

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

    public MutableLiveData<String> getShowErrorMessage() {
        return showErrorMessage;
    }

    public MutableLiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public MutableLiveData<Boolean> getIsValid() {
        return isValid;
    }

    public MutableLiveData<Boolean> getLoginGoogleValid() {
        return loginGoogleValid;
    }

    public MutableLiveData<Boolean> getLoginFacebookValid() {
        return loginFacebookValid;
    }
}
