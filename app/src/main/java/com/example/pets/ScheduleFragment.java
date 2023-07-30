package com.example.pets;

import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.pets.Adapters.AdapterSchedule;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ScheduleFragment extends Fragment {
    ImageButton addTime;

    FirebaseDatabase database;

    AdapterSchedule scheduleAdapter;
    private int  mHour, mMinute;
    RecyclerView recyclerView;
    FirebaseDatabase firebaseDatabase;
    String userId;
    private List<String> schedule;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_schedule, container, false);

        schedule=new ArrayList<>();
        recyclerView=v.findViewById(R.id.timeRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        scheduleAdapter =new AdapterSchedule(schedule);
        recyclerView.setAdapter(scheduleAdapter);
        database = FirebaseDatabase.getInstance();

        ArrayList<Map<String, Integer>> timeList = new ArrayList<>();
        Map<String, Integer> timeEntry = new HashMap<>();


//        firebaseDatabase = FirebaseDatabase.getInstance();
//        DatabaseReference Schedule=firebaseDatabase.getReference("Schedule");
//        Schedule.setValue(timeList);






        addTime=v.findViewById(R.id.AddTime);
        addTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get current time
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Create time picker dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                        (view, hourOfDay, minute) -> {
                            schedule.add(hourOfDay+":"+minute);
                            scheduleAdapter.notifyDataSetChanged();

                            database.getReference("Food").setValue(hourOfDay+":"+minute);

//                            timeEntry.put("hour", hourOfDay); // Replace with the desired hour value
//                            timeEntry.put("minute", minute); // Replace with the desired minute value
//                            timeList.add(timeEntry);
                        }, mHour, mMinute, false);

                // Display time picker dialog
                timePickerDialog.show();
            }
        });
//        timeEntry.put("hour", 10);
//        timeEntry.put("minute", 30);
//        Schedule.setValue(timeList);

        recyclerView.setAdapter(scheduleAdapter);



//        Temp data
        EditText tempSet=v.findViewById(R.id.tempSet);

        DatabaseReference TempRef = database.getReference("temperatureData");
//        TempRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                int tempValue=dataSnapshot.getValue(Integer.class);
//                tempSet.setText(tempValue+"");
//
//
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

//        tempSet.setFocusable(false);
//        tempSet.addTextChangedListener(new TextWatcher() {
//                                           @Override
//                                           public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//                                           }
//
//                                           @Override
//                                           public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//                                               database.getReference("temperatureData").setValue(tempSet.getText());
//                                               Toast.makeText(getContext(),"Temperature "+tempSet.getText()+ "update Sucessfully",Toast.LENGTH_SHORT);
//
//                                           }
//
//                                           @Override
//                                           public void afterTextChanged(Editable s) {
//
//                                           }
//                                       }
//        );




        return v;

    }



}