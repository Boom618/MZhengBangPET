package com.ty.zbpet.ui.base

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.SparseArray
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

    private val mView: SparseArray<View> = SparseArray(10)

    private lateinit var rootView: View
    /**
     * Activity Layout 布局
     *
     * @return
     */
    protected abstract val activityLayout: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activityLayout)
        rootView = layoutInflater.inflate(activityLayout, null, false)

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
    protected fun initToolBar(midString: Int = 0, rightString: String? = null, listener: View.OnClickListener? = null) {

        // 左边返回
        findViewById<View>(R.id.iv_back).setOnClickListener {
            clearCache()
            finish()
        }

        // 中间标题
        val midText = findViewById<TextView>(R.id.tv_title)
        if (midString == 0) {
            midText.text = ""
        } else {
            midText.setText(midString)
        }

        // 右边监听事件
        val right = findViewById<TextView>(R.id.tv_right)

        if (rightString.isNullOrEmpty()) {
            right.visibility = View.GONE
        } else {
            right.text = rightString
            right.setOnClickListener(listener)
        }

    }
    /**
     * 重置 ACache 中保存的的数据
     */
    private fun clearCache() {
    }

    fun <T : View> get(id: Int): T {
        return bindView(id)
    }

    private fun <T : View> bindView(id: Int): T {

        val view: T = rootView.findViewById(id)
        mView.put(id, view)
        return view
    }

    /**
     * vararg 可变长度
     */
    fun setViewOnClickListener(listener: View.OnClickListener, vararg ids: Int) {
        if (ids.isNotEmpty()) {
            for (id in ids) {
                get<View>(id).setOnClickListener(listener)
            }
        }
    }

    fun setViewOnClickListener(listener: View.OnClickListener, vararg views: View) {
        if (views.isNotEmpty()) {
            for (view in views) {
                view.setOnClickListener(listener)
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        clearCache()

        mUnbinder!!.unbind()
    }
}

