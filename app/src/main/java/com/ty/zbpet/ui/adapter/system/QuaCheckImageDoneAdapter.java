package com.ty.zbpet.ui.adapter.system;

import android.content.Context;

import com.ty.zbpet.bean.system.QualityCheckDoneDetails;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * @author TY on 2018/12/13.
 * <p>
 * 质检 Rc 中图片 列表
 */
public class QuaCheckImageDoneAdapter extends CommonAdapter<QualityCheckDoneDetails.ListBean> {


    public QuaCheckImageDoneAdapter(Context context, int layoutId, List<QualityCheckDoneDetails.ListBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, QualityCheckDoneDetails.ListBean imageBean, int position) {

    }
}
