package com.ty.zbpet.util

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.ty.zbpet.ui.widght.NormalAlertDialog

/**
 * @author TY on 2019/1/8.
 */
object DialogUtil {


    /**
     * 关闭软键盘
     *
     * @param context
     * @param view
     */
    fun hideInputWindow(context: Context, view: View) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    /**
     * 发货出库
     * 删除该 item
     *
     * @param titleText  标题 "温馨提示"
     * @param pointContent 内容
     */
    @JvmStatic
    fun deleteItemDialog(
        context: Context,
        titleText: String,
        pointContent: String,
        listener: NormalAlertDialog.onNormalOnclickListener
    ) {
        val dialog = NormalAlertDialog.Builder(context)
            .setTitleVisible(true)
            .setTitleText(titleText)
            .setRightButtonText("确认")
            .setLeftButtonText("取消")
            .setContentText(pointContent)
            .setRightListener(listener)
            .setLeftListener { dialog -> dialog.dismiss() }
            .build()

        dialog.show()
    }
}