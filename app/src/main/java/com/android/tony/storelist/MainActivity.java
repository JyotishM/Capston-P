package com.android.tony.storelist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<StoreListModel> storeListModelList;
    StoreListAdapter storeListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.storeRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayoutManager.VERTICAL));
        storeListModelList = new ArrayList<>();
        storeListAdapter = new StoreListAdapter(storeListModelList);
        recyclerView.setAdapter(storeListAdapter);

        storeListModelList.add(new StoreListModel(Uri.parse("https://images.unsplash.com/photo-1528698827591-e19ccd7bc23d?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=755&q=80"), "Store 1", "Phagwara", "4.0"));
        storeListModelList.add(new StoreListModel(Uri.parse("https://images.unsplash.com/photo-1528698827591-e19ccd7bc23d?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=755&q=80"), "Store 2", "Jalandhar", "4.8"));
        storeListModelList.add(new StoreListModel(Uri.parse("https://images.unsplash.com/photo-1528698827591-e19ccd7bc23d?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=755&q=80"), "Store 3", "Near Haveli", "3.0"));
        storeListModelList.add(new StoreListModel(Uri.parse("https://images.unsplash.com/photo-1528698827591-e19ccd7bc23d?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=755&q=80"), "Store 4", "Viva Mall", "3.8"));
        storeListModelList.add(new StoreListModel(Uri.parse("https://images.unsplash.com/photo-1528698827591-e19ccd7bc23d?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=755&q=80"), "Store 5", "Viva Mall", "3.9"));
        storeListAdapter.notifyDataSetChanged();

        recyclerView.addOnItemTouchListener(new StoreRecyclerViewClickListener(getApplicationContext(), recyclerView, new StoreRecyclerViewClickListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                StoreListModel model = storeListModelList.get(position);
                Toast.makeText(getApplicationContext(),model.getStoreName(),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

    }
}
