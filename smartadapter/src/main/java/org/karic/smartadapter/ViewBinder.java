package org.karic.smartadapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.StringRes;
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

    @SuppressWarnings("unchecked")
    public <T extends View> T find(@IdRes int id) {
        return (T) finder.find(id);
    }

    public void setText(@IdRes int id, CharSequence text) {
        TextView tv = find(id);
        tv.setText(text);
    }

    public void setText(@IdRes int id, @StringRes int resId) {
        TextView tv = find(id);
        tv.setText(resId);
    }
}
