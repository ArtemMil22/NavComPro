package com.example.navcompro.model.accounts.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.navcompro.model.accounts.entities.Account
import com.example.navcompro.model.accounts.entities.SignUpData

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
    @ColumnInfo(name = "password") val password: String,
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
        fun fromSignUpData(signUpData: SignUpData): AccountDbEntity = AccountDbEntity(
            id = 0,
            email = signUpData.email,
            username = signUpData.username,
            password = signUpData.password,
            createdAt = System.currentTimeMillis()
        )
    }

}