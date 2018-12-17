package com.ty.zbpet.ui.adapter.system;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.ty.zbpet.R;
import com.ty.zbpet.bean.system.QualityCheckDoneDetails;
import com.ty.zbpet.bean.system.QualityCheckTodoDetails;
import com.ty.zbpet.ui.widght.SpaceItemDecoration;
import com.ty.zbpet.util.ResourceUtil;
import com.ty.zbpet.util.ZBUiUtils;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author TY on 2018/12/13.
 * <p>
 * 质检 待办详情
 */
public class QuaCheckTodoDetailAdapter extends CommonAdapter<QualityCheckTodoDetails.DataBean> {

    private Context context;
    private RecyclerView recyclerView;
    private ImageView addImage;
    private GridLayoutManager gridLayoutManager;
    private List<String> imageList = new ArrayList<>();
    private QuaCheckImageTodoAdapter adapter;

    public QuaCheckTodoDetailAdapter(Context context, int layoutId, List datas) {
        super(context, layoutId, datas);

        this.context = context;

        gridLayoutManager = new GridLayoutManager(context, 3);
        gridLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
    }

    @Override
    protected void convert(ViewHolder holder, QualityCheckTodoDetails.DataBean dataBean, int position) {


        imageList.add("");
        imageList.add("");

        int size = 2;
        if (adapter == null) {
            if (size < 3) {
                recyclerView = holder.itemView.findViewById(R.id.rc_image);
                addImage = holder.itemView.findViewById(R.id.add_image);
                recyclerView.addItemDecoration(new SpaceItemDecoration(ResourceUtil.dip2px(5), true));
                recyclerView.setLayoutManager(gridLayoutManager);

                QuaCheckImageTodoAdapter adapter = new QuaCheckImageTodoAdapter(context, R.layout.item_sys_qua_check_image, imageList);
                recyclerView.setAdapter(adapter);

                addImage.setVisibility(View.VISIBLE);

                addImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // TODO 选择相册 刷新列表
                        ZBUiUtils.selectGalleryOrPhoto(v.getContext());
                    }
                });
            } else {
                addImage.setVisibility(View.GONE);
            }
        }

    }
}
