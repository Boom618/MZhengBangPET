package com.ty.zbpet.ui.adapter.diffadapter

import android.support.v7.util.DiffUtil
import com.ty.zbpet.bean.TodoCarCodeData
import com.ty.zbpet.bean.material.MaterialDetails

/**
 * @author TY on 2018/12/29.
 */
class TodoCarCodeDiffUtil(private val mOldList: List<MaterialDetails.ListBean>?, private val mNewList: List<MaterialDetails.ListBean>?) : DiffUtil.Callback() {


    override fun getOldListSize(): Int {
        return mOldList?.size ?: 0
//        return mOldList?.size ?: 0
    }

    override fun getNewListSize(): Int {
        return mNewList?.size ?: 0
    }

    /**
     * 更新扫码成功的库位码,是当前 item
     *
     * areItemsTheSame 返回 true 才会去比较 areContentsTheSame 中的内容
     */
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return true
    }

    /**
     *
     * 比较库位码 ： positionNo  是否改变
     *
     * areContentsTheSame 返回 false 才会去比较 getChangePayload 中的内容
     */
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {

        return mOldList!![oldItemPosition].positionNo.equals(mNewList!![newItemPosition].positionNo)
    }

//    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
//        return super.getChangePayload(oldItemPosition, newItemPosition)
//    }
}