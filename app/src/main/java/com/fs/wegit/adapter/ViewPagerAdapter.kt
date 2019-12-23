package com.fs.wegit.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class ViewPagerAdapter(
    fm: FragmentManager,
    private val fragments: Array<Fragment>,
    private val titles: Array<String>
) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {


    override fun getItem(position: Int) = fragments[position]


    override fun getCount() = fragments.size

    override fun getPageTitle(position: Int) = titles[position]
}