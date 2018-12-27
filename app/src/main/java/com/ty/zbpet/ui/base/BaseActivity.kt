package com.ty.zbpet.ui.base

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.TextView

import com.ty.zbpet.R
import com.ty.zbpet.util.ACache
import com.ty.zbpet.constant.CodeConstant

import butterknife.ButterKnife
import butterknife.Unbinder

/**
 * @author TY
 */
abstract class BaseActivity : AppCompatActivity() {

    private var mUnbinder: Unbinder? = null
    private var mCache: ACache? = null

    /**
     * Activity Layout 布局
     *
     * @return
     */
    protected abstract val activityLayout: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activityLayout)

        mUnbinder = ButterKnife.bind(this)
        mCache = ACache.get(application)

        initOneData()

        onBaseCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()

        initTwoView()
    }

    override fun onResume() {
        super.onResume()
    }

    /**
     * 初始化 View
     *
     * @param savedInstanceState
     */
    protected abstract fun onBaseCreate(savedInstanceState: Bundle?)

    /**
     * 初始化 Data
     */
    protected abstract fun initOneData()

    /**
     * 初始化 View
     */
    protected abstract fun initTwoView()

    /**
     * 打开一个 AC
     *
     * @param clz                    跳转类
     * @param isCloseCurrentActivity 关闭当前页
     */
    @JvmOverloads
    fun gotoActivity(clz: Class<*>, isCloseCurrentActivity: Boolean = false, ex: Bundle? = null) {
        val intent = Intent(this, clz)
        if (ex != null) {
            intent.putExtras(ex)
        }
        startActivity(intent)
        if (isCloseCurrentActivity) {
            finish()
        }
    }


    @JvmOverloads
    protected fun initToolBar(intId: Int = 0, listener: View.OnClickListener? = null) {

        // 左边返回
        findViewById<View>(R.id.iv_back).setOnClickListener {
            clearCache()
            finish()
        }

        // 中间标题
        val midText = findViewById<TextView>(R.id.tv_title)
        if (intId == 0) {
            midText.text = ""
        } else {
            midText.setText(intId)
        }

        // 右边监听事件
        val right = findViewById<TextView>(R.id.tv_right)

        if (null == listener) {
            right.visibility = View.GONE
        } else {
            right.setOnClickListener(listener)
        }

    }

    /**
     * @param intId     中间标题
     * @param rightText 右边文字
     * @param listener  右边 listener
     */
    protected fun initToolBar(intId: Int, rightText: String, listener: View.OnClickListener) {
        // 左边返回
        findViewById<View>(R.id.iv_back).setOnClickListener {
            clearCache()
            finish()
        }

        // 中间标题
        val topText = findViewById<TextView>(R.id.tv_title)
        topText.setText(intId)

        // 右边监听事件
        val right = findViewById<TextView>(R.id.tv_right)
        right.text = rightText
        right.setOnClickListener(listener)
    }

    /**
     * 重置 ACache 中保存的的数据
     */
    private fun clearCache() {
        // 库位码 内容
        mCache!!.put(CodeConstant.SCAN_BOX_KEY, "")
        mCache!!.put(CodeConstant.ET_ZKG, "")

        //DataUtils.clearId();
    }


    override fun onDestroy() {
        super.onDestroy()
        clearCache()

        mUnbinder!!.unbind()
    }
}

