package com.didik.moflix.utils.state

sealed class LoaderState {
    object ShowLoading : LoaderState()
    object HideLoading : LoaderState()
}