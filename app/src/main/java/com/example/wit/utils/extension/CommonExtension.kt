package com.example.wit.utils.extension

import android.util.TypedValue
import com.example.wit.di.KoinApplication

// true인 경우에만 block이 호출되고, 전달받은 값 그대로 반환합니다.
fun Boolean.ifTrue(block: (Boolean) -> Unit): Boolean = apply {
    if (this) block(this)
}

// false인 경우에만 block이 호출되고, 전달받은 값 그대로 반환합니다.
fun Boolean.ifFalse(block: (Boolean) -> Unit): Boolean = apply {
    if (not()) block(this)
}

fun Int.dpToPx(): Int = TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_DIP,
    this.toFloat(),
    KoinApplication.appContext.resources?.displayMetrics
).toInt()
