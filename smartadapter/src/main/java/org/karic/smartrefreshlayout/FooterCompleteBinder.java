package org.karic.smartrefreshlayout;

import org.karic.smartadapter.R;
import org.karic.smartadapter.ViewBinder;

class FooterCompleteBinder extends ViewBinder<VMFooterComplete> {
    public FooterCompleteBinder(int layout) {
        super(R.layout.sa_view_binder_complete);
    }

    @Override
    public void bindData(VMFooterComplete data) {
        if (data.text != null) {
            setText(R.id.tv_text, data.text);
        }
    }
}
