package org.karic.smartadapter;

import java.util.List;

public class DefaultViewBinder extends ViewBinder<Object> {
    public DefaultViewBinder() {
        super(R.layout.sa_view_binder_default);
    }

    @Override
    public void bindData(Object data, int position, List<Object> payloads) {
    }
}