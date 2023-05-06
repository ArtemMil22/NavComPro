package com.example.navcompro.model.boxes.room.views

import androidx.room.ColumnInfo
import androidx.room.DatabaseView
import androidx.room.Embedded
import com.example.navcompro.model.boxes.room.entities.SettingsTuples

//   #8: This database view looks like 'accounts_boxes_settings' table but
// * returns default ('1') values for rows which don't exist in 'accounts_boxes_settings'.
//            1) use JOIN and LEFT JOIN
//            2) use ifnull() SQLite-function for assigning default value
//            3) use @DatabaseView annotation to create a view
//            4) use 'DB Browser for SQLite' app to test SQL-queries.

@DatabaseView(
    viewName = "settings_view",
    value = "SELECT \n" +
            "  accounts.id as account_id, \n" +
            "  boxes.id as box_id,\n" +
            " boxes.color_name,\n" +
            " boxes.color_value,\n" +
            "  ifnull(accounts_boxes_settings.is_active, 1) as is_active\n" +
            "FROM accounts\n" +
            "JOIN boxes\n" +
            "LEFT JOIN accounts_boxes_settings\n" +
            "  ON accounts_boxes_settings.account_id = accounts.id \n" +
            "    AND accounts_boxes_settings.box_id = boxes.id"
)
data class SettingDbView(
    @ColumnInfo(name = "account_id") val accountId: Long,
    @ColumnInfo(name = "box_id") val boxId: Long,
    @ColumnInfo(name = "color_name") val colorName: String,
    @ColumnInfo(name = "color_value") val colorValue: String,
    @Embedded val settings: SettingsTuples,
)

//  #13: Remove 'color_name' and 'color_value' columns from the database view. Make the structure
//           of the view the same as in 'accounts_boxes_settings' table. The only difference: view
//           should also contain rows which are absent in 'accounts_boxes_settings' table filled
//           with default 'is_active' value (is_active = 1)