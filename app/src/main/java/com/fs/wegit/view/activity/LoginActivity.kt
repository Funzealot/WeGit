package com.fs.wegit.view.activity

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.fs.lib.base.BaseVMActivity
import com.fs.lib.state.Resource
import com.fs.lib.state.Status
import com.fs.wegit.App
import com.fs.wegit.R
import com.fs.wegit.model.AuthEntity
import com.fs.wegit.repository.LoginRepo
import com.fs.wegit.source.LoginSource
import com.fs.wegit.vm.LoginVM
import kotlinx.android.synthetic.main.login_layout.*

class LoginActivity : BaseVMActivity<LoginVM>() {


    override fun setLayoutId() = R.layout.login_layout


    override fun initViewModel() =
        ViewModelProviders.of(this, LoginVM.ViewModelFactory(LoginRepo(LoginSource()))).get(LoginVM::class.java)

    override fun initObserver() {
        viewModel.authLiveData.observe(this, Observer {
            render(it)
        })

        viewModel.authUserLiveData.observe(this, Observer {
            intentHomeActivity()
        })

    }


    private fun render(resource: Resource<AuthEntity>) {

        when (resource.status) {

            Status.LOADING -> {
                showLoading()
            }

            Status.SUCCESS -> {
                val token = resource.data!!.token
                Log.d("FUN", "授权成功......   ${resource.data?.token}")
                saveToken(token)

            }

            Status.ERROR -> {
                hideLoading()
            }
        }
    }

    private fun intentHomeActivity() {


        val intent = Intent(this, HomeActivity::class.java)
        intent.putExtra("auth_user", viewModel.authUserLiveData.value?.data)
        startActivity(intent)
        finish()
    }

    private fun saveToken(token: String) {

        App.TOKEN = token
        val userName = account_edit.text.toString()
        val password = password_edit.text.toString()
        val edit = getSharedPreferences("token", Context.MODE_PRIVATE).edit()
        edit.putString("token", token)
        edit.putString("user_name", userName)
        edit.putString("password", password)
        edit.apply()
    }

    override fun initView() {

        login_bt.setOnClickListener {
            if (checkInput()) {
                val userName = account_edit.text.toString()
                val password = password_edit.text.toString()
                viewModel.auth(userName, password)
                viewModel.getAuthUserInfo()
            }
        }

    }

    //   11f06fc97fbb5ec74bed3e6fd99e374f28b43b1b
    private fun checkInput(): Boolean {

        val accountInput = account_edit.text.toString()
        val passwordInput = password_edit.text.toString()

        if (accountInput.isEmpty())
            account_input.error = "账号不能为空"
        if (passwordInput.isEmpty())
            password_input.error = "密码不能为空"

        return accountInput.isNotEmpty() and passwordInput.isNotEmpty()
    }


    private fun showLoading() {

        login_bt.text = "登 录 中......"
    }

    private fun hideLoading() {

        login_bt.text = "登 录"
    }
}