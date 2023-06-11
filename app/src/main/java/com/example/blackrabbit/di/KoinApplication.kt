package com.example.blackrabbit.di

import android.app.Application
import android.content.Context
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat

class KoinApplication : Application() {

    companion object {
        lateinit var appContext: KoinApplication

        fun resources(): Resources = appContext.resources

        fun string(@StringRes resId: Int): String {
            return appContext.getString(resId)
        }

        fun color(@ColorRes resId: Int): Int {
            return ContextCompat.getColor(appContext, resId)
        }

        fun drawable(@DrawableRes resId: Int): Drawable? {
            return ContextCompat.getDrawable(appContext, resId)
        }

        fun packageName(): String {
            return appContext.packageName
        }
    }

    override fun onCreate() {
        super.onCreate()

        appContext = this
    }
}