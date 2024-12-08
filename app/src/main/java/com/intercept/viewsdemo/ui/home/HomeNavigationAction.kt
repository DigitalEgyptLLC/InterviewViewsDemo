package com.intercept.viewsdemo.ui.home

sealed class HomeNavigationAction {
    data object NavigateToDetails : HomeNavigationAction()
}