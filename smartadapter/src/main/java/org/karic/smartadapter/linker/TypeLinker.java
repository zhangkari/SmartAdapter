package org.karic.smartadapter.linker;

import android.util.ArrayMap;

import org.karic.smartadapter.ViewBinder;

import java.util.ArrayList;
import java.util.List;

public class TypeLinker implements Linker {
    private ArrayMap<Class<?>, ViewBinder<?>> map = new ArrayMap<>(16);
    private List<Class<?>> types = new ArrayList<>(16);

    @Override
    public <T> void register(Class<T> clazz, ViewBinder<T> h) {
        map.put(clazz, h);
        types.add(clazz);
    }

    @Override
    public void clear() {
        map.clear();
        types.clear();
        map = null;
        types = null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> ViewBinder<T> getBinder(Class<T> clazz) {
        return (ViewBinder<T>) map.get(clazz);
    }

    @Override
    public int indexOfType(Class<?> bean) {
        return types.indexOf(bean);
    }

    @Override
    public Class<?> getTypeByIndex(int index) {
        return types.get(index);
    }
}