package com.example.wit.view.sample

import androidx.activity.viewModels
import com.example.wit.R
import com.example.wit.base.view.activity.BindViewModelActivity
import com.example.wit.databinding.ActivityRidingMateBinding
import com.example.wit.view.main.MainViewModel

class RidingMateActivity: BindViewModelActivity<ActivityRidingMateBinding, MainViewModel>() {

    override val layoutRes: Int
        get() = R.layout.activity_riding_mate

    override val viewModel: MainViewModel by viewModels()

    override val showLoadingView: (() -> Unit)
        get() = ::showLoadingView
    override val hideLoadingView: (() -> Unit)
        get() = ::hideLoadingView


    override fun initView() {

    }


    override fun initViewModel() {

    }
}