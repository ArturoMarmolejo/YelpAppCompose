package com.arturomarmolejo.yelpappcompose.presentation.viewmodel

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arturomarmolejo.yelpappcompose.core.UIState
import com.arturomarmolejo.yelpappcompose.data.model.Businesse
import com.arturomarmolejo.yelpappcompose.data.model.YelpResponse
import com.arturomarmolejo.yelpappcompose.data.repositories.YelpRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class YelpViewModel @Inject constructor(
    private val yelpRepository: YelpRepository
): ViewModel() {

    private val _allBusinessesByLocation: MutableStateFlow<UIState<YelpResponse>> = MutableStateFlow(UIState.LOADING)
    val allBusinessesByLocation: StateFlow<UIState<YelpResponse>> get() = _allBusinessesByLocation

    private val _singleBusiness: MutableStateFlow<UIState<Businesse?>> = MutableStateFlow(UIState.LOADING)
    val singleBusiness: StateFlow<UIState<Businesse?>> get() = _singleBusiness

    fun getAllBusinessesByLocation(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            yelpRepository.getAllBusinessesByLocation(latitude, longitude).collect {
                _allBusinessesByLocation.value = it
            }
        }
    }

    fun getBusinessById(id: String) {
        viewModelScope.launch {
            yelpRepository.getBusinessById(id).collect {
                _singleBusiness.value = it
            }
        }
    }
}