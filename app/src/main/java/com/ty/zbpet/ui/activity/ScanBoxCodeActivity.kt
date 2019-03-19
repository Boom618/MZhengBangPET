package com.ty.zbpet.ui.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.util.DiffUtil
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.pda.scanner.ScanReader
import com.ty.zbpet.R
import com.ty.zbpet.constant.CodeConstant
import com.ty.zbpet.presenter.system.CommInterface
import com.ty.zbpet.presenter.system.CommPresenter
import com.ty.zbpet.ui.adapter.BindBoxCodeAdapter
import com.ty.zbpet.ui.adapter.diffadapter.MaterialDiffUtil
import com.ty.zbpet.ui.base.BaseActivity
import com.ty.zbpet.ui.widght.DividerItemDecoration
import com.ty.zbpet.util.ResourceUtil
import com.ty.zbpet.util.ZBUiUtils
import com.ty.zbpet.util.scan.ScanBoxInterface
import com.ty.zbpet.util.scan.ScanObservable
import kotlinx.android.synthetic.main.activity_scan_box_code.*
import java.util.*

/**
 * @author TY
 * 扫码功能
 */
class ScanBoxCodeActivity : BaseActivity(), ScanBoxInterface , CommInterface {

    override val activityLayout: Int
        get() = R.layout.activity_scan_box_code

    private var isOpen: Boolean = false
    /**
     * 箱码数据
     */
    private var boxCodeList: ArrayList<String>? = ArrayList()
    private val oldList = ArrayList<String>()
    private var adapter: BindBoxCodeAdapter? = null
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
        state = intent.getBooleanExtra(CodeConstant.PAGE_STATE, false)
        boxCodeList = intent.getStringArrayListExtra("boxCodeList")
        if (boxCodeList == null) {
            boxCodeList = ArrayList()
        }

        val mLayoutManager = LinearLayoutManager(ResourceUtil.getContext())
        recyclerView!!.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST, ResourceUtil.dip2px(1), ResourceUtil.getColor(R.color.split_line)))
        recyclerView!!.layoutManager = mLayoutManager
        adapter = BindBoxCodeAdapter(ResourceUtil.getContext(), boxCodeList)
        recyclerView!!.adapter = adapter
    }

    override fun initTwoView() {

        //初始化扫描仪
        isOpen = scanInit()

        if (state) {
            initToolBar(R.string.box_binding_list, View.OnClickListener { returnActivity() })
        } else {
            initToolBar(R.string.box_binding_list)
        }
    }

    /**
     * 返回上级 Ac
     */
    private fun returnActivity() {
        val intent = Intent()
        intent.putExtra("itemId", itemId)
        intent.putStringArrayListExtra("boxCodeList", boxCodeList)
        setResult(RESULT_SCAN_CODE, intent)
        finish()
    }

    private fun scanInit(): Boolean {
        return null != scanReader && scanReader.open(applicationContext)
    }

    override fun onDestroy() {
        super.onDestroy()
        scanReader!!.close()
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

        presenter.urlAnalyze(positionNo)
    }

    override fun urlAnalyze(codeNo: String) {
        checkBoxCode(codeNo)
    }

    override fun showError(msg: String) {
        ZBUiUtils.showToast(msg)
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

        if (boxCodeList!!.contains(positionNo)) {
            ZBUiUtils.showToast("该箱码扫码过")
        } else {
            //ArrayList<String> oldList = new ArrayList<>(boxCodeList);
            oldList.clear()
            oldList.addAll(boxCodeList!!)
            boxCodeList!!.add(positionNo)
            if (adapter != null) {
                val diffResult = DiffUtil.calculateDiff(MaterialDiffUtil(oldList, boxCodeList))
                diffResult.dispatchUpdatesTo(adapter!!)
            }
        }
    }

    companion object {
        private const val RESULT_SCAN_CODE = 2
    }

}
