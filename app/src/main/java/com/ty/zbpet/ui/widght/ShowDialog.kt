package com.ty.zbpet.ui.widght

import android.content.Context
import com.xiasuhuei321.loadingdialog.view.LoadingDialog

/**
 * @author TY on 2019/3/28.
 */
object ShowDialog {

    fun showFullDialog(context: Context,tip:String) :LoadingDialog{
      val dialog =   LoadingDialog(context).setLoadingText(tip)
                .setSuccessText("加载成功")
                .setInterceptBack(false)
                .setLoadSpeed(LoadingDialog.Speed.SPEED_TWO)
                .closeSuccessAnim()
                .setRepeatCount(1)

        dialog.show()
        return dialog
    }
}