package com.ty.zbpet.ui.adapter.product;

import android.content.Context;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;

import com.ty.zbpet.R;
import com.ty.zbpet.bean.PickOutTodoDetailsData;
import com.ty.zbpet.bean.product.ProductDetailsIn;
import com.ty.zbpet.util.ACache;
import com.ty.zbpet.util.CodeConstant;
import com.ty.zbpet.util.ZBUiUtils;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * @author TY on 2018/11/22.
 *
 * 外采入库 待办详情
 */
public class BuyInTodoDetailAdapter extends CommonAdapter<ProductDetailsIn.ListBean> {


    public BuyInTodoDetailAdapter(Context context, int layoutId, List<ProductDetailsIn.ListBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, ProductDetailsIn.ListBean list, final int position) {
        holder.setText(R.id.tv_name, list.getGoodsName())
                .setText(R.id.tv_num, list.getOrderNumber() + "  " + list.getUnitS());

    }


}
