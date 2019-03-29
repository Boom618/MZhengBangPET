package com.ty.zbpet.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import com.ty.zbpet.R
import com.ty.zbpet.util.ZBUiUtils
import kotlinx.android.synthetic.main.item_box_code.view.*


/**
 * @author TY
 */
class BindBoxCodeAdapter(mContext: Context, private val info: MutableList<String>?) : RecyclerView.Adapter<BindBoxCodeAdapter.ItemHolder>() {
    private val mInflater: LayoutInflater = LayoutInflater.from(mContext)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
       val view = mInflater.inflate(R.layout.item_box_code, null)
        return ItemHolder(view!!)
    }

    override fun onBindViewHolder(itemHolder: ItemHolder, position: Int) {
        val data = info!![position]

        itemHolder.itemView.tv_box_code.text = data

        itemHolder.itemView.iv_del!!.setOnClickListener { deleteItem(position) }
    }

    override fun getItemCount(): Int {
        return info?.size ?: 0
    }

    class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            ButterKnife.bind(this, itemView)
        }
    }


    /**
     * 删除条目
     *
     * @param pos
     */
    private fun deleteItem(pos: Int) {
        if (pos == info!!.size) {
            ZBUiUtils.showWarning("请重新添加数据！")
            return
        }
        try {
            info.removeAt(pos)
            notifyItemRemoved(pos)
            //刷新列表，否则回出现删除错位
            if (pos != info.size) {
                notifyItemRangeChanged(pos, info.size - pos)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            ZBUiUtils.showWarning("角标越界异常！")
        }

    }
}
