package com.example.navcompro.model.accounts.room

import androidx.room.Dao
import androidx.room.Query
import com.example.navcompro.model.accounts.room.entities.AccountSignInTuple
import com.example.navcompro.model.accounts.room.entities.AccountUpdateUsernameTuple

@Dao
interface AccountsDao {

    @Query("SELECT id,password FROM accounts WHERE email = :email")
    suspend fun findByEmail(email: String):AccountSignInTuple?

    suspend fun updateUserName(accountUpdateUsernameTuple: AccountUpdateUsernameTuple)

}