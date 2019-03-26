package com.ty.zbpet.ui.widght

import android.animation.Animator
import android.content.Context
import android.animation.ValueAnimator
import android.view.View
import com.dyhdyh.widget.loading.bar.LoadingBar


/**
 * @author TY on 2019/3/26.
 * 全屏加载 dialog
 */
class Loading {

    fun fullLoading(context: Context,view:View){
        LoadingBar.make(view)
        val animator = ValueAnimator.ofInt(0, 100).setDuration(2000)
        animator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationEnd(animation: Animator?) {
            }

            override fun onAnimationRepeat(animation: Animator?) {
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationStart(animation: Animator?) {
            }

        })
        animator.start()
    }
}