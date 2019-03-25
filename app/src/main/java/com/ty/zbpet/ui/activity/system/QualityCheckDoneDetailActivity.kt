package com.ty.zbpet.ui.activity.system

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.ty.zbpet.R
import com.ty.zbpet.bean.CarPositionNoData
import com.ty.zbpet.bean.material.MaterialDetails
import com.ty.zbpet.constant.CodeConstant
import com.ty.zbpet.presenter.material.MaterialPresenter
import com.ty.zbpet.presenter.material.MaterialUiListInterface
import com.ty.zbpet.ui.adapter.LayoutInit
import com.ty.zbpet.ui.adapter.system.GridImageAdapter
import com.ty.zbpet.ui.adapter.system.QuaCheckDoneDetailAdapter
import com.ty.zbpet.ui.base.BaseActivity
import com.ty.zbpet.ui.widght.SpaceItemDecoration
import com.ty.zbpet.util.ResourceUtil
import com.ty.zbpet.util.ZBUiUtils
import kotlinx.android.synthetic.main.activity_content_qua_row_two.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author TY on 2018/12/12.
 */
class QualityCheckDoneDetailActivity : BaseActivity(), MaterialUiListInterface<MaterialDetails.ListBean> {

    private var gridLayoutManager: GridLayoutManager? = null
    private var sapOrderNo: String? = null
    private var supplierName: String? = null
    private var warehouseId: String? = null
    private var orderId: String? = null
    private lateinit var selectTime: String

    private var adapter: QuaCheckDoneDetailAdapter? = null

    private var themeId: Int = 0
    private val chooseMode = PictureMimeType.ofImage()

    private var imageAdapter: GridImageAdapter? = null
    private var selectList: MutableList<LocalMedia> = ArrayList()
    private val temp = ArrayList<LocalMedia>()

    private var listBeans: List<MaterialDetails.ListBean> = ArrayList()
    private val presenter = MaterialPresenter(this)

    override val activityLayout: Int
        get() = R.layout.activity_content_qua_row_two

    private val onAddPicClickListener = GridImageAdapter.onAddPicClickListener {
        // 进入相册 以下是例子：不需要的api可以不写
        PictureSelector.create(this@QualityCheckDoneDetailActivity)
                .openGallery(chooseMode)
                .theme(themeId)
                .maxSelectNum(3)
                .minSelectNum(1)
                .selectionMode(PictureConfig.SINGLE)
                // 是否显示拍照按钮
                .isCamera(true)
                // 是否传入已选图片
                //                        .selectionMedia(selectList)
                // 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                .previewEggs(false)
                // 小于100kb的图片不压缩
                .minimumCompressSize(100)
                //结果回调onActivityResult code
                .forResult(PictureConfig.CHOOSE_REQUEST)
    }

    override fun onBaseCreate(savedInstanceState: Bundle?) {

    }

    override fun initOneData() {

        sapOrderNo = intent.getStringExtra("sapOrderNo")
        supplierName = intent.getStringExtra("supplierName")
        warehouseId = intent.getStringExtra("warehouseId")
        orderId = intent.getStringExtra("orderId")

        //presenter.fetchQualityCheckDoneInfo()
        // 质检图片样式
        themeId = R.style.picture_default_style

        gridLayoutManager = GridLayoutManager(this, 3)
        gridLayoutManager!!.orientation = LinearLayoutManager.VERTICAL
        recycler_image.layoutManager = gridLayoutManager
        imageAdapter = GridImageAdapter(this@QualityCheckDoneDetailActivity, onAddPicClickListener)

    }

    override fun initTwoView() {

        val format = SimpleDateFormat(CodeConstant.DATE_SIMPLE_H_M, Locale.CHINA)
        selectTime = format.format(Date())

        tv_time.text = selectTime
        in_storage_detail.text = "质检明细"

        initToolBar(R.string.label_check)

    }

    override fun showMaterial(list: MutableList<MaterialDetails.ListBean>) {

        listBeans = list
        if (adapter == null) {

            LayoutInit.initLayoutManager(ResourceUtil.getContext(), rv_in_storage_detail)
            rv_in_storage_detail.addItemDecoration(SpaceItemDecoration(ResourceUtil.dip2px(10), false))

            adapter = QuaCheckDoneDetailAdapter(this, R.layout.item_sys_qua_check, list)
            rv_in_storage_detail.adapter = adapter
        }

    }


    override fun showCarSuccess(position: Int, carData: CarPositionNoData?) {
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun saveSuccess() {
        finish()
    }

    override fun showError(msg: String?) {
        ZBUiUtils.showToast(msg)
    }


    //========================  onActivityResult ======================

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                PictureConfig.CHOOSE_REQUEST -> {
                    // 图片、视频、音频选择结果回调
                    temp.addAll(selectList)
                    selectList = PictureSelector.obtainMultipleResult(data)
                    val path = selectList[0].path
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true  注意：音视频除外
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true  注意：音视频除外
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                    selectList.addAll(temp)
                    presenter.updateImage(this@QualityCheckDoneDetailActivity, selectList.size - 1, path)
                    imageAdapter!!.setList(selectList)
                    temp.clear()
                    imageAdapter!!.notifyDataSetChanged()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.dispose()
    }
}
