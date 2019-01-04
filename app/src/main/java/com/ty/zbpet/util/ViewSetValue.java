package com.ty.zbpet.util;

import android.text.TextUtils;
import android.widget.EditText;

/**
 * @author TY on 2018/12/3.
 */
public class ViewSetValue {

    /**
     * 库位码 扫码设值 【准备删除】
     *
     * @param result   拼接的 库位码
     * @param position adapter position
     * @param etCode   控件
     */
    @Deprecated
    public static void setValue(String result, int position, EditText etCode) {
        if (!TextUtils.isEmpty(result)) {
            String[] split = result.split("@");
            int id = Integer.valueOf(split[0]);
            if (position == id) {
                String value = split[1];
                etCode.setText(value);
            }
        }
    }
}
