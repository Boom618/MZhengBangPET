package com.ty.zbpet.ui.adapter

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * @author TY on 2019/1/10.
 */
object LayoutInit {

    @JvmStatic
    fun initLayoutManager(context: Context, recycler_view: RecyclerView) {
        val layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        recycler_view.layoutManager = layoutManager
    }
}