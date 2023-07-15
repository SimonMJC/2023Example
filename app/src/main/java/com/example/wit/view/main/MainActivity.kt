package com.example.wit.view.main

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.wit.R
import com.example.wit.databinding.ActivityMainBinding
import com.naver.maps.geometry.LatLng
import com.naver.maps.geometry.LatLngBounds
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.LocationTrackingMode
import com.naver.maps.map.MapView
import com.naver.maps.map.NaverMap
import com.naver.maps.map.NaverMap.LAYER_GROUP_BUILDING
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import com.naver.maps.map.util.MarkerIcons
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

    override var naverMap: NaverMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mapView = binding.mvMain
    }

    override fun onMapReady(map: NaverMap) {
        this.naverMap = map
        Log.e("MainActivity", "onMapReady::$map")

        lifecycleScope.launch(coroutineContext + coroutineExceptionHandler) {
            repeatOnLifecycle(Lifecycle.State.STARTED) {

                // mapview setting
                naverMap?.run {
                    mapType = NaverMap.MapType.Basic
                    locationTrackingMode = LocationTrackingMode.Follow
                    setLayerGroupEnabled(LAYER_GROUP_BUILDING, true)
                    isIndoorEnabled = true
                    uiSettings.run {
                        isLocationButtonEnabled = true
                    }
                    addOnLocationChangeListener { location ->
                        Log.e("NAVER_MAP", "addOnLocationChangeListener::${location.latitude}, ${location.longitude}")
                    }

                    val cameraUpdate = CameraUpdate.scrollTo(LatLng(37.5670135, 126.9783740))
                    this.moveCamera(cameraUpdate)
                }
            }
        }
    }

    override fun initView() {
        binding.run {

        }
    }

    override fun initViewModel() {
        lifecycleScope.launch(coroutineContext + exceptionHandler) {
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

                /* viewModel.sideEffects
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
                     .launchIn(lifecycleScope)*/
            }
        }
    }

    private fun initMarker() {
        naverMap?.run {
            val marker = Marker().apply {
                icon = MarkerIcons.BLACK
                iconTintColor = Color.RED
                map = naverMap
            }
            marker.position = LatLng(37.5670135, 126.9783740)

            Log.e("NAVER_MAP", "initMarker::${marker.position}")
        }
    }
}