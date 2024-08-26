package com.shashank.countrylist.country.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shashank.countrylist.core.utils.NetworkResult
import com.shashank.countrylist.country.countryList.model.CountryListItem
import com.shashank.countrylist.country.domain.CountryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * 'CountryViewModel' manages the state of Country List, Swipe-to-Refresh to re-fetch data from api.
 * Also transports Country Flag value to requested UI
 */

@HiltViewModel
class CountryViewModel @Inject constructor(
    private val countryRepository: CountryRepository
) : ViewModel() {

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
    }
    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean>
        get() = _isRefreshing.asStateFlow()

    private val _countryList: MutableStateFlow<NetworkResult<List<CountryListItem>?>> =
        MutableStateFlow(NetworkResult.Loading())
    val countryList = _countryList.asStateFlow()

    fun getCountryFlag(countryCode: String): String {
        return countryRepository.getCountryFlag(countryCode)
    }

    fun refresh() {
        _isRefreshing.value = true
        getCountryList()
    }

    fun getCountryList() {
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            _countryList.value = countryRepository.getCountryList()
            _isRefreshing.emit(false)
        }
    }
}