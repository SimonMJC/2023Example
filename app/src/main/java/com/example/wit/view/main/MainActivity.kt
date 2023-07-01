package com.example.wit.view.main

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
import com.example.wit.R
import com.example.wit.base.view.activity.BaseActivity
import com.example.wit.databinding.ActivityMainBinding
import com.naver.maps.geometry.LatLng
import com.naver.maps.geometry.LatLngBounds
import com.naver.maps.map.MapView
import com.naver.maps.map.NaverMap
import com.naver.maps.map.NaverMap.LAYER_GROUP_BUILDING
import com.naver.maps.map.OnMapReadyCallback
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class MainActivity : BindMapActivity<ActivityMainBinding, MainViewModel>() {

    override val layoutRes: Int
        get() = R.layout.activity_main
    override val showLoadingView: (() -> Unit)?
        get() = ::showLoadingView
    override val hideLoadingView: (() -> Unit)?
        get() = ::hideLoadingView

    override val viewModel: MainViewModel by viewModels()

    override var mapView: MapView? = null


    //    private val viewModel by viewModels<MainViewModel>()

    //    private var binding: ActivityMainBinding? = null

    //    private var mapView: MapView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mapView = binding.mvMain
        mapView?.onCreate(savedInstanceState)
    }

    override fun onMapReady(map: NaverMap) {
        Log.e("MainActivity", "onMapReady::$map")
        val bounds = LatLngBounds.Builder()
            .include(LatLng(37.5640984, 126.9712268))
            .include(LatLng(37.5651279, 126.9767904))
            .include(LatLng(37.5625365, 126.9832241))
            .include(LatLng(37.5585305, 126.9809297))
            .include(LatLng(37.5590777, 126.974617))
            .build()

        lifecycleScope.launch(coroutineContext + mapExceptionHandler) {
            repeatOnLifecycle(Lifecycle.State.STARTED) {

                // mapview setting
                with(map) {
                    mapType = NaverMap.MapType.Basic
                    setLayerGroupEnabled(LAYER_GROUP_BUILDING, true)
                    isIndoorEnabled = true
                    uiSettings.run {
                        isLocationButtonEnabled = true
                    }
                }
            }
        }
    }

    override fun initView() {
        binding.run {
            viewModel.getResult()
        }
    }

    override fun initViewModel() {
        lifecycleScope.launch(coroutineContext + exceptionHandler) {
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
}