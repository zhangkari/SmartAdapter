package org.karic.smartadapter.finder;

import android.app.Activity;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class ViewFinder implements Finder {
    private View root;

    public ViewFinder(@NonNull View view) {
        root = view;
    }

    public ViewFinder(@NonNull Activity activity) {
        root = activity.findViewById(android.R.id.content);
    }

    public ViewFinder(@NonNull Fragment fragment) {
        View view = fragment.getView();
        if (view == null) {
            throw new NullPointerException("fragment must has View");
        }
        root = view;
    }

    @Override
    public View getRoot() {
        return root;
    }

    @Override
    public <T extends View> T find(int id) {
        return root.findViewById(id);
    }
}