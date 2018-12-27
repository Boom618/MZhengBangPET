package com.ty.zbpet.ui.base


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import butterknife.Unbinder
import com.ty.zbpet.constant.CodeConstant
import com.ty.zbpet.util.ACache


/**
 * @author TY
 */
abstract class BaseFragment : Fragment() {

    /**
     * 功能列表：
     * 1、ButterKnife 初始化、解绑
     * 2、Layout
     * 3、
     */



    private var mUnbinder: Unbinder? = null

    protected var mCache: ACache? = null

    /**
     * Fragment Layout
     *
     * @return
     */
    protected abstract val fragmentLayout: Int


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        val view = inflater.inflate(fragmentLayout, container, false)

        mUnbinder = ButterKnife.bind(this, view)

        mCache = ACache.get(view.context)
        // onBaseCreate(view);
        return onBaseCreate(view)
    }

    /**
     * 初始化 View
     * @param view
     * @return
     */
    protected abstract fun onBaseCreate(view: View): View

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // TODO: Use the ViewModel

    }


    /**
     * 重置 ACache 中保存的的数据
     */
    private fun clearCache() {
        mCache!!.put(CodeConstant.SCAN_BOX_KEY, "")
        mCache!!.put(CodeConstant.ET_ZKG, "")
    }

    override fun onDestroy() {
        super.onDestroy()

        clearCache()

        mUnbinder!!.unbind()
    }
}
