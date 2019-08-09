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

    protected int layout;
    protected View view;

    public ViewBinder(@LayoutRes int layout) {
        this.layout = layout;
    }

    @SuppressWarnings("unchecked")
    private static <Binder extends ViewBinder> Binder reflectDefault(Binder binder) {
        try {
            Constructor constructor = binder.getClass().getDeclaredConstructor();
            constructor.setAccessible(true);
            return (Binder) constructor.newInstance();
        } catch (Exception e) {
            Log.e(TAG, "reflect default constructor failed!");
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    private static <Binder extends ViewBinder> Binder reflectParent(Binder binder) {
        try {
            Constructor constructor = binder.getClass().getDeclaredConstructor(int.class);
            constructor.setAccessible(true);
            return (Binder) constructor.newInstance(binder.layout);
        } catch (Exception e) {
            Log.e(TAG, "reflect init(int) failed!");
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public static <Binder extends ViewBinder> Binder clone(Binder binder) {
        Binder instance = reflectDefault(binder);
        if (instance == null) {
            instance = reflectParent(binder);
        }
        if (instance == null) {
            Log.e(TAG, "clone error: use the default instance");
            instance = binder;
        }
        return instance;
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

    public void setHint(@IdRes int id, CharSequence text) {
        TextView tv = find(id);
        tv.setHint(text);
    }

    public void setHint(@IdRes int id, @StringRes int resId) {
        TextView tv = find(id);
        tv.setHint(resId);
    }

    public void setVisible(@IdRes int id, int visibility) {
        find(id).setVisibility(visibility);
    }

    public void setOnClickListener(@IdRes int id, View.OnClickListener listener) {
        find(id).setOnClickListener(listener);
    }

    public void setEnabled(@IdRes int id, boolean enabled) {
        find(id).setEnabled(enabled);
    }
}
