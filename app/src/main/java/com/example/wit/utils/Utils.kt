package com.example.wit.utils

import android.app.Activity
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.os.Build
import android.view.inputmethod.InputMethodManager
import com.example.wit.di.KoinApplication
import com.example.wit.utils.extension.valueLong
import java.text.NumberFormat

object Utils {
    fun getPendingIntentFlag(flag: Int): Int = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        flag or PendingIntent.FLAG_IMMUTABLE
    } else {
        flag
    }


    fun isGrantedExactAlarm(context: Context): Boolean =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            (context.getSystemService(Context.ALARM_SERVICE) as AlarmManager).canScheduleExactAlarms()
        } else {
            true
        }

    fun getAppVersion(): String =
        KoinApplication.appContext.packageManager.getPackageInfo(KoinApplication.appContext.packageName, 0).versionName

    fun hideSoftKeyboard(activity: Activity) {
        try {
            val inputMethodManager =
                activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(activity.currentFocus!!.windowToken, 0)
        } catch (ignored: Exception) {

        }

    }

    fun showSoftKeyboard(activity: Activity) {
        val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(
            InputMethodManager.SHOW_FORCED,
            InputMethodManager.HIDE_IMPLICIT_ONLY
        )
    }


    fun numberComma(number: String?, default: String? = null): String {
        return try {
            if (number.isNullOrEmpty()) {
                return if (default.isNullOrEmpty()) {
                    "0"
                } else {
                    default
                }
            }

            NumberFormat.getInstance().format(number.valueLong())
        } catch (e: Exception) {
            if (default.isNullOrEmpty()) {
                "0"
            } else {
                default
            }
        }
    }
}