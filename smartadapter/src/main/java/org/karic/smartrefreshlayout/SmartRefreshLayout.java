package org.karic.smartrefreshlayout;

import android.content.Context;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import org.karic.smartadapter.SmartAdapter;

import java.util.List;

public class SmartRefreshLayout extends SwipeRefreshLayout {
    private static final String TAG = "SmartRefreshLayout";

    private RecyclerView mRecyclerView;
    private SmartAdapter mAdapter;
    private OnLoadMoreListener mLoadListener;
    private boolean mLoadComplete;
    private boolean mSetupFlag;
    private boolean mIsLoading;

    public SmartRefreshLayout(@NonNull Context context) {
        super(context);
    }

    public SmartRefreshLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void setOnLoadMoreListener(OnLoadMoreListener listener) {
        mLoadListener = listener;
    }

    @Override
    public void setRefreshing(boolean refreshing) {
        super.setRefreshing(refreshing);
        mLoadComplete = false;
    }

    public void refreshComplete(List<?> data, OnDataAvailableListener listener) {
        setRefreshing(false);
        setLoadCompleteSafe(false);
        if (listener != null) {
            listener.onDataAvailable(data, mAdapter);
        }
    }

    public void loadMoreComplete(List<?> data, OnDataAvailableListener listener) {
        setLoadingMoreSafe(false);
        if (listener != null) {
            listener.onDataAvailable(data, mAdapter);
        }
        setLoadCompleteSafe(Utils.isEmpty(data));
    }

    @Deprecated
    public void setLoadingMore(boolean loadingMore) {
        setLoadingMoreSafe(loadingMore);
    }

    private void setLoadingMoreSafe(boolean loadingMore) {
        if (Looper.getMainLooper() != Looper.myLooper()) {
            throw new RuntimeException("Do not invoke me outside main-thread !");
        }

        mIsLoading = loadingMore;
        if (mRecyclerView == null || mAdapter == null) {
            return;
        }

        if (loadingMore) {
            Object obj = mAdapter.getLast();
            if (!(obj instanceof VMFooterLoading)) {
                mAdapter.addData(new VMFooterLoading());
            }
        } else {
            Object obj = mAdapter.getLast();
            if (obj instanceof VMFooterLoading) {
                mAdapter.removeLast();
            }
        }
    }

    private void addLoadingMore() {
        Object obj = mAdapter.getLast();
        if (!(obj instanceof VMFooterLoading)) {
            mAdapter.addData(new VMFooterLoading());
        }
    }

    private void removeLoadingMore() {
        Object obj = mAdapter.getLast();
        if (obj instanceof VMFooterLoading) {
            mAdapter.removeLast();
        }
    }

    private void addLoadComplete() {
        Object obj = mAdapter.getLast();
        if (!(obj instanceof VMFooterComplete)) {
            mAdapter.addData(new VMFooterComplete());
        }
    }

    private void removeLoadComplete() {
        Object obj = mAdapter.getLast();
        if (obj instanceof VMFooterComplete) {
            mAdapter.removeLast();
        }
    }

    @Deprecated
    public void setLoadComplete(boolean loadComplete) {
        setLoadCompleteSafe(loadComplete);
    }

    private void setLoadCompleteSafe(boolean loadComplete) {
        if (Looper.getMainLooper() != Looper.myLooper()) {
            throw new RuntimeException("Do not invoke me outside main-thread !");
        }

        mLoadComplete = loadComplete;
        if (mRecyclerView == null || mAdapter == null) {
            return;
        }

        if (loadComplete) {
            mAdapter.addData(new VMFooterComplete());
        } else {
            if (mAdapter.getLast() instanceof VMFooterComplete) {
                mAdapter.removeLast();
            }
        }
    }

    @Override
    public void onMeasure(int widthSpec, int heightSpec) {
        super.onMeasure(widthSpec, heightSpec);
        findRecyclerView();
        findSmartAdapter();
    }

    private void findRecyclerView() {
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            if (view instanceof RecyclerView) {
                mRecyclerView = (RecyclerView) view;
                break;
            }
        }
    }

    private void findSmartAdapter() {
        if (mRecyclerView == null) {
            return;
        }
        try {
            if (!mSetupFlag) {
                mAdapter = (SmartAdapter) mRecyclerView.getAdapter();
            }
            mSetupFlag = true;
            mAdapter.register(VMFooterComplete.class, new FooterCompleteBinder(), false);
            mAdapter.register(VMFooterLoading.class, new FooterLoadingBinder(), false);
        } catch (Exception e) {
            throw new RuntimeException("must set SmartAdapter in RecyclerView !");
        }

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 && hasScrolledToBottom(recyclerView) && !mIsLoading && !mLoadComplete) {
                    if (mLoadListener != null) {
                        mLoadListener.onLoadMore();
                    }
                    setLoadingMore(true);
                }
            }
        });
    }

    private static boolean hasScrolledToBottom(RecyclerView recyclerView) {
        if (recyclerView == null) {
            return false;
        }
        return recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset() >= recyclerView.computeVerticalScrollRange();
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    public interface OnDataAvailableListener {
        void onDataAvailable(List<?> data, SmartAdapter adapter);
    }
}
