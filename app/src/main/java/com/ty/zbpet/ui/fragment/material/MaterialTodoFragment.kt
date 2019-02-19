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
import com.ty.zbpet.bean.material.MaterialList
import com.ty.zbpet.presenter.material.MaterialPresenter
import com.ty.zbpet.presenter.material.MaterialUiListInterface
import com.ty.zbpet.ui.activity.material.ArrivalInTodoDetailActivity
import com.ty.zbpet.ui.adapter.material.MaterialTodoAdapter
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
 *
 * 待办 （ 入库 ） Fragment
 */
class MaterialTodoFragment : BaseFragment(), MaterialUiListInterface<MaterialList.ListBean> {
    override val fragmentLayout: Int
        get() = R.layout.zb_content_list_fragment

    private var adapter: MaterialTodoAdapter? = null
    private val materialPresenter = MaterialPresenter(this)

    /**
     * 加载的 inflater.inflate  的 View
     *
     * @param view layout inflate 的 View
     */
    override fun onBaseCreate(view: View): View {

        // 设置 Header 样式
        view.refreshLayout!!.setRefreshHeader(MaterialHeader(view.context!!))
        // 设置 Footer 为 球脉冲 样式
        view.refreshLayout!!.setRefreshFooter(BallPulseFooter(view.context!!).setSpinnerStyle(SpinnerStyle.Scale))


        return view

    }

    override fun loadData() {
        materialPresenter.fetchTODOMaterial()
    }

    override fun onResume() {
        super.onResume()

        refreshLayout!!.setOnRefreshListener { refreshLayout ->
            // 传入 false 表示刷新失败
            refreshLayout.finishRefresh(1000)
            // 刷新数据
            materialPresenter.fetchTODOMaterial()
        }
        refreshLayout!!.setOnLoadMoreListener { refreshLayout ->
            // 传入 false 表示刷新失败
            refreshLayout.finishLoadMore(1000)
            ZBUiUtils.showToast("没有更多数据了")
        }
    }

    override fun showMaterial(list: List<MaterialList.ListBean>) {

        if (adapter == null) {
            val manager = LinearLayoutManager(ResourceUtil.getContext())
            recyclerView.addItemDecoration(SpaceItemDecoration(ResourceUtil.dip2px(10), false))
            recyclerView.layoutManager = manager
            adapter = MaterialTodoAdapter(ResourceUtil.getContext(), R.layout.item_material_todo, list)
            recyclerView.adapter = adapter

            adapter!!.setOnItemClickListener(object : MultiItemTypeAdapter.OnItemClickListener {
                override fun onItemClick(view: View, holder: RecyclerView.ViewHolder, position: Int) {
                    val intent = Intent(activity, ArrivalInTodoDetailActivity::class.java)
//                     val intent = Intent(activity, ArrivalInTodoDetailActivityR::class.java)
                    intent.putExtra("sapOrderNo", list[position].sapOrderNo)
                    intent.putExtra("supplierNo", list[position].supplierNo)
                    intent.putExtra("creatorNo", list[position].creatorNo)
                    intent.putExtra("factoryNo", list[position].factoryNo)
                    intent.putExtra("BSART", list[position].BSART)
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

        fun newInstance(tag: String): MaterialTodoFragment {
            val fragment = MaterialTodoFragment()
            val bundle = Bundle()
            bundle.putString("someInt", tag)
            fragment.arguments = bundle

            return fragment
        }
    }
}
