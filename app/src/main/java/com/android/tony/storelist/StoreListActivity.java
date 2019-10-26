package com.android.tony.storelist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class StoreListActivity extends AppCompatActivity {
    /*List<StoreListModel> storeListModelList;
    StoreListAdapter storeListAdapter;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_list);

    }

    public void onClickData(View v) {
        startActivity(new Intent(StoreListActivity.this, ItemTypeActivity.class).putExtra("piedata", ((Button) findViewById(v.getId())).getText()));

    }
}


