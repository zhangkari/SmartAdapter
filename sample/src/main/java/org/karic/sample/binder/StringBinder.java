package org.karic.sample.binder;

import android.widget.TextView;

import org.karic.sample.R;
import org.karic.smartadapter.ViewBinder;

import java.util.List;

public class StringBinder extends ViewBinder<String> {
    public StringBinder() {
        super(R.layout.layout_item_string);
    }

    @Override
    public void bindData(String data, int position, List<Object> payloads) {
        TextView tv = find(R.id.tv_value);
        tv.setText(data);
        view.setTag(data);
        view.setOnClickListener(listenerInfo.onClickListener);
    }
}