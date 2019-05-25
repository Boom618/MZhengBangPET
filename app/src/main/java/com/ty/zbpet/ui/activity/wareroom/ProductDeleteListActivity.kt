package com.ty.zbpet.ui.activity.wareroom

import android.os.Bundle
import com.ty.zbpet.R
import com.ty.zbpet.bean.eventbus.ErrorMessage
import com.ty.zbpet.bean.eventbus.SuccessMessage
import com.ty.zbpet.bean.eventbus.system.DeleteCheckMessage
import com.ty.zbpet.bean.system.ReceiptList
import com.ty.zbpet.presenter.system.SystemPresenter
import com.ty.zbpet.ui.ActivitiesHelper
import com.ty.zbpet.ui.adapter.LayoutInit
import com.ty.zbpet.ui.adapter.wareroom.ProductDeleteAdapter
import com.ty.zbpet.base.BaseActivity
import com.ty.zbpet.bean.eventbus.system.EndLoadingMessage
import com.ty.zbpet.bean.eventbus.system.StartLoadingMessage
import com.ty.zbpet.constant.CodeConstant
import com.ty.zbpet.constant.TipString
import com.ty.zbpet.ui.widght.NormalAlertDialog
import com.ty.zbpet.ui.widght.ShowDialog
import com.ty.zbpet.ui.widght.SpaceItemDecoration
import com.ty.zbpet.util.DialogUtil
import com.ty.zbpet.util.ResourceUtil
import com.ty.zbpet.util.ZBUiUtils
import com.xiasuhuei321.loadingdialog.view.LoadingDialog
import kotlinx.android.synthetic.main.activity_product_inventory_list.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * @author TY on 2019/4/1.
 * 成品单据删除列表
 */
class ProductDeleteListActivity : BaseActivity() {


    private val presenter = SystemPresenter()
    private var adapter: ProductDeleteAdapter? = null

    override val activityLayout: Int
        get() = R.layout.activity_product_inventory_list

    override fun onBaseCreate(savedInstanceState: Bundle?) {
        EventBus.getDefault().register(this)
    }

    override fun initOneData() {
        initToolBar(R.string.product_delete)
        LayoutInit.initLayoutManager(this, recycler_inventory)
        recycler_inventory.addItemDecoration(SpaceItemDecoration(ResourceUtil.dip2px(CodeConstant.ITEM_DECORATION), false))
        presenter.getCheckList()
    }

    override fun initTwoView() {


    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun getGoodsList(list: MutableList<ReceiptList.ListBean>) {

        val activity = ActivitiesHelper.get().lastActivity
        if (activity is ProductDeleteListActivity) {
            adapter = ProductDeleteAdapter(this, R.layout.item_delete_product_list, list)
            recycler_inventory.adapter = adapter
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun DeleteEvnet(event: DeleteCheckMessage) {
        val id = event.getId()
        val sapCheckNo = event.sapCheckNo()

        DialogUtil.deleteItemDialog(this@ProductDeleteListActivity, TipString.deleteOrder,
                TipString.deleteOrderYes, NormalAlertDialog.onNormalOnclickListener {
            presenter.deleteCheck(id, sapCheckNo)
            it.dismiss()
        })
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun successEvnet(event: SuccessMessage) {
        presenter.getCheckList()
        ZBUiUtils.showSuccess(event.success())
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun ErrorEvnet(event: ErrorMessage) {
        ZBUiUtils.showError(event.error())
    }

    private var dialog: LoadingDialog? = null
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun ErrorEvnet(event: StartLoadingMessage) {
        dialog = ShowDialog.showFullDialog(this, event.message())
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun ErrorEvnet(event: EndLoadingMessage) {
        dialog?.close()
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }
}