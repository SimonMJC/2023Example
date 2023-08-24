package com.example.wit.view.sample.tabs

import androidx.fragment.app.activityViewModels
import com.example.wit.R
import com.example.wit.base.view.fragment.BindViewModelFragment
import com.example.wit.databinding.FragmentSocialBinding
import com.example.wit.view.main.MainViewModel

class SocialFragment: BindViewModelFragment<FragmentSocialBinding, MainViewModel>() {
    override val layoutId: Int
        get() = R.layout.fragment_social


    override val viewModel: MainViewModel by activityViewModels()
    override val showLoadingView: (() -> Unit)?
        get() = ::showLoadingView
    override val hideLoadingView: (() -> Unit)?
        get() = ::hideLoadingView

    override fun initView() {
    }

    override fun initObserve() {
    }
}