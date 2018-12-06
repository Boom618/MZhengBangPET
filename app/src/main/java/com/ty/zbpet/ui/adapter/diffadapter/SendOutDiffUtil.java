package com.ty.zbpet.ui.adapter.diffadapter;

import android.support.v7.util.DiffUtil;

import com.ty.zbpet.bean.product.ProductTodoDetails;

import java.util.List;

/**
 * @author TY on 2018/12/4.
 * <p>
 * 发货出库 待办详情 添加按钮
 */
public class SendOutDiffUtil extends DiffUtil.Callback {

    private List<ProductTodoDetails.ListBean> oldList, newList;

    public SendOutDiffUtil(List<ProductTodoDetails.ListBean> oldList, List<ProductTodoDetails.ListBean> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }


    @Override
    public int getOldListSize() {
        return oldList == null ? 0 : oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList == null ? 0 : newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {

        return oldList.get(oldItemPosition).getClass().equals(newList.get(newItemPosition).getClass());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {

        ProductTodoDetails.ListBean oldBean = oldList.get(oldItemPosition);
        ProductTodoDetails.ListBean newBean = newList.get(newItemPosition);

        return oldBean.equals(newBean);
    }
}
