package com.fs.wegit.view.activity

import android.content.Intent
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.fs.lib.base.BaseVMActivity
import com.fs.lib.state.Resource
import com.fs.lib.state.Status
import com.fs.wegit.R
import com.fs.wegit.adapter.FileListAdapter
import com.fs.wegit.model.FileItemEntity
import com.fs.wegit.repository.FileListRepo
import com.fs.wegit.source.FileListSource
import com.fs.wegit.vm.FileListVM
import kotlinx.android.synthetic.main.file_holder_layout.*

class FileListActivity : BaseVMActivity<FileListVM>() {


    private lateinit var fileListAdapter: FileListAdapter
    private val fileList = arrayListOf<FileItemEntity>()
    private var baseContentUrl = ""
    private var currentContentUrl = ""

    override fun initViewModel() =
        ViewModelProviders.of(this, FileListVM.ViewModelFactory(FileListRepo(FileListSource())))
            .get("FileListVM", FileListVM::class.java)

    override fun initObserver() {

        viewModel?.fileListLiveData?.observe(this, Observer {
            render(it)
        })

    }

    override fun setLayoutId(): Int = R.layout.file_holder_layout


    override fun initView() {

        fileListAdapter = FileListAdapter(this, fileList)
        file_list.adapter = fileListAdapter
        file_list.setOnItemClickListener { parent, view, position, id ->
            if (fileList[position].type == "dir") {
                currentContentUrl = currentContentUrl + "/" + fileList[position].name
                viewModel?.getFileList(currentContentUrl)
            } else {
                val intent = Intent(this, CodeActivity::class.java)
                intent.putExtra("file_url", fileList[position].download_url)

                startActivity(intent)
            }
        }
    }


    override fun initData() {

        baseContentUrl = intent.getStringExtra("content_url")!!
        currentContentUrl = baseContentUrl
        viewModel?.getFileList(baseContentUrl)

    }


    private fun render(resource: Resource<List<FileItemEntity>>) {

        when (resource.status) {

            Status.LOADING -> {

            }
            Status.SUCCESS -> {
                resource.data?.let {
                    fileList.clear()
                    fileList.addAll(it)
                    fileListAdapter.notifyDataSetChanged()
                }

            }
            Status.INIT -> {
            }
            Status.ERROR -> {
            }
            Status.EMPTY -> {
            }
        }
    }
}