package org.karic.smartadapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.StringRes;

import java.lang.reflect.Constructor;

public abstract class ViewBinder<Bean> {
    static final String TAG = "ViewBinder";

    int layout;
    View view;

    ListenerInfo listenerInfo;
    ViewHolder holder;

    public ViewBinder(@LayoutRes int layout) {
        this.layout = layout;
        listenerInfo = new ListenerInfo();
    }

    public static <Binder extends ViewBinder> Binder fetch(Binder binder) {
        if (binder.view == null) {
            return binder;
        } else {
            return clone(binder);
        }
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
    private static <Binder extends ViewBinder> Binder clone(Binder binder) {
        checkConstructor(binder.getClass());

        Binder instance = reflectDefault(binder);
        if (instance == null) {
            instance = reflectParent(binder);
        }
        if (instance == null) {
            Log.e(TAG, "clone error: use the default instance");
            throw new RuntimeException("clone ViewBinder failed !");
        }
        instance.listenerInfo = binder.listenerInfo;
        return instance;
    }

    private static void checkConstructor(Class<? extends ViewBinder> clazz) {
        Constructor[] constructors = clazz.getDeclaredConstructors();
        if (constructors.length != 1) {
            throw new RuntimeException("ViewBinder only allow 1 constructor !");
        }

        Constructor c = constructors[0];
        Class<?>[] params = c.getParameterTypes();

        if (params.length == 0) {
            return;
        }

        if (params.length == 1) {
            if (params[0] != int.class) {
                throw new RuntimeException("ViewBinder constructor can have only one parameter with type of @ResLayout int !");
            }
        }

        throw new RuntimeException("ViewBinder constructor can have one parameter at most !");
    }

    protected void onCreate(ViewGroup parent) {
        view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
    }

    protected abstract void bindData(Bean data);

    @SuppressWarnings("unchecked")
    protected <T extends View> T find(@IdRes int id) {
        return (T) view.findViewById(id);
    }

    protected void setText(@IdRes int id, CharSequence text) {
        TextView tv = find(id);
        tv.setText(text);
    }

    protected void setText(@IdRes int id, @StringRes int resId) {
        TextView tv = find(id);
        tv.setText(resId);
    }

    protected void setHint(@IdRes int id, CharSequence text) {
        TextView tv = find(id);
        tv.setHint(text);
    }

    protected void setHint(@IdRes int id, @StringRes int resId) {
        TextView tv = find(id);
        tv.setHint(resId);
    }

    protected void setVisible(@IdRes int id, int visibility) {
        find(id).setVisibility(visibility);
    }

    protected void setOnClickListener(@IdRes int id, View.OnClickListener listener) {
        find(id).setOnClickListener(listener);
    }

    protected void setEnabled(@IdRes int id, boolean enabled) {
        find(id).setEnabled(enabled);
    }

    protected void setOnClickListener(View.OnClickListener listener) {
        listenerInfo.onClickListener = listener;
    }

    protected void setOnCheckedChangedListener(RadioGroup.OnCheckedChangeListener listener) {
        listenerInfo.radioGroupCheckedListener = listener;
    }

    protected void setOnCheckedChangedListener(CompoundButton.OnCheckedChangeListener listener) {
        listenerInfo.onCheckedChangeListener = listener;
    }

    protected int getPosition() {
        return holder.getAdapterPosition();
    }

    protected View getView() {
        return view;
    }

    protected View.OnClickListener getOnClickListener() {
        return listenerInfo.onClickListener;
    }

    protected RadioGroup.OnCheckedChangeListener getRadioGroupCheckedChangeListener() {
        return listenerInfo.radioGroupCheckedListener;
    }

    protected CompoundButton.OnCheckedChangeListener getCheckedChangeListener() {
        return listenerInfo.onCheckedChangeListener;
    }

    static class ListenerInfo {
        View.OnClickListener onClickListener;
        RadioGroup.OnCheckedChangeListener radioGroupCheckedListener;
        CompoundButton.OnCheckedChangeListener onCheckedChangeListener;
    }
}
