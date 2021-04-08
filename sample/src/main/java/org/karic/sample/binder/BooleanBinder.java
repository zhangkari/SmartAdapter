package org.karic.sample.binder;

import android.widget.TextView;

import org.karic.sample.R;
import org.karic.smartadapter.ViewBinder;

import java.util.List;

public class BooleanBinder extends ViewBinder<Boolean> {
    public BooleanBinder() {
        super(R.layout.layout_item_boolean);
    }

    @Override
    public void bindData(Boolean data, int position, List<Object> payloads) {
        TextView tv = find(R.id.tv_value);
        tv.setText(String.valueOf(data));
        view.setTag(data);
        view.setOnClickListener(listenerInfo.onClickListener);
    }
}