package org.karic.smartadapter;

import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import org.karic.smartadapter.linker.Linker;
import org.karic.smartadapter.linker.TypeLinker;

import java.util.ArrayList;
import java.util.List;

public class SmartAdapter extends RecyclerView.Adapter {
    private List<?> mData;
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
            binder = new DefaultViewBinder();
        } else {
            binder = mLinker.getBinder(mLinker.getTypeByIndex(viewType));
            if (binder == null) {
                binder = new DefaultViewBinder();
            }
        }

        return new ViewHolder(binder.inflate(parent)).setBinder(binder);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.getBinder().bindData(mData.get(position));
    }

    public void setData(List<?> data) {
        refreshData(data, true);
    }

    @SuppressWarnings("unchecked")
    public void refreshData(List data, boolean eraseOld) {
        if (eraseOld) {
            mData.clear();
        }
        if (data != null) {
            mData.addAll(data);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public int getItemViewType(int position) {
        Class<?> cls = mData.get(position).getClass();
        if (cls == null) {
            return -1;
        }
        return mLinker.indexOfType(cls);
    }

    public void register(Class cls, ViewBinder holder) {
        mLinker.register(cls, holder);
    }

    public void unregister(Class cls) {
        mLinker.unregister(cls);
    }
}