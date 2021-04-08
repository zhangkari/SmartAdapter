package org.karic.smartrefreshlayout;

import org.karic.smartadapter.R;
import org.karic.smartadapter.ViewBinder;

import java.util.List;

class FooterLoadingBinder extends ViewBinder<VMFooterLoading> {
    public FooterLoadingBinder() {
        super(R.layout.sa_view_binder_loading);
    }

    @Override
    public void bindData(VMFooterLoading data, int position, List<Object> payloads) {
        if (data.text != null) {
            setText(R.id.tv_text, data.text);
        }
    }
}
