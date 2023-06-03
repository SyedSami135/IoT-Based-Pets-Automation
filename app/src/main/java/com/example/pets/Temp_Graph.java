package com.example.pets;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.Color;
import android.os.Bundle;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp_graph);

        chart = findViewById(R.id.chart);

        List<Entry> entries = new ArrayList<>();
        entries.add(new Entry(0, 10));
        entries.add(new Entry(1, 15));
        entries.add(new Entry(2, 20));
        entries.add(new Entry(3, 15));
        entries.add(new Entry(4, 12));
        entries.add(new Entry(5, 18));
        entries.add(new Entry(6, 20));
        entries.add(new Entry(7, 15));
        entries.add(new Entry(8, 12));
        entries.add(new Entry(9, 18));

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


        String[] labels = new String[] {"12:00 PM", "1:00 PM", "2:00 PM", "3:00 PM", "4:00 PM", "5:00 PM"};
        XAxis xAxis = chart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        YAxis yAxis = chart.getAxisLeft();
        yAxis.setAxisMinimum(10);

        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setDrawLabels(false); // remove values on right y-axis


        chart.getXAxis().setGridColor(Color.TRANSPARENT);
        chart.getAxisLeft().setGridColor(Color.TRANSPARENT);
        chart.getAxisRight().setGridColor(Color.TRANSPARENT);

        chart.getXAxis().setAxisLineWidth(0);
        chart.getAxisLeft().setAxisLineWidth(0);
        chart.getAxisRight().setAxisLineWidth(0);

        chart.getDescription().setEnabled(false);
        chart.getLegend().setEnabled(false);
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
    }
}

















