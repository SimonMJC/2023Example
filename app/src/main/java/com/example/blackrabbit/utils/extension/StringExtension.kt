package com.example.blackrabbit.utils.extension

import android.app.Activity
import android.content.Context
import android.widget.Toast
import com.example.blackrabbit.di.KoinApplication
import com.example.blackrabbit.BuildConfig
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar

fun String.toToast(context: Context?) {
    context?.let {
        Toast.makeText(it, this, Toast.LENGTH_SHORT).show()
    }
}

fun String.toDebugToast(context: Context?) {
    if (BuildConfig.DEBUG) this.toToast(context)
}

fun String?.toSnackBar(
    activity: Activity,
    callback: BaseTransientBottomBar.BaseCallback<Snackbar>? = null
) {
    this?.let { msg ->
        Snackbar.make(activity.findViewById(android.R.id.content), msg, Snackbar.LENGTH_LONG)
            .apply {
                callback?.let { addCallback(it) }
            }.show()
    }
}

fun Int?.toSnackBar(
    activity: Activity,
    callback: BaseTransientBottomBar.BaseCallback<Snackbar>? = null
) {
    this?.let { KoinApplication.string(it).toSnackBar(activity, callback) }
}