package com.ty.zbpet.ui.adapter.material

import android.content.Context
import android.text.InputType
import android.view.View
import android.widget.EditText

import com.ty.zbpet.R
import com.ty.zbpet.bean.material.MaterialDetails
import com.ty.zbpet.util.ACache
import com.ty.zbpet.constant.CodeConstant
import com.ty.zbpet.util.ViewSetValue
import com.ty.zbpet.util.ZBUiUtils
import com.zhy.adapter.recyclerview.CommonAdapter
import com.zhy.adapter.recyclerview.base.ViewHolder

/**
 * @author TY on 2018/11/22.
 *
 *
 * 领料出库 待办详情
 */
class BackGoodsTodoDetailAdapter(private val context: Context, layoutId: Int, datas: List<MaterialDetails.ListBean>)
    : CommonAdapter<MaterialDetails.ListBean>(context, layoutId, datas) {


    internal var listener = mContext as SaveEditListener

    override fun convert(holder: ViewHolder, list: MaterialDetails.ListBean, position: Int) {
        holder.setText(R.id.tv_name, list.materialName)
                .setText(R.id.tv_num, list.orderNumber + "  " + list.unitS)
                .setText(R.id.tv_box_num, "含量：" + list.concentration + "%")
                .setText(R.id.tv_box_num_unit, "ZKG：" + list.ZKG!!)
                .setText(R.id.bulk_num, "库存量：" + list.stockNumber!!)

        // 1、库位码
        val etCode = holder.itemView.findViewById<EditText>(R.id.et_code)
        // TYPE_NULL 禁止手机软键盘  TYPE_CLASS_TEXT : 开启软键盘。
        etCode.inputType = InputType.TYPE_NULL
        // ScanObservable.scanBox 扫码成功保存的 ID 和 Value
        val result = ACache.get(context).getAsString(CodeConstant.SCAN_BOX_KEY)

        // 库位码设置
        ViewSetValue.setValue(result, position, etCode)

        etCode.onFocusChangeListener = View.OnFocusChangeListener { view, hasFocus ->
            // 关闭软键盘
            ZBUiUtils.hideInputWindow(context, view)
            // 焦点改变 接口回调
            listener.saveEditAndGetHasFocusPosition(CodeConstant.ET_CODE, hasFocus, position, etCode)
        }

        // 2、数量
        val etNumber = holder.itemView.findViewById<EditText>(R.id.et_number)

        etNumber.onFocusChangeListener = EditTextOnFocusChangeListener(CodeConstant.ET_BULK_NUM, position, etNumber)

        // 3、Sap NO
        val etSapNo = holder.itemView.findViewById<EditText>(R.id.et_batch_no)
        etSapNo.onFocusChangeListener = EditTextOnFocusChangeListener(CodeConstant.ET_BATCH_NO, position, etSapNo)
    }


    /**
     * 输入框 Focus Listener
     */
    internal inner class EditTextOnFocusChangeListener(private val etType: String, private val position: Int, private val editText: EditText) : View.OnFocusChangeListener {

        override fun onFocusChange(view: View, hasFocus: Boolean) {

            if (CodeConstant.ET_BATCH_NO == etType && !hasFocus) {
                // 关闭软键盘
                ZBUiUtils.hideInputWindow(context, view)
            }


            listener.saveEditAndGetHasFocusPosition(etType, hasFocus, position, editText)
        }
    }

    /**
     * 列表输入框处理：焦点、位置、内容
     */
    interface SaveEditListener {

        /**
         * 输入框的处理
         *
         * @param etType   输入框标识
         * @param hasFocus 有无焦点
         * @param position 位置
         * @param editText 内容
         */
        fun saveEditAndGetHasFocusPosition(etType: String, hasFocus: Boolean?, position: Int, editText: EditText)

    }
}
