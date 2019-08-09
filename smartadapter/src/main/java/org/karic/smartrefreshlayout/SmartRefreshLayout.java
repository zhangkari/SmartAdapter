package org.karic.smartrefreshlayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import org.karic.smartadapter.SmartAdapter;

public class SmartRefreshLayout extends SwipeRefreshLayout {
    private static final String TAG = "SmartRefreshLayout";

    private RecyclerView mRecyclerView;
    private SmartAdapter mAdapter;
    private OnLoadMoreListener mLoadListener;
    private boolean mLoadComplete;
    private boolean mSetupFlag;
    private boolean mIsLoading;

    private int mLastY;
    private boolean mScrollDown;

    public SmartRefreshLayout(@NonNull Context context) {
        super(context);
    }

    public SmartRefreshLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void setOnLoadMoreListener(OnLoadMoreListener listener) {
        mLoadListener = listener;
    }

    public void setLoadingMore(boolean loadingMore) {
        if (mRecyclerView == null || mAdapter == null) {
            return;
        }

        mIsLoading = loadingMore;
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
        mLoadComplete = false;
    }

    public void setLoadComplete(boolean loadComplete) {
        mLoadComplete = loadComplete;
        if (mRecyclerView == null || mAdapter == null) {
            return;
        }

        if (loadComplete) {
            mAdapter.addData(new VMFooterComplete());
        } else {
            mAdapter.removeLast();
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
            mAdapter.register(VMFooterComplete.class, new FooterCompleteBinder());
            mAdapter.register(VMFooterLoading.class, new FooterLoadingBinder());
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
}
