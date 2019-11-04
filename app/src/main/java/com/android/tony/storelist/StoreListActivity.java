package com.android.tony.storelist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class StoreListActivity extends AppCompatActivity {
    /*List<StoreListModel> storeListModelList;
    StoreListAdapter storeListAdapter;*/
    String msg = "";
    TextView textView;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_list);
        textView = findViewById(R.id.mltextView);
        progressBar = findViewById(R.id.progressBar2);

        findViewById(R.id.logoutbutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(StoreListActivity.this, MainActivity.class));
                StoreListActivity.this.finish();
            }
        });

        findViewById(R.id.mlbutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                FirebaseDatabase.getInstance().getReference().child("data").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        double max = Double.parseDouble(dataSnapshot.child("91").child("Total Revenue").getValue().toString());
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            if (max < Double.parseDouble(dataSnapshot1.child("Total Revenue").getValue().toString())) {
                                max = Double.parseDouble(dataSnapshot1.child("Total Revenue").getValue().toString());
                                msg = "Item: " + dataSnapshot1.child("Item Type").getValue().toString() + " Total Revenue: " + dataSnapshot1.child("Total Revenue").getValue().toString() + " Units Sold:" + dataSnapshot1.child("Units Sold").getValue().toString();
                            }

                            progressBar.setVisibility(View.GONE);
                            textView.setText(msg);
                            textView.setVisibility(View.VISIBLE);
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

    }

    public void onClickData(View v) {
        startActivity(new Intent(StoreListActivity.this, ItemTypeActivity.class).putExtra("piedata", ((Button) findViewById(v.getId())).getText()));

    }

    @Override
    protected void onStart() {
        super.onStart();
        textView.setText(msg);
    }
}


