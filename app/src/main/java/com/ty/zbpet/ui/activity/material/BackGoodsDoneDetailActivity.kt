package com.ty.zbpet.ui.activity.material

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.InputType
import android.view.View
import android.widget.ImageView
import android.widget.TextView

import com.ty.zbpet.R
import com.ty.zbpet.bean.CarPositionNoData
import com.ty.zbpet.bean.ResponseInfo
import com.ty.zbpet.bean.material.MaterialDetails
import com.ty.zbpet.bean.material.MaterialDoneSave
import com.ty.zbpet.net.HttpMethods
import com.ty.zbpet.presenter.material.BackGoodsPresenter
import com.ty.zbpet.presenter.material.MaterialUiObjInterface
import com.ty.zbpet.ui.adapter.material.BackGoodsDoneDetailAdapter
import com.ty.zbpet.ui.base.BaseActivity
import com.ty.zbpet.ui.widght.SpaceItemDecoration
import com.ty.zbpet.constant.CodeConstant
import com.ty.zbpet.net.RequestBodyJson
import com.ty.zbpet.util.DataUtils
import com.ty.zbpet.util.ResourceUtil
import com.ty.zbpet.util.ZBUiUtils
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter

import java.text.SimpleDateFormat
import java.util.ArrayList
import java.util.Date
import java.util.Locale

import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_content_row_two.*
import okhttp3.RequestBody

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

    private val presenter = BackGoodsPresenter(this)

    override val activityLayout: Int
        get() = R.layout.activity_content_row_two


    override fun onBaseCreate(savedInstanceState: Bundle?) {

    }

    override fun initOneData() {

        orderId = intent.getStringExtra("orderId")

        presenter.fetchBackDoneListInfo(orderId)
    }

    override fun initTwoView() {


        initToolBar(R.string.back_goods, View.OnClickListener { backGoodsDoneSave(initDoneBody()) })

        val titleName = findViewById<TextView>(R.id.in_storage_detail)
        titleName.text = "退货明细"

        val format = SimpleDateFormat(CodeConstant.DATE_SIMPLE_H_M, Locale.CHINA)
        selectTime = format.format(Date())

        tv_time!!.text = selectTime

        et_desc!!.inputType = InputType.TYPE_NULL
    }

    /**
     * 出库 保存
     */
    private fun backGoodsDoneSave(body: RequestBody) {

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

    private fun initDoneBody(): RequestBody {

        val requestBody = MaterialDoneSave()

        val remark = et_desc!!.text.toString().trim { it <= ' ' }

        requestBody.orderId = orderId
        requestBody.warehouseId = warehouseId
        requestBody.outTime = selectTime
        requestBody.remark = remark
        val json = DataUtils.toJson(requestBody, 1)

        return RequestBodyJson.requestBody(json)
    }

    override fun detailObjData(obj: MaterialDetails) {

        val list = obj.list
        warehouseId = list!![0].warehouseId!!

        val manager = LinearLayoutManager(ResourceUtil.getContext())
        rv_in_storage_detail.addItemDecoration(SpaceItemDecoration(ResourceUtil.dip2px(10), false))
        rv_in_storage_detail.layoutManager = manager
        adapter = BackGoodsDoneDetailAdapter(this, R.layout.item_material_detail_three_done, list)
        rv_in_storage_detail.adapter = adapter

        adapter!!.setOnItemClickListener(object : MultiItemTypeAdapter.OnItemClickListener {
            override fun onItemClick(view: View, holder: RecyclerView.ViewHolder, position: Int) {

                val rlDetail = holder.itemView.findViewById<View>(R.id.gone_view)
                val ivArrow = holder.itemView.findViewById<ImageView>(R.id.iv_arrow)

                if (rlDetail.visibility == View.VISIBLE) {
                    rlDetail.visibility = View.GONE
                    ivArrow.setImageResource(R.mipmap.ic_collapse)
                } else {
                    rlDetail.visibility = View.VISIBLE
                    ivArrow.setImageResource(R.mipmap.ic_expand)

                }

                ZBUiUtils.hideInputWindow(view.context, view)

            }

            override fun onItemLongClick(view: View, holder: RecyclerView.ViewHolder, position: Int): Boolean {
                return false
            }
        })

    }

    override fun showCarSuccess(position: Int, carData: CarPositionNoData) {

    }


}
