package com.fs.wegit.view.activity

import android.content.Context
import android.content.Intent
import android.view.MenuItem
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fs.lib.base.BaseVMActivity
import com.fs.lib.state.Resource
import com.fs.lib.state.Status
import com.fs.lib.widget.StatusLayoutManager
import com.fs.wegit.R
import com.fs.wegit.adapter.RepoListAdapter
import com.fs.wegit.helper.GlideHelper
import com.fs.wegit.model.AuthUserEntity
import com.fs.wegit.model.RepoItemEntity
import com.fs.wegit.model.RepoListEntity
import com.fs.wegit.repository.RepoListRepo
import com.fs.wegit.source.RepoListSource
import com.fs.wegit.vm.RepoListVM
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.home_layout.*
import kotlinx.android.synthetic.main.home_layout.navigation_view
import kotlinx.android.synthetic.main.home_layout.tool_bar

class HomeActivity : BaseVMActivity<RepoListVM>() {


    lateinit var statusLayoutManager: StatusLayoutManager

    override fun initViewModel(): RepoListVM =
        ViewModelProviders.of(this, RepoListVM.ViewModelFactory(RepoListRepo(RepoListSource())))
            .get(RepoListVM::class.java)

    private lateinit var repoListAdapter: RepoListAdapter
    private val repoList = arrayListOf<RepoItemEntity>()

    override fun initView() {

        setSupportActionBar(tool_bar)

        val contentView = getContentView()
        statusLayoutManager = StatusLayoutManager.Builder(contentView)
            .loadingLayout(R.layout.status_loading) {
                val tv: TextView = it?.findViewById(R.id.loading_text)!!
                tv.text = "加载中......"
            }

            .build()

        val layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        repoListAdapter = RepoListAdapter(this, repoList)

        with(repoListAdapter) {
            itemListener = {
                val intent = Intent(this@HomeActivity, RepoActivity::class.java)
                intent.putExtra("repo", repoList[it])
                startActivity(intent)
            }

            userImgListener = {
                val intent = Intent(this@HomeActivity, UserActivity::class.java)
                intent.putExtra("userUrl", repoList[it].owner.url)
                startActivity(intent)
            }

            userNameListener = {
                val intent = Intent(this@HomeActivity, UserActivity::class.java)
                intent.putExtra("userUrl", repoList[it].owner.url)
                startActivity(intent)

            }
        }

        with(repo_list) {
            val divider = DividerItemDecoration(this@HomeActivity, RecyclerView.VERTICAL)
            ContextCompat.getDrawable(this@HomeActivity, R.drawable.divider)?.let { divider.setDrawable(it) }
            addItemDecoration(divider)
            setLayoutManager(layoutManager)
            adapter = repoListAdapter
        }

        navigation_view.setNavigationItemSelectedListener {

            when(it.itemId) {

                R.id.logout -> {
                    logout()
                }

            }

            return@setNavigationItemSelectedListener true

        }

        val authUser = intent.getSerializableExtra("auth_user")

        authUser?.let {

            val group = navigation_view.getHeaderView(0)
            val userName: TextView = group.findViewById(R.id.account_name)
            val userImage: CircleImageView = group.findViewById(R.id.account_img)
            userName.text = (authUser as AuthUserEntity).login
            GlideHelper.loadToView(this, authUser.avatar_url, userImage)

        }

    }

    override fun initData() {

        viewModel.loadRepoList("android")
        viewModel.loadUser()

    }

    private fun renderUser(resource: Resource<AuthUserEntity>) {

        when (resource.status) {

            Status.SUCCESS -> {

                val group = navigation_view.getHeaderView(0)
                val userName: TextView = group.findViewById(R.id.account_name)
                val userImage: CircleImageView = group.findViewById(R.id.account_img)

                userName.text = resource.data?.login
                GlideHelper.loadToView(this, resource.data!!.avatar_url, userImage)

            }
        }
    }

    override fun initObserver() {

        viewModel.repoLiveData.observe(this, Observer {
            render(it)
        })

        viewModel.userLiveData.observe(this, Observer {
            renderUser(it)
        })

    }

    private fun render(fileRes: Resource<RepoListEntity>) {

        when (fileRes.status) {

            Status.INIT -> {
                statusLayoutManager.setStatus(Status.LOADING)
            }

            Status.LOADING -> {

                statusLayoutManager.setStatus(Status.LOADING)
            }
            Status.SUCCESS -> {
                fileRes.data?.items?.let { repoList.addAll(it) }
                repoListAdapter.notifyDataSetChanged()
                statusLayoutManager.setStatus(Status.SUCCESS)
            }

            else -> {
            }
        }
    }




    private fun logout() {

        val edit = getSharedPreferences("token", Context.MODE_PRIVATE).edit()
        edit.clear()
        edit.apply()
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    override fun setLayoutId() = R.layout.home_layout
}