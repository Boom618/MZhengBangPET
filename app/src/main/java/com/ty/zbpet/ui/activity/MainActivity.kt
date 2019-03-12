package com.ty.zbpet.ui.activity

import android.os.Bundle
import android.view.View
import com.ty.zbpet.R
import com.ty.zbpet.constant.CodeConstant
import com.ty.zbpet.ui.activity.system.PersonCenterActivity
import com.ty.zbpet.ui.activity.system.QualityCheckTabActivity
import com.ty.zbpet.ui.activity.system.TransferStorageActivity
import com.ty.zbpet.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main1.*

/**
 * @author TY
 */
class MainActivity : BaseActivity(), View.OnClickListener {

    override val activityLayout: Int
        get() = R.layout.activity_main1


    override fun onBaseCreate(savedInstanceState: Bundle?) {

    }

    override fun initOneData() {

    }

    override fun initTwoView() {

        setViewOnClickListener(this,
                tv_arrival_in_storage,
                tv_pick_out_storage,
                tv_purchase_returns,
                tv_purchase_in_storage,
                tv_produce_in_storage,
//                tv_send_out_storage,
//                tv_return_in_storage,
//                tv_inventory,
//                tv_transfer_storage,
                tv_person_center)
    }

    override fun onClick(view: View) {
        val bundle = Bundle()
        when (view.id) {
            R.id.tv_arrival_in_storage -> {
                //原辅料——到货入库
                bundle.putInt(CodeConstant.ACTIVITY_TYPE, 1)
                gotoActivity(PrimaryActivity::class.java, false, bundle)
            }
            R.id.tv_pick_out_storage -> {
                //原辅料——领料出库 PickOutStorageActivity
                bundle.putInt(CodeConstant.ACTIVITY_TYPE, 2)
                gotoActivity(PrimaryActivity::class.java, false, bundle)
            }
            R.id.tv_purchase_returns -> {
                //原辅料——采购退货
                bundle.putInt(CodeConstant.ACTIVITY_TYPE, 3)
                gotoActivity(PrimaryActivity::class.java, false, bundle)
            }
            R.id.tv_purchase_in_storage -> {
                //成品——外采入库
                bundle.putInt(CodeConstant.ACTIVITY_TYPE, 4)
                gotoActivity(PrimaryActivity::class.java, false, bundle)
            }

            R.id.tv_produce_in_storage -> {
                //成品——生产入库
                bundle.putInt(CodeConstant.ACTIVITY_TYPE, 5)
                gotoActivity(PrimaryActivity::class.java, false, bundle)
            }
            R.id.tv_send_out_storage -> {
                //成品——发货出库
                bundle.putInt(CodeConstant.ACTIVITY_TYPE, 6)
                gotoActivity(PrimaryActivity::class.java, false, bundle)
            }
            R.id.tv_return_in_storage -> {
                //成品——退货入库
                bundle.putInt(CodeConstant.ACTIVITY_TYPE, 7)
                gotoActivity(PrimaryActivity::class.java, false, bundle)
            }
            R.id.tv_inventory ->
                //仓库管理——盘点
                //gotoActivity(InventoryActivity::class.java)
//                gotoActivity(QualityCheckActivity::class.java)
                gotoActivity(QualityCheckTabActivity::class.java)
            R.id.tv_transfer_storage ->
                //仓库管理——移库
                gotoActivity(TransferStorageActivity::class.java)
            R.id.tv_person_center ->
                //个人中心
                gotoActivity(PersonCenterActivity::class.java)
        }
    }

    override fun onBackPressed() {
        // exist app 会调用：onPause()和 onStop()
        moveTaskToBack(true)
    }

}
