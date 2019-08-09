package org.karic.smartadapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.StringRes;

import java.lang.reflect.Constructor;

public abstract class ViewBinder<Bean> {
    static final String TAG = "ViewBinder";

    int layout;
    View view;

    public ViewBinder(@LayoutRes int layout) {
        this.layout = layout;
    }

    @SuppressWarnings("unchecked")
    public static <Binder extends ViewBinder> Binder clone(Binder binder) {
        try {
            Constructor constructor = binder.getClass().getDeclaredConstructor();
            constructor.setAccessible(true);
            return (Binder) constructor.newInstance();
        } catch (Exception e) {
            Log.e(TAG, "error:  !!!! " + e.toString());
            return binder;
        }
    }

    protected void onCreate(ViewGroup parent) {
        view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
    }

    public abstract void bindData(Bean data);

    @SuppressWarnings("unchecked")
    public <T extends View> T find(@IdRes int id) {
        return (T) view.findViewById(id);
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
