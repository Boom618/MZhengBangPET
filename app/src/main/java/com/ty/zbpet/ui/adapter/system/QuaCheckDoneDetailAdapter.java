package com.ty.zbpet.ui.adapter.system;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.ty.zbpet.R;
import com.ty.zbpet.bean.system.QualityCheckDoneDetails;
import com.ty.zbpet.ui.widght.SpaceItemDecoration;
import com.ty.zbpet.util.ResourceUtil;
import com.ty.zbpet.util.ZBUiUtils;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * @author TY on 2018/12/13.
 * <p>
 * 质检 已办详情
 */
public class QuaCheckDoneDetailAdapter extends CommonAdapter<QualityCheckDoneDetails.DataBean> {


    public QuaCheckDoneDetailAdapter(Context context, int layoutId, List datas) {
        super(context, layoutId, datas);

    }

    @Override
    protected void convert(ViewHolder holder, QualityCheckDoneDetails.DataBean listBean, int position) {

        List<String> pathList = listBean.getPathList();


    }
}
