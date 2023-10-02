package com.example.wit.base.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BindFragment<B : ViewDataBinding> : BaseFragment() {

    private var _binding: B? = null
    open val binding: B get() = _binding!!

     val bindingInitialized: Boolean
        get() = _binding != null && isAdded

    /**
     * setContentView로 호출할 Layout의 리소스 Id.
     * ex) R.layout.activity_main
     */
    abstract val layoutId: Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding?.let { _binding = null }
    }
}