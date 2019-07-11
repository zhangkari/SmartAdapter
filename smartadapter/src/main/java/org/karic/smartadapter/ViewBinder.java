package org.karic.smartadapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.LayoutRes;
import org.karic.smartadapter.finder.Finder;
import org.karic.smartadapter.finder.ViewFinder;

public abstract class ViewBinder<Bean> {
    private int layout;
    protected Finder finder;

    public ViewBinder(@LayoutRes int layout) {
        this.layout = layout;
    }

    View inflate(ViewGroup parent) {
        finder = new ViewFinder(LayoutInflater.from(parent.getContext()).inflate(layout, parent, false));
        return finder.getRoot();
    }

    public abstract void bindData(Bean data);
}
