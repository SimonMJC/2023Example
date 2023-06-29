package com.example.blackrabbit.view.main

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.blackrabbit.base.intent.MainEvent
import com.example.blackrabbit.base.intent.MainState
import com.example.blackrabbit.core.remoteResult
import com.example.blackrabbit.data.model.MapSideEffect
import com.example.blackrabbit.data.model.MapState
import com.example.blackrabbit.data.repository.toilet.ToiletRepository
import com.example.blackrabbit.data.repository.toilet.ToiletRepositoryImpl
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.runningFold
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container

class MainViewModel : ViewModel() {

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

    fun fetchLocation() {
        viewModelScope.launch {
            events.send(MainEvent.Loading)
            val locations = repo.getLocations()
            //            events.send(MainEvent.Loaded(locations))
            _sideEffects.send("${locations.size} is loaded")
        }
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