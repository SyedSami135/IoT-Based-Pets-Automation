package com.example.pets;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.List;




public class Temp_Graph extends AppCompatActivity {
    LineChart chart;


    FirebaseDatabase database;
    TextView hum,temp;
    ImageView fanbtnOn,fanbtnOff;
    int lightValue,TempValue,HumValue,fanValue,heaterValue;
    ToggleButton lightbtn,heaterbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp_graph);

        chart = findViewById(R.id.chart);

        List<Entry> entries = new ArrayList<>();
        entries.add(new Entry(0, 33));
        entries.add(new Entry(1, 35));
        entries.add(new Entry(2, 34));
        entries.add(new Entry(3, 31));
        entries.add(new Entry(4, 33));

        LineDataSet dataSet = new LineDataSet(entries, "Temperature");
        dataSet.setDrawCircles(true);

        dataSet.setCircleColor(Color.WHITE);
        dataSet.setCircleRadius(4);

        dataSet.setCircleHoleColor(Color.YELLOW);


        dataSet.setColor(Color.YELLOW);
        dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        dataSet.setDrawFilled(true);
        dataSet.setFillDrawable(ContextCompat.getDrawable(this, R.drawable.gradient));
        dataSet.setLineWidth(1f);
        dataSet.setValueTextSize(12);
        LineData lineData = new LineData(dataSet);
        chart.setData(lineData);


        String[] labels = new String[] {"12:00", "1:00", "2:00", "3:00", "4:00"};
        XAxis xAxis = chart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        YAxis yAxis = chart.getAxisLeft();


        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setDrawLabels(false); // remove values on right y-axis


        chart.getXAxis().setGridColor(Color.TRANSPARENT);
        chart.getAxisLeft().setGridColor(Color.TRANSPARENT);
        chart.getAxisRight().setGridColor(Color.TRANSPARENT);

        chart.getXAxis().setAxisLineWidth(0);
        chart.getAxisLeft().setAxisLineWidth(0);
        chart.getAxisRight().setAxisLineWidth(0);

        chart.getDescription().setEnabled(false);
        chart.getLegend().setEnabled(true);
        chart.setTouchEnabled(true);
        chart.invalidate();
        chart.setGridBackgroundColor(Color.WHITE);
        chart.setDrawGridBackground(false);

//        33333333333333333333333333333333333333333333333333333333333333333333333333333333333333333
//        3####################################################################################
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference dataRef = database.getReference("temperatureData");
//
//        long timestamp = System.currentTimeMillis(); // Get current timestamp
//        double temperature = 25.5; // Replace with the actual temperature value
//
//        TemperatureData temperatureData = new TemperatureData(timestamp, temperature);
//
//        dataRef.push().setValue(temperatureData);
//
//
//        dataRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    TemperatureData temperatureData = snapshot.getValue(TemperatureData.class);
//                    // Process temperatureData object, which includes timestamp and temperature
//                    // You can add it to a list or use it directly for graph plotting
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                // Handle error
//            }
//        });
//        GraphView graphView = findViewById(R.id.graphView);
//
//// Retrieve temperature data from Firebase
//        dataRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                LineGraphSeries<DataPoint> series = new LineGraphSeries<>();
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    TemperatureData temperatureData = snapshot.getValue(TemperatureData.class);
//                    long timestamp = temperatureData.getTimestamp();
//                    double temperature = temperatureData.getTemperature();
//                    series.appendData(new DataPoint(timestamp, temperature), true, Integer.MAX_VALUE);
//                }
//                graphView.addSeries(series);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                // Handle error
//            }
//        });
//


        lightbtn= findViewById(R.id.lightbtn);

        fanbtnOn=findViewById(R.id.Fan_On);
        fanbtnOff= findViewById(R.id.FanOff);
        heaterbtn= findViewById(R.id.heaterbtn);



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


    }
}

















