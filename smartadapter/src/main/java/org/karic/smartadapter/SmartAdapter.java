package org.karic.smartadapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.karic.smartadapter.linker.Linker;
import org.karic.smartadapter.linker.TypeLinker;
import org.karic.smartrefreshlayout.VMFooterComplete;
import org.karic.smartrefreshlayout.VMFooterLoading;

import java.util.ArrayList;
import java.util.List;

public class SmartAdapter extends RecyclerView.Adapter {
    private List<Object> mData;
    private Linker mLinker;

    public SmartAdapter() {
        mData = new ArrayList<>();
        mLinker = new TypeLinker();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewBinder binder;
        if (viewType < 0) {
            return new ViewHolder(new DefaultViewBinder());
        } else {
            binder = mLinker.getBinder(mLinker.getTypeByIndex(viewType));
            if (binder == null) {
                return new ViewHolder(new DefaultViewBinder());
            }
        }
        binder = ViewBinder.fetch(binder);
        binder.onCreate(parent);
        return new ViewHolder(binder);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.binder.bindData(mData.get(position));
    }

    public void setData(List<?> data) {
        refreshData(data, false);
    }

    @SuppressWarnings("unchecked")
    public void refreshData(List data, boolean keepOld) {
        if (!keepOld) {
            mData.clear();
        }
        if (data != null) {
            mData.addAll(data);
        }
        notifyDataSetChanged();
    }

    public void addData(Object obj) {
        mData.add(obj);
        notifyDataSetChanged();
    }

    public Object getLast() {
        if (mData.size() >= 1) {
            return mData.get(mData.size() - 1);
        }
        return null;
    }

    public void removeLast() {
        if (mData.size() >= 1) {
            mData.remove(mData.size() - 1);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public int getItemViewType(int position) {
        Class<?> cls = mData.get(position).getClass();
        return mLinker.indexOfType(cls);
    }

    public void register(Class cls, ViewBinder holder) {
        mLinker.register(cls, holder);
    }

    public void clear(Class cls) {
        mLinker.clear();
    }

    public void registerLoadingMore(ViewBinder<VMFooterLoading> vb) {
        mLinker.register(VMFooterLoading.class, vb);
    }

    public void registerLoadComplete(ViewBinder<VMFooterComplete> vb) {
        mLinker.register(VMFooterComplete.class, vb);
    }
}