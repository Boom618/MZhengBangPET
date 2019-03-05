package com.ty.zbpet.ui.fragment.material


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.scwang.smartrefresh.header.MaterialHeader
import com.scwang.smartrefresh.layout.constant.SpinnerStyle
import com.scwang.smartrefresh.layout.footer.BallPulseFooter
import com.ty.zbpet.R
import com.ty.zbpet.bean.CarPositionNoData
import com.ty.zbpet.bean.material.MaterialList
import com.ty.zbpet.presenter.material.MaterialUiListInterface
import com.ty.zbpet.presenter.material.PickOutPresenter
import com.ty.zbpet.ui.activity.material.PickOutTodoDetailActivity
import com.ty.zbpet.ui.adapter.material.PickOutTodoAdapter
import com.ty.zbpet.ui.base.BaseFragment
import com.ty.zbpet.ui.widght.SpaceItemDecoration
import com.ty.zbpet.util.ResourceUtil
import com.ty.zbpet.util.ZBUiUtils
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter
import kotlinx.android.synthetic.main.activity_main_todo_and_done.*
import kotlinx.android.synthetic.main.zb_content_list_fragment.*
import kotlinx.android.synthetic.main.zb_content_list_fragment.view.*


/**
 * A simple [Fragment] subclass.
 * Use the [PickOutTodoFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 * @author TY
 *
 *
 * 领料出库 待办列表
 */
class PickOutTodoFragment : BaseFragment(), MaterialUiListInterface<MaterialList.ListBean> {
    override fun showCarSuccess(position: Int, carData: CarPositionNoData?) {

    }

    override fun showSuccess() {
    }

    override fun showError(msg: String?) {
    }


    private var adapter: PickOutTodoAdapter? = null

    private val materialPresenter = PickOutPresenter(this)
    private lateinit var mParam1: String

    override val fragmentLayout: Int
        get() = R.layout.zb_content_list_fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onBaseCreate(view: View): View {

        val leftTime = activity!!.findViewById<TextView>(R.id.tv_time_left)
        val rightTime = activity!!.findViewById<TextView>(R.id.tv_time_right)

//        leftTime.setOnClickListener {
//            ZBUiUtils.showPickDate(it.context) { date, _ ->
//                var selectTime = ZBUiUtils.getTime(date)
//                leftTime.text = selectTime
////
//                ZBUiUtils.showToast(ZBUiUtils.getTime(date))
//            }
//        }
        // 设置 Header 样式
        view.refreshLayout!!.setRefreshHeader(MaterialHeader(this.context!!))
        // 设置 Footer 为 球脉冲 样式
        view.refreshLayout!!.setRefreshFooter(BallPulseFooter(this.context!!).setSpinnerStyle(SpinnerStyle.Scale))
        return view
    }

    override fun loadData() {
        materialPresenter.fetchPickOutTodoList()
    }

    override fun onResume() {
        super.onResume()

        refreshLayout!!.setOnRefreshListener { refreshLayout ->
            // 传入 false 表示刷新失败
            refreshLayout.finishRefresh(1000)
            // 刷新数据
            materialPresenter.fetchPickOutTodoList()
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
            recyclerView!!.addItemDecoration(SpaceItemDecoration(ResourceUtil.dip2px(10), false))
            recyclerView!!.layoutManager = manager
            adapter = PickOutTodoAdapter(context!!, R.layout.item_pick_out_todo, list)
            recyclerView!!.adapter = adapter

            adapter!!.setOnItemClickListener(object : MultiItemTypeAdapter.OnItemClickListener {
                override fun onItemClick(view: View, holder: RecyclerView.ViewHolder, position: Int) {
                    val intent = Intent(activity, PickOutTodoDetailActivity::class.java)
                    intent.putExtra("sapOrderNo", list[position].sapOrderNo)
                    intent.putExtra("sapFirmNo", list[position].sapFirmNo)
                    intent.putExtra("content", list[position].content)
                    intent.putExtra("supplierId", list[position].supplierId)
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

        private const val ARG_PARAM1 = "param1"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment PickOutTodoFragment.
         */
        fun newInstance(tag: String): PickOutTodoFragment {
            val fragment = PickOutTodoFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, tag)
            fragment.arguments = args
            return fragment
        }
    }
}
