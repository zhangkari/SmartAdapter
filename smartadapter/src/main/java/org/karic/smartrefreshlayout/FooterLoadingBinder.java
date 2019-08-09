package org.karic.smartrefreshlayout;

import org.karic.smartadapter.R;
import org.karic.smartadapter.ViewBinder;

class FooterLoadingBinder extends ViewBinder<VMFooterLoading> {
    public FooterLoadingBinder() {
        super(R.layout.sa_view_binder_loading);
    }

    @Override
    public void bindData(VMFooterLoading data) {
        if (data.text != null) {
            setText(R.id.tv_text, data.text);
        }
    }
}
