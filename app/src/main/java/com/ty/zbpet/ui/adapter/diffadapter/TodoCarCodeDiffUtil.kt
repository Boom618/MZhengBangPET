package com.ty.zbpet.ui.adapter.diffadapter

import android.os.Bundle
import androidx.recyclerview.widget.DiffUtil
import com.ty.zbpet.bean.material.MaterialDetails

/**
 * @author TY on 2018/12/29.
 */
class TodoCarCodeDiffUtil(private val mOldList: List<MaterialDetails.ListBean>?, private val mNewList: List<MaterialDetails.ListBean>?) : DiffUtil.Callback() {


    override fun getOldListSize(): Int {
        return mOldList?.size ?: 0
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

    /**
     * getChangePayload()方法是在 areItemsTheSame()返回 true，而 areContentsTheSame()返回 false 时被回调的，
     * 也就是一个 Item 的内容发生了变化，而这个变化有可能是局部的（例如微博的点赞，我们只需要刷新图标而不是整个 Item ）。
     * 所以可以在 getChangePayload()中封装一个 Object 来告诉 RecyclerView 进行局部的刷新。
     *
     * 注意：返回的这个对象会在什么地方收到呢？实际上在 RecyclerView.Adapter 中有两个 onBindViewHolder 方法，
     * 一个是我们必须要重写的，而另一个的第三个参数就是一个 payload 的列表：
     */
    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Bundle {
        val listBean = mNewList?.get(newItemPosition)
        val diffBundle = Bundle()
        diffBundle.putString("positionNo",listBean?.positionNo)

        return diffBundle
    }
}