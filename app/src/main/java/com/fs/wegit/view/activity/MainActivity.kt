package com.fs.wegit.view.activity

import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.ViewModelProviders
import com.fs.lib.base.BaseVMActivity
import com.fs.wegit.R
import com.fs.wegit.repository.UserRepo
import com.fs.wegit.source.UserSource
import com.fs.wegit.vm.UserVM

import kotlinx.android.synthetic.main.test_home_layout.*


class MainActivity : BaseVMActivity<UserVM>() {


    override fun initViewModel(): UserVM = ViewModelProviders
        .of(this, UserVM.ViewModelFactory(UserRepo(UserSource())))
        .get(UserVM::class.java)


    override fun setLayoutId() = R.layout.test_home_layout

    override fun initView() {

        setSupportActionBar(tool_bar)
        supportActionBar?.title = null
        with(supportActionBar!!) {
            setHomeButtonEnabled(false)
            setDisplayHomeAsUpEnabled(false)
        }
//         val toggle = ActionBarDrawerToggle(
//            this,
//            drawer,
//            tool_bar,
//            R.string.navigation_drawer_open,
//            R.string.navigation_drawer_close
//        )
//        toggle.isDrawerIndicatorEnabled = true
//        toggle.syncState()
//        drawer.addDrawerListener(toggle)
//
        val titles = arrayOf("", "", "")
//        view_pager.adapter = ViewPagerAdapter(supportFragmentManager)


/*
        with(tool_bar) {
                        setNavigationOnClickListener {
                if (!drawer.isDrawerOpen(GravityCompat.START))
                    drawer.openDrawer(GravityCompat.START)
                else drawer.closeDrawer(GravityCompat.START)

            }
            //  设置菜单栏监听
            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.menu1 -> {
                        Log.d(TAG, "menu1 打开")
                    }
                }
                return@setOnMenuItemClickListener true
            }
        }
*/
    }


    //  toolbar 显示菜单栏 需要覆写此方法
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.home_menu, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.search -> {
                Log.d("FUN", "menu1 打开")

            }
        }
        return true
    }

    override fun initObserver() {
    }
}
