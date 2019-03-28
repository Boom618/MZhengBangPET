package com.ty.zbpet.ui.adapter.system

import android.content.Context
import com.ty.zbpet.R
import com.ty.zbpet.bean.eventbus.system.CheckDoneDetailEvent
import com.ty.zbpet.bean.material.MaterialDetails
import com.zhy.adapter.recyclerview.CommonAdapter
import com.zhy.adapter.recyclerview.base.ViewHolder

/**
 * @author TY on 2018/12/13.
 *
 *
 * 质检 已办详情
 */
class QuaCheckDoneDetailAdapter(context: Context, layoutId: Int, datas: List<CheckDoneDetailEvent.CheckReportListBean>)
    : CommonAdapter<CheckDoneDetailEvent.CheckReportListBean>(context, layoutId, datas) {

    override fun convert(holder: ViewHolder, listBean: CheckDoneDetailEvent.CheckReportListBean, position: Int) {
        holder.setText(R.id.tv_name, listBean.materialName)
                .setText(R.id.tv_num, "${listBean.checkNum} ${listBean.unit}")
                .setText(R.id.et_content, "${listBean.percent}")

    }
}
