package com.ty.zbpet.ui.activity.material

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.CheckBox
import com.ty.zbpet.R
import com.ty.zbpet.bean.CarPositionNoData
import com.ty.zbpet.bean.material.MaterialDetails
import com.ty.zbpet.bean.material.MaterialDoneSave
import com.ty.zbpet.constant.CodeConstant
import com.ty.zbpet.net.RequestBodyJson
import com.ty.zbpet.presenter.material.MaterialPresenter
import com.ty.zbpet.presenter.material.MaterialUiListInterface
import com.ty.zbpet.ui.adapter.material.MaterialDoneDetailAdapter
import com.ty.zbpet.base.BaseActivity
import com.ty.zbpet.ui.widght.ShowDialog
import com.ty.zbpet.ui.widght.SpaceItemDecoration
import com.ty.zbpet.util.DataUtils
import com.ty.zbpet.util.ResourceUtil
import com.ty.zbpet.util.ZBUiUtils
import com.xiasuhuei321.loadingdialog.view.LoadingDialog
import kotlinx.android.synthetic.main.activity_content_reversal.*
import okhttp3.RequestBody
import java.util.*

/**
 * @author TY on 2018/11/14.
 *
 *
 * 已办 详情
 */
class ArrivalInDoneDetailActivity : BaseActivity(), MaterialUiListInterface<MaterialDetails.ListBean> {
    override val activityLayout: Int
        get() = R.layout.activity_content_reversal

    private lateinit var orderId: String
    private lateinit var sapOrderNo: String
    private lateinit var supplierName: String

    private lateinit var listBean: List<MaterialDetails.ListBean>

    private lateinit var adapter: MaterialDoneDetailAdapter
    private val materialPresenter = MaterialPresenter(this)


    override fun onBaseCreate(savedInstanceState: Bundle?) {
    }

    override fun initOneData() {

        sapOrderNo = intent.getStringExtra("sapOrderNo")
        supplierName = intent.getStringExtra("supplierName")
        orderId = intent.getStringExtra("orderId")
    }

    override fun initTwoView() {

        initToolBar(R.string.purchase_house_reversal)
        bt_reversal.setOnClickListener { materialDoneInSave(initRequestBody()) }
    }

    /**
     * 已办 保存
     * @param body
     */
    private fun materialDoneInSave(body: RequestBody?) {

        if (body == null) {
            return
        }
        materialPresenter.materialDoneInSave(body)

    }

    /**
     * 初始化 请求参数
     *
     * @return
     */
    private fun initRequestBody(): RequestBody? {

        val data = MaterialDoneSave()
        val list = ArrayList<MaterialDoneSave.ListBean>()
        val size = listBean.size

        for (i in 0 until size ){
            val view = recycler_reversal.getChildAt(i)
            val checkBox = view.findViewById<CheckBox>(R.id.check)
            if (checkBox.isChecked) {
                val bean = MaterialDoneSave.ListBean()
                bean.id = listBean[i].id
                bean.orderId = orderId
                list.add(bean)
            }
        }

        if(list.size == 0){
            ZBUiUtils.showWarning("请选择您要冲销的列表")
            return null
        }

        data.list = list
        data.supplierName = supplierName
        data.moveType = "106"

        val json = DataUtils.toJson(data, 1)
        return RequestBodyJson.requestBody(json)

    }

    override fun onStart() {
        super.onStart()
        materialPresenter.fetchDoneMaterialDetails(orderId)
    }

    override fun showMaterial(list: List<MaterialDetails.ListBean>) {
        listBean = list

        val manager = LinearLayoutManager(ResourceUtil.getContext())
        recycler_reversal.addItemDecoration(SpaceItemDecoration(ResourceUtil.dip2px(CodeConstant.ITEM_DECORATION), false))
        recycler_reversal.layoutManager = manager

        // TODO 侧滑删除
        // detailRc.addOnItemTouchListener(new SwipeItemLayout.OnSwipeItemTouchListener(this));
        adapter = MaterialDoneDetailAdapter(this, R.layout.item_reversal_check, list)
        recycler_reversal.adapter = adapter

    }
    override fun showCarSuccess(position: Int, carData: CarPositionNoData?) {
    }

    override fun saveSuccess() {
        finish()
    }

    override fun showError(msg: String?) {
        ZBUiUtils.showError(msg)
    }

    private var dialog: LoadingDialog? = null
    override fun showLoading() {
        dialog = ShowDialog.showFullDialog(this@ArrivalInDoneDetailActivity, "保存中")
    }

    override fun hideLoading() {
        dialog?.close()
    }
}
