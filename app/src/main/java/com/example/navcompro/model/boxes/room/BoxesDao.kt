package com.example.navcompro.model.boxes.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.navcompro.model.boxes.entities.BoxAndSettings
import com.example.navcompro.model.boxes.room.entities.AccountBoxSettingDbEntity
import com.example.navcompro.model.boxes.room.entities.BoxAndSettingsTuple
import com.example.navcompro.model.boxes.room.entities.BoxDbEntity
import com.example.navcompro.model.boxes.room.views.SettingDbView
import kotlinx.coroutines.flow.Flow

@Dao
interface BoxesDao {

    // получение ящиков с их настройками из БД для дашборда и настроек
    // Rewrite the getBoxesAndSettings method: use BoxAndSettingsTuple instead of Map
    //  Rewrite getBoxesAndSettings method again: use database view instead of complex SQL-query.
    @Query("SELECT * FROM settings_view WHERE account_id = :accountId")
    fun getBoxesAndSettings(accountId:Long):Flow<List<SettingDbView>>

    //  Rewrite getBoxesAndSettings method again: Use SettingWithEntitiesTuple which
    //  represents joined data from the database view, 'accounts' and 'boxes' tables

    // обновление настроек
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setActiveFlagForBox(AccountBoxSettingDbEntity:AccountBoxSettingDbEntity)
}