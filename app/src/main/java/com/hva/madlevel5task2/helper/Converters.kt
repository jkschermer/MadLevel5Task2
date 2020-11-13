package com.hva.madlevel5task2.helper

import androidx.room.TypeConverter
import java.util.*

class Converters {

    companion object {
        @TypeConverter
        @JvmStatic
        fun fromTimeStamp(value: Long?): Date? {
            return value?.let { Date(it) }
        }

        @TypeConverter
        @JvmStatic
        fun dateToTimeStamp(date: Date?): Long? {
            return date?.time?.toLong()
        }
    }
}