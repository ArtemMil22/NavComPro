package com.example.navcompro.model.boxes.room

import com.example.navcompro.model.accounts.AccountsRepository
import com.example.navcompro.model.boxes.BoxesRepository
import com.example.navcompro.model.boxes.entities.Box
import com.example.navcompro.model.boxes.entities.BoxAndSettings
import com.example.navcompro.model.room.wrapSQLiteException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.mapLatest

class RoomBoxesRepository(
    private val accountsRepository: AccountsRepository,
    private val boxesDao: BoxesDao,
    private val ioDispatcher: CoroutineDispatcher
) : BoxesRepository {

    override suspend fun getBoxesAndSettings(onlyActive: Boolean): Flow<List<BoxAndSettings>> {
        return accountsRepository.getAccount()
            .flatMapLatest { account ->
                if (account == null) return@flatMapLatest flowOf(emptyList())
                queryBoxesAndSettings(account.id)
            }
            .mapLatest { boxAndSettings ->
                if (onlyActive) {
                    boxAndSettings.filter { it.isActive }
                } else {
                    boxAndSettings
                }
            }
    }

    override suspend fun activateBox(box: Box) = wrapSQLiteException(ioDispatcher) {
        setActiveFlagForBox(box, true)
    }

    override suspend fun deactivateBox(box: Box) = wrapSQLiteException(ioDispatcher) {
        setActiveFlagForBox(box, false)
    }

    private fun queryBoxesAndSettings(accountId: Long): Flow<List<BoxAndSettings>> {
        TODO("#19: fetch boxes and settings from BoxesDao and map them to the " +
                "list of BoxAbdSettings instances")
    }

    private suspend fun setActiveFlagForBox(box: Box, isActive: Boolean) {
        // todo #20: get the current account (throw AuthException if there is no logged-in user)
        //           and then use BoxesDao to activate/deactivate the box for the current account
    }
}