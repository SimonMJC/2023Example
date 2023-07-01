package com.example.wit.base.view.activity

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BindActivity<B : ViewDataBinding> : BaseActivity() {

    abstract val layoutRes: Int
    lateinit var binding: B

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = DataBindingUtil.setContentView(this, layoutRes)
        super.onCreate(savedInstanceState)
    }
}