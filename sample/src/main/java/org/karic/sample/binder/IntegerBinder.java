package org.karic.sample.binder;

import android.widget.TextView;

import org.karic.sample.R;
import org.karic.smartadapter.ViewBinder;

public class IntegerBinder extends ViewBinder<Integer> {
    public IntegerBinder() {
        super(R.layout.layout_item_int);
    }

    @Override
    public void bindData(Integer data) {
        TextView tv = find(R.id.tv_value);
        tv.setText(String.valueOf(data));
        view.setTag(data);
        view.setOnClickListener(listenerInfo.onClickListener);
    }
}
