package com.example.navcompro.model.boxes.room.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded

//  Create a SettingsTuple class which contains only one property: val isActive: Boolean
//  reflecting 'is_active' column from 'accounts_boxes_settings' table

data class SettingsTuples(
    @ColumnInfo(name = "is_active") val isActive: Boolean,
)

// Create a BoxAndSettingsTuple class which joins two entities: BoxDbEntity and
// AccountBoxSettingDbEntity. Please note that AccountBoxSettingDbEntity is optional.
data class BoxAndSettingsTuple(
    @Embedded val boxDbEntity: BoxDbEntity,
    @Embedded val settingDbEntity: AccountBoxSettingDbEntity?
)