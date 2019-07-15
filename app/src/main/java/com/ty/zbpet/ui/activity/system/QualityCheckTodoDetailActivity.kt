package com.ty.zbpet.ui.activity.system

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.ty.zbpet.R
import com.ty.zbpet.bean.CarPositionNoData
import com.ty.zbpet.bean.material.MaterialDetails
import com.ty.zbpet.bean.system.QuaCheckModify
import com.ty.zbpet.constant.CodeConstant
import com.ty.zbpet.net.RequestBodyJson
import com.ty.zbpet.presenter.material.MaterialPresenter
import com.ty.zbpet.presenter.material.MaterialUiListInterface
import com.ty.zbpet.ui.adapter.LayoutInit
import com.ty.zbpet.ui.adapter.system.GridImageAdapter
import com.ty.zbpet.ui.adapter.system.QuaCheckTodoDetailAdapter
import com.ty.zbpet.base.BaseActivity
import com.ty.zbpet.ui.widght.ShowDialog
import com.ty.zbpet.ui.widght.SpaceItemDecoration
import com.ty.zbpet.util.DataUtils
import com.ty.zbpet.util.ResourceUtil
import com.ty.zbpet.util.ZBUiUtils
import com.xiasuhuei321.loadingdialog.view.LoadingDialog
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_content_qua_row_two.*
import okhttp3.RequestBody
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author TY on 2018/12/12.
 * 质检待办详情
 */
class QualityCheckTodoDetailActivity : BaseActivity(), MaterialUiListInterface<MaterialDetails.ListBean> {

    private var gridLayoutManager: androidx.recyclerview.widget.GridLayoutManager? = null
    private var sapOrderNo: String? = null
    private var sapFirmNo: String? = null
    private var supplierNo: String? = null
    private var selectTime: String? = null

    private var adapter: QuaCheckTodoDetailAdapter? = null

    private var themeId: Int = 0
    private val chooseMode = PictureMimeType.ofImage()

    private var imageAdapter: GridImageAdapter? = null
    private var selectList: MutableList<LocalMedia> = ArrayList()
    private val temp = ArrayList<LocalMedia>()
    private val pathList = ArrayList<String>()

    /**
     * 中断请求
     */
    private val disposable: Disposable? = null
    private var listBeans: List<MaterialDetails.ListBean> = ArrayList()
    private val presenter = MaterialPresenter(this)

    override// 质检 单独 layout
    val activityLayout: Int
        get() = R.layout.activity_content_qua_row_two

    private val onAddPicClickListener = GridImageAdapter.onAddPicClickListener {
        // 进入相册 以下是例子：不需要的api可以不写
        PictureSelector.create(this@QualityCheckTodoDetailActivity)
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
        DataUtils.clearImagePath()
    }

    override fun initOneData() {

        sapOrderNo = intent.getStringExtra("sapOrderNo")
        sapFirmNo = intent.getStringExtra("sapFirmNo")
        supplierNo = intent.getStringExtra("supplierNo")

        // 原辅料采购 添加的 sign 标识字段
        presenter.fetchTODOMaterialDetails("",sapFirmNo, sapOrderNo, supplierNo)

        // 质检图片样式
        themeId = R.style.picture_default_style

        gridLayoutManager = androidx.recyclerview.widget.GridLayoutManager(this, 3)
        gridLayoutManager!!.orientation = androidx.recyclerview.widget.LinearLayoutManager.VERTICAL

        recycler_image.layoutManager = gridLayoutManager
        imageAdapter = GridImageAdapter(this@QualityCheckTodoDetailActivity, onAddPicClickListener)
        imageAdapter?.setList(selectList)
        imageAdapter?.setSelectMax(3)
        recycler_image.adapter = imageAdapter

    }

    override fun initTwoView() {

        val format = SimpleDateFormat(CodeConstant.DATE_SIMPLE_H_M, Locale.CHINA)
        selectTime = format.format(Date())

        tv_time.text = selectTime
        in_storage_detail.text = "质检明细"

        tv_time.setOnClickListener { v ->
            ZBUiUtils.showPickDate(v.context) { date, v ->
                selectTime = ZBUiUtils.getTime(date)
                tv_time.text = selectTime

                ZBUiUtils.showSuccess(selectTime)
            }
        }

        initToolBar(R.string.label_quality_check, "保存", View.OnClickListener { v ->
            ZBUiUtils.hideInputWindow(v.context, v)
            quaCheckTodoSave(initRequestBody())
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

        val fileName = DataUtils.getImagePathList()
        var fileString = ""
        // fileName : 0d43f2c6a15f2587f81d23e6e3a2e5ae.jpg,da5c82971d620334025195f262733812.png
        for (i in 0 until fileName.size) {
            val imageName = fileName[i]
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
            ZBUiUtils.showSuccess("请完善你要质检的信息")
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

    /**
     * 质检新增
     */
    private fun quaCheckTodoSave(body: RequestBody?) {

        if (body == null) {
            return
        }
        presenter.quaCheckTodoSave(body)

    }


    override fun showMaterial(list: List<MaterialDetails.ListBean>) {

        listBeans = list
        if (adapter == null) {
            LayoutInit.initLayoutManager(ResourceUtil.getContext(), rv_in_storage_detail)
            rv_in_storage_detail.addItemDecoration(SpaceItemDecoration(ResourceUtil.dip2px(10), false))
            adapter = QuaCheckTodoDetailAdapter(this, R.layout.item_sys_qua_check, list)
            rv_in_storage_detail.adapter = adapter
        }

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
                    imageAdapter!!.setList(selectList)
                    temp.clear()
                    imageAdapter!!.notifyDataSetChanged()
                }
            }
        }
    }

    override fun showCarSuccess(position: Int, carData: CarPositionNoData) {

    }

    private var dialog: LoadingDialog? = null
    override fun showLoading() {
        dialog = ShowDialog.showFullDialog(this@QualityCheckTodoDetailActivity, "保存中")
    }

    override fun hideLoading() {
        dialog?.close()
    }

    override fun saveSuccess() {
        finish()
    }

    override fun showError(msg: String) {
        ZBUiUtils.showError(msg)
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
