package com.ty.zbpet.ui.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

/**
 * @author PVer on 2018/12/21.
 *
 *
 * 主页 待办/已办 列表
 */
class MainListAdapter(fm: FragmentManager, private  val mTitles:Array<String> ,private val mFragments: List<Fragment>) : FragmentPagerAdapter(fm) {

    override fun getCount(): Int {
        return mFragments.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mTitles[position]
    }

    override fun getItem(position: Int): Fragment {
        return mFragments[position]
    }
}
