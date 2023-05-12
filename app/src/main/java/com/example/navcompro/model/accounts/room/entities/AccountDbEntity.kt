package com.example.navcompro.model.accounts.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.navcompro.model.accounts.entities.Account
import com.example.navcompro.model.accounts.entities.SignUpData
import com.example.navcompro.utils.security.SecurityUtils

@Entity(
    tableName = "accounts",
    indices = [
        Index("email", unique = true)
    ]
)
 data class AccountDbEntity(
    @ColumnInfo(name = "id") @PrimaryKey (autoGenerate = true) val id: Long,
    @ColumnInfo (name = "email", collate = ColumnInfo.NOCASE) val email: String,
    @ColumnInfo(name = "username") val username: String,
    @ColumnInfo(name = "hash") val hash: String,
    @ColumnInfo(name = "salt", defaultValue = "") val salt : String,
    @ColumnInfo (name = "created_at") val createdAt: Long
 ) {

    // маперы для быстрого преобразования данные сущности из табл
    // в стандартный аккаунт апп для публичного использования
    fun toAccount(): Account = Account(
        id = id,
        email = email,
        username = username,
        createdAt = createdAt
    )

    //обратное преобразование из заполненного бланка регистрации
    // изначально данные попав в SingUpData, должно преобразоваться в сущность БД
    companion object {
        fun fromSignUpData(signUpData: SignUpData, securityUtils: SecurityUtils): AccountDbEntity {
           val salt = securityUtils.generateSalt()
            val hash = securityUtils.passwordToHash(signUpData.password,salt)
            signUpData.password.fill('*')
            signUpData.repeatPassword.fill('*')

            return AccountDbEntity(
                id = 0,
                email = signUpData.email,
                username = signUpData.username,
                hash =securityUtils.bytesToString(hash),
                salt = securityUtils.bytesToString(salt),
                createdAt = System.currentTimeMillis()
            )
        }
    }
}