package com.example.wit.view.main

import android.os.Bundle
import android.util.Log
import androidx.databinding.ViewDataBinding
import com.example.wit.base.view.activity.BindViewModelActivity
import com.example.wit.base.viewmodel.BaseViewModel
import com.naver.maps.map.LocationTrackingMode
import com.naver.maps.map.MapView
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.util.FusedLocationSource
import kotlinx.coroutines.CoroutineExceptionHandler

abstract class BindMapActivity<B : ViewDataBinding, VM : BaseViewModel> :
        BindViewModelActivity<B, VM>(), OnMapReadyCallback {

    companion object {
        const val LOCATION_PERMISSION_REQUEST_CODE = 1001
    }
    abstract var mapView: MapView?
    abstract var naverMap: NaverMap?

    private lateinit var locationSource: FusedLocationSource

    val coroutineExceptionHandler by lazy {
        CoroutineExceptionHandler { _, exception ->
            Log.e("BindMapActivity", "mapExceptionHandler::${exception.message}")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mapView?.onCreate(savedInstanceState)
        locationSource =
            FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.e("BindMapActivity", "onSaveInstanceState::$outState")
        mapView?.onSaveInstanceState(outState)
    }

    override fun onStart() {
        super.onStart()
        Log.e("BindMapActivity", "onStart::")
        mapView?.onStart()
    }

    override fun onResume() {
        super.onResume()
        Log.e("BindMapActivity", "onResume::")
        mapView?.onResume()
    }

    override fun onPause() {
        super.onPause()
        Log.e("BindMapActivity", "onPause::")
        mapView?.onPause()
    }

    override fun onStop() {
        super.onStop()
        Log.e("BindMapActivity", "onStop::")
        mapView?.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("BindMapActivity", "onDestroy::")
        mapView?.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        Log.e("BindMapActivity", "onLowMemory::")
        mapView?.onLowMemory()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (locationSource.onRequestPermissionsResult(requestCode, permissions,
                grantResults)) {
            if (!locationSource.isActivated) { // 권한 거부됨
                naverMap?.locationTrackingMode = LocationTrackingMode.None
            }
            return
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

    }
}