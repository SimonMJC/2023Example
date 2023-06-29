package com.example.blackrabbit.base.intent

sealed interface MainEvent {

    object Loading : MainEvent
    class Loaded(val size: Int) : MainEvent
}