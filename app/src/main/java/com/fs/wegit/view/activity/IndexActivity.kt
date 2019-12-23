package com.fs.wegit.view.activity

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.fs.lib.base.BaseActivity
import com.fs.wegit.App
import com.fs.wegit.R
import kotlinx.android.synthetic.main.activity_index.*

class IndexActivity : BaseActivity() {


    override fun setLayoutId() = R.layout.activity_index

    override fun init() {
        super.init()
        checkPermission()
        checkUser()
    }

    private fun checkUser() {

        val edit = getSharedPreferences("token", Context.MODE_PRIVATE)
        val token = edit.getString("token", "") ?: ""
        if (token.isNotEmpty()) {
            App.TOKEN = token
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        } else {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    private fun checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE), 0)
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)


    }
}
