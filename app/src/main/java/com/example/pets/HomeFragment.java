package com.example.pets;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class HomeFragment extends Fragment {

    FirebaseDatabase database;
    TextView hum,temp;
    ImageView fanbtnOn,fanbtnOff;
    int lightValue,TempValue,HumValue,fanValue,heaterValue;
    ToggleButton lightbtn,heaterbtn;
    CardView tempView,humView;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_home, container, false);


        lightbtn= view.findViewById(R.id.lightbtn);
        hum= view.findViewById(R.id.humidity);
        temp= view.findViewById(R.id.temperature);
        fanbtnOn= view.findViewById(R.id.Fan_On);
        fanbtnOff= view.findViewById(R.id.FanOff);
        heaterbtn= view.findViewById(R.id.heaterbtn);


        tempView= view.findViewById(R.id.temperatureCard);
        humView= view.findViewById(R.id.humidityCard);


        tempView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),Temp_Graph.class);
                startActivity(intent);
            }
        });
//        humView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(getContext(),Temp_Graph.class);
//                startActivity(intent);
//            }
//        });


        database = FirebaseDatabase.getInstance();

//        LED Reference
        DatabaseReference led = database.getReference("led");
        led.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                lightValue=dataSnapshot.getValue(Integer.class);
                lightbtn.setChecked(lightValue == 1);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

//        Led On Click
        lightbtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    database.getReference("led").setValue(1);
                } else {
                    database.getReference("led").setValue(0);
                }
            }
        });

//        Humidity Reference
        DatabaseReference HumidityRef = database.getReference("Humidity");
        HumidityRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                HumValue = dataSnapshot.getValue(Integer.class);
                hum.setText(HumValue+"%");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

//        Temperature Reference
        DatabaseReference TemperatureRef = database.getReference("Temperature");
        TemperatureRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                TempValue = dataSnapshot.getValue(Integer.class);
                temp.setText(TempValue+"°");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

//        Fan Reference
        DatabaseReference fanRef = database.getReference("fan");
        fanRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                fanValue = dataSnapshot.getValue(Integer.class);
                if(fanValue==1){
                    fanbtnOn.setImageDrawable(getResources().getDrawable(R.drawable.fanon));
                    fanbtnOff.setImageDrawable(getResources().getDrawable(R.drawable.fanoff));
                }else if(fanValue==0){
                    fanbtnOn.setImageDrawable(getResources().getDrawable(R.drawable.fanoff));
                    fanbtnOff.setImageDrawable(getResources().getDrawable(R.drawable.fanon));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


// FanOn Click Listener
        fanbtnOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database.getReference("fan").setValue(1);
                    Toast.makeText(getContext(), "Fan ON", Toast.LENGTH_SHORT).show();
                    fanbtnOn.setImageDrawable(getResources().getDrawable(R.drawable.fanon));
                    fanbtnOff.setImageDrawable(getResources().getDrawable(R.drawable.fanoff));

            }
        });

        // FanOff Click Listener
        fanbtnOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database.getReference("fan").setValue(0);
                Toast.makeText(getContext(), "Fan OFF", Toast.LENGTH_SHORT).show();
                fanbtnOff.setImageDrawable(getResources().getDrawable(R.drawable.fanon));
                fanbtnOn.setImageDrawable(getResources().getDrawable(R.drawable.fanoff));

            }
        });

//      Heater Reference
        DatabaseReference HeaterRef = database.getReference("heater");
        HeaterRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                heaterValue = dataSnapshot.getValue(Integer.class);

                heaterbtn.setChecked(heaterValue == 1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
//      Heater On Click
        heaterbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (heaterValue==1){
                    database.getReference("heater").setValue(0);
                }else {
                    database.getReference("heater").setValue(1);

                }
            }
        });





        return view;
    }

}
