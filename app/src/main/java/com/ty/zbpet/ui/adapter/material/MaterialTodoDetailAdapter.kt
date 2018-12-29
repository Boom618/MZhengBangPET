package com.ty.zbpet.ui.adapter.material

import android.content.Context
import android.text.InputType
import android.view.View
import android.widget.EditText
import android.widget.TextView

import com.ty.zbpet.R
import com.ty.zbpet.bean.material.MaterialDetails
import com.ty.zbpet.constant.CodeConstant
import com.ty.zbpet.util.ACache
import com.ty.zbpet.util.ViewSetValue
import com.ty.zbpet.util.ZBUiUtils
import com.zhy.adapter.recyclerview.CommonAdapter
import com.zhy.adapter.recyclerview.base.ViewHolder


/**
 * @author TY on 2018/11/5.
 *
 *
 * 到货入库 （待办 详情）
 */
class MaterialTodoDetailAdapter(private val context: Context, layoutId: Int, datas: List<MaterialDetails.ListBean>)
    : CommonAdapter<MaterialDetails.ListBean>(context, layoutId, datas) {


//    internal var listener = mContext as SaveEditListener

    override fun convert(holder: ViewHolder, info: MaterialDetails.ListBean, position: Int) {

        val itemView = holder.itemView

        val tvName = itemView.findViewById<TextView>(R.id.tv_name)
        tvName.text = info.materialName

        val tvNum = itemView.findViewById<TextView>(R.id.tv_num)
        tvNum.text = info.orderNumber!! + info.unitS!!

        val tvBoxNum = itemView.findViewById<TextView>(R.id.tv_box_num)
        tvBoxNum.text = "含量：" + info.concentration + "%"

        // 1 ZKG
        val zkg = itemView.findViewById<EditText>(R.id.et_zkg)
        zkg.setText(info.ZKG)

//        val zkgString = ACache.get(context).getAsString(CodeConstant.ET_ZKG)
//        ViewSetValue.setValue(zkgString, position, zkg)

//        zkg.onFocusChangeListener = EditTextOnFocusChangeListener(CodeConstant.ET_ZKG, position, zkg)

        // 2、库位码
        val etCode = itemView.findViewById<EditText>(R.id.et_code)
        // TYPE_NULL 禁止手机软键盘  TYPE_CLASS_TEXT : 开启软键盘。
        etCode.setText(info.positionNo)
        etCode.inputType = InputType.TYPE_NULL
        // ScanObservable.scanBox 扫码成功保存的 ID 和 Value
//        val value = ACache.get(context).getAsString(CodeConstant.SCAN_BOX_KEY)
////
//        ViewSetValue.setValue(value, position, etCode)


        etCode.onFocusChangeListener = View.OnFocusChangeListener { view, _ ->
            // 关闭软键盘
            ZBUiUtils.hideInputWindow(context, view)

            //listener.saveEditAndGetHasFocusPosition(CodeConstant.ET_CODE, hasFocus, position, etCode)
        }

//        etCode.tag = position

        // 3、入库数量
        val etInNum = itemView.findViewById<EditText>(R.id.et_bulk_num)
        // 通过设置tag，防止 position 紊乱
//        etInNum.tag = position
//        val numString = etInNum.text.toString().trim { it <= ' ' }
        etInNum.setText(info.orderNumber)
//        etInNum.onFocusChangeListener = EditTextOnFocusChangeListener(CodeConstant.ET_BULK_NUM, position, etInNum)

        // 4、sap 物料批次号
        val sap = itemView.findViewById<EditText>(R.id.et_batch_no)
//        sap.onFocusChangeListener = EditTextOnFocusChangeListener(CodeConstant.ET_BATCH_NO, position, sap)
    }


//    /**
//     * 输入框 Focus Listener
//     */
//    internal inner class EditTextOnFocusChangeListener(private val etType: String, private val position: Int, private val editText: EditText) : View.OnFocusChangeListener {
//
//        override fun onFocusChange(view: View, hasFocus: Boolean) {
//
//            if (CodeConstant.ET_BATCH_NO == etType && !hasFocus) {
//                // 关闭软键盘
//                ZBUiUtils.hideInputWindow(context, view)
//            }
//            listener.saveEditAndGetHasFocusPosition(etType, hasFocus, position, editText)
//
//        }
//    }
//
//    /**
//     * 列表输入框处理：焦点、位置、内容
//     */
//    interface SaveEditListener {
//
//        /**
//         * 输入框的处理
//         *
//         * @param etType   输入框标识  code  sap
//         * @param hasFocus 有无焦点
//         * @param position 位置
//         * @param editText 内容控件
//         */
//        fun saveEditAndGetHasFocusPosition(etType: String, hasFocus: Boolean?, position: Int, editText: EditText)
//
//    }
}
