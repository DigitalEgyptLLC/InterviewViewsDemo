package com.intercept.viewsdemo.ui.home

sealed class HomeUIState {
    data object Loading : HomeUIState()

    data class Success(val list: List<HomeUIModel>) : HomeUIState()

    data class Failure(val errorMessage: String) : HomeUIState()
}