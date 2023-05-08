package com.example.navcompro.model.accounts.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.navcompro.model.accounts.room.entities.*
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountsDao {

    @Query("SELECT id,password FROM accounts WHERE email = :email")
    suspend fun findByEmail(email: String): AccountSignInTuple?

    @Update(entity = AccountDbEntity::class)
    suspend fun updateUserName(accountUpdateUsernameTuple: AccountUpdateUsernameTuple)

    @Insert
    suspend fun createAccount(accountDbEntity: AccountDbEntity)

    @Query("SELECT * FROM accounts WHERE id = :accountId")
    fun getById(accountId: Long): Flow<AccountDbEntity?>

    @Transaction
    @Query("SELECT * FROM accounts WHERE accounts.id = :accountId")
    fun getAccountAndEditedBoxes(accountId: Long): AccountAndEditBoxesTuple

    @Transaction
    @Query("SELECT * FROM accounts")
    fun getAllData():Flow<List<AccountAndAllSettingsTuple>>
}