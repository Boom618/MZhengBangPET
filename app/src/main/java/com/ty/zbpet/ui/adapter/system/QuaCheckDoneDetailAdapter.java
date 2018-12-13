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
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * @author TY on 2018/12/13.
 * <p>
 * 质检 已办详情
 */
public class QuaCheckDoneDetailAdapter extends CommonAdapter<QualityCheckDoneDetails.ListBean> {


    private Context context;
    private RecyclerView recyclerView;
    private ImageView addImage;
    private GridLayoutManager gridLayoutManager;


    public QuaCheckDoneDetailAdapter(Context context, int layoutId, List datas) {
        super(context, layoutId, datas);
        this.context = context;

        gridLayoutManager = new GridLayoutManager(context, 3);
        gridLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
    }

    @Override
    protected void convert(ViewHolder holder, QualityCheckDoneDetails.ListBean listBean, int position) {

        List<QualityCheckDoneDetails.ImageBean> imageList = listBean.getImageList();

        int size = imageList.size();
        if (size > 0) {
            recyclerView = holder.itemView.findViewById(R.id.rc_image);
            recyclerView.addItemDecoration(new SpaceItemDecoration(ResourceUtil.dip2px(5), true));
            addImage = holder.itemView.findViewById(R.id.add_image);
            addImage.setVisibility(View.GONE);
            recyclerView.setLayoutManager(gridLayoutManager);
            QuaCheckImageAdapter adapter = new QuaCheckImageAdapter(context, R.layout.item_sys_qua_check_image, imageList);
            recyclerView.setAdapter(adapter);
        } else {
            recyclerView.setVisibility(View.GONE);
            addImage.setVisibility(View.GONE);
        }


    }
}
