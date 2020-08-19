package org.karic.sample;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import org.karic.sample.binder.BooleanBinder;
import org.karic.sample.binder.IntegerBinder;
import org.karic.sample.binder.StringBinder;
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

        ViewBinder<Integer> intBinder = new IntegerBinder();
        intBinder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "int click:" + v.getTag(), Toast.LENGTH_SHORT).show();
            }
        });
        adapter.register(Integer.class, intBinder);

        ViewBinder<String> strBinder = new StringBinder();
        strBinder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "String click:" + v.getTag(), Toast.LENGTH_SHORT).show();
            }
        });
        adapter.register(String.class, strBinder);

        ViewBinder<Boolean> boolBinder = new BooleanBinder();
        boolBinder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "bool click:" + v.getTag(), Toast.LENGTH_SHORT).show();
            }
        });
        adapter.register(Boolean.class, boolBinder);

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
}