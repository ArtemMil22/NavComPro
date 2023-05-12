package com.example.navcompro.model.boxes.room.views

import androidx.room.Embedded
import androidx.room.Relation
import com.example.navcompro.model.accounts.room.entities.AccountDbEntity
import com.example.navcompro.model.boxes.room.entities.BoxDbEntity

data class SettingWithEntitiesTuple(

    @Embedded val settingDbEntity: SettingDbView,

    @Relation(
        parentColumn = "account_id",
        entityColumn = "id"
    )
    val accountDbEntity: AccountDbEntity,

    @Relation(
        parentColumn = "box_id",
        entityColumn = "id"
    )
    val boxDbEntity: BoxDbEntity
)