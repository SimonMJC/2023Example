package com.example.wit.base.view.fragment

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import com.example.wit.base.viewmodel.BaseViewModel
import com.example.wit.base.viewmodel.VMController

/**
 * # since 2023.01 - 2023.02
 * # created by Na Hee Jae
 * reference from cashwalk
 */
abstract class BindViewModelFragment<B : ViewDataBinding, VM : BaseViewModel>
    : BindFragment<B>(), VMController<VM>, View.OnClickListener {

    abstract fun initObserve()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        remoteLoadingController()
        initObserve()
    }

    override fun onClick(v: View?) {}

    private fun remoteLoadingController() {
        this.viewModel.loadingController = this
    }
}