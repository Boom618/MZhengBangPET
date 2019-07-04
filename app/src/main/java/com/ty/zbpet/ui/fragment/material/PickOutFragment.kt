package com.ty.zbpet.ui.fragment.material


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.View
import com.scwang.smartrefresh.header.MaterialHeader
import com.ty.zbpet.R
import com.ty.zbpet.bean.CarPositionNoData
import com.ty.zbpet.bean.eventbus.SearchMessage
import com.ty.zbpet.bean.material.MaterialList
import com.ty.zbpet.constant.CodeConstant
import com.ty.zbpet.presenter.material.MaterialUiListInterface
import com.ty.zbpet.presenter.material.PickOutPresenter
import com.ty.zbpet.ui.activity.material.PickOutDoneDetailActivity
import com.ty.zbpet.ui.activity.material.PickOutTodoDetailActivity
import com.ty.zbpet.ui.adapter.LayoutInit
import com.ty.zbpet.ui.adapter.material.PickOutDoneAdapter
import com.ty.zbpet.ui.adapter.material.PickOutTodoAdapter
import com.ty.zbpet.base.BaseFragment
import com.ty.zbpet.ui.widght.SpaceItemDecoration
import com.ty.zbpet.util.ResourceUtil
import com.ty.zbpet.util.ZBUiUtils
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter
import kotlinx.android.synthetic.main.zb_content_list_fragment.*
import kotlinx.android.synthetic.main.zb_content_list_fragment.view.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


/**
 * A simple [Fragment] subclass.
 * Use the [PickOutFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 * @author TY
 *
 *
 * 领料出库列表
 */
class PickOutFragment : BaseFragment(), MaterialUiListInterface<MaterialList.ListBean> {

    private lateinit var fragmentType: String
    private var adapterTodo: PickOutTodoAdapter? = null
    private var adapterDone: PickOutDoneAdapter? = null

    private val presenter = PickOutPresenter(this)

    override val fragmentLayout: Int
        get() = R.layout.zb_content_list_fragment

    override fun onBaseCreate(view: View): View {
        EventBus.getDefault().register(this)
        // 设置 Header 样式
        view.refreshLayout!!.setRefreshHeader(MaterialHeader(this.context!!))
        // 设置 Footer 为 球脉冲 样式
        //view.refreshLayout!!.setRefreshFooter(BallPulseFooter(this.context!!).setSpinnerStyle(SpinnerStyle.Scale))
        return view
    }

    override fun loadData() {
        fragmentType = arguments!!.getString(CodeConstant.FRAGMENT_TYPE)!!
        when (fragmentType) {
            //CodeConstant.FRAGMENT_TODO -> presenter.fetchPickOutTodoList("", "", "", "")
            CodeConstant.FRAGMENT_DONE -> presenter.fetchPickOutDoneList(CodeConstant.PICK_OUT_TYPE, "", "", "")
        }
    }

    override fun onStart() {
        super.onStart()
        if (isVisble) {
            fragmentType = arguments!!.getString(CodeConstant.FRAGMENT_TYPE)!!
            when (fragmentType) {
                CodeConstant.FRAGMENT_TODO -> presenter.fetchPickOutTodoList("", "", "", "")
                CodeConstant.FRAGMENT_DONE -> presenter.fetchPickOutDoneList(CodeConstant.PICK_OUT_TYPE, "", "", "")
            }
        }
    }

    override fun onResume() {
        super.onResume()

        refreshLayout!!.setOnRefreshListener { refreshLayout ->
            // 传入 false 表示刷新失败
            refreshLayout.finishRefresh(1000)
            // 刷新数据
            when (fragmentType) {
                CodeConstant.FRAGMENT_TODO -> presenter.fetchPickOutTodoList("", "", "", "")
                CodeConstant.FRAGMENT_DONE -> presenter.fetchPickOutDoneList(CodeConstant.PICK_OUT_TYPE, "", "", "")
            }
        }
//        refreshLayout!!.setOnLoadMoreListener { refreshLayout ->
//            // 传入 false 表示刷新失败
//            refreshLayout.finishLoadMore(1000)
//            ZBUiUtils.showSuccess("没有更多数据了")
//        }

    }


    override fun showMaterial(list: List<MaterialList.ListBean>) {
        if (list.isEmpty()) {
            ZBUiUtils.showWarning("领料出库没有找到结果")
        }

        recyclerView?.let { LayoutInit.initLayoutManager(ResourceUtil.getContext(), it) }
        if (adapterTodo == null && adapterDone == null) {
            recyclerView?.addItemDecoration(SpaceItemDecoration(ResourceUtil.dip2px(CodeConstant.ITEM_DECORATION), false))
        }
        when (fragmentType) {
            CodeConstant.FRAGMENT_TODO -> {
                adapterTodo = PickOutTodoAdapter(context!!, R.layout.item_pick_out_todo, list)
                recyclerView?.adapter = adapterTodo

                adapterTodo?.setOnItemClickListener(object : MultiItemTypeAdapter.OnItemClickListener {
                    override fun onItemClick(view: View, holder: RecyclerView.ViewHolder, position: Int) {
                        val intent = Intent(activity, PickOutTodoDetailActivity::class.java)
                        intent.putExtra("sapOrderNo", list[position].sapOrderNo)
                        intent.putExtra("sapFirmNo", list[position].sapFirmNo)
                        intent.putExtra("orderTime", list[position].orderTime)
                        intent.putExtra("sign", list[position].sign)
                        intent.putExtra("content", list[position].content)
                        startActivity(intent)
                    }

                    override fun onItemLongClick(view: View, holder: RecyclerView.ViewHolder, position: Int): Boolean {
                        return false
                    }
                })
            }
            CodeConstant.FRAGMENT_DONE -> {
                adapterDone = PickOutDoneAdapter(context!!, R.layout.item_pick_out_done, list)
                recyclerView?.adapter = adapterDone

                adapterDone?.setOnItemClickListener(object : MultiItemTypeAdapter.OnItemClickListener {
                    override fun onItemClick(view: View, holder: RecyclerView.ViewHolder, position: Int) {
                        val intent = Intent(activity, PickOutDoneDetailActivity::class.java)
                        intent.putExtra("sapOrderNo", list[position].sapOrderNo)
                        intent.putExtra("orderId", list[position].orderId)
                        intent.putExtra("materielVoucherNo", list[position].materielVoucherNo)
                        startActivity(intent)
                    }

                    override fun onItemLongClick(view: View, holder: RecyclerView.ViewHolder, position: Int): Boolean {
                        return false
                    }
                })
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEventMainThread(event: SearchMessage) {
        if (isVisble) {
            val sign = event.sign()
            val search = event.getSearch()
            val startTime = event.leftTime()
            val endTime = event.rightTime()
            when (fragmentType) {
                CodeConstant.FRAGMENT_TODO -> presenter.fetchPickOutTodoList(sign, search, startTime, endTime)
                CodeConstant.FRAGMENT_DONE -> presenter.fetchPickOutDoneList(CodeConstant.PICK_OUT_TYPE, search, startTime, endTime)
            }
        }
    }

    override fun showCarSuccess(position: Int, carData: CarPositionNoData?) {

    }

    override fun saveSuccess() {
    }

    override fun showError(msg: String?) {
        ZBUiUtils.showError(msg)
    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.dispose()
        EventBus.getDefault().unregister(this)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment PickOutTodoFragment.
         */
        fun newInstance(type: String): PickOutFragment {
            val fragment = PickOutFragment()
            val bundle = Bundle()
            bundle.putString(CodeConstant.FRAGMENT_TYPE, type)
            fragment.arguments = bundle
            return fragment
        }
    }
}
