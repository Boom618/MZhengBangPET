package com.ty.zbpet.ui.activity.material

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import android.widget.CheckBox
import android.widget.EditText
import com.ty.zbpet.R
import com.ty.zbpet.bean.CarPositionNoData
import com.ty.zbpet.bean.material.MaterialDetails
import com.ty.zbpet.bean.material.MaterialDoneSave
import com.ty.zbpet.constant.CodeConstant
import com.ty.zbpet.net.RequestBodyJson
import com.ty.zbpet.presenter.material.MaterialUiListInterface
import com.ty.zbpet.presenter.material.PickOutPresenter
import com.ty.zbpet.ui.adapter.material.PickingDoneDetailAdapter
import com.ty.zbpet.base.BaseActivity
import com.ty.zbpet.constant.TipString
import com.ty.zbpet.ui.widght.ShowDialog
import com.ty.zbpet.ui.widght.SpaceItemDecoration
import com.ty.zbpet.util.DataUtils
import com.ty.zbpet.util.ResourceUtil
import com.ty.zbpet.util.SimpleCache
import com.ty.zbpet.util.ZBUiUtils
import com.xiasuhuei321.loadingdialog.view.LoadingDialog
import kotlinx.android.synthetic.main.activity_content_reversal.*
import okhttp3.RequestBody

/**
 * @author TY on 2018/11/22.
 * 领料出库 已办详情
 */
class PickOutDoneDetailActivity : BaseActivity(), MaterialUiListInterface<MaterialDetails.ListBean> {


    private lateinit var adapter: PickingDoneDetailAdapter

    private lateinit var sapOrderNo: String
    private lateinit var orderId: String
    private var materielVoucherNo: String? = ""
    private lateinit var listBean: List<MaterialDetails.ListBean>

    private val presenter = PickOutPresenter(this)

    override val activityLayout: Int
        get() = R.layout.activity_content_reversal


    override fun onBaseCreate(savedInstanceState: Bundle?) {

    }

    override fun initOneData() {
        sapOrderNo = intent.getStringExtra("sapOrderNo")
        orderId = intent.getStringExtra("orderId")
        materielVoucherNo = intent.getStringExtra("materielVoucherNo")

        presenter.fetchPickOutDoneListDetails(orderId)
    }

    override fun initTwoView() {

        initToolBar(R.string.pick_out_storage_reversal)
        bt_reversal.setOnClickListener { view ->
            ZBUiUtils.hideInputWindow(view.context, view)
            initDoneBody()?.let { presenter.pickOutDoneSave(it) }
        }

    }

    /**
     * 构建保存 body
     * @return
     */
    private fun initDoneBody(): RequestBody? {

        val data = MaterialDoneSave()
        val list = ArrayList<MaterialDoneSave.ListBean>()
        val size = listBean.size

        for (i in 0 until size) {

            val view = recycler_reversal.getChildAt(i)
            if (view != null) {
                val checkBox = view.findViewById<CheckBox>(R.id.check)

                val backNum = listBean[i].backNum
                // 已冲销数量
                val num = view.findViewById<EditText>(R.id.et_back_num).text.toString()
                //val value = SimpleCache.getNumber(i.toString())
                if (checkBox.isChecked && num.isNotEmpty()) {
                    val subNumberService = Integer.parseInt(backNum)
                    val subNumber = Integer.parseInt(num)
                    val countNumber = subNumberService + subNumber
                    // 总量
                    val giveNumber = Integer.parseInt(listBean[i].giveNumber)
                    if (giveNumber < countNumber) {
                        ZBUiUtils.showWarning("冲销数量不能大于总量")
                        return null
                    }
                    val bean = MaterialDoneSave.ListBean()
                    bean.id = listBean[i].id
                    bean.backNum = num
                    bean.giveNumber = listBean[i].giveNumber
                    list.add(bean)
                }
            }
        }

        if (list.size == 0) {
            ZBUiUtils.showWarning("请选择您要冲销的列表")
            return null
        }

        data.list = list
        data.orderId = orderId
        data.moveType = "262"

        val json = DataUtils.toJson(data, 1)

        return RequestBodyJson.requestBody(json)
    }

    override fun showMaterial(list: List<MaterialDetails.ListBean>) {
        listBean = list
        if (list.isEmpty()) {
            return
        }
//        for (i in 0 until list.size) {
//            SimpleCache.clearKey(i.toString())
//        }

        val manager = androidx.recyclerview.widget.LinearLayoutManager(ResourceUtil.getContext())
        recycler_reversal.addItemDecoration(SpaceItemDecoration(ResourceUtil.dip2px(CodeConstant.ITEM_DECORATION), false))
        recycler_reversal.layoutManager = manager
        // 禁止 RecycleView 回收
        recycler_reversal.recycledViewPool.setMaxRecycledViews(0, 0)
        adapter = PickingDoneDetailAdapter(this, materielVoucherNo, R.layout.item_reversal_check, list)
        recycler_reversal.adapter = adapter

    }

    override fun showCarSuccess(position: Int, carData: CarPositionNoData?) {

    }

    override fun saveSuccess() {
        ZBUiUtils.showSuccess(TipString.success)
        finish()
    }

    override fun showError(msg: String?) {
        ZBUiUtils.showError(msg)
    }

    private var dialog: LoadingDialog? = null
    override fun showLoading() {
        dialog = ShowDialog.showFullDialog(this@PickOutDoneDetailActivity, TipString.saveIng)
    }

    override fun hideLoading() {
        dialog?.close()
    }
}
