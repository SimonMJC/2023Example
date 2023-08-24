package com.example.wit.base.view.activity

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AnticipateInterpolator
import androidx.activity.OnBackPressedCallback
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.wit.R
import com.example.wit.core.LoadingController
import com.example.wit.di.KoinApplication
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlin.coroutines.CoroutineContext

abstract class BaseActivity : AppCompatActivity(), LoadingController, CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    private val job = SupervisorJob()

    protected open var backPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            Log.e("BaseActivity", "OnBackPressedCallback::")
            finish()
        }
    }


    private var splashScreen: SplashScreen? = null

    val exceptionHandler by lazy {
        CoroutineExceptionHandler { _, exception ->
            Log.e("BaseActivity", "CoroutineException::${exception.message}")
        }
    }


    abstract fun initView()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        splashScreen = installSplashScreen()
        startSplash()
        Log.e("BaseActivity", "onCreate::$savedInstanceState")
        onBackPressedDispatcher.addCallback(this, backPressedCallback)
        window.statusBarColor = getColor(R.color.white)
        initView()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.e("BaseActivity", "onSaveInstanceState::$outState")

    }

    override fun onStart() {
        super.onStart()
        Log.e("BaseActivity", "onStart::")
    }

    override fun onResume() {
        super.onResume()
        Log.e("BaseActivity", "onResume::")
    }

    override fun onPause() {
        super.onPause()
        Log.e("BaseActivity", "onPause::")
    }

    override fun onStop() {
        super.onStop()
        Log.e("BaseActivity", "onStop::")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("BaseActivity", "onDestroy::")
    }

    override fun onLowMemory() {
        super.onLowMemory()
        Log.e("BaseActivity", "onLowMemory::")
    }

    override fun hideLoadingView() {
        if (!isFinishing && !isDestroyed) {

        }
    }

    override fun showLoadingView() {
        if (!isFinishing) {

        }
    }

    private fun startSplash() {
        splashScreen?.setOnExitAnimationListener { splashScreenView ->
            val scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 0.5f, 2f, 1f)
            val scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 0.5f, 2f, 1f)

            ObjectAnimator.ofPropertyValuesHolder(splashScreenView.iconView, scaleX, scaleY).run {
                interpolator = AnticipateInterpolator()
                duration = 2000L
                doOnEnd {
                    splashScreenView.remove()
                }
                start()
            }
        }
    }

    open fun String?.toSnackBar(callback: BaseTransientBottomBar.BaseCallback<Snackbar>? = null) {
        this?.let { msg ->
            Snackbar.make(findViewById(android.R.id.content), msg, Snackbar.LENGTH_LONG).apply {
                callback?.let { addCallback(it) }
            }.show()
        }
    }

    open fun Int?.toSnackBar(callback: BaseTransientBottomBar.BaseCallback<Snackbar>? = null) {
        this?.let { KoinApplication.string(it).toSnackBar(callback) }
    }
}