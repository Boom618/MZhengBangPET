package com.ty.zbpet.ui.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.PictureMimeType
import com.qingmei2.rximagepicker.core.RxImagePicker
import com.qingmei2.rximagepicker_extension.MimeType
import com.qingmei2.rximagepicker_extension_zhihu.ZhihuConfigurationBuilder
import com.ty.zbpet.R
import com.ty.zbpet.bean.eventbus.system.ImageEvent
import com.ty.zbpet.ui.adapter.system.RecyclerImageAdapter
import com.ty.zbpet.ui.base.BaseActivity
import com.ty.zbpet.util.DataUtils
import com.ty.zbpet.util.ZBUiUtils
import com.ty.zbpet.util.image.ZhiHuImagePicker
import kotlinx.android.synthetic.main.activity_demo.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * @author TY on 2019/3/27.
 */
class DemoMainActivity : BaseActivity() {

    private var adapter: RecyclerImageAdapter? = null

    override val activityLayout: Int
        get() = R.layout.activity_demo

    override fun onBaseCreate(savedInstanceState: Bundle?) {
        EventBus.getDefault().register(this)
    }

    override fun initOneData() {

        openGallery.setOnClickListener { openGallery(image) }
        openCamera.setOnClickListener { openCamera(image) }
        list.add("http://f.hiphotos.baidu.com/image/pic/item/9c16fdfaaf51f3de19e15a669aeef01f3a297965.jpg")
        list.add("http://d.hiphotos.baidu.com/image/pic/item/4e4a20a4462309f7dbef7b047c0e0cf3d7cad620.jpg")
        DataUtils.saveImage("9c16fdfaaf51f3de19e15a669aeef01f3a297965.jpg")
        DataUtils.saveImage("4e4a20a4462309f7dbef7b047c0e0cf3d7cad620.jpg")
    }
    val list = ArrayList<String>()
    override fun initTwoView() {

        val layoutManager = GridLayoutManager(this, 3)
        layoutManager.orientation = LinearLayoutManager.VERTICAL

        recycler_demo.layoutManager = layoutManager
        adapter = RecyclerImageAdapter(this@DemoMainActivity, list)
        recycler_demo.adapter = adapter

    }

    private val rxImagePicker = RxImagePicker.create(ZhiHuImagePicker::class.java)

    @SuppressLint("CheckResult")
    private fun openGallery(imageView: ImageView) {

        rxImagePicker.openGallery(this, ZhihuConfigurationBuilder(MimeType.ofImage(), false)
                .maxSelectable(1)
                .countable(true)
//                .spanCount(1)
                .theme(R.style.Zhihu_Dracula)
                .build())
                .subscribe {
                    Glide.with(this@DemoMainActivity)
                            .load(it.uri)
                            .into(imageView)
                }
    }

    @SuppressLint("CheckResult")
    private fun openCamera(imageView: ImageView) {
        rxImagePicker.openCamera(this).subscribe {
            Glide.with(this@DemoMainActivity)
                    .load(it.uri)
                    .into(imageView)
        }

    }

    @SuppressLint("CheckResult")
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun ImageEvent(event: ImageEvent) {


        PictureSelector.create(this)
                .openGallery(PictureMimeType.ofImage())
                .isCamera(true)
                .selectionMode(1)
                .forResult(PictureConfig.CHOOSE_REQUEST)



    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                PictureConfig.CHOOSE_REQUEST -> {
                    // 图片、视频、音频选择结果回调
                    val selectList = PictureSelector.obtainMultipleResult(data)
                    val path = selectList[0].path
                    ZBUiUtils.showToast(path)
                    list.add(path)
                    adapter?.notifyDataSetChanged()
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true  注意：音视频除外
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true  注意：音视频除外
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
//                    selectList.addAll(temp)
                    //presenter.updateImage(this@QualityCheckTodoDetailActivity, selectList.size - 1, path)
//                    imageAdapter!!.setList(selectList)
//                    temp.clear()
//                    imageAdapter!!.notifyDataSetChanged()
                    val imageList = DataUtils.getImagePathList()
                }
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }
}