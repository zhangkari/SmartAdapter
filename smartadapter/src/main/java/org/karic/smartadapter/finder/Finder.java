package org.karic.smartadapter.finder;

import android.view.View;
import androidx.annotation.IdRes;

public interface Finder {
    View getRoot();
    <T extends View> T find(@IdRes int id);
}