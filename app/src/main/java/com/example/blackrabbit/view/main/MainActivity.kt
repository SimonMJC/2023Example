package com.example.blackrabbit.view.main

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AnticipateInterpolator
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.blackrabbit.R
import com.example.blackrabbit.utils.extension.setPreference

class MainActivity : AppCompatActivity() {

    private var splashScreen: SplashScreen? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        splashScreen = installSplashScreen()
        startSplash()
        setContentView(R.layout.activity_main)


        ("PreferenceKey" to "is Test").setPreference()
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
}