package com.example.navcompro.model.boxes

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.graphics.Color
import androidx.core.content.contentValuesOf
import com.example.navcompro.model.AuthException
import com.example.navcompro.model.accounts.AccountsRepository
import com.example.navcompro.model.boxes.entities.Box
import com.example.navcompro.model.sqlite.AppSQLiteContract.AccountsBoxesSettingsTable
import com.example.navcompro.model.sqlite.AppSQLiteContract.BoxesTable
import com.example.navcompro.model.sqlite.wrapSQLiteException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*

class SQLiteBoxesRepository(
    private val db: SQLiteDatabase,
    private val accountsRepository: AccountsRepository,
    private val ioDispatcher: CoroutineDispatcher,
) : BoxesRepository {

    private val reconstructFlow = MutableSharedFlow<Unit>(replay = 1).also { it.tryEmit(Unit) }

    override suspend fun getBoxes(onlyActive: Boolean): Flow<List<Box>> {
        return combine(accountsRepository.getAccount(), reconstructFlow) { account, _ ->
            queryBoxes(onlyActive, account?.id)
        }
            .flowOn(ioDispatcher)
    }

    override suspend fun activateBox(box: Box) = wrapSQLiteException(ioDispatcher) {
        setActiveFlagForBox(box, true)
    }

    override suspend fun deactivateBox(box: Box) = wrapSQLiteException(ioDispatcher) {
        setActiveFlagForBox(box, false)
    }

    private suspend fun setActiveFlagForBox(box: Box, isActive: Boolean) {
        val account = accountsRepository.getAccount().first() ?: throw AuthException()
        saveActiveFlag(account.id, box.id, isActive)
        reconstructFlow.tryEmit(Unit)
    }

    private fun queryBoxes(onlyActive: Boolean, accountId: Long?): List<Box> {
        if (accountId == null) return emptyList()

        val cursor = queryBoxes(onlyActive, accountId)
        return cursor.use {
            val list = mutableListOf<Box>()
            while (cursor.moveToNext()) {
                list.add(parseBox(cursor))
            }
            return@use list
        }
    }

    private fun parseBox(cursor: Cursor): Box {
        return Box(
            id = cursor.getLong(
                cursor.getColumnIndexOrThrow(
                    BoxesTable.COLUMN_ID
                )
            ),
            colorName = cursor.getString(
                cursor.getColumnIndexOrThrow(
                    BoxesTable.COLUMN_COLOR_NAME
                )
            ),
            colorValue = Color.parseColor(
                cursor.getString(
                    cursor.getColumnIndexOrThrow(
                        BoxesTable.COLUMN_COLOR_VALUE
                    )
                )
            )
        )
    }

    //user меняет checkBox на экране настроек
    private fun saveActiveFlag(accountId: Long, boxId: Long, isActive: Boolean) {
        db.insertWithOnConflict(
            AccountsBoxesSettingsTable.TABLE_NAME,
            null,
            contentValuesOf(
                AccountsBoxesSettingsTable.COLUMN_BOX_ID to boxId,
                AccountsBoxesSettingsTable.COLUMN_ACCOUNT_ID to accountId,
                AccountsBoxesSettingsTable.COLUMN_IS_ACTIVE to isActive
            ),
            // стратегия при конфликте замена старой строки на новую
            SQLiteDatabase.CONFLICT_REPLACE
        )
    }

    private fun queryBoxes(onlyActive: Boolean, accountId: Long): Cursor {
        return if (onlyActive) {
            // объеденияем две таблицы
            val sql = "SELECT ${BoxesTable.TABLE_NAME}.*" +
                    "FROM ${BoxesTable.TABLE_NAME}" +
                    "LEFT JOIN ${AccountsBoxesSettingsTable.TABLE_NAME}" +
                    " ON ${AccountsBoxesSettingsTable.COLUMN_BOX_ID} = ${BoxesTable.COLUMN_ID}" +
                    " AND ${AccountsBoxesSettingsTable.COLUMN_ACCOUNT_ID} = ?" +
                    "WHERE ${AccountsBoxesSettingsTable.COLUMN_IS_ACTIVE} is NULL" +
                    " OR ${AccountsBoxesSettingsTable.COLUMN_IS_ACTIVE} = 1"
            // запрос и, что будем вставлять
                return db.rawQuery(sql, arrayOf(accountId.toString()))
        } else {
            db.rawQuery("SELECT * FROM ${BoxesTable.TABLE_NAME}", null)
        }
    }
}