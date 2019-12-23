package com.fs.wegit.view.activity

import android.content.Intent
import android.view.View
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import br.tiagohm.markdownview.css.styles.Github
import com.fs.lib.base.BaseVMActivity
import com.fs.lib.state.Resource
import com.fs.lib.state.Status
import com.fs.lib.widget.StatusLayoutManager
import com.fs.wegit.R
import com.fs.wegit.model.RepoItemEntity
import com.fs.wegit.model.RepoPageEntity
import com.fs.wegit.repository.RepoPageRepo
import com.fs.wegit.source.RepoSource
import com.fs.wegit.vm.RepoVM
import kotlinx.android.synthetic.main.repo_page.*
import java.io.Serializable

class RepoActivity : BaseVMActivity<RepoVM>() {


    lateinit var statusLayoutManager: StatusLayoutManager

    override fun initViewModel(): RepoVM = ViewModelProviders.of(
        this, RepoVM.ViewModelFactory(
            RepoPageRepo(RepoSource())
        )
    )
        .get("RepoVM", RepoVM::class.java)


    private val repoItem: Serializable by lazy { intent.getSerializableExtra("repo")!! }
    override fun setLayoutId(): Int = R.layout.repo_page
    override fun initObserver() {
        viewModel?.repoPageLiveData?.observe(this, Observer {
            render(it)
        })

    }

    override fun initData() {
        super.initData()
        if (viewModel?.repoPageLiveData?.value == null) {
            viewModel?.initRepoItem(repoItem as RepoItemEntity)
        }
    }


    override fun initView() {

        statusLayoutManager = StatusLayoutManager.Builder(getContentView())
            .loadingLayout(R.layout.status_loading) {
                it!!.findViewById<TextView>(R.id.loading_text).text = "加载中......"
            }.build()

        home_page_item.setOnClickListener {

        }

        language_item.setOnClickListener {

        }

        trends_item.setOnClickListener {

        }

        trends_item.setOnClickListener {

        }

        file_item.setOnClickListener {

            val intent = Intent(this, FileListActivity::class.java)
            intent.putExtra("content_url", viewModel?.contentUrl)
            startActivity(intent)
        }

    }


    private fun bindData(repoInfo: RepoPageEntity) {

        repo_name.text = repoInfo.repoItem?.name

        repoInfo.repoItem?.description ?: let {
            repo_des.text = "暂无描述~"
        }

        repoInfo.repoItem?.description?.let {
            if (it.isEmpty())
                repo_des.text = "暂无描述~"
            else repo_des.text = it
        }


        starts_count.text = repoInfo.repoItem?.stargazers_count.toString()
        forks_count.text = repoInfo.repoItem?.forks_count.toString()
        issues_count.text = repoInfo.repoItem?.open_issues_count.toString()
        watchers_count.text = repoInfo.repoItem?.watchers_count.toString()
        home_page_url.text = repoInfo.repoItem?.homepage

        val css = Github()
        css.removeRule(".scrollup")
        readme_content.addStyleSheet(css)
        readme_content.loadMarkdown(repoInfo.readme)


        if (repoInfo.repoItem?.homepage == null || repoInfo.repoItem.homepage.isEmpty()) {
            home_page_item.visibility = View.GONE
        }

    }


    private fun render(resource: Resource<RepoPageEntity>) {

        when (resource.status) {

            Status.INIT -> {
                statusLayoutManager.setStatus(Status.LOADING)
            }

            Status.LOADING -> {

            }

            Status.SUCCESS -> {

                statusLayoutManager.setStatus(Status.SUCCESS)
                resource.data?.let { bindData(repoInfo = it) }

            }

            else -> {
            }
        }
    }

}