package com.example.navcompro.tabs.screens.main.tabs.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import com.example.navcompro.tabs.model.accounts.AccountsRepository
import com.example.navcompro.model.accounts.entities.Account
import com.example.navcompro.tabs.utils.MutableLiveEvent
import com.example.navcompro.tabs.utils.publishEvent
import com.example.navcompro.tabs.utils.share

class ProfileViewModel(
    private val accountsRepository: AccountsRepository
) : ViewModel() {

    private val _account = MutableLiveData<Account>()
    val account = _account.share()

    private val _restartFromLoginEvent = MutableLiveEvent<Unit>()
    val restartWithSignInEvent = _restartFromLoginEvent.share()

    init {
        viewModelScope.launch {
            accountsRepository.getAccount().collect {
                _account.value = it
            }
        }
    }

    fun logout() {
        // now logout is not async, so simply call it and restart the app from login screen
        accountsRepository.logout()
        restartAppFromLoginScreen()
    }

    private fun restartAppFromLoginScreen() {
        _restartFromLoginEvent.publishEvent()
    }

}