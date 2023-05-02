package com.example.navcompro.model.accounts.room.entities

//  Create a tuple for fetching account id + account password.
//  Tuple classes should not be annotated with @Entity but
//  their fields may be annotated with @ColumnInfo.
data class AccountSignInTuple(
    val id: Long,
    val password: String,
)

//  Create a tuple for updating account username.
//  Such tuples should contain a primary key ('id') in order to notify
//  Room which row you want to update and fields to be updated
//  ('username' is this case).
class AccountUpdateUsernameTuple(
    val id: Long,
    val username: String,
)