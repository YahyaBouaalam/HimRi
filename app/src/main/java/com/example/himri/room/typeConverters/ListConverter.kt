package com.example.himri.room.typeConverters

import androidx.room.TypeConverter

class ListConverter {
    @TypeConverter
    fun fromListToString(list: List<String>?): String? {
        return list?.joinToString(",")
    }

    @TypeConverter
    fun fromStringToList(string: String?): List<String>? {
        return string?.split(",")?.toMutableList()
    }
}