package com.ty.zbpet.ui.activity.system

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.jaeger.library.StatusBarUtil
import com.ty.zbpet.R
import com.ty.zbpet.bean.eventbus.system.PersonCenterEvent
import com.ty.zbpet.presenter.user.UserInterface
import com.ty.zbpet.presenter.user.UserPresenter
import com.ty.zbpet.ui.ActivitiesHelper
import com.ty.zbpet.base.BaseActivity
import com.ty.zbpet.util.SimpleCache
import com.ty.zbpet.util.ZBUiUtils
import kotlinx.android.synthetic.main.activity_person_center_collapsing.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * 质检个人中心
 *
 * @author TY
 */
class PersonCenterActivity : BaseActivity(), UserInterface {

    private var isExit = false

    private val presenter = UserPresenter(this)

    override val activityLayout: Int
        get() = R.layout.activity_person_center_collapsing//collapsing

    override fun onBaseCreate(savedInstanceState: Bundle?) {
        EventBus.getDefault().register(this)

    }

    override fun initOneData() {

    }

    override fun onStart() {
        super.onStart()
        presenter.userCenter()
    }

    override fun initTwoView() {
        StatusBarUtil.setColor(this, StatusBarUtil.DEFAULT_STATUS_BAR_ALPHA)

//        iv_back.setOnClickListener { finish() }
        toolbar.setNavigationOnClickListener { finish() }

        center_appbar_layout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { _, verticalOffset ->
            //  负值
            if (verticalOffset <= -collapsing_toobar.height / 2) {
                collapsing_toobar.title = "个人中心"
            } else {
                collapsing_toobar.title = ""
            }
        })

        ll_track.setOnClickListener { gotoActivity(StockTrackActivity::class.java) }
        ll_position.setOnClickListener { gotoActivity(PositionQueryActivity::class.java) }
        btn_modify_pwd.setOnClickListener { gotoActivity(UserUpDataPass::class.java) }
        btn_cancel.setOnClickListener { exitApp() }
        loadCirclePic(this, image_heard)

    }

    //加载圆形图片
    fun loadCirclePic(context: Context, imageView: ImageView) {
        Glide.with(context)
                .asBitmap()
                .load(R.mipmap.demo_bg)
//                .diskCacheStrategy(DiskCacheStrategy.ALL) //设置缓存
                .into(object : BitmapImageViewTarget(imageView) {
                    override fun setResource(resource: Bitmap?) {
                        val circularBitmapDrawable = RoundedBitmapDrawableFactory.create(context.resources, resource)
                        circularBitmapDrawable.isCircular = true
                        imageView.setImageDrawable(circularBitmapDrawable)
                    }
                })

    }

    /**
     * 退出登录
     */
    private fun exitApp() {
        isExit = true
//        presenter.userLogOut()
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or
                Intent.FLAG_ACTIVITY_CLEAR_TOP or
                Intent.FLAG_ACTIVITY_NO_HISTORY
        startActivity(intent)

        SimpleCache.clearAll()
        ActivitiesHelper.get().finishAll()
        finish()
    }

    @SuppressLint("SetTextI18n")
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onUserDataEvnet(event: PersonCenterEvent) {
        name.text = "姓名：${event.userInfo?.name}"
        job_number.text = "公司编号：${event.companyInfo?.companyNo}"
        tv_belong_company.text = event.companyInfo?.companyName
        tv_phone.text = event.userInfo?.userName

    }


    override fun onSuccess() {
        if (isExit) {
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or
                    Intent.FLAG_ACTIVITY_CLEAR_TOP or
                    Intent.FLAG_ACTIVITY_NO_HISTORY
            startActivity(intent)

            SimpleCache.clearAll()
            ActivitiesHelper.get().finishAll()
            finish()
        } else {
            // 个人中心数据

        }

    }

    override fun onError(e: String) {
        ZBUiUtils.showError(e)

    }

    override fun showLoading() {

    }

    override fun hideLoading() {
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }
}
