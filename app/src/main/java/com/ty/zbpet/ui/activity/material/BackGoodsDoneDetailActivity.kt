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
import com.ty.zbpet.presenter.material.BackGoodsPresenter
import com.ty.zbpet.presenter.material.MaterialUiObjInterface
import com.ty.zbpet.ui.adapter.material.BackGoodsDoneDetailAdapter
import com.ty.zbpet.ui.base.BaseActivity
import com.ty.zbpet.ui.widght.SpaceItemDecoration
import com.ty.zbpet.util.DataUtils
import com.ty.zbpet.util.ResourceUtil
import com.ty.zbpet.util.ZBUiUtils
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_content_reversal.*
import kotlinx.android.synthetic.main.activity_content_row_two.*
import okhttp3.RequestBody
import java.util.*

/**
 * @author TY on 2018/11/22.
 * 采购退货 已办详情
 */
class BackGoodsDoneDetailActivity : BaseActivity(), MaterialUiObjInterface<MaterialDetails> {


    lateinit var adapter: BackGoodsDoneDetailAdapter

    lateinit var selectTime: String
    /**
     * 仓库 ID
     */
    lateinit var warehouseId: String

    lateinit var orderId: String

    private var listBean  = ArrayList<MaterialDetails.ListBean>()

    private val presenter = BackGoodsPresenter(this)

    override val activityLayout: Int
        get() = R.layout.activity_content_reversal//R.layout.activity_content_row_two


    override fun onBaseCreate(savedInstanceState: Bundle?) {

    }

    override fun initOneData() {

        orderId = intent.getStringExtra("orderId")

        presenter.fetchBackDoneListInfo(orderId)
    }

    override fun initTwoView() {


        initToolBar(R.string.purchase_back_goods)
        bt_reversal.setOnClickListener { backGoodsDoneSave(initDoneBody()) }

    }

    /**
     * 出库 保存
     */
    private fun backGoodsDoneSave(body: RequestBody?) {

        if (body == null) {
            return
        }

        HttpMethods.getInstance().getBackDoneSave(object : SingleObserver<ResponseInfo> {
            override fun onError(e: Throwable) {
                ZBUiUtils.showToast(e.message)
            }

            override fun onSubscribe(d: Disposable) {

            }

            override fun onSuccess(responseInfo: ResponseInfo) {
                if (CodeConstant.SERVICE_SUCCESS == responseInfo.tag) {
                    // 入库成功（保存）
                    ZBUiUtils.showToast(responseInfo.message)
                    runOnUiThread { finish() }
                } else {
                    ZBUiUtils.showToast(responseInfo.message)
                }
            }
        }, body)
    }

    private fun initDoneBody(): RequestBody? {

        val data = MaterialDoneSave()
        val list = ArrayList<MaterialDoneSave.ListBean>()
        val size = listBean.size

        for (i in 0 until size) {
            val view = recycler_reversal.getChildAt(i)
            val checkBox = view.findViewById<CheckBox>(R.id.iv_tag)
            if (checkBox.isChecked) {
                val bean = MaterialDoneSave.ListBean()
                bean.id = listBean[i].id
                bean.orderId = orderId
                list.add(bean)
            }
        }

        if (list.size == 0) {
            ZBUiUtils.showToast("请选择您要冲销的列表")
            return null
        }

        val remark = et_desc!!.text.toString().trim { it <= ' ' }

        data.list = list
        data.orderId = orderId
        data.warehouseId = warehouseId
        data.outTime = selectTime
        data.moveType = "102"
        data.remark = remark
        val json = DataUtils.toJson(data, 1)

        return RequestBodyJson.requestBody(json)
    }

    override fun detailObjData(obj: MaterialDetails) {

        listBean = obj.list!!
        warehouseId = listBean[0].warehouseId!!

        val manager = LinearLayoutManager(ResourceUtil.getContext())
        recycler_reversal.addItemDecoration(SpaceItemDecoration(ResourceUtil.dip2px(10), false))
        recycler_reversal.layoutManager = manager
        //adapter = BackGoodsDoneDetailAdapter(this, R.layout.item_material_detail_three_done, listBean)
        adapter = BackGoodsDoneDetailAdapter(this, R.layout.item_reversal_check, listBean)
        recycler_reversal.adapter = adapter

    }

    override fun showCarSuccess(position: Int, carData: CarPositionNoData) {

    }


}
