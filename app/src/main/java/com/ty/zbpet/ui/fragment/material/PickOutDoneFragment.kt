package com.ty.zbpet.ui.fragment.material


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.scwang.smartrefresh.header.MaterialHeader
import com.scwang.smartrefresh.layout.constant.SpinnerStyle
import com.scwang.smartrefresh.layout.footer.BallPulseFooter
import com.ty.zbpet.R
import com.ty.zbpet.bean.CarPositionNoData
import com.ty.zbpet.bean.material.MaterialList
import com.ty.zbpet.constant.CodeConstant
import com.ty.zbpet.presenter.material.MaterialUiListInterface
import com.ty.zbpet.presenter.material.PickOutPresenter
import com.ty.zbpet.ui.activity.material.PickOutDoneDetailActivity
import com.ty.zbpet.ui.adapter.material.PickOutDoneAdapter
import com.ty.zbpet.ui.base.BaseFragment
import com.ty.zbpet.ui.widght.SpaceItemDecoration
import com.ty.zbpet.util.ResourceUtil
import com.ty.zbpet.util.ZBUiUtils
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter
import kotlinx.android.synthetic.main.zb_content_list_fragment.*
import kotlinx.android.synthetic.main.zb_content_list_fragment.view.*


/**
 * A simple [Fragment] subclass.
 *
 * @author TY
 * 领料出库 已办列表
 */
class PickOutDoneFragment : BaseFragment(), MaterialUiListInterface<MaterialList.ListBean> {
    override fun showCarSuccess(position: Int, carData: CarPositionNoData?) {

    }

    override fun saveSuccess() {
    }

    override fun showError(msg: String?) {
    }

    private var adapter: PickOutDoneAdapter? = null

    private val presenter = PickOutPresenter(this)

    /**
     * 下拉刷新 flag
     */
    private var refresh = false

    override val fragmentLayout: Int
        get() = R.layout.zb_content_list_fragment


    override fun onBaseCreate(view: View): View {
        // 设置 Header 样式
        view.refreshLayout!!.setRefreshHeader(MaterialHeader(context!!))
        // 设置 Footer 为 球脉冲 样式
        view.refreshLayout!!.setRefreshFooter(BallPulseFooter(context!!).setSpinnerStyle(SpinnerStyle.Scale))
        return view
    }

    override fun loadData() {
        presenter.fetchPickOutDoneList(CodeConstant.PICK_OUT_TYPE)
    }

    override fun onResume() {
        super.onResume()

        refreshLayout!!.setOnRefreshListener { refreshLayout ->
            // 传入 false 表示刷新失败
            refreshLayout.finishRefresh(1000)
            // 刷新数据
            presenter.fetchPickOutDoneList(CodeConstant.PICK_OUT_TYPE)
            refresh = true
        }
        refreshLayout!!.setOnLoadMoreListener { refreshLayout ->
            // 传入 false 表示刷新失败
            refreshLayout.finishLoadMore(1000)
            ZBUiUtils.showToast("没有更多数据了")
        }
    }


    override fun showMaterial(list: List<MaterialList.ListBean>) {

        if (adapter == null || refresh) {
            refresh = false
            if (adapter == null) {
                val manager = LinearLayoutManager(ResourceUtil.getContext())
                recyclerView!!.addItemDecoration(SpaceItemDecoration(ResourceUtil.dip2px(10), false))
                recyclerView!!.layoutManager = manager
            }
            adapter = PickOutDoneAdapter(context!!, R.layout.item_pick_out_done, list)
            recyclerView!!.adapter = adapter

            adapter!!.setOnItemClickListener(object : MultiItemTypeAdapter.OnItemClickListener {
                override fun onItemClick(view: View, holder: RecyclerView.ViewHolder, position: Int) {
                    val intent = Intent(activity, PickOutDoneDetailActivity::class.java)
                    intent.putExtra("sapOrderNo", list[position].sapOrderNo)
                    intent.putExtra("orderId", list[position].orderId)
                    startActivity(intent)
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

    companion object {

        private val ARG_PARAM = "ARG_PARAM"

        fun newInstance(tag: String): PickOutDoneFragment {
            val fragment = PickOutDoneFragment()
            val args = Bundle()
            args.putString(ARG_PARAM, tag)
            fragment.arguments = args
            return fragment
        }
    }
}
