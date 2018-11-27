package com.ty.zbpet.ui.adapter.material;

import android.content.Context;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;

import com.ty.zbpet.R;
import com.ty.zbpet.bean.PickOutDoneDetailsData;
import com.ty.zbpet.bean.material.MaterialDetailsOut;
import com.ty.zbpet.util.ACache;
import com.ty.zbpet.util.CodeConstant;
import com.ty.zbpet.util.ZBUiUtils;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * @author TY on 2018/11/22.
 *
 * 领料出库 已办详情
 */
public class BackGoodsDoneDetailAdapter extends CommonAdapter<MaterialDetailsOut.ListBean> {


    public BackGoodsDoneDetailAdapter(Context context, int layoutId, List<MaterialDetailsOut.ListBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, MaterialDetailsOut.ListBean list, final int position) {

        EditText number = holder.itemView.findViewById(R.id.et_number);
        EditText batchNo = holder.itemView.findViewById(R.id.et_batch_no);
        number.setInputType(InputType.TYPE_NULL);
        batchNo.setInputType(InputType.TYPE_NULL);

        holder.setText(R.id.tv_name, list.getMaterialName())
                .setText(R.id.tv_num, " 数量 + 单位 ")
                .setText(R.id.tv_box_num, "含量： ？")
                .setText(R.id.tv_box_num_unit, "ZKG ：? ")
                .setText(R.id.bulk_num, "库存量：? ")
                .setText(R.id.et_code, "库位码 ? ")
                .setText(R.id.et_number, "数量 ？ ")
                .setText(R.id.et_batch_no, "SAP 物料：? ");

    }


}
