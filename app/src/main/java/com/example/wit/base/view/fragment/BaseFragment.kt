package com.example.wit.base.view.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.fragment.app.Fragment
import com.example.wit.core.LoadingController
import com.example.wit.di.KoinApplication
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlin.coroutines.CoroutineContext


abstract class BaseFragment : Fragment(), LoadingController, CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    private val job = SupervisorJob()


    var isFragmentVisible: Boolean = false


    abstract fun initView()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loggingLifeCycle("onCreate")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loggingLifeCycle("onViewCreated")
//        initLoadingDialog()
        initView()
    }

    override fun onResume() {
        super.onResume()
        loggingLifeCycle("onResume")
    }

    override fun onPause() {
        super.onPause()
        loggingLifeCycle("onPause")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        loggingLifeCycle("onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
        hideLoadingView()
        loggingLifeCycle("onDestroy")
    }

    override fun hideLoadingView() {
        Handler(Looper.getMainLooper()).post {
//            loadingDialog?.let { dialog ->
//                if (!isDetached && !isRemoving && dialog.isShowing) dialog.dismiss()
//            }
        }
    }

    override fun showLoadingView() {
        Handler(Looper.getMainLooper()).post {
//            loadingDialog?.let { dialog ->
//                if (isAdded) dialog.show()
//            }
        }
    }

    override fun setMenuVisibility(menuVisible: Boolean) {
        super.setMenuVisibility(menuVisible)
        isFragmentVisible = menuVisible
    }

    private fun loggingLifeCycle(lifeCycle: String) {
//        CLog.i("$lifeCycle:::$this")
    }


    fun String?.toSnackBar(callback: BaseTransientBottomBar.BaseCallback<Snackbar>? = null) {
        activity?.findViewById<View>(android.R.id.content)?.let { content ->
            this?.let {
                Snackbar.make(
                    content,
                    it,
                    Snackbar.LENGTH_LONG
                ).apply {
                    callback?.let { addCallback(it) }
                }.show()
            }
        }
    }

    fun Int?.toSnackBar(callback: BaseTransientBottomBar.BaseCallback<Snackbar>? = null) {
        this?.let { KoinApplication.string(it).toSnackBar(callback) }
    }
}