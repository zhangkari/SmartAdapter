package org.karic.smartadapter.linker;

import android.util.ArrayMap;

import org.karic.smartadapter.ViewBinder;

import java.util.ArrayList;
import java.util.List;

public class TypeLinker implements Linker {
    private ArrayMap<Class, ViewBinder> map = new ArrayMap<>(16);
    private List<Class<?>> types = new ArrayList<>(16);

    @Override
    public void register(Class<?> clazz, ViewBinder h) {
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

    @Override
    public ViewBinder getBinder(Class<?> clazz) {
        return map.get(clazz);
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