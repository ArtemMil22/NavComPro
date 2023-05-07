package com.example.navcompro.model.boxes.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.navcompro.model.boxes.room.entities.AccountBoxSettingDbEntity
import com.example.navcompro.model.boxes.room.views.SettingDbView
import com.example.navcompro.model.boxes.room.views.SettingWithEntitiesTuple
import kotlinx.coroutines.flow.Flow

@Dao
interface BoxesDao {

    // получение ящиков с их настройками из БД для дашборда и настроек
    @Transaction
    @Query("SELECT * FROM settings_view WHERE account_id = :accountId")
    fun getBoxesAndSettings(accountId:Long):Flow<List<SettingWithEntitiesTuple>>

    // обновление настроек
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setActiveFlagForBox(AccountBoxSettingDbEntity:AccountBoxSettingDbEntity)

}