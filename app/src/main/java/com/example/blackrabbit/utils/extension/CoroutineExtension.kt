package com.example.blackrabbit.utils.extension

import android.util.Log
import android.view.View
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import org.joda.time.DateTime

/**
 * ## 클릭된 View를 Flow로 반환
 */
@OptIn(ExperimentalCoroutinesApi::class)
fun View.clicks(): Flow<Unit> = callbackFlow {
    setOnClickListener { trySend(Unit) }
    awaitClose {
        Log.i("clicks:: in awaitClose -> ", DateTime.now().toString())
        setOnClickListener(null)
    }
}

/**
 * ## Flow<T>.throttleFirst(duration: Long)
 * ## 마지막 emit한 시간과 현재 시간을 비교하여 flow를 발행한다. 조건에 부합할 시 무시
 */
fun <T> Flow<T>.throttleFirst(duration: Long): Flow<T> = flow {
    var lastEmitTime = 0L
    collect {
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastEmitTime > duration) {
            lastEmitTime = currentTime
            emit(it)
        } else Log.e("throttleFirst", "emit ignore")
    }
}