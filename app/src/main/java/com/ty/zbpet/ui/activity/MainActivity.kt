package com.ty.zbpet.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.TextView

import com.ty.zbpet.R
import com.ty.zbpet.ui.activity.material.ArrivalInStorageActivity
import com.ty.zbpet.ui.activity.material.BackGoodsActivity
import com.ty.zbpet.ui.activity.material.PickOutStorageActivity
import com.ty.zbpet.ui.activity.product.ProductInStorageActivity
import com.ty.zbpet.ui.activity.product.BuyInStorageActivity
import com.ty.zbpet.ui.activity.product.ReturnGoodsActivity
import com.ty.zbpet.ui.activity.product.SendOutActivity
import com.ty.zbpet.ui.activity.system.InventoryActivity
import com.ty.zbpet.ui.activity.system.PersonCenterActivity
import com.ty.zbpet.ui.activity.system.QualityCheckActivity
import com.ty.zbpet.ui.activity.system.TransferStorageActivity
import com.ty.zbpet.ui.base.BaseActivity

/**
 * @author TY
 */
class MainActivity : BaseActivity(), View.OnClickListener {


    override fun onBaseCreate(savedInstanceState: Bundle?) {

    }

    override fun getActivityLayout(): Int {
        return R.layout.activity_main1
    }

    override fun initOneData() {

    }

    override fun initTwoView() {
        findViewById<TextView>(R.id.tv_arrival_in_storage).setOnClickListener(this)
        findViewById<TextView>(R.id.tv_pick_out_storage).setOnClickListener(this)
        findViewById<TextView>(R.id.tv_purchase_returns).setOnClickListener(this)

        findViewById<TextView>(R.id.tv_purchase_in_storage).setOnClickListener(this)
        findViewById<TextView>(R.id.tv_produce_in_storage).setOnClickListener(this)
        findViewById<TextView>(R.id.tv_send_out_storage).setOnClickListener(this)
        findViewById<TextView>(R.id.tv_return_in_storage).setOnClickListener(this)

        findViewById<TextView>(R.id.tv_inventory).setOnClickListener(this)
        findViewById<TextView>(R.id.tv_person_center).setOnClickListener(this)

    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.tv_arrival_in_storage ->
                //原辅料——到货入库
                gotoActivity(ArrivalInStorageActivity::class.java)
            R.id.tv_pick_out_storage ->
                //原辅料——领料出库
                gotoActivity(PickOutStorageActivity::class.java)
            R.id.tv_purchase_returns ->
                //原辅料——采购退货
                gotoActivity(BackGoodsActivity::class.java)
            R.id.tv_purchase_in_storage ->
                //成品——外采入库
                gotoActivity(BuyInStorageActivity::class.java)
            R.id.tv_produce_in_storage ->
                //成品——生产入库
                gotoActivity(ProductInStorageActivity::class.java)
            R.id.tv_send_out_storage ->
                //成品——发货出库
                gotoActivity(SendOutActivity::class.java)
            R.id.tv_return_in_storage ->
                //成品——退货入库
                gotoActivity(ReturnGoodsActivity::class.java)
            R.id.tv_inventory ->
                //仓库管理——盘点
                //gotoActivity(InventoryActivity::class.java)
                gotoActivity(QualityCheckActivity::class.java)
            R.id.tv_transfer_storage ->
                //仓库管理——移库
                gotoActivity(TransferStorageActivity::class.java)
            R.id.tv_person_center ->
                //个人中心
                gotoActivity(PersonCenterActivity::class.java)
            else -> {
            }
        }
    }

}
