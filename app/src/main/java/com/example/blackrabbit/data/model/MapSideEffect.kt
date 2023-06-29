package com.example.blackrabbit.data.model

sealed class MapSideEffect {

    data class Toast(val text: String): MapSideEffect()

    data class SnackBar(val text: String): MapSideEffect()

}