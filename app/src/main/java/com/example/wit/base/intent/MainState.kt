package com.example.wit.base.intent

data class MainState(
    val size: Int = 0,
    val loading: Boolean = false,
    val error: String? = null
)
