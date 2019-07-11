package org.karic.sample;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import org.karic.smartadapter.SmartAdapter;
import org.karic.smartadapter.ViewBinder;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    SwipeRefreshLayout refreshLayout;
    SmartAdapter adapter;
    List<Object> data;

    @Override
    public void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_main);

        refreshLayout = findViewById(R.id.refreshLayout);
        data = new ArrayList<>();
        adapter = new SmartAdapter();
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        adapter.setData(data);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        adapter.register(Integer.class, new ViewBinder<Integer>(R.layout.layout_item_int) {
            @Override
            public void bindData(Integer data) {
                TextView tv = finder.find(R.id.tv_value);
                tv.setText(String.valueOf(data));
            }
        });

        adapter.register(String.class, new ViewBinder<String>(R.layout.layout_item_string) {
            @Override
            public void bindData(String data) {
                TextView tv = finder.find(R.id.tv_value);
                tv.setText(String.valueOf(data));
            }
        });

        adapter.register(Boolean.class, new ViewBinder<Boolean>(R.layout.layout_item_boolean) {
            @Override
            public void bindData(Boolean data) {
                TextView tv = finder.find(R.id.tv_value);
                tv.setText(String.valueOf(data));
            }
        });

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.setRefreshing(false);
                data.add(Math.abs((int) System.currentTimeMillis() % 10000));
                data.add("No." + (System.currentTimeMillis() % 200));
                data.add(System.currentTimeMillis() % 2 == 0);
                adapter.refreshData(data, true);
            }
        });
    }
}