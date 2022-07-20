package com.example.appcinema.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appcinema.R;
import com.example.appcinema.adapter.TicketAdapter;
import com.example.appcinema.databinding.FragmentMyticketBinding;
import com.example.appcinema.model.Movie;
import com.example.appcinema.model.Order;
import com.example.appcinema.viewmodel.TicketViewModel;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class MyTicketFragment extends Fragment {

    TicketAdapter ticketAdapter;
    FragmentMyticketBinding binding;
    TicketViewModel ticketViewModel;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMyticketBinding.inflate(inflater,container,false);
        binding.setLifecycleOwner(this);
        view = binding.getRoot();
        ticketViewModel = new ViewModelProvider(this).get(TicketViewModel.class);
        observerViewModel();
        setListeners();
        return view;
    }

    private void setListeners() {
        binding.btnAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ticketViewModel.getListOrderLiveDate().observe(getViewLifecycleOwner(), new Observer<List<Order>>() {
                    @Override
                    public void onChanged(List<Order> orders) {
                        ticketAdapter = new TicketAdapter(getContext(),orders);
                        binding.rvMyTicket.setAdapter(ticketAdapter);
                    }
                });
            }
        });
        binding.btnNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ticketViewModel.getListNewOrder().observe(getViewLifecycleOwner(), new Observer<List<Order>>() {
                    @Override
                    public void onChanged(List<Order> orders) {
                        ticketAdapter = new TicketAdapter(getContext(),orders);
                        binding.rvMyTicket.setAdapter(ticketAdapter);
                    }
                });

            }
        });
        binding.btnExpire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               ticketViewModel.getListExpireOrder().observe(getViewLifecycleOwner(), new Observer<List<Order>>() {
                   @Override
                   public void onChanged(List<Order> orders) {
                       ticketAdapter = new TicketAdapter(getContext(),orders);
                       binding.rvMyTicket.setAdapter(ticketAdapter);
                   }
               });
            }
        });
    }

    private void observerViewModel() {
        binding.rvMyTicket.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext(),LinearLayoutManager.VERTICAL,false);
        binding.rvMyTicket.setLayoutManager( linearLayoutManager);
        binding.rvMyTicket.setItemAnimator(new DefaultItemAnimator());
        ticketViewModel.getListOrderLiveDate().observe(getViewLifecycleOwner(), new Observer<List<Order>>() {
            @Override
            public void onChanged(List<Order> orders) {
                ticketAdapter = new TicketAdapter(getContext(),orders);
                binding.rvMyTicket.setAdapter(ticketAdapter);
            }
        });

    }
}