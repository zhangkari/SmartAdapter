package org.karic.sample;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import org.karic.sample.binder.BooleanBinder;
import org.karic.sample.binder.IntegerBinder;
import org.karic.smartadapter.SmartAdapter;
import org.karic.smartadapter.ViewBinder;
import org.karic.smartrefreshlayout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    SmartRefreshLayout refreshLayout;
    SmartAdapter adapter;
    List<Object> data;
    int counter = 0;

    @Override
    public void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_main);

        refreshLayout = findViewById(R.id.refreshLayout);
        data = loadData();
        adapter = new SmartAdapter();
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        adapter.setData(data);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        adapter.register(Integer.class, new IntegerBinder());
        adapter.register(String.class, new StringBinder());
        adapter.register(Boolean.class, new BooleanBinder());

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        adapter.setData(loadData());
                        refreshLayout.setRefreshing(false);
                        refreshLayout.setLoadingMore(false);
                    }
                }, 3000);

            }
        });

        refreshLayout.setOnLoadMoreListener(new SmartRefreshLayout.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                refreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.setLoadingMore(false);
                        adapter.refreshData(loadData(), false);
                        refreshLayout.setLoadComplete(adapter.getItemCount() >= 20);
                    }
                }, 3000);
            }
        });
    }

    private List<Object> loadData() {
        List<Object> data = new ArrayList<>();
        data.add(counter);
        data.add("No." + counter);
        data.add(counter % 2 == 0);
        counter++;

        data.add(counter);
        data.add("No." + counter);
        data.add(counter % 2 == 0);
        counter++;

        data.add(counter);
        data.add("No." + counter);
        data.add(counter % 2 == 0);
        counter++;

        return data;
    }

    public static class StringBinder extends ViewBinder<String> {
        public StringBinder() {
            super(R.layout.layout_item_string);
        }

        @Override
        public void bindData(String data) {
            TextView tv = find(R.id.tv_value);
            tv.setText(data);
        }
    }
}