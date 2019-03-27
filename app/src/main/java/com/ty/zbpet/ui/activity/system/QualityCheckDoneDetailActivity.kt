package com.ty.zbpet.ui.activity.system

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.ty.zbpet.R
import com.ty.zbpet.bean.eventbus.ErrorMessage
import com.ty.zbpet.bean.eventbus.SuccessMessage
import com.ty.zbpet.bean.eventbus.system.CheckDoneDetailEvent
import com.ty.zbpet.bean.system.QuaCheckModify
import com.ty.zbpet.constant.CodeConstant
import com.ty.zbpet.net.RequestBodyJson
import com.ty.zbpet.presenter.material.MaterialPresenter
import com.ty.zbpet.ui.adapter.LayoutInit
import com.ty.zbpet.ui.adapter.system.GridImageAdapter
import com.ty.zbpet.ui.adapter.system.QuaCheckDoneDetailAdapter
import com.ty.zbpet.ui.base.BaseActivity
import com.ty.zbpet.ui.widght.SpaceItemDecoration
import com.ty.zbpet.util.DataUtils
import com.ty.zbpet.util.ResourceUtil
import com.ty.zbpet.util.ZBUiUtils
import kotlinx.android.synthetic.main.activity_content_qua_row_two.*
import okhttp3.RequestBody
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author TY on 2018/12/12.
 * 质检已办详情
 */
class QualityCheckDoneDetailActivity : BaseActivity() {

    private var gridLayoutManager: GridLayoutManager? = null
    private var id: String? = null
    private var sapOrderNo: String? = null
    private var fileName: String = ""
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
    private val httpUrlRemote = "http://117.40.132.236:3099/"
    private val httpUrlLocal = "http://192.168.11.2:3099/"

    private var listBeans: List<CheckDoneDetailEvent.CheckReportListBean> = ArrayList()
    private val presenter = MaterialPresenter()

    override val activityLayout: Int
        get() = R.layout.activity_content_qua_row_two


    override fun onBaseCreate(savedInstanceState: Bundle?) {
        EventBus.getDefault().register(this)
    }

    override fun initOneData() {

        id = intent.getStringExtra("id")
        sapOrderNo = intent.getStringExtra("sapOrderNo")
        supplierName = intent.getStringExtra("supplierName")
        fileName = intent.getStringExtra("fileName")
        warehouseId = intent.getStringExtra("warehouseId")
        orderId = intent.getStringExtra("orderId")


        presenter.fetchQualityCheckDoneInfo(id)
        // 质检图片样式
        themeId = R.style.picture_default_style

    }

    override fun initTwoView() {

        val format = SimpleDateFormat(CodeConstant.DATE_SIMPLE_H_M, Locale.CHINA)
        selectTime = format.format(Date())

        tv_time.text = selectTime
        in_storage_detail.text = "质检明细"

        initToolBar(R.string.check_update, "保存", View.OnClickListener {
            checkSave(initRequestBody())
        })
    }


