package com.ty.zbpet.util

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import com.flyco.tablayout.SegmentTabLayout
import com.flyco.tablayout.listener.OnTabSelectListener

/**
 * @author TY on 2018/12/29.
 * 主页 Viewpager 切换
 *
 */
class TabLayoutViewPage {


    /**
     * fm:          FragmentManager
     * viewpager:   关联的 viewpager
     * stl:         tabLayout 关联的标签
     * mFragments:  加载的 Fragments
     */
     fun setViewPageToTab(fm: FragmentManager, viewpager: ViewPager, stl: SegmentTabLayout, mFragments: List<Fragment>) {

        // TabLayout
        viewpager.adapter = MainListAdapter(fm, mFragments)
        stl.setTabData(mTitles)
        stl.setOnTabSelectListener(object : OnTabSelectListener {

            override fun onTabReselect(position: Int) {

            }

            override fun onTabSelect(position: Int) {

                viewpager.currentItem = position
            }
        })

        viewpager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {

            }

            override fun onPageSelected(position: Int) {
                stl.currentTab = position
            }
        })

        viewpager.currentItem = 0
    }


    /**
     * @author PVer on 2018/12/21.
     *
     *
     * 主页 待办/已办 列表
     */
    class MainListAdapter(fm: FragmentManager, private val mFragments: List<Fragment>) : FragmentPagerAdapter(fm) {


        override fun getCount(): Int {
            return mFragments.size
        }

        override fun getPageTitle(position: Int): CharSequence {
            return mTitles[position]
        }

        override fun getItem(position: Int): Fragment {
            return mFragments[position]
        }
    }

    companion object {
        val mTitles = arrayOf("待办", "已办")
    }
}