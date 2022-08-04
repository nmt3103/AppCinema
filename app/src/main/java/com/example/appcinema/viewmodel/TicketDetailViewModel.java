package com.example.appcinema.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.appcinema.R;
import com.example.appcinema.model.Movie;
import com.example.appcinema.model.Order;
import com.example.appcinema.model.Room;
import com.example.appcinema.model.Slot;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TicketDetailViewModel extends ViewModel {
    private MutableLiveData<Order> orderLiveData;

    public TicketDetailViewModel() {
        orderLiveData = new MutableLiveData<>();
    }
    public MutableLiveData<Order> getOrderLiveData() {
        return orderLiveData;
    }
}
