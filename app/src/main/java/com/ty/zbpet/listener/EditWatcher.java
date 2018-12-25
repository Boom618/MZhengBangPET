package com.ty.zbpet.listener;

import android.text.Editable;
import android.text.TextWatcher;

import com.ty.zbpet.util.CodeConstant;
import com.ty.zbpet.util.DataUtils;
import com.ty.zbpet.util.ZBUiUtils;

/**
 * 输入框数据保存
 *
 * @author TY
 */

public class EditWatcher implements TextWatcher {

    /**
     * 输入框类型 和 位置
     */
    private int type;
    private int position;

    public EditWatcher(int position, int type) {
        this.type = type;
        this.position = position;

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

        String temp = s.toString().trim();

        if (CodeConstant.ET_PERCENT_INT == type) {
            DataUtils.setPercent(position, temp);
        } else if (CodeConstant.ET_CONTENT_INT == type) {
            DataUtils.setContent(position, temp);
        } else if (CodeConstant.ET_ZKG_INT == type) {
            DataUtils.setZkg(position, temp);
        } else if (CodeConstant.ET_NUMBER_INT == type) {
            DataUtils.setNumber(position, temp);
        } else if (CodeConstant.ET_CODE_INT == type) {
            DataUtils.setCode(position, temp);
        } else if (CodeConstant.ET_START_CODE_INT == type) {
            DataUtils.setStartCode(position, temp);
        } else if (CodeConstant.ET_END_CODE_INT == type) {
            DataUtils.setEndCode(position, temp);
        } else if (CodeConstant.ET_SAP_INT == type) {
            DataUtils.setSap(position, temp);
        }
    }
}