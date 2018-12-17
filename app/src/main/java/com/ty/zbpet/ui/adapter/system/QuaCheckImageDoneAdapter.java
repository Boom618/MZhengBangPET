package com.ty.zbpet.ui.adapter.system;

import android.content.Context;

import com.ty.zbpet.bean.system.QualityCheckDoneDetails;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * @author TY on 2018/12/13.
 *
 * 质检 Rc 中图片 列表
 *
 */
public class QuaCheckImageDoneAdapter extends CommonAdapter<QualityCheckDoneDetails.ImageBean> {


    public QuaCheckImageDoneAdapter(Context context, int layoutId, List<QualityCheckDoneDetails.ImageBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, QualityCheckDoneDetails.ImageBean imageBean, int position) {

    }
}
