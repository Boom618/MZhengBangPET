package com.ty.zbpet.listener

import android.text.Editable
import android.text.TextWatcher

import com.ty.zbpet.constant.CodeConstant
import com.ty.zbpet.util.DataUtils

/**
 * 输入框数据保存 / 输入框类型 和 位置
 *
 * @author TY
 */
class EditWatcher(private val position: Int,
                  private val type: Int) : TextWatcher {

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

    }

    override fun afterTextChanged(s: Editable) {

        val temp = s.toString().trim { it <= ' ' }

        when (type) {
            CodeConstant.ET_PERCENT_INT -> DataUtils.setPercent(position, temp)
            CodeConstant.ET_CONTENT_INT -> DataUtils.setContent(position, temp)
            CodeConstant.ET_ZKG_INT -> DataUtils.setZkg(position, temp)
            CodeConstant.ET_NUMBER_INT -> DataUtils.setNumber(position, temp)
            CodeConstant.ET_CODE_INT -> DataUtils.setCode(position, temp)
            CodeConstant.ET_START_CODE_INT -> DataUtils.setStartCode(position, temp)
            CodeConstant.ET_END_CODE_INT -> DataUtils.setEndCode(position, temp)
            CodeConstant.ET_SAP_INT -> DataUtils.setSap(position, temp)
        }
    }
}