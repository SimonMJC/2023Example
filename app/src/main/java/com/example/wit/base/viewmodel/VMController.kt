package com.example.wit.base.viewmodel

interface VMController<VM : BaseViewModel> {

    val viewModel: VM
    val showLoadingView: (() -> Unit)?
    val hideLoadingView: (() -> Unit)?
}