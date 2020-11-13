package com.hva.madlevel5task2.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.util.*

@Entity(tableName = "gameTable")
data class Game (
    val title: String? = null,
    val platform: String? = null,
    @TypeConverters
    var releaseDate: Date? = null,
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null
) {

}