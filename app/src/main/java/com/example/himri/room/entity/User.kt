package com.example.himri.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class User (

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "userid")
    val userid: Long? = null,

    @ColumnInfo(name = "username")
    val name: String = "",

    @ColumnInfo(name = "allergies")
    val allergies: List<String> = listOf<String>()
)