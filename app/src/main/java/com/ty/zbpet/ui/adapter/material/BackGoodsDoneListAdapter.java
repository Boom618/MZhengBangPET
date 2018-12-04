package com.ty.zbpet.ui.adapter.material;

import android.content.Context;
import android.view.View;

import com.ty.zbpet.R;
import com.ty.zbpet.bean.material.MaterialDoneList;
import com.ty.zbpet.bean.material.MaterialTodoList;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * @author TY on 2018/11/26.
 * <p>
 * 采购退货 已办列表
 */
public class BackGoodsDoneListAdapter extends CommonAdapter<MaterialDoneList.ListBean> {


    public BackGoodsDoneListAdapter(Context context, int layoutId, List<MaterialDoneList.ListBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, MaterialDoneList.ListBean list, int position) {
        holder.setText(R.id.tv_operator, "冲销")
                .setText(R.id.tv_no, list.getSapOrderNo())
                .setText(R.id.tv_type, list.getType() + "")
                .setText(R.id.tv_supplier, list.getSupplierName())
                .setText(R.id.tv_date, list.getInTime())
                .setText(R.id.tv_status, list.getState() + "");
    }

}
