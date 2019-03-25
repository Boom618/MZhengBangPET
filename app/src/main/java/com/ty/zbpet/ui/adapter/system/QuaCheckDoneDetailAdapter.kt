package com.ty.zbpet.ui.adapter.system

import android.content.Context
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView

import com.ty.zbpet.R
import com.ty.zbpet.bean.material.MaterialDetails
import com.ty.zbpet.bean.system.QualityCheckDoneDetails
import com.ty.zbpet.ui.widght.SpaceItemDecoration
import com.ty.zbpet.util.ResourceUtil
import com.ty.zbpet.util.ZBUiUtils
import com.zhy.adapter.recyclerview.CommonAdapter
import com.zhy.adapter.recyclerview.base.ViewHolder

/**
 * @author TY on 2018/12/13.
 *
 *
 * 质检 已办详情
 */
class QuaCheckDoneDetailAdapter(context: Context, layoutId: Int, datas: List<MaterialDetails.ListBean>) : CommonAdapter<MaterialDetails.ListBean>(context, layoutId, datas) {

    override fun convert(holder: ViewHolder, listBean: MaterialDetails.ListBean, position: Int) {
        holder.setText(R.id.tv_name, listBean.materialName)
                .setText(R.id.tv_num, listBean.number + listBean.unit)

    }
}
