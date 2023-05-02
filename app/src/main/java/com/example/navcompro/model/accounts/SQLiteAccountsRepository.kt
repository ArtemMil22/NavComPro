package com.example.navcompro.model.accounts

import android.database.sqlite.SQLiteDatabase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import com.example.navcompro.model.*
import com.example.navcompro.model.accounts.entities.Account
import com.example.navcompro.model.accounts.entities.SignUpData
import com.example.navcompro.model.settings.AppSettings
import com.example.navcompro.model.sqlite.AppSQLiteContract
import com.example.navcompro.model.sqlite.AppSQLiteContract.AccountsTable
import com.example.navcompro.model.sqlite.wrapSQLiteException
import com.example.navcompro.utils.AsyncLoader
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

/**
 * Simple implementation of [AccountsRepository] which holds accounts data in the app memory.
 */
class SQLiteAccountsRepository(
    private val db: SQLiteDatabase,
    private val appSettings: AppSettings,
    private val ioDispatcher: CoroutineDispatcher,
) : AccountsRepository {

    private val currentAccountIdFlow = AsyncLoader {
        MutableStateFlow(AccountId(appSettings.getCurrentAccountId()))
    }

    override suspend fun isSignedIn(): Boolean {
        delay(2000)
        return appSettings.getCurrentAccountId() != AppSettings.NO_ACCOUNT_ID
    }

    override suspend fun signIn(email: String, password: String) =
        wrapSQLiteException(ioDispatcher) {
            if (email.isBlank()) throw EmptyFieldException(Field.Email)
            if (password.isBlank()) throw EmptyFieldException(Field.Password)

            delay(1000)

            val accountId = findAccountIdByEmailAndPassword(email, password)
            appSettings.setCurrentAccountId(accountId)
            currentAccountIdFlow.get().value = AccountId(accountId)

            return@wrapSQLiteException
        }

    override suspend fun signUp(signUpData: SignUpData) = wrapSQLiteException(ioDispatcher) {
        signUpData.validate()
        delay(1000)
        createAccount(signUpData)
    }

    override suspend fun logout() {
        appSettings.setCurrentAccountId(AppSettings.NO_ACCOUNT_ID)
        currentAccountIdFlow.get().value = AccountId(AppSettings.NO_ACCOUNT_ID)
    }

    override suspend fun getAccount(): Flow<Account?> {
        return currentAccountIdFlow.get()
            .map { accountId ->
                getAccountById(accountId.value)
            }
            .flowOn(ioDispatcher)
    }

    override suspend fun updateAccountUsername(newUsername: String) =
        wrapSQLiteException(ioDispatcher) {
            if (newUsername.isBlank()) throw EmptyFieldException(Field.Username)
            delay(1000)
            val accountId = appSettings.getCurrentAccountId()
            if (accountId == AppSettings.NO_ACCOUNT_ID) throw AuthException()

            updateUsernameForAccountId(accountId, newUsername)

            currentAccountIdFlow.get().value = AccountId(accountId)
            return@wrapSQLiteException
        }

    private fun findAccountIdByEmailAndPassword(email: String, password: String): Long {

        val cursor = db.query(
            AccountsTable.TABLE_NAME,
            arrayOf(AccountsTable.COLUMN_ID,AccountsTable.COLUMN_PASSWORD),
            "${AccountsTable.COLUMN_EMAIL} = ?",
            // что подставить
            arrayOf(email),
        //группировку не используем, выборку по группам и сортировку
        null,null,null
        )
        return cursor.use {
            if(cursor.count == 0) throw AuthException()
            cursor.moveToFirst()
            val passwordFromDb = cursor.getString(
                cursor.getColumnIndexOrThrow(AccountsTable.COLUMN_PASSWORD)
            )
            if (passwordFromDb!=password) throw AuthException()

            cursor.getLong(
                cursor.getColumnIndexOrThrow(AccountsTable.COLUMN_ID)
            )
        }
    }

    private fun createAccount(signUpData: SignUpData) {
        TODO(
            "#4 \n " +
                    "1) Insert a new row into accounts table here using data provided by SignUpData class \n" +
                    "2) throw AccountAlreadyExistsException if there is another account with such email in the database \n" +

                    "Tip: use SQLiteDatabase.insertOrThrow method and surround it with try-catch(e: SQLiteConstraintException)"
        )
    }

    private fun getAccountById(accountId: Long): Account? {
        TODO(
            "#5 \n " +
                    "1) Fetch account data by ID from the database \n" +
                    "2) Return NULL if accountId = AppSettings.NO_ACCOUNT_ID or there is no row with such ID in the database \n" +
                    "3) Do not forget to close Cursor"
        )
    }

    private fun updateUsernameForAccountId(accountId: Long, newUsername: String) {
        TODO(
            "#6 \n " +
                    "Update username column of the row with the specified account ID"
        )
    }

    private class AccountId(val value: Long)
}