//package com.ty.zbpet.ui.activity.material
//
//import android.os.Bundle
//import android.support.v7.widget.LinearLayoutManager
//import android.support.v7.widget.RecyclerView
//import android.widget.EditText
//import android.widget.TextView
//import com.pda.scanner.ScanReader
//import com.pda.scanner.Scanner
//import com.ty.zbpet.R
//import com.ty.zbpet.bean.PickOutTodoDetailsData
//import com.ty.zbpet.presenter.material.MaterialUiObjInterface
//import com.ty.zbpet.presenter.material.PickOutPresenter
//import com.ty.zbpet.ui.adapter.material.PickOutTodoDetailAdapter
//import com.ty.zbpet.ui.base.BaseActivity
//import com.ty.zbpet.ui.widght.SpaceItemDecoration
//import com.ty.zbpet.util.CodeConstant
//import com.ty.zbpet.util.ResourceUtil
//import com.ty.zbpet.util.ZBUiUtils
//import com.ty.zbpet.util.scan.ScanBoxInterface
//import com.ty.zbpet.util.scan.ScanObservable
//import java.text.SimpleDateFormat
//import java.util.*
//
//
///**
// *
// * @author TY
// * 领料出库 待办详情
// * 有错误啊啊
// */
//class PickOutTodoDetailActivityK : BaseActivity(), MaterialUiObjInterface<PickOutTodoDetailsData>, ScanBoxInterface {
//
//
//    var reView: RecyclerView? = null
//    var tvTime: TextView? = null
//    var etDesc: EditText? = null
//
//    var adapter: PickOutTodoDetailAdapter? = null
//
//    private var selectTime: String? = null
//
//    private val scanner: Scanner = ScanReader.getScannerInstance()
//    private val scanObservable: ScanObservable = ScanObservable(this)
//    private val presenter: PickOutPresenter = PickOutPresenter(this)
//    private var sapOrderNo: String? = null
//
//    override fun onBaseCreate(savedInstanceState: Bundle?) {
//
//    }
//
//    override fun getActivityLayout(): Int {
//        return R.layout.activity_pick_out_detail
//    }
//
//    override fun initOneData() {
//        sapOrderNo = intent.getStringExtra("sapOrderNo")
//
//        presenter.fetchPickOutTodoListDetails(sapOrderNo)
//    }
//
//    override fun initTwoView() {
//
//        initToolBar(R.string.pick_out_storage) {
//
//            pickOutSave()
//        }
//
//        reView = findViewById(R.id.rv_in_storage_detail)
//        tvTime = findViewById(R.id.tv_time)
//        etDesc = findViewById(R.id.et_desc)
//
//        val sdf = SimpleDateFormat(CodeConstant.DATE_SIMPLE_H_M, Locale.CHINA)
//        selectTime = sdf.format(Date())
//
//        tvTime!!.text = selectTime
//
//        tvTime!!.setOnClickListener {
//            ZBUiUtils.showPickDate(this) { date, _ ->
//
//                selectTime = ZBUiUtils.getTime(date)
//                tvTime!!.text = selectTime
//                ZBUiUtils.showToast(selectTime)
//            }
//        }
//
//    }
//
//    /**
//     * 保存
//     *
//     */
//    private fun pickOutSave() {
//        finish()
//    }
//
//    override fun onStart() {
//        super.onStart()
//
//    }
//
//    override fun detailObjData(data: PickOutTodoDetailsData) {
//
//
//        val manager = LinearLayoutManager(ResourceUtil.getContext())
//        reView!!.addItemDecoration(SpaceItemDecoration(ResourceUtil.dip2px(10), false))
//        reView!!.layoutManager = manager
//        adapter = PickOutTodoDetailAdapter(this, R.layout.item_pick_out_todo_list_detail, data.list)
//        reView!!.adapter = adapter
//
//        //adapter!!.setOnItemClickListener View view, RecyclerView.ViewHolder holder, int position
////        adapter!!.setOnItemClickl
//
//
//    }
//
//    override fun showSuccess(position: Int, count: Int) {
//        TODO("not implemented")
//    }
//
//    override fun ScanSuccess(position: Int, msg: String?) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//}
