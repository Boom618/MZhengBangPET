package com.ty.zbpet.bean.eventbus.system

import android.widget.ImageView

/**
 * @author TY on 2019/3/27.
 */
class ImageEvent(private val view: ImageView) {

    fun getImageView(): ImageView {
        return view
    }
}