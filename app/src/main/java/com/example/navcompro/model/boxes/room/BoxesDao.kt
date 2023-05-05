package com.example.navcompro.model.boxes.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.navcompro.model.boxes.entities.BoxAndSettings
import com.example.navcompro.model.boxes.room.entities.AccountBoxSettingDbEntity
import com.example.navcompro.model.boxes.room.entities.BoxDbEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BoxesDao {

    // получение ящиков с их настройками из БД для дашборда и настроек
    @Query("SELECT * FROM boxes LEFT JOIN accounts_boxes_settings " +
            " ON boxes.id = accounts_boxes_settings.box_id " +
            " AND accounts_boxes_settings.account_id = :accountId")
    fun getBoxesAndSettings(accountId:Long):Flow<Map<BoxDbEntity,AccountBoxSettingDbEntity>>

    // обновление настроек
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setActiveFlagForBox(AccountBoxSettingDbEntity:AccountBoxSettingDbEntity)
}