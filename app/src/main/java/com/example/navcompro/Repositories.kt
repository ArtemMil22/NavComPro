package com.example.navcompro.tabs

import com.example.navcompro.tabs.model.accounts.AccountsRepository
import com.example.navcompro.tabs.model.accounts.InMemoryAccountsRepository
import com.example.navcompro.tabs.model.boxes.BoxesRepository
import com.example.navcompro.tabs.model.boxes.InMemoryBoxesRepository

object Repositories {

    val accountsRepository: AccountsRepository = InMemoryAccountsRepository()

    val boxesRepository: BoxesRepository = InMemoryBoxesRepository(accountsRepository)

}