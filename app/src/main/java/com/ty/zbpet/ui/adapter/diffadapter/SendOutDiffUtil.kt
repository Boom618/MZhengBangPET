package com.ty.zbpet.ui.adapter.diffadapter

import android.support.v7.util.DiffUtil

import com.ty.zbpet.bean.product.ProductDetails

/**
 * @author TY on 2018/12/4.
 *
 *
 * 发货出库 待办详情 添加按钮
 */
class SendOutDiffUtil(private val oldList: List<ProductDetails.ListBean>?, private val newList: List<ProductDetails.ListBean>?) : DiffUtil.Callback() {


    override fun getOldListSize(): Int {
        return oldList?.size ?: 0
    }

    override fun getNewListSize(): Int {
        return newList?.size ?: 0
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {

        return oldList!![oldItemPosition].javaClass == newList!![newItemPosition].javaClass
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {

        val oldBean = oldList!![oldItemPosition]
        val newBean = newList!![newItemPosition]

        return oldBean == newBean
    }
}
