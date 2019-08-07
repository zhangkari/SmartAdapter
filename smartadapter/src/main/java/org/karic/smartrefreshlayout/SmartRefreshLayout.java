package org.karic.smartrefreshlayout;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TableRow;
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

        if (loadingMore) {
            mAdapter.addData(new VMFooterLoading());
        } else {
            mAdapter.removeLast();
        }
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
            mAdapter = (SmartAdapter) mRecyclerView.getAdapter();
        } catch (Exception e) {
            throw new RuntimeException("must set SmartAdapter in RecyclerView !");
        }
    }

    @Override
    public void onStopNestedScroll(View target) {
        super.onStopNestedScroll(target);

        if (mLoadListener != null && mAdapter != null &&
                hasScrolledToBottom(mRecyclerView)) {

            Log.d(TAG, "onStopNestedScroll mLoadComplete:" + mLoadComplete);
            if (mLoadComplete) {
                setLoadComplete(true);
            } else {
                setLoadingMore(true);
                mLoadListener.onLoadMore();
            }
        }
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
