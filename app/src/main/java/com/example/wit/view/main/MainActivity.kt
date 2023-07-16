package com.example.wit.view.main

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.wit.R
import com.example.wit.base.view.activity.BindViewModelActivity
import com.example.wit.databinding.ActivityMainBinding
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.LocationTrackingMode
import com.naver.maps.map.MapView
import com.naver.maps.map.NaverMap
import com.naver.maps.map.NaverMap.LAYER_GROUP_BUILDING
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.util.MarkerIcons
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class MainActivity : BindViewModelActivity<ActivityMainBinding, MainViewModel>(), OnMapReadyCallback {

    override val layoutRes: Int
        get() = R.layout.activity_main
    override val showLoadingView: (() -> Unit)?
        get() = ::showLoadingView
    override val hideLoadingView: (() -> Unit)?
        get() = ::hideLoadingView
    override val viewModel: MainViewModel by viewModels()

    private lateinit var mapView: MapView
    private var naverMap: NaverMap? = null
    private val coroutineExceptionHandler by lazy {
        CoroutineExceptionHandler { _, exception ->
            Log.e("BindMapActivity", "mapExceptionHandler::${exception.message}")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.run {
            mapView = mvMain
            mapView.onCreate(savedInstanceState)
            mapView.getMapAsync(this@MainActivity)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }


    override fun initView() {
        binding.run {
            btnMarker.setOnClickListener {
                Log.e("MainActivity", "initView:: $it")
                initMarker()
            }
        }
    }

    override fun initViewModel() {
        lifecycleScope.launch(Dispatchers.Main + coroutineExceptionHandler) {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.run {
                    getToiletLocations()
                    toiletList.onEach { infos ->
                        Log.e("", "initViewModel:: ${infos.size}")
                        infos.forEach { info ->
                            Log.e("", "initViewModel:: ${info.centerX}/${info.centerY}")
                        }
                    }.launchIn(lifecycleScope)
                }
            }
        }
    }

    override fun onMapReady(map: NaverMap) {
        Log.e("MainActivity", "onMapReady::$map")

        lifecycleScope.launch(Dispatchers.Main + coroutineExceptionHandler) {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                naverMap = map
                naverMap?.run {
                    mapType = NaverMap.MapType.Basic
                    locationTrackingMode = LocationTrackingMode.Follow
                    setLayerGroupEnabled(LAYER_GROUP_BUILDING, true)
                    isIndoorEnabled = true
                    uiSettings.isLocationButtonEnabled = true
                    addOnLocationChangeListener { location ->
                        Log.e("MainActivity", "LocationChange::${location.latitude}, ${location.longitude}")
                    }

                    val cameraUpdate = CameraUpdate.scrollTo(LatLng(37.5670135, 126.9783740))
                    moveCamera(cameraUpdate)
                }
            }
        }
    }

    private fun initMarker() {
        Marker().run {
            icon = MarkerIcons.BLACK
            iconTintColor = Color.RED
            position = LatLng(37.5670135, 126.9783740)
            map = naverMap
            Log.e("MainActivity", "initMarker::$position")
        }
    }
}