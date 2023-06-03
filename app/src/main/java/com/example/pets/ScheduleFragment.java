package com.example.pets;

import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;


public class ScheduleFragment extends Fragment {
    Button timeButton;
    TextView tvSelectedTime;

    private int  mHour, mMinute;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_schedule, container, false);


            timeButton= v.findViewById(R.id.timeButton);
            tvSelectedTime= v.findViewById(R.id.selectedTimeTextView);
            timeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Get current time
                    final Calendar c = Calendar.getInstance();
                    mHour = c.get(Calendar.HOUR_OF_DAY);
                    mMinute = c.get(Calendar.MINUTE);

                    // Create time picker dialog
                    TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                            new TimePickerDialog.OnTimeSetListener() {
                                @Override
                                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                    // Set selected time to TextView
                                    tvSelectedTime.setText(hourOfDay + ":" + minute);
                                }
                            }, mHour, mMinute, false);

                    // Display time picker dialog
                    timePickerDialog.show();
                }
            });























        return v;

    }


}