package org.karic.smartadapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public final class ViewHolder extends RecyclerView.ViewHolder {
    ViewBinder binder;

    public ViewHolder(@NonNull ViewBinder binder) {
        super(binder.view);
        this.binder = binder;
    }
}