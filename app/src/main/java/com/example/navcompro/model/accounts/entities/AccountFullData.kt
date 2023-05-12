package com.example.navcompro.model.accounts.entities

import com.example.navcompro.model.boxes.entities.BoxAndSettings

//Информация об учетной записи со всеми ящиками и их настройками

data class AccountFullData(
    val account: Account,
    val boxesAndSettings: List<BoxAndSettings>
)