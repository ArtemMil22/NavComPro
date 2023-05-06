package com.example.navcompro.model.boxes.room

import com.example.navcompro.model.AuthException
import com.example.navcompro.model.accounts.AccountsRepository
import com.example.navcompro.model.boxes.BoxesRepository
import com.example.navcompro.model.boxes.entities.Box
import com.example.navcompro.model.boxes.entities.BoxAndSettings
import com.example.navcompro.model.boxes.room.entities.AccountBoxSettingDbEntity
import com.example.navcompro.model.boxes.room.entities.SettingsTuples
import com.example.navcompro.model.room.wrapSQLiteException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*

class RoomBoxesRepository(
    private val accountsRepository: AccountsRepository,
    private val boxesDao: BoxesDao,
    private val ioDispatcher: CoroutineDispatcher,
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

    //#19: fetch boxes and settings from BoxesDao and map them to the " +
    // "list of BoxAbdSettings instances")
    private fun queryBoxesAndSettings(accountId: Long): Flow<List<BoxAndSettings>> {
        return boxesDao.getBoxesAndSettings(accountId)
            .map { entities ->
                entities.map {
                    val boxEntity = it.boxDbEntity
                    val settingsEntity = it.settingDbEntity
                    BoxAndSettings(
                        box = boxEntity.toBox(),
                        isActive = settingsEntity == null || settingsEntity.settings.isActive
                    )
                }
            }
    }

    //   get the current account (throw AuthException if there is no logged-in user)
    //   and then use BoxesDao to activate/deactivate the box for the current account
    private suspend fun setActiveFlagForBox(box: Box, isActive: Boolean) {
        val account = accountsRepository.getAccount().first() ?: throw AuthException()
        boxesDao.setActiveFlagForBox(
            AccountBoxSettingDbEntity(
                accountId = account.id,
                boxId = box.id,
                settings = SettingsTuples(isActive = isActive)
            )
        )
    }
}