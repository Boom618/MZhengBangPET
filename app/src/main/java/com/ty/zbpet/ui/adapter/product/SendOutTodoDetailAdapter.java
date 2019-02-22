package com.ty.zbpet.ui.adapter.product;

import android.content.Context;
import android.view.View;
import android.widget.EditText;

import com.ty.zbpet.R;
import com.ty.zbpet.bean.product.ProductDetails;
import com.ty.zbpet.constant.CodeConstant;
import com.ty.zbpet.util.ZBUiUtils;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * @author TY on 2018/11/22.
 * <p>
 * 发货出库 待办详情
 */
public class SendOutTodoDetailAdapter extends CommonAdapter<ProductDetails.ListBean> {


    public SendOutTodoDetailAdapter(Context context, int layoutId, List<ProductDetails.ListBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, ProductDetails.ListBean list, final int position) {
        holder.setText(R.id.tv_name, list.getGoodsName())
                .setText(R.id.tv_select_ware, "商品名称：" + list.getGoodsName())
                .setText(R.id.btn_binding_code, "箱码出库")
                .setText(R.id.tv_send_number,"应发数量：" + list.getSendNumber())
                .setText(R.id.tv_num, list.getNumber() + list.getUnit());

    }



}
