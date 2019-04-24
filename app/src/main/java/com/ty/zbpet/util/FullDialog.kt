package com.ty.zbpet.util

import android.content.Context

/**
 * @author TY on 2019/4/23.
 */
object FullDialog {


    fun dialogLoading(context: Context, msg: String): LoadingDialog {
        val dialog = LoadingDialog.Builder(context)
                .setMessage(msg)
                .setCancelable(false)
                .create()

        dialog.show()
        return dialog
    }


}