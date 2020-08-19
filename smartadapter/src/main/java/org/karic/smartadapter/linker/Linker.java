package org.karic.smartadapter.linker;

import org.karic.smartadapter.ViewBinder;

public interface Linker {
    <T> void register(Class<T> clazz, ViewBinder<T> d);

    <T> ViewBinder<T> getBinder(Class<T> clazz);

    int indexOfType(Class<?> clazz);

    Class<?> getTypeByIndex(int index);

    void clear();
}