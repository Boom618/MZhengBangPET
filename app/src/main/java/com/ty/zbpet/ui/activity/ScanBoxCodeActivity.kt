package com.ty.zbpet.ui.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.util.DiffUtil
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import com.pda.scanner.ScanReader
import com.ty.zbpet.R
import com.ty.zbpet.constant.CodeConstant
import com.ty.zbpet.presenter.system.CommInterface
import com.ty.zbpet.presenter.system.CommPresenter
import com.ty.zbpet.ui.adapter.diffadapter.MaterialDiffUtil
import com.ty.zbpet.base.BaseActivity
import com.ty.zbpet.constant.TipString
import com.ty.zbpet.ui.adapter.ScanBoxCodeAdapter
import com.ty.zbpet.ui.widght.DividerItemDecoration
import com.ty.zbpet.util.ResourceUtil
import com.ty.zbpet.util.ZBUiUtils
import com.ty.zbpet.util.scan.ScanBoxInterface
import com.ty.zbpet.util.scan.ScanObservable
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter
import kotlinx.android.synthetic.main.activity_scan_box_code.*
import java.lang.Exception
import kotlin.collections.ArrayList

/**
 * @author TY
 * 扫码功能
 */
class ScanBoxCodeActivity : BaseActivity(), ScanBoxInterface, CommInterface {

    override val activityLayout: Int
        get() = R.layout.activity_scan_box_code

    private var isOpen: Boolean = false
    // 商品编号
    private var goodsNo = ""
    /**
     * 箱码数据
     */
    private var boxCodeList: ArrayList<String> = ArrayList()
    private val oldList = ArrayList<String>()
    private var adapter: ScanBoxCodeAdapter? = null
    private var itemId: Int = 0

    /**
     * 查看 or 保存
     */
    private var state: Boolean = false
    private val scanReader = ScanReader.getScannerInstance()
    private val scanObservable = ScanObservable(this)

    private var presenter = CommPresenter(this)


    override fun onBaseCreate(savedInstanceState: Bundle?) {

    }

    override fun initOneData() {
        itemId = intent.getIntExtra("itemId", -1)
        goodsNo = intent.getStringExtra("goodsNo")
        state = intent.getBooleanExtra(CodeConstant.PAGE_STATE, false)
        // 箱码列表 传过来有可能为 null 。
        try {
            boxCodeList = intent.getStringArrayListExtra("boxCodeList")
        } catch (e: Exception) {
            boxCodeList = ArrayList()
            e.printStackTrace()
        }

        val mLayoutManager = LinearLayoutManager(ResourceUtil.getContext())
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST, ResourceUtil.dip2px(1), ResourceUtil.getColor(R.color.split_line)))
        recyclerView.layoutManager = mLayoutManager
//        adapter = BindBoxCodeAdapter(ResourceUtil.getContext(), boxCodeList)
        adapter = ScanBoxCodeAdapter(ResourceUtil.getContext(), R.layout.item_box_code, boxCodeList)
        recyclerView.adapter = adapter
        adapter?.setOnItemClickListener(object : MultiItemTypeAdapter.OnItemClickListener {
            override fun onItemLongClick(view: View?, holder: RecyclerView.ViewHolder, position: Int): Boolean {
                return true
            }

            override fun onItemClick(view: View, holder: RecyclerView.ViewHolder, position: Int) {
                val imageDel = holder.itemView.findViewById<ImageView>(R.id.iv_del)

                imageDel.setOnClickListener {
                    val size = boxCodeList.size
                    try {
                        val boxNo = boxCodeList[position]
                        boxCodeList.remove(boxNo)
                        tv_scan_number.text = "${TipString.boxCodeNumber}：${size - 1}"
                        adapter?.notifyItemRemoved(position)
                        // 修复  删除错位 == TODO[问题：01] 还是存在错位
                        // 列表从 positionStart 位置到itemCount数量的列表项批量删除数据时调用，伴有动画效果
                        //adapter?.notifyItemRangeRemoved(position, size)
                        // 列表从positionStart位置到itemCount数量的列表项进行数据刷新
                        ZBUiUtils.showWarning("${TipString.deleteBoxCode} $boxNo")
                        //ZBUiUtils.showWarning("position = $position ")
                        adapter?.notifyItemRangeChanged(0, size - 1)
                    } catch (e: Exception) {
                        ZBUiUtils.showWarning("Exception pos = $position ")
                        adapter?.notifyDataSetChanged()
                        e.printStackTrace()
                    }
                }
            }
        })
    }

    override fun initTwoView() {

        //初始化扫描仪
        isOpen = scanInit()

        if (state) {
            initToolBar(R.string.box_binding_list, TipString.save, View.OnClickListener { returnActivity() })
        } else {
            initToolBar(R.string.box_binding_list)
        }
    }

    /**
     * 返回上级 Ac
     */
    private fun returnActivity() {
        when (boxCodeList.size) {
            0 -> ZBUiUtils.showWarning(TipString.scanPlease)
            else -> {
                val intent = Intent()
                intent.putExtra("itemId", itemId)
                intent.putStringArrayListExtra("boxCodeList", boxCodeList)
                setResult(CodeConstant.RESULT_CODE, intent)
                finish()
            }
        }
    }

    private fun scanInit(): Boolean {
        return null != scanReader && scanReader.open(applicationContext)
    }

    override fun onDestroy() {
        super.onDestroy()
        scanReader?.close()
    }

    override fun onKeyDown(keyCode: Int, event: android.view.KeyEvent): Boolean {

        if (keyCode == CodeConstant.KEY_CODE_131
                || keyCode == CodeConstant.KEY_CODE_135
                || keyCode == CodeConstant.KEY_CODE_139) {

            // 扫描
            if (isOpen && state) {
                scanObservable.scanBox(scanReader, -1)
            }
            return true
        }

        return super.onKeyDown(keyCode, event)
    }


    /**
     * 扫描成功
     *
     * @param position
     * @param positionNo
     */
    override fun ScanSuccess(position: Int, positionNo: String) {

        presenter.urlAnalyze(positionNo, goodsNo)
//        checkBoxCode(positionNo)
    }

    override fun urlAnalyze(codeNo: String) {
        checkBoxCode(codeNo)
    }

    override fun showError(msg: String) {
        ZBUiUtils.showError(msg)
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    /**
     * 箱码不需要验证，直接传给服务器
     *
     * @param positionNo
     */
    private fun checkBoxCode(positionNo: String) {

        if (boxCodeList.contains(positionNo)) {
            ZBUiUtils.showWarning(TipString.boxCodeHasBeenScan)
        } else {
            boxCodeList.add(0, positionNo)
            //  列表position位置添加一条数据时可以调用，伴有动画效果
            adapter?.notifyItemInserted(0)
            // 添加数据移动到顶端（可选） 否则超过屏幕数据不可见
            recyclerView.scrollToPosition(0)
            //ArrayList<String> oldList = new ArrayList<>(boxCodeList);
//            oldList.clear()
//            oldList.addAll(boxCodeList)
//            boxCodeList.add(positionNo)
            val number = boxCodeList.size.toString()
            tv_scan_number.text = "${TipString.boxCodeNumber}：$number"
//            val diffResult = DiffUtil.calculateDiff(MaterialDiffUtil(oldList, boxCodeList))
//            adapter?.let { diffResult.dispatchUpdatesTo(it) }

        }
    }

}
