package com.fs.lib.base

abstract class BaseVMActivity<VM : IViewModel> : BaseActivity() {

    lateinit var viewModel: VM
        private set

    final override fun init() {
        viewModel = initViewModel()
        lifecycle.addObserver(viewModel as IViewModel)
        initObserver()
        super.init()
    }


    abstract fun initViewModel(): VM
    abstract fun initObserver()

}