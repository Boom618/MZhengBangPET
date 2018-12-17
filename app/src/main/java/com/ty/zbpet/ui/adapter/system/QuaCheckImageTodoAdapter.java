package com.ty.zbpet.ui.adapter.system;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.ty.zbpet.R;
import com.ty.zbpet.bean.system.QualityCheckDoneDetails;
import com.ty.zbpet.bean.system.QualityCheckTodoDetails;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * @author TY on 2018/12/13.
 *
 * 质检 Rc 中图片 列表
 *
 */
public class QuaCheckImageTodoAdapter extends CommonAdapter<String> {

    private Context context;


    public QuaCheckImageTodoAdapter(Context context, int layoutId, List<String> datas) {
        super(context, layoutId, datas);
        this.context = context;
    }


    @Override
    protected void convert(ViewHolder holder, String imageBean, int position) {




    }
}
