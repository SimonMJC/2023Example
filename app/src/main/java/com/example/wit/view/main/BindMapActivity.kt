package com.example.wit.view.main

import android.os.Bundle
import android.util.Log
import androidx.databinding.ViewDataBinding
import com.example.wit.base.view.activity.BindViewModelActivity
import com.example.wit.base.viewmodel.BaseViewModel
import com.naver.maps.map.MapView
import com.naver.maps.map.OnMapReadyCallback
import kotlinx.coroutines.CoroutineExceptionHandler

abstract class BindMapActivity<B : ViewDataBinding, VM : BaseViewModel> :
        BindViewModelActivity<B, VM>(), OnMapReadyCallback {

    abstract var mapView: MapView?

    val mapExceptionHandler by lazy {
        CoroutineExceptionHandler { _, exception ->
            Log.e("BindMapActivity", "mapExceptionHandler::${exception.message}")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mapView?.onCreate(savedInstanceState)
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


}