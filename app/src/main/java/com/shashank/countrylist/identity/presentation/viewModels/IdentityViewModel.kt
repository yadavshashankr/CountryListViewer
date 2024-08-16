package com.shashank.countrylist.identity.presentation.viewModels


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shashank.countrylist.core.utils.NetworkResult
import com.shashank.countrylist.identity.domain.IdentityRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
/**
 * 'IdentityViewModel' helps manage state of Identity data. It also controls SplashScreen visibility.
 */

@HiltViewModel
class IdentityViewModel @Inject constructor(
    private val identityRepository: IdentityRepository
): ViewModel() {

    private val coroutineExceptionHandler = CoroutineExceptionHandler{ _, throwable ->
        throwable.printStackTrace()
    }

    private val _isReady: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isReady = _isReady.asStateFlow()

    private val _verifyResult: MutableStateFlow<NetworkResult<String?>> = MutableStateFlow(NetworkResult.Error(""))
    val verifyResult = _verifyResult.asStateFlow()

    init {
        initiateAppProcess()
    }

    private fun initiateAppProcess() {
        viewModelScope.launch {
            delay(2000L)
            _isReady.value = true
        }
    }

    fun doVerification(username: String, password: String){
        _verifyResult.value = NetworkResult.Loading()

        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            val response = identityRepository.doVerification(username, password)
            _verifyResult.value = response
        }
    }

    fun clearError(){
        _verifyResult.value = NetworkResult.Error("")
    }
}