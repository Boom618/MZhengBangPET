package com.ty.zbpet.ui.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import androidx.core.widget.ContentLoadingProgressBar
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.ty.zbpet.R


/**
 * @author TY on 2019/2/20.
 *
 * 添加 加载 Adapter
 */
class RecyclerViewFoot(private val context: Context, private val data: ArrayList<String>)
    : androidx.recyclerview.widget.RecyclerView.Adapter<androidx.recyclerview.widget.RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): androidx.recyclerview.widget.RecyclerView.ViewHolder {
        return if (viewType == TYPE_FOOTER) {
            val view = LayoutInflater.from(context).inflate(R.layout.activity_recyclerview_foot, parent, false)
            FootViewHolder(view)
        }else{
            val view = LayoutInflater.from(context).inflate(R.layout.activity_recyclerview_foot, parent, false)
            MainViewHolder(view)
        }
    }

    override fun getItemCount(): Int {
        return data.size + 1
    }

    override fun onBindViewHolder(holder: androidx.recyclerview.widget.RecyclerView.ViewHolder, position: Int) {

    }




    override fun getItemViewType(position: Int): Int {
        if (position == data.size) {
            return TYPE_FOOTER
        }
        return TYPE_CONTENT
    }

    private class MainViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView){

        init {
            itemView.findViewById<TextView>(R.id.id_ll_ok)
        }
    }

    /**
     * 加载更多 View
     */
    private inner class FootViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
        internal var contentLoadingProgressBar: ContentLoadingProgressBar = itemView.findViewById(R.id.pb_progress)

    }

    companion object {
        private const val TYPE_CONTENT = 0//正常内容
        private const val TYPE_FOOTER = 1//加载 View
    }

}