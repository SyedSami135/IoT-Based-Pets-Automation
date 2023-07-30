package com.example.pets;



import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.pets.Adapters.RecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

public class NotificationFragment extends Fragment {

    RecyclerView recyclerView;
    RecyclerAdapter recyclerAdapter;

    List<String> moviesList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v= inflater.inflate(R.layout.fragment_notification, container, false);


        moviesList = new ArrayList<>();

        recyclerView = v. findViewById(R.id.recyclerView);
        recyclerAdapter = new RecyclerAdapter(moviesList);

//        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(recyclerAdapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);



        moviesList.add("Temperature was set!");
        moviesList.add("Light is turned ON!");
        moviesList.add("Light is turned off!");
        moviesList.add("Temperature was set!");
        moviesList.add("Fan is turned on!");
        moviesList.add("Fan is turned off!");
        moviesList.add("Light is turned ON!");
        moviesList.add("Light is turned off!");
        moviesList.add("Temperature was set!");
        moviesList.add("Light is turned ON!");
        moviesList.add("Light is turned off!");
        moviesList.add("Temperature was set!");
        moviesList.add("Fan is turned on!");
        moviesList.add("Fan is turned off!");
        moviesList.add("Light is turned ON!");
        moviesList.add("Light is turned off!");


        return v;
    }
}