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
import com.ty.zbpet.presenter.material.MaterialPresenter
import com.ty.zbpet.presenter.material.MaterialUiListInterface
import com.ty.zbpet.ui.activity.material.ArrivalInDoneDetailActivity
import com.ty.zbpet.ui.activity.material.ArrivalInTodoDetailActivity
import com.ty.zbpet.ui.adapter.LayoutInit
import com.ty.zbpet.ui.adapter.material.MaterialDoneAdapter
import com.ty.zbpet.ui.adapter.material.MaterialTodoAdapter
import com.ty.zbpet.ui.base.BaseFragment
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
 *
 * @author TY
 *
 * 待办 （ 入库 ） Fragment
 */
class ArrivalInFragment : BaseFragment(),MaterialUiListInterface<MaterialList.ListBean> {

    override val fragmentLayout: Int
        get() = R.layout.zb_content_list_fragment

    private var fragmentType = ""
    private var adapterTodo: MaterialTodoAdapter? = null
    private var adapterDone: MaterialDoneAdapter? = null
    private val presenter = MaterialPresenter(this)

    /**
     * 加载的 inflater.inflate  的 View
     *
     * @param view layout inflate 的 View
     */
    override fun onBaseCreate(view: View): View {
        EventBus.getDefault().register(this)
        // 设置 Header 样式
        view.refreshLayout!!.setRefreshHeader(MaterialHeader(view.context!!))
        // 设置 Footer 为 球脉冲 样式
        //view.refreshLayout!!.setRefreshFooter(BallPulseFooter(view.context!!).setSpinnerStyle(SpinnerStyle.Scale))

        return view

    }

    override fun loadData() {
        fragmentType = arguments!!.getString(CodeConstant.FRAGMENT_TYPE)!!
        when (fragmentType) {
            //CodeConstant.FRAGMENT_TODO -> presenter.fetchTODOMaterial("", "", "")
            CodeConstant.FRAGMENT_DONE -> presenter.fetchDoneMaterial(CodeConstant.BUY_IN_TYPE,"","","")
        }
    }

    override fun onStart() {
        super.onStart()
        fragmentType = arguments!!.getString(CodeConstant.FRAGMENT_TYPE)!!
        if (isVisble) {
            when (fragmentType) {
                CodeConstant.FRAGMENT_TODO -> presenter.fetchTODOMaterial("", "", "")
                CodeConstant.FRAGMENT_DONE -> presenter.fetchDoneMaterial(CodeConstant.BUY_IN_TYPE,"","","")
            }
        }
    }

    override fun onResume() {
        super.onResume()

        refreshLayout.setOnRefreshListener { refreshLayout ->
            // 传入 false 表示刷新失败
            refreshLayout.finishRefresh(1000)
            // 刷新数据
            when (fragmentType) {
                CodeConstant.FRAGMENT_TODO -> presenter.fetchTODOMaterial("", "", "")
                CodeConstant.FRAGMENT_DONE -> presenter.fetchDoneMaterial(CodeConstant.BUY_IN_TYPE,"","","")
            }
        }
//        refreshLayout!!.setOnLoadMoreListener { refreshLayout ->
//            // 传入 false 表示刷新失败
//            refreshLayout.finishLoadMore(1000)
//            ZBUiUtils.showSuccess("没有更多数据了")
//        }
    }

    override fun showMaterial(list: List<MaterialList.ListBean>) {

        LayoutInit.initLayoutManager(ResourceUtil.getContext(), recyclerView)
        if (adapterTodo == null && adapterDone == null) {
            recyclerView.addItemDecoration(SpaceItemDecoration(ResourceUtil.dip2px(CodeConstant.ITEM_DECORATION), false))
        }
        if (list.isEmpty()) {
            ZBUiUtils.showWarning("采购入库没有找到结果")
        }
        when(fragmentType){
            CodeConstant.FRAGMENT_TODO ->{
                adapterTodo = MaterialTodoAdapter(ResourceUtil.getContext(), R.layout.item_material_todo, list)
                recyclerView.adapter = adapterTodo

                adapterTodo?.setOnItemClickListener(object : MultiItemTypeAdapter.OnItemClickListener {
                    override fun onItemClick(view: View, holder: RecyclerView.ViewHolder, position: Int) {
                        val intent = Intent(activity, ArrivalInTodoDetailActivity::class.java)
                        intent.putExtra("sapOrderNo", list[position].sapOrderNo)
                        intent.putExtra("supplierNo", list[position].supplierNo)
                        intent.putExtra("supplierName", list[position].supplierName)
                        intent.putExtra("creatorNo", list[position].creatorNo)
                        intent.putExtra("sapFirmNo", list[position].sapFirmNo)
                        intent.putExtra("content", list[position].content)
                        startActivity(intent)
                    }

                    override fun onItemLongClick(view: View, holder: RecyclerView.ViewHolder, position: Int): Boolean {
                        return false
                    }
                })
            }
            CodeConstant.FRAGMENT_DONE ->{
                adapterDone = MaterialDoneAdapter(ResourceUtil.getContext(), R.layout.activity_content_list_three, list)
                recyclerView.adapter = adapterDone
                adapterDone?.setOnItemClickListener(object : MultiItemTypeAdapter.OnItemClickListener {
                    override fun onItemClick(view: View, holder: RecyclerView.ViewHolder, position: Int) {
                        val intent = Intent(activity, ArrivalInDoneDetailActivity::class.java)
                        intent.putExtra("sapOrderNo", list[position].sapOrderNo)
                        intent.putExtra("supplierName", list[position].supplierName)
                        intent.putExtra("orderId", list[position].orderId)
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
            val search = event.getSearch()
            val startTime = event.leftTime()
            val endTime = event.rightTime()
            when (fragmentType) {
                CodeConstant.FRAGMENT_TODO -> presenter.fetchTODOMaterial(search,startTime,endTime)
                CodeConstant.FRAGMENT_DONE -> presenter.fetchDoneMaterial(CodeConstant.BUY_IN_TYPE,search,startTime,endTime)
            }
        }
    }
    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun showCarSuccess(position: Int, carData: CarPositionNoData?) {

    }

    override fun saveSuccess() {
    }

    override fun showError(msg: String) {
        ZBUiUtils.showError(msg)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.dispose()
        EventBus.getDefault().unregister(this)
    }

    companion object {

        fun newInstance(type: String): ArrivalInFragment {
            val fragment = ArrivalInFragment()
            val bundle = Bundle()
            bundle.putString(CodeConstant.FRAGMENT_TYPE, type)
            fragment.arguments = bundle

            return fragment
        }
    }
}
