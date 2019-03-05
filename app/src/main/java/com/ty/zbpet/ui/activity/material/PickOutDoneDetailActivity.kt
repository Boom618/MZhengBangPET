package com.ty.zbpet.ui.activity.material

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.CheckBox
import com.ty.zbpet.R
import com.ty.zbpet.bean.CarPositionNoData
import com.ty.zbpet.bean.ResponseInfo
import com.ty.zbpet.bean.material.MaterialDetails
import com.ty.zbpet.bean.material.MaterialDoneSave
import com.ty.zbpet.constant.CodeConstant
import com.ty.zbpet.net.HttpMethods
import com.ty.zbpet.net.RequestBodyJson
import com.ty.zbpet.presenter.material.MaterialUiListInterface
import com.ty.zbpet.presenter.material.PickOutPresenter
import com.ty.zbpet.ui.adapter.material.PickingDoneDetailAdapter
import com.ty.zbpet.ui.base.BaseActivity
import com.ty.zbpet.ui.widght.SpaceItemDecoration
import com.ty.zbpet.util.DataUtils
import com.ty.zbpet.util.ResourceUtil
import com.ty.zbpet.util.ZBUiUtils
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_content_reversal.*
import okhttp3.RequestBody

/**
 * @author TY on 2018/11/22.
 * 领料出库 已办详情
 */
class PickOutDoneDetailActivity : BaseActivity(), MaterialUiListInterface<MaterialDetails.ListBean> {
    override fun showCarSuccess(position: Int, carData: CarPositionNoData?) {

    }

    override fun showSuccess() {

    }

    override fun showError(msg: String?) {

    }

    private lateinit var adapter: PickingDoneDetailAdapter

    private lateinit var sapOrderNo: String
    private lateinit var orderId: String
    private lateinit var listBean: List<MaterialDetails.ListBean>

    private val presenter = PickOutPresenter(this)

    override val activityLayout: Int
        get() = R.layout.activity_content_reversal


    override fun onBaseCreate(savedInstanceState: Bundle?) {

    }

    override fun initOneData() {
        sapOrderNo = intent.getStringExtra("sapOrderNo")
        orderId = intent.getStringExtra("orderId")

        presenter.fetchPickOutDoneListDetails(orderId)
    }

    override fun initTwoView() {

        initToolBar(R.string.pick_out_storage_reversal)
        bt_reversal.setOnClickListener { pickOutDoneSave(initDoneBody()) }

    }

    /**
     * 出库 保存
     */
    private fun pickOutDoneSave(body: RequestBody?) {

        if (body == null) {
            return
        }

        HttpMethods.getInstance().pickOutDoneSave(object : SingleObserver<ResponseInfo> {
            override fun onError(e: Throwable) {
                ZBUiUtils.showToast(e.message)
            }

            override fun onSubscribe(d: Disposable) {

            }

            override fun onSuccess(responseInfo: ResponseInfo) {
                if (CodeConstant.SERVICE_SUCCESS == responseInfo.tag) {
                    // 入库成功（保存）
                    ZBUiUtils.showToast(responseInfo.message)
                    finish()
                } else {
                    ZBUiUtils.showToast(responseInfo.message)
                }
            }
        }, body)
    }


    /**
     * 构建保存 body
     * @return
     */
    private fun initDoneBody(): RequestBody? {

        val data = MaterialDoneSave()
        val list = ArrayList<MaterialDoneSave.ListBean>()
        val size = listBean.size

        for (i in 0 until size ){
            val view = recycler_reversal.getChildAt(i)
            val checkBox = view.findViewById<CheckBox>(R.id.check)
            if (checkBox.isChecked) {
                val bean = MaterialDoneSave.ListBean()
                bean.id = listBean[i].id
                list.add(bean)
            }
        }

        if(list.size == 0){
            ZBUiUtils.showToast("请选择您要冲销的列表")
            return null
        }

        data.list = list
        data.orderId = orderId
        data.moveType = "262"

        val json = DataUtils.toJson(data, 1)

        return RequestBodyJson.requestBody(json)
    }

    override fun showMaterial(list: List<MaterialDetails.ListBean>) {
        if (list.isEmpty()) {
            return
        }
        listBean = list

        val manager = LinearLayoutManager(ResourceUtil.getContext())
        recycler_reversal.addItemDecoration(SpaceItemDecoration(ResourceUtil.dip2px(10), false))
        recycler_reversal.layoutManager = manager
        adapter = PickingDoneDetailAdapter(this, R.layout.item_reversal_check, list)
        recycler_reversal.adapter = adapter

    }


    override fun showLoading() {

    }

    override fun hideLoading() {

    }
}
