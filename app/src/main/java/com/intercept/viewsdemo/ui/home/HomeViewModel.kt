package com.intercept.viewsdemo.ui.home

import android.content.Context
import androidx.lifecycle.ViewModel
import com.intercept.viewsdemo.data.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@OptIn(DelicateCoroutinesApi::class)
@HiltViewModel
class HomeViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val homeRepository: HomeRepository
) : ViewModel() {

    val uiState = MutableStateFlow<HomeUIState>(HomeUIState.Loading)
    val actions = MutableStateFlow<HomeNavigationAction?>(null)

    init {
        fetchHomeData()
    }

    private fun fetchHomeData() {
        GlobalScope.launch {
            withContext(Dispatchers.IO) {
                homeRepository.fetchHomeData().fold(
                    onSuccess = {
                        uiState.value = HomeUIState.Success(list = it.map { it.toUIModel(context) })
                    }, onFailure = {
                        uiState.value =
                            HomeUIState.Failure(it.localizedMessage ?: "Something went wrong!")
                    })
            }
        }
    }

    fun onDetailsClicked() {
        actions.value = HomeNavigationAction.NavigateToDetails
    }

}