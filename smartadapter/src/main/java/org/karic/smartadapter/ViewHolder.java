package org.karic.smartadapter;

import android.view.View;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public final class ViewHolder extends RecyclerView.ViewHolder {
    private ViewBinder binder;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public ViewHolder setBinder(ViewBinder binder) {
        this.binder = binder;
        return this;
    }

    public ViewBinder getBinder() {
        return binder;
    }
}