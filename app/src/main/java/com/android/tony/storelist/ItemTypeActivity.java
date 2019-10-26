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
    int color[] = {Color.BLUE,Color.RED,Color.GREEN,Color.GRAY,Color.MAGENTA,Color.YELLOW,Color.BLACK};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_type);
        setTitle(getIntent().getStringExtra("piedata"));

        pieChartView = findViewById(R.id.chartitem);
        progressBar = findViewById(R.id.progressBar);
        itemData = new ArrayList<>();
        pieData = new ArrayList<>();
        pieDataOccur = new HashMap<>();

        FirebaseDatabase.getInstance().getReference().child("data").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshotIter : dataSnapshot.getChildren()) {
                    itemData.add(dataSnapshotIter.child(getIntent().getStringExtra("piedata")).getValue().toString());

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
    }

    private void setupPie() {
        for(String data:itemData)
        {
            Integer j = pieDataOccur.get(data);
            pieDataOccur.put(data,(j==null)?1:j+1);
        }
        int i=0;
        for(Map.Entry<String,Integer> val:pieDataOccur.entrySet())
        {
            pieData.add(new SliceValue(val.getValue(),color[i]).setLabel(val.getKey() + " " + ( (((double)val.getValue()*100)/(double)itemData.size())) + "%"));
            i++;
            if(i>=color.length)
                i=0;
        }
        PieChartData pieChartData = new PieChartData(pieData);
        pieChartData.setHasLabels(true);
        //pieChartData.setHasCenterCircle(true).setCenterText1(getIntent().getStringExtra("piedata")).setCenterText1FontSize(32).setCenterText1Color(getResources().getColor(R.color.colorAccentSecond));
        pieChartView.setPieChartData(pieChartData);

    }

}