    /**
     * 构建请求 RequestBody
     *
     * @return
     */
    private fun initRequestBody(): RequestBody? {

        val requestBody = QuaCheckModify()

        val infoBean = QuaCheckModify.MaterialCheckReportInfoBean()
        val list = ArrayList<QuaCheckModify.MaterialInfosBean>()

        val fileName = DataUtils.getImageFileName()
        var fileString = ""
        // fileName : 0d43f2c6a15f2587f81d23e6e3a2e5ae.jpg,da5c82971d620334025195f262733812.png
        for (i in 0 until fileName.size()) {
            val imageName = fileName.get(i)
            if (!TextUtils.isEmpty(imageName)) {
                fileString += "$imageName,"
            }
        }

        val size = listBeans.size

        for (i in 0 until size) {

            val child = rv_in_storage_detail.getChildAt(i)
            val content = child.findViewById<EditText>(R.id.et_content)

            val bean = QuaCheckModify.MaterialInfosBean()
            val dataBean = listBeans[i]
            val number = content.text.toString().trim { it <= ' ' }
            if (!TextUtils.isEmpty(number)) {

                bean.unit = dataBean.unit
                bean.materialNo = dataBean.materialNo
                bean.materialName = dataBean.materialName
                // 含量
                bean.percent = number
                list.add(bean)
            }
        }

        if (list.size == 0) {
            ZBUiUtils.showToast("请完善你要质检修改的信息")
            return null
        }

        val desc = et_desc.text.toString().trim { it <= ' ' }

        var tempFile = ""
        if (!TextUtils.isEmpty(fileString)) {
            tempFile = fileString.substring(0, fileString.length - 1)
        }
        infoBean.checkDesc = desc
        infoBean.checkTime = selectTime
        infoBean.sapOrderNo = sapOrderNo
        infoBean.fileName = tempFile

        requestBody.materialInfos = list
        requestBody.materialCheckReportInfo = infoBean

        val json = DataUtils.toJson(requestBody, 1)

        return RequestBodyJson.requestBody(json)

    }

    private fun checkSave(body: RequestBody?) {
        if (body == null) {
            return
        }
        presenter.quaCheckDoneSave(body)

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun showMaterial(event: CheckDoneDetailEvent) {

        val pathList = event.pathList!!
        initPath(pathList)
        listBeans = event.checkReportList!!
        if (adapter == null) {

            LayoutInit.initLayoutManager(ResourceUtil.getContext(), rv_in_storage_detail)
            rv_in_storage_detail.addItemDecoration(SpaceItemDecoration(ResourceUtil.dip2px(10), false))

            adapter = QuaCheckDoneDetailAdapter(this, R.layout.item_sys_qua_check, listBeans)
            rv_in_storage_detail.adapter = adapter
        }
    }

    private fun initPath(pathList: MutableList<String>) {

        //   图片服务器地址：117.40.132.236:3099
        for (i in 0 until pathList.size) {
            val phone = LocalMedia()
            phone.path = httpUrlLocal + pathList[i]
            selectList.add(phone)
        }

        val openGallery = PictureSelector.create(this@QualityCheckDoneDetailActivity)
        openGallery.openGallery(chooseMode)
                .selectionMedia(selectList)



        val onAddPicClickListener = GridImageAdapter.onAddPicClickListener {
            // 进入相册 以下是例子：不需要的api可以不写
            openGallery.openGallery(chooseMode)
                    .theme(themeId)
                    .maxSelectNum(3)
                    .minSelectNum(1)
                    .selectionMode(PictureConfig.SINGLE)
                    // 是否显示拍照按钮
                    .isCamera(true)
                    // 是否传入已选图片
                    .selectionMedia(selectList)
                    // 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                    .previewEggs(false)
                    // 小于100kb的图片不压缩
                    .minimumCompressSize(100)
                    //结果回调onActivityResult code
                    .forResult(PictureConfig.CHOOSE_REQUEST)
        }

        gridLayoutManager = GridLayoutManager(this, 3)
        gridLayoutManager!!.orientation = LinearLayoutManager.VERTICAL
        recycler_image.layoutManager = gridLayoutManager

        imageAdapter = GridImageAdapter(this@QualityCheckDoneDetailActivity, onAddPicClickListener)
        imageAdapter?.setList(selectList)
        imageAdapter?.setSelectMax(3)
        recycler_image.adapter = imageAdapter
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
                    presenter.updateImage(selectList.size - 1, path)
                    imageAdapter?.setList(selectList)
                    temp.clear()
                    imageAdapter?.notifyDataSetChanged()
                }
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun errorEvent(event: ErrorMessage) {
        ZBUiUtils.showToast(event.error())
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun successEvent(event: SuccessMessage) {
        ZBUiUtils.showToast(event.success())
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
        presenter.dispose()
    }
}
