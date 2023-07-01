package com.example.wit.base.view.activity

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import com.example.wit.base.viewmodel.BaseViewModel
import com.example.wit.base.viewmodel.VMController

abstract class BindViewModelActivity<B : ViewDataBinding, VM : BaseViewModel>
    : BindActivity<B>(), VMController<VM> {

    abstract fun initViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        remoteLoadingController()
        initViewModel()
    }

    private fun remoteLoadingController() {
        this.viewModel.loadingController = this
    }
}