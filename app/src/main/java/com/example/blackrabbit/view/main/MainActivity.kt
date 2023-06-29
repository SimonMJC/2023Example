package com.example.blackrabbit.view.main

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AnticipateInterpolator
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.blackrabbit.R
import com.example.blackrabbit.data.model.MapSideEffect
import com.example.blackrabbit.data.model.MapState
import com.example.blackrabbit.databinding.ActivityMainBinding
import com.example.blackrabbit.di.KoinApplication
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.orbitmvi.orbit.compose.collectSideEffect
import org.orbitmvi.orbit.viewmodel.observe

class MainActivity : AppCompatActivity() {

    private var splashScreen: SplashScreen? = null

    private val viewModel by viewModels<MainViewModel>()

    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        splashScreen = installSplashScreen()
        startSplash()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)


        initView()

        initViewModel()
    }

    private fun initView() {
        binding?.run {
            viewModel.getResult()
            tvTitle.setOnClickListener {
                //                viewModel.fetchLocation()
            }
        }
        setStatusBarColor(this)

    }

    private fun initViewModel() {
        lifecycleScope.launch(Dispatchers.Main + CoroutineExceptionHandler { _, exception ->
            Log.e("MainActivity", "${exception.message}")
        }) {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.sideEffects
                    .onEach {
                        Toast.makeText(this@MainActivity, it, Toast.LENGTH_SHORT).show()
                    }
                    .launchIn(lifecycleScope)

                viewModel.state
                    .onEach {
                        when (it.loading) {
                            true -> {
                                Log.e("Loading", it.toString())
                            }

                            false -> {
                                Log.e("Loading", it.toString())
                            }
                        }
                    }
                    .launchIn(lifecycleScope)
            }
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

    private fun setStatusBarColor(activity: Activity) {
        activity.window.statusBarColor = getColor(R.color.white) // KoinApplication.color(R.color.black)
    }
}