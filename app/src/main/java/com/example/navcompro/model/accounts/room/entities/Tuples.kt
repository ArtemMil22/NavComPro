package com.example.navcompro.model.accounts.room.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.navcompro.model.boxes.room.entities.AccountBoxSettingDbEntity
import com.example.navcompro.model.boxes.room.entities.BoxDbEntity
import com.example.navcompro.model.boxes.room.entities.SettingsTuples
import com.example.navcompro.model.boxes.room.views.SettingDbView

//  Create a tuple for fetching account id + account password.
//  Tuple classes should not be annotated with @Entity but
//  their fields may be annotated with @ColumnInfo.
data class AccountSignInTuple(
   @ColumnInfo(name = "id") val id: Long,
   @ColumnInfo(name = "hash") val hash: String,
   @ColumnInfo(name = "salt") val salt: String,
)

//  Create a tuple for updating account username.
//  Such tuples should contain a primary key ('id') in order to notify
//  Room which row you want to update and fields to be updated
//  ('username' is this case).
data class AccountUpdateUsernameTuple(
    val id: Long,
    val username: String,
)

// 17 Create an AccountAndEditedBoxesTuple class which joins queries all boxes which settings have
// been edited for the specified account.
//  Hint: use @Relation annotation with 'associateBy' argument.

data class AccountAndEditBoxesTuple(
    @Embedded val accountDbEntity: AccountDbEntity,

    @Relation(
        parentColumn = "id", // AccountDbEntity
        entityColumn = "id", // BoxEntity
        associateBy = Junction(
            value = AccountBoxSettingDbEntity::class,
            parentColumn = "account_id",
            entityColumn = "box_id"
        )
    )
    val boxes: List<BoxDbEntity>,
)

// 19 Create an AccountAndAllSettingsTuple + SettingAndBoxTuple classes (hint: both of them with
//  @Relation annotation). AccountAndAllSettingsTuple should fetch account data from 'accounts'
//  table and also should contain a reference to the SettingAndBoxTuple. The latter should
//  contain data from 'SettingDbView' ('is_active' flag) and also it should contain a reference
//  to the BoxDbEntity (data from 'boxes' table).

data class AccountAndAllSettingsTuple(
    @Embedded val accountDbEntity: AccountDbEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "account_id",
        entity = SettingDbView::class
    )
    val settings: List<SettingAndBoxTuple>
)

data class SettingAndBoxTuple(
    @Embedded val accountBoxSettingDbEntity: SettingDbView,
    @Relation(
        parentColumn = "box_id",
        entityColumn = "id"
    )
    val boxDbEntity: BoxDbEntity
)
