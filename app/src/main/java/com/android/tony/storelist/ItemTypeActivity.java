package com.android.tony.storelist;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

public class ItemTypeActivity extends AppCompatActivity {

    ProgressBar progressBar;
    PieChartView pieChartView;
    List<String> itemData;
    List<SliceValue> pieData;
    Map<String, Integer> pieDataOccur;
    String type;
    float[] k;
    String[] year = {"2010", "2011", "2012", "2013", "2014", "2015", "2016", "2017"};
    int[] color = {Color.BLUE, Color.RED, Color.GREEN, Color.GRAY, Color.MAGENTA, Color.YELLOW, Color.BLACK,Color.DKGRAY};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_type);
        type = getIntent().getStringExtra("piedata");
        setTitle(type);
        k = new float[8];
        for (int i = 0; i < 8; i++)
            k[i] = 0;
        pieChartView = findViewById(R.id.chartitem);
        progressBar = findViewById(R.id.progressBar);
        itemData = new ArrayList<>();
        pieData = new ArrayList<>();
        pieDataOccur = new HashMap<>();

        if (type.equals("Item Type") || type.equals("Region")) {
            FirebaseDatabase.getInstance().getReference().child("data").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot dataSnapshotIter : dataSnapshot.getChildren()) {
                        itemData.add(dataSnapshotIter.child(type).getValue().toString());
                    }

                    setupPie();
                    progressBar.setVisibility(View.GONE);
                    FirebaseDatabase.getInstance().getReference().child("data").removeEventListener(this);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    progressBar.setVisibility(View.GONE);
                    FirebaseDatabase.getInstance().getReference().child("data").removeEventListener(this);
                }
            });
        } else {
            FirebaseDatabase.getInstance().getReference().child("data").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot dataSnapshotIter : dataSnapshot.getChildren()) {
                        //Log.i("date",dataSnapshotIter.child("Order Date").getValue().toString().split("/")[2]);
                        switch (dataSnapshotIter.child("Order Date").getValue().toString().split("/")[2]) {

                            case "2010":
                                //Log.i("date",dataSnapshotIter.child("Order Date").getValue().toString().split("/")[2]);
                                k[0] += Float.valueOf(dataSnapshotIter.child(type).getValue().toString());
                                break;
                            case "2011":
                                k[1] += Float.valueOf(dataSnapshotIter.child(type).getValue().toString());
                                break;
                            case "2012":
                                k[2] += Float.valueOf(dataSnapshotIter.child(type).getValue().toString());
                                break;
                            case "2013":
                                k[3] += Float.valueOf(dataSnapshotIter.child(type).getValue().toString());
                                break;
                            case "2014":
                                k[4] += Float.valueOf(dataSnapshotIter.child(type).getValue().toString());
                                break;
                            case "2015":
                                k[5] += Float.valueOf(dataSnapshotIter.child(type).getValue().toString());
                                break;
                            case "2016":
                                k[6] += Float.valueOf(dataSnapshotIter.child(type).getValue().toString());
                                break;
                            case "2017":
                                k[7] += Float.valueOf(dataSnapshotIter.child(type).getValue().toString());
                                break;
                        }
                    }
                    setupPie1();
                    progressBar.setVisibility(View.GONE);
                    FirebaseDatabase.getInstance().getReference().child("data").removeEventListener(this);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    progressBar.setVisibility(View.GONE);
                    FirebaseDatabase.getInstance().getReference().child("data").removeEventListener(this);
                }
            });
        }

    }

    private void setupPie1() {
        int p =0;
        for(int a=0;a<k.length;a++)
            p+=k[a];
        pieData.clear();
        int j = 0;
        for (int i = 0; i < 8; i++) {
            pieData.add(new SliceValue(k[i], color[j]).setLabel(year[i]+ " " + (( k[i] * 100) / p) + "%"));
            j++;
            if (j >= color.length)
                j = 0;
        }
        PieChartData pieChartData = new PieChartData(pieData);
        pieChartData.setHasLabels(true);
        pieChartView.setPieChartData(pieChartData);
    }

    private void setupPie() {
        pieData.clear();
        for (String data : itemData) {
            Integer j = pieDataOccur.get(data);
            pieDataOccur.put(data, (j == null) ? 1 : j + 1);
        }
        int i = 0;
        for (Map.Entry<String, Integer> val : pieDataOccur.entrySet()) {
            pieData.add(new SliceValue(val.getValue(), color[i]).setLabel(val.getKey() + " " + ((((double) val.getValue() * 100) / (double) itemData.size())) + "%"));
            i++;
            if (i >= color.length)
                i = 0;
        }
        PieChartData pieChartData = new PieChartData(pieData);
        pieChartData.setHasLabels(true);
        //pieChartData.setHasCenterCircle(true).setCenterText1(getIntent().getStringExtra("piedata")).setCenterText1FontSize(32).setCenterText1Color(getResources().getColor(R.color.colorAccentSecond));
        pieChartView.setPieChartData(pieChartData);

    }

}
