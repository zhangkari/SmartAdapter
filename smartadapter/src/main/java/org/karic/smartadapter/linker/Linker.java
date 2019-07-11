package org.karic.smartadapter.linker;

import org.karic.smartadapter.ViewBinder;

public interface Linker {
    void register(Class<?> clazz, ViewBinder d);

    void unregister(Class<?> clazz);

    ViewBinder getBinder(Class<?> clazz);

    int indexOfType(Class<?> clazz);

    Class<?> getTypeByIndex(int index);
}