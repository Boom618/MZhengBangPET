package com.ty.zbpet.ui.activity.material

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.InputType
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView

import com.ty.zbpet.R
import com.ty.zbpet.bean.ResponseInfo
import com.ty.zbpet.bean.material.MaterialDetailsOut
import com.ty.zbpet.bean.material.MaterialDoneSave
import com.ty.zbpet.net.HttpMethods
import com.ty.zbpet.presenter.material.MaterialPresenter
import com.ty.zbpet.presenter.material.MaterialUiListInterface
import com.ty.zbpet.ui.adapter.material.MaterialDoneDetailAdapter
import com.ty.zbpet.ui.base.BaseActivity
import com.ty.zbpet.ui.widght.SpaceItemDecoration
import com.ty.zbpet.constant.CodeConstant
import com.ty.zbpet.util.DataUtils
import com.ty.zbpet.util.ResourceUtil
import com.ty.zbpet.util.ZBUiUtils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

import butterknife.BindView
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import okhttp3.RequestBody
import android.support.annotation.Nullable

/**
 * @author TY on 2018/11/14.
 *
 *
 * 已办 详情
 */
class ArrivalInDoneDetailActivity : BaseActivity(), MaterialUiListInterface<MaterialDetailsOut.ListBean> {

    @BindView(R.id.rv_in_storage_detail)
    internal var detailRc: RecyclerView? = null
    @BindView(R.id.tv_time)
    internal var tvTime: TextView? = null

    @BindView(R.id.et_desc)
    internal var etDesc: EditText? = null

    /**
     * 时间选择
     */
    private var selectTime: String? = null


    private var orderId: String? = null
    private var mInWarehouseOrderId: String? = null
    private var warehouseId: String? = null
    private var sapOrderNo: String? = null
    private var positionId: String? = null

    private var adapter: MaterialDoneDetailAdapter? = null
    private val materialPresenter = MaterialPresenter(this)


    override fun onBaseCreate(@Nullable savedInstanceState: Bundle) {
        val sdf = SimpleDateFormat(CodeConstant.DATE_SIMPLE_H_M, Locale.CHINA)
        selectTime = sdf.format(Date())
        tvTime!!.text = selectTime
    }

    override fun getActivityLayout(): Int {
        return R.layout.activity_content_row_two
    }

    override fun initOneData() {

        mInWarehouseOrderId = intent.getStringExtra("mInWarehouseOrderId")
        sapOrderNo = intent.getStringExtra("sapOrderNo")
        warehouseId = intent.getStringExtra("warehouseId")
        orderId = intent.getStringExtra("orderId")
    }

    override fun initTwoView() {

        findViewById<View>(R.id.add_ship).visibility = View.GONE
        val titleName = findViewById<TextView>(R.id.in_storage_detail)
        titleName.text = "到货明细"
        etDesc!!.inputType = InputType.TYPE_NULL

        initToolBar(R.string.material_reversal) { materialDoneInSave(initRequestBody()) }
    }

    /**
     * 已办 保存
     * @param body
     */
    private fun materialDoneInSave(body: RequestBody) {
        HttpMethods.getInstance().materialDoneInSave(object : SingleObserver<ResponseInfo> {
            override fun onError(e: Throwable) {
                ZBUiUtils.showToast(e.message)
            }

            override fun onSubscribe(d: Disposable) {

            }

            override fun onSuccess(responseInfo: ResponseInfo) {

                if (CodeConstant.SERVICE_SUCCESS == responseInfo.tag) {
                    ZBUiUtils.showToast(responseInfo.message)
                    finish()
                } else {
                    ZBUiUtils.showToast(responseInfo.message)
                }
            }

        }, body)

    }

    /**
     * 初始化 请求参数
     *
     * @return
     */
    private fun initRequestBody(): RequestBody {

        val bean = MaterialDoneSave()

        bean.warehouseId = warehouseId
        bean.orderId = mInWarehouseOrderId
        bean.sapOrderNo = sapOrderNo
        bean.positionId = positionId
        bean.orderId = orderId

        val json = DataUtils.toJson(bean, 1)

        return RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"), json)

    }

    override fun onStart() {
        super.onStart()
        materialPresenter.fetchDoneMaterialDetails(orderId)
    }

    override fun showMaterial(list: List<MaterialDetailsOut.ListBean>) {

        positionId = list[0].positionId

        if (adapter == null) {
            val manager = LinearLayoutManager(ResourceUtil.getContext())
            detailRc!!.addItemDecoration(SpaceItemDecoration(ResourceUtil.dip2px(10), false))
            detailRc!!.layoutManager = manager

            // TODO 侧滑删除
            // detailRc.addOnItemTouchListener(new SwipeItemLayout.OnSwipeItemTouchListener(this));
            adapter = MaterialDoneDetailAdapter(this, R.layout.item_material_done_detail, list)
            detailRc!!.adapter = adapter

            adapter!!.setOnItemClickListener(object : MultiItemTypeAdapter.OnItemClickListener {

                override fun onItemClick(view: View, holder: RecyclerView.ViewHolder, position: Int) {
                    val rlDetail = holder.itemView.findViewById<View>(R.id.view_gone)
                    val ivArrow = holder.itemView.findViewById<ImageView>(R.id.iv_arrow)

                    if (rlDetail.visibility == View.VISIBLE) {
                        rlDetail.visibility = View.GONE
                        ivArrow.setImageResource(R.mipmap.ic_collapse)
                    } else {
                        rlDetail.visibility = View.VISIBLE
                        ivArrow.setImageResource(R.mipmap.ic_expand)
                    }
                }

                override fun onItemLongClick(view: View, holder: RecyclerView.ViewHolder, position: Int): Boolean {
                    return false
                }
            })
        }

    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }
}
