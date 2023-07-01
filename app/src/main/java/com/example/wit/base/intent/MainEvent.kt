package com.example.wit.base.intent

sealed interface MainEvent {

    object Loading : MainEvent
    class Loaded(val size: Int) : MainEvent
}