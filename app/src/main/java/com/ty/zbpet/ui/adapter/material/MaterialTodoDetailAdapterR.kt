package com.ty.zbpet.ui.adapter.material

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.text.InputType
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView

import com.ty.zbpet.R
import com.ty.zbpet.bean.material.MaterialDetailsIn
import com.ty.zbpet.listener.EditWatcher
import com.ty.zbpet.ui.adapter.viewholder.BottomViewHolder
import com.ty.zbpet.util.ACache
import com.ty.zbpet.constant.CodeConstant
import com.ty.zbpet.util.DataUtils
import com.ty.zbpet.util.ViewSetValue
import com.ty.zbpet.util.ZBUiUtils


/**
 * @author TY on 2018/11/5.
 *
 *
 * 到货入库 （待办 详情）
 */
class MaterialTodoDetailAdapterR(private val context: Context, private val lists: List<MaterialDetailsIn.ListBean>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var holder: RecyclerView.ViewHolder? = null

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val inflater = LayoutInflater.from(context)

        if (ONE_ITEM == viewType) {
            val view = inflater.inflate(R.layout.item_main_middle_todo, viewGroup, false)
            holder = MiddleViewHolder(view)
        } else {
            val view = inflater.inflate(R.layout.item_main_bottom_other, viewGroup, false)
            holder = BottomViewHolder(view)
        }
        return holder as RecyclerView.ViewHolder
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {

        val itemViewType = viewHolder.itemViewType

        if (itemViewType == ONE_ITEM) {

            val itemView = viewHolder.itemView
            val info = lists[position]

            val name = itemView.findViewById<TextView>(R.id.tv_name)
            name.text = info.materialName

            val tvNum = itemView.findViewById<TextView>(R.id.tv_num)
            tvNum.text = info.orderNumber!! + info.unitS!!

            val tvBoxNum = itemView.findViewById<TextView>(R.id.tv_box_num)
            tvBoxNum.text = "含量：" + info.concentration + "%"

            // 1 ZKG
            val zkg = itemView.findViewById<EditText>(R.id.et_zkg)
            zkg.addTextChangedListener(EditWatcher(position, CodeConstant.ET_ZKG_INT))

            // 2、库位码
            val etCode = itemView.findViewById<EditText>(R.id.et_code)
            etCode.addTextChangedListener(EditWatcher(position, CodeConstant.ET_CODE_INT))

            etCode.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus -> DataUtils.setCodeFocus(position, hasFocus) }
            val value = ACache.get(context).getAsString(CodeConstant.SCAN_BOX_KEY)

            ViewSetValue.setValue(value, position, etCode)

            // 3、入库数量
            val etInNum = itemView.findViewById<EditText>(R.id.et_bulk_num)
            etInNum.addTextChangedListener(EditWatcher(position, CodeConstant.ET_NUMBER_INT))

            // 4、sap 物料批次号
            val sap = itemView.findViewById<EditText>(R.id.et_batch_no)
            sap.addTextChangedListener(EditWatcher(position, CodeConstant.ET_SAP_INT))
        }
    }

    override fun getItemViewType(position: Int): Int {

        return if (TextUtils.isEmpty(lists[position].tag)) {
            ONE_ITEM
        } else TWO_ITEM
    }

    override fun getItemCount(): Int {
        return lists.size
    }

    /**
     * 中间 服务器的 list 列表
     */
    private class MiddleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        internal var tvName: TextView = itemView.findViewById(R.id.tv_name)
        internal var tvNum: TextView = itemView.findViewById(R.id.tv_num)
        internal var tvBoxNum: TextView = itemView.findViewById(R.id.tv_box_num)
        internal var rlDetail: View = itemView.findViewById(R.id.rl_detail)
        internal var ivArrow: ImageView = itemView.findViewById(R.id.iv_arrow)
        internal var zkg: EditText = itemView.findViewById(R.id.et_zkg)
        internal var etCode: EditText = itemView.findViewById(R.id.et_code)
        internal var etInNum: EditText = itemView.findViewById(R.id.et_bulk_num)
        internal var sap: EditText = itemView.findViewById(R.id.et_batch_no)

        init {

            // TYPE_NULL 禁止手机软键盘  TYPE_CLASS_TEXT : 开启软键盘。
            etCode.inputType = InputType.TYPE_NULL

            ivArrow.setOnClickListener { v ->
                if (rlDetail.visibility == View.VISIBLE) {
                    rlDetail.visibility = View.GONE
                    ivArrow.setImageResource(R.mipmap.ic_collapse)
                } else {
                    rlDetail.visibility = View.VISIBLE
                    ivArrow.setImageResource(R.mipmap.ic_expand)
                }

                ZBUiUtils.hideInputWindow(v.context, v)
            }
        }
    }

    companion object {

        val ONE_ITEM = 1
        val TWO_ITEM = 2
    }

}
