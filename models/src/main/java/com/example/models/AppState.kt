package com.example.models

sealed class AppState<T> {
    data class Success<T>(val data: T?) : AppState<T>()
    data class Error<T>(val error: Throwable) : AppState<T>()
    data class Loading<T>(val progress: Int?) : AppState<T>()
}



