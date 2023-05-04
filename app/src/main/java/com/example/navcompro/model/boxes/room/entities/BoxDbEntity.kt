package com.example.navcompro.model.boxes.room.entities

import android.graphics.Color
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.navcompro.model.boxes.entities.Box


@Entity(
    tableName = "boxes"
)
data class BoxDbEntity(
    @PrimaryKey val id:Long,
    @ColumnInfo(name = "color_name") val colorName:String,
    @ColumnInfo(name = "color_value") val colorValue:String
) {
    fun toBox(): Box = Box(
        id = id,
        colorValue = Color.parseColor(colorValue),
        colorName = colorName
    )
}