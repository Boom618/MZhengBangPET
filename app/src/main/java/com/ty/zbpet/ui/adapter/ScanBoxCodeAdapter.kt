package com.ty.zbpet.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import com.ty.zbpet.R
import com.ty.zbpet.util.ZBUiUtils
import com.zhy.adapter.recyclerview.CommonAdapter
import com.zhy.adapter.recyclerview.base.ViewHolder
import kotlinx.android.synthetic.main.item_box_code.view.*


/**
 * @author TY
 */
class ScanBoxCodeAdapter(mContext: Context, layout: Int, info: MutableList<String>)
    : CommonAdapter<String>(mContext, layout, info) {

    override fun convert(holder: ViewHolder, codeString: String, position: Int) {

        holder.setText(R.id.tv_box_code, codeString)

    }
}
