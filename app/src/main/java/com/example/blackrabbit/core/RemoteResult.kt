package com.example.blackrabbit.core

import com.example.blackrabbit.data.model.ErrorData
import com.example.blackrabbit.data.model.ErrorResult

/**
     * # remoteResult
     * ## Repository에서 호출한 Router의 api 응답 결과를 Pair<Response, ErrorData>로 반환
     * api().remoteResult { response -> 새로운 모델로 맵핑 }
     */
    fun <R : ErrorResult?, T : Any?> R.remoteResult(block: (R?) -> T?): Pair<T?, ErrorData?> {
        val baseResult = this as? ErrorResult
        return when {
            baseResult == null -> null to ErrorData(null, null, "response is null")
            baseResult.error != null -> null to baseResult.error
            else -> block.invoke(this) to null
        }
    }

    /**
     * # Pair<*,*>.result
     * ## remoteResult의 반환값을 onSuccess와 onError 블럭으로 나누어 반환
     * ```
     * ex) withContext {
     *      repository.api().result(
     *          onSuccess = { ... },
     *          onError = { ... }
     *          )
     *      }
     * ```
     */
    suspend fun <S, E> Pair<S?, E?>.result(
        onSuccess: suspend S.() -> Unit,
        onError: (suspend E.() -> Any?)? = null
    ) = let { (response, error) ->
            response?.run { onSuccess(this) } ?: error?.run { onError?.invoke(this) }
        }
