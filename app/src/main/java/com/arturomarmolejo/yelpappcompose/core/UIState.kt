package com.arturomarmolejo.yelpappcompose.core

sealed class UIState<out T> {
    data object LOADING: UIState<Nothing>()
    data class SUCCESS<T>(val response: T): UIState<T>()
    data class ERROR(val exception: Exception): UIState<Nothing>()
}