package com.ty.zbpet.listener

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

/**
 * @author TY on 2019/2/20.
 *
 * RecyclerView 加载更多
 */
abstract class LoadMoreListener : RecyclerView.OnScrollListener() {

    private var countItem  = 0
    private var lastItem  = 0
    private var isScolled = false
    private var layoutManager: RecyclerView.LayoutManager? = null

    protected abstract fun onLoading(countItem: Int, lastItem: Int)

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        isScolled = when (newState) {
            // 拖拽或者惯性滑动时 isScolled 设置为 true
            // SCROLL_STATE_IDLE,空闲 SCROLL_STATE_DRAGGING,拖拽 SCROLL_STATE_SETTLING,固定
            RecyclerView.SCROLL_STATE_DRAGGING -> true
            RecyclerView.SCROLL_STATE_SETTLING -> true
            else -> false
        }
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        if (recyclerView.layoutManager is LinearLayoutManager) {
            layoutManager = recyclerView.layoutManager
            countItem = layoutManager!!.itemCount
            lastItem = (layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()
        }
        if (isScolled && countItem != lastItem && lastItem == countItem - 1) {
            onLoading(countItem, lastItem)
        }

    }

}