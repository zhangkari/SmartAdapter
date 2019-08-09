package org.karic.sample;

import android.os.Handler;
import android.os.Looper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class DataModel {
    private static int counter = 0;

    public static void loadData(final OnLoadCallback cb) {
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                final List<Object> data = getData();
                executeOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        cb.onSuccess(data);
                    }
                });
            }
        });
    }


    private static void executeOnUiThread(Runnable runnable) {
        new Handler(Looper.getMainLooper()).post(runnable);
    }

    private static List<Object> getData() {
        List<Object> data = new ArrayList<>();
        data.add(counter);
        data.add("No." + counter);
        data.add(counter % 2 == 0);
        counter++;

        data.add(counter);
        data.add("No." + counter);
        data.add(counter % 2 == 0);
        counter++;

        data.add(counter);
        data.add("No." + counter);
        data.add(counter % 2 == 0);
        counter++;

        try {
            Thread.sleep(3000);
        } catch (Exception e) {

        }

        return data;
    }

    public interface OnLoadCallback {
        void onSuccess(List<Object> data);
    }
}
