package com.ty.zbpet.ui.activity.material

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.EditText
import com.ty.zbpet.R
import com.ty.zbpet.bean.material.MaterialDetailsOut
import com.ty.zbpet.presenter.material.MaterialPresenter
import com.ty.zbpet.presenter.material.MaterialUiListInterface
import com.ty.zbpet.ui.adapter.material.MaterialDoneDetailAdapter
import com.ty.zbpet.ui.base.BaseActivity
import com.ty.zbpet.ui.widght.SpaceItemDecoration
import com.ty.zbpet.util.ResourceUtil

/**
 * @author TY on 2018/11/15.
 *
 * 已办详情 Kotlin
 */
class ArrivalInDoneDetailActivityK : BaseActivity(), MaterialUiListInterface<MaterialDetailsOut.ListBean> {

    var detailRc: RecyclerView? = null

    var etDesc: EditText? = null

    var mInWarehouseOrderId: String = ""

    var adapter: MaterialDoneDetailAdapter? = null

    val materialPresenter: MaterialPresenter = MaterialPresenter(this)


    override fun onBaseCreate(savedInstanceState: Bundle?) {
    }

    override fun getActivityLayout(): Int {
        return R.layout.activity_material_done_detail
    }

    override fun initOneData() {
        mInWarehouseOrderId = intent.getStringExtra("mInWarehouseOrderId")
    }

    override fun initTwoView() {
        detailRc = findViewById(R.id.rc_done_detail_list)
        etDesc = this.findViewById(R.id.et_desc)

        initToolBar(R.string.material_reversal) {
            finish()
        }
    }

    override fun showMaterial(list: MutableList<MaterialDetailsOut.ListBean>) {
        val manager = LinearLayoutManager(ResourceUtil.getContext())
        detailRc!!.addItemDecoration(SpaceItemDecoration(ResourceUtil.dip2px(10), false))
        detailRc!!.layoutManager = manager

        adapter = MaterialDoneDetailAdapter(this, R.layout.item_material_done_detail, list)
        detailRc!!.adapter = adapter

//        adapter!!.setOnItemClickListener(temp : MaterialDoneDetailAdapter.OnItemClickListener {
//
//            override fun onItemClick(view: View, holder: RecyclerView.ViewHolder, position: Int) {
//                val intent = Intent(view.context, BackGoodsTodoDetailActivity::class.java)
//                intent.putExtra("supplierId", list[position].supplierId)
//                startActivity(intent)
//            }
//
//            override fun onItemLongClick(view: View, holder: RecyclerView.ViewHolder, position: Int): Boolean {
//                return false
//            }
//        })


    }

    override fun onStart() {
        super.onStart()
        materialPresenter.fetchDoneMaterialDetails(mInWarehouseOrderId)
    }

    override fun showLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}