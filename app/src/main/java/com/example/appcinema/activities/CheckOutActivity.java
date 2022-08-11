package com.example.appcinema.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Toast;

import com.example.appcinema.R;
import com.example.appcinema.databinding.ActivityCheckOutBinding;
import com.example.appcinema.model.Movie;
import com.example.appcinema.model.Order;
import com.example.appcinema.model.Room;
import com.example.appcinema.utilities.Constants;
import com.example.appcinema.utilities.PreferenceManager;
import com.example.appcinema.viewmodel.CheckOutViewModel;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class CheckOutActivity extends AppCompatActivity {
    ActivityCheckOutBinding binding;
    CheckOutViewModel viewModel;
    Room room;
    int num;
    String slots,location;
    Movie movieChoose;
    PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(CheckOutActivity.this,R.layout.activity_check_out);
        binding.setLifecycleOwner(this);
        viewModel = new ViewModelProvider(this).get(CheckOutViewModel.class);

        Intent intent = getIntent();
        if (intent.getExtras() != null){
            movieChoose = (Movie) intent.getSerializableExtra("movieChoose");
            room = (Room) intent.getSerializableExtra("roomConfirm");
            slots = intent.getStringExtra("seatSelected");
            num = intent.getIntExtra("numberSeat",0);
            location = intent.getStringExtra("location");
            initData();
            setListener();
        } else {
            Toast.makeText(this, "Do not reveive data", Toast.LENGTH_SHORT).show();
        }


    }

    private void initData() {
        Picasso.get().load(movieChoose.getImgPoster()).into(binding.imgTicket);
        binding.tvName.setText(movieChoose.getName());
        binding.rateBarMovie.setRating(movieChoose.getRate());
        binding.tvCate.setText(movieChoose.getCate());
        binding.tvTime.setText(movieChoose.getTime());

        preferenceManager = new PreferenceManager(getApplicationContext());
        binding.tvNameUser.setText(preferenceManager.getString(Constants.KEY_NAME));
        binding.tvCinema.setText(location);
        binding.tvDateTime.setText(room.getDate()+", " + room.getTime());
        binding.tvSeat.setText(slots);
        binding.tvPrice.setText("50.000 x " + String.valueOf(num));
        int totalPrice = 50000 * num;

        binding.tvTotal.setText(String.valueOf(totalPrice));

    }

    private void setListener() {
        binding.btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });
        binding.btnCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int totalPrice = 50000 * num;
//                new Locale("vi","VN")
                Format formatter = new SimpleDateFormat("dd-M-yyyy hh:mm:ss",Locale.getDefault());
                Order order = new Order(movieChoose,room,formatter.format(new Date()),location,totalPrice,slots);
                order.setCustomerId(preferenceManager.getString(Constants.KEY_USER_ID));
                QRGEncoder qrgEncoder = new QRGEncoder(order.toString(), null, QRGContents.Type.TEXT, 500);
                try {
                    Bitmap bitmap = qrgEncoder.getBitmap();
                    String myQrGen =  encodeImage(bitmap);
                    order.setImgQr(myQrGen);
                    viewModel.addOrder(order);
                    viewModel.getIsSuccess().observe(CheckOutActivity.this, new Observer<Boolean>() {
                        @Override
                        public void onChanged(Boolean aBoolean) {
                            if (aBoolean){
                                Intent intent = new Intent(CheckOutActivity.this, RoomActivity.class);
                                intent.putExtra("IsSuccess", true);
                                setResult(RESULT_OK,intent);
                                finish();
                            }
                        }
                    });


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
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
}