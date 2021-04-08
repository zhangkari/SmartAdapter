package org.karic.smartrefreshlayout;

import org.karic.smartadapter.R;
import org.karic.smartadapter.ViewBinder;

import java.util.List;

class FooterCompleteBinder extends ViewBinder<VMFooterComplete> {
    public FooterCompleteBinder() {
        super(R.layout.sa_view_binder_complete);
    }

    @Override
    public void bindData(VMFooterComplete data, int position, List<Object> payloads) {
        if (data.text != null) {
            setText(R.id.tv_text, data.text);
        }
    }
}
