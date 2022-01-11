package com.example.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    indices = [Index(value = ["methodName"], unique = true)]
)
data class Handbooks(
    @PrimaryKey
    @ColumnInfo(name = "methodName")
    val methodName: String,                                                                         // название аналогичного метода на сервере

    @ColumnInfo(name = "jsonString")
    val jsonString: String,                                                                         // данные в формате JSON
)
