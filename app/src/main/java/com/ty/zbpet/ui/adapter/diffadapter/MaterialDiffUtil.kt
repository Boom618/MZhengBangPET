package com.ty.zbpet.ui.adapter.diffadapter

import android.support.v7.util.DiffUtil

/**
 * @author TY on 2018/11/27.
 *
 *
 * Different 扫码更新列表(箱码绑定)
 */
class MaterialDiffUtil(private val mOldList: List<String>?, private val mNewList: List<String>?) : DiffUtil.Callback() {


    override fun getOldListSize(): Int {
        return mOldList?.size ?: 0
    }

    override fun getNewListSize(): Int {
        return mNewList?.size ?: 0
    }

    /**
     * 比较 ID
     *
     * @param i
     * @param i1
     * @return
     */
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldList!![oldItemPosition].javaClass == mNewList!![newItemPosition].javaClass
    }

    /**
     * 比较 内容
     *
     * @param i
     * @param i1
     * @return
     */
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {

        val oldStr = mOldList!![oldItemPosition]
        val newStr = mNewList!![newItemPosition]

        return oldStr == newStr
    }

    /**
     * 需要更新的数据
     *
     * @param oldItemPosition
     * @param newItemPosition
     * @return
     */
    //    @Nullable
    //    @Override
    //    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
    //
    //
    //
    //
    //
    //        return super.getChangePayload(oldItemPosition, newItemPosition);
    //    }
}
