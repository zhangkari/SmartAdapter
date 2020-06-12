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

import java.util.List;

public class MainActivity extends AppCompatActivity {
    SmartRefreshLayout refreshLayout;
    SmartAdapter adapter;
    List<Object> data;

    @Override
    public void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_main);

        refreshLayout = findViewById(R.id.refreshLayout);
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
                refreshData();
            }
        });

        refreshLayout.setOnLoadMoreListener(new SmartRefreshLayout.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                loadMore();
            }
        });

        refreshData();
    }

    private void refreshData() {
        DataModel.loadData(new DataModel.OnLoadCallback() {
            @Override
            public void onSuccess(List<Object> data) {
                refreshLayout.refreshComplete(data, new SmartRefreshLayout.OnDataAvailableListener() {
                    @Override
                    public void onDataAvailable(List<?> data, SmartAdapter adapter) {
                        adapter.refreshData(data, false);
                    }
                });
            }
        });
    }

    private void loadMore() {
        DataModel.loadData(new DataModel.OnLoadCallback() {
            @Override
            public void onSuccess(List<Object> data) {
                refreshLayout.loadMoreComplete(data, new SmartRefreshLayout.OnDataAvailableListener() {
                    @Override
                    public void onDataAvailable(List<?> data, SmartAdapter adapter) {
                        adapter.refreshData(data, true);
                    }
                });
            }
        });
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