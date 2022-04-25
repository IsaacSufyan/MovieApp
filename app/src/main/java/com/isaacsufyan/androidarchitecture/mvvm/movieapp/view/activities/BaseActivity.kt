package com.isaacsufyan.androidarchitecture.mvvm.movieapp.view.activities

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

abstract class BaseActivity : AppCompatActivity() {

    override fun onResume() {
        super.onResume()
        this.getToolbarInstance()?.let { this.initView(it) }
    }

    private fun initView(toolbar: Toolbar) = setSupportActionBar(toolbar)

    abstract fun getToolbarInstance(): Toolbar?
}