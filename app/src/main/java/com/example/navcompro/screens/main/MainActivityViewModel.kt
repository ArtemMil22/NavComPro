package com.example.navcompro.tabs.screens.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import com.example.navcompro.tabs.model.accounts.AccountsRepository
import com.example.navcompro.tabs.utils.share

class MainActivityViewModel(
    private val accountsRepository: AccountsRepository
) : ViewModel() {

    private val _username = MutableLiveData<String>()
    val username = _username.share()

    init {
        viewModelScope.launch {
            // listening for the current account and send the username to be displayed in the toolbar
            accountsRepository.getAccount().collect {
                if (it == null) {
                    _username.value = ""
                } else {
                    _username.value = "@${it.username}"
                }
            }
        }
    }
}