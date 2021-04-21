package com.didik.moflix.utils.state

sealed class ViewState<out T : Any> {
    data class RenderLoading(val isLoading: Boolean) : ViewState<Nothing>()
    data class RenderData<out T : Any>(val data: T) : ViewState<T>()
    data class RenderError(val error: String) : ViewState<Nothing>()
}