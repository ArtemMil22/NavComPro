package com.example.navcompro.model.boxes.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.navcompro.model.boxes.entities.BoxAndSettings
import com.example.navcompro.model.boxes.room.entities.AccountBoxSettingDbEntity
import com.example.navcompro.model.boxes.room.entities.BoxAndSettingsTuple
import com.example.navcompro.model.boxes.room.entities.BoxDbEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BoxesDao {

    // получение ящиков с их настройками из БД для дашборда и настроек
    // Rewrite the getBoxesAndSettings method: use BoxAndSettingsTuple instead of Map
    @Query("SELECT * FROM boxes LEFT JOIN accounts_boxes_settings " +
            " ON boxes.id = accounts_boxes_settings.box_id " +
            " AND accounts_boxes_settings.account_id = :accountId")
    fun getBoxesAndSettings(accountId:Long):Flow<List<BoxAndSettingsTuple>>

    //  Rewrite getBoxesAndSettings method again: use database view instead of complex SQL-query.

    //  Rewrite getBoxesAndSettings method again: Use SettingWithEntitiesTuple which
    //  represents joined data from the database view, 'accounts' and 'boxes' tables


    // обновление настроек
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setActiveFlagForBox(AccountBoxSettingDbEntity:AccountBoxSettingDbEntity)
}