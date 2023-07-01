package com.example.wit.base.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wit.core.LoadingController
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.SocketException
import java.net.UnknownHostException

abstract class BaseViewModel : ViewModel() {

    companion object {
        const val EVENT_COROUTINE_EXCEPTION = 400
        const val EVENT_SOCKET_EXCEPTION = 401
        const val EVENT_HTTP_EXCEPTION = 402
        const val EVENT_UNKNOWN_HOST_EXCEPTION = 403
    }

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        hideLoadingView()
        Log.e("CoroutineExceptionHandler->", throwable.message.toString())
        when (throwable) {
            is SocketException -> emitError(EVENT_SOCKET_EXCEPTION) // Bad Internet
            is HttpException -> emitError(EVENT_HTTP_EXCEPTION) // Parse Error
            is UnknownHostException -> emitError(EVENT_UNKNOWN_HOST_EXCEPTION) // Wrong connection
            else -> emitError(EVENT_COROUTINE_EXCEPTION)
        }
    }

    private val _errorEvent = MutableSharedFlow<Any?>()
    val errorEvent: SharedFlow<Any?>
        get() = _errorEvent.asSharedFlow()

    var loadingController: LoadingController? = null

    fun showLoadingView() {
        loadingController?.showLoadingView()
    }

    fun hideLoadingView() {
        loadingController?.hideLoadingView()
    }

    fun emitError(err: Any?) {
        when (err) {
            EVENT_COROUTINE_EXCEPTION,
            EVENT_HTTP_EXCEPTION,
            EVENT_SOCKET_EXCEPTION,
            EVENT_UNKNOWN_HOST_EXCEPTION -> Log.e("BaseViewModel", "emitError::$err")

            else -> launch { _errorEvent.emit(err) }
        }
    }

    /**
     * # Coroutines
     * ## ex) launch { ... }
     */

    fun ViewModel.launch(
        dispatcher: CoroutineDispatcher = Dispatchers.Main,
        block: suspend CoroutineScope.() -> Unit
    ) {
        viewModelScope.launch(dispatcher + exceptionHandler) {
            block()
        }
    }
}