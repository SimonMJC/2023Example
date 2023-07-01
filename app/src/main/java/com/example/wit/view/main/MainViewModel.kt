package com.example.wit.view.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wit.base.intent.MainEvent
import com.example.wit.base.intent.MainState
import com.example.wit.base.viewmodel.BaseViewModel
import com.example.wit.data.repository.toilet.ToiletRepository
import com.example.wit.data.repository.toilet.ToiletRepositoryImpl
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.runningFold
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel : BaseViewModel() {

    private val repo: ToiletRepository by lazy { ToiletRepositoryImpl() }

    private val _sideEffects = Channel<String>()
    val sideEffects = _sideEffects.receiveAsFlow()

    private val events = Channel<MainEvent>()

    val state: StateFlow<MainState> = events.receiveAsFlow()
        .runningFold(MainState(), ::reduceState)
        .stateIn(viewModelScope, SharingStarted.Eagerly, MainState())

    private fun reduceState(current: MainState, event: MainEvent): MainState = when (event) {
        is MainEvent.Loading -> current.copy(loading = true)
        is MainEvent.Loaded -> current.copy(loading = false, size = event.size)

    }

    fun getResult() {
        viewModelScope.launch {
            events.send(MainEvent.Loading)
            val result = repo.getToiletList()
            result.service?.listTotalCount?.let {
                events.send(MainEvent.Loaded(it))
                _sideEffects.send("$it is loaded")
            }
        }
    }
}