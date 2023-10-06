package com.example.himri.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history_table")
data class Recipe(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "recipeid")
    val recipeid: Long? = null,

    val apiId: Int? = null,

    @ColumnInfo(name = "recipeName")
    val recipeName: String ="",

    @ColumnInfo(name = "calories")
    val calories: String ="",

    @ColumnInfo(name = "carbs")
    val carbs: String ="",

    @ColumnInfo(name = "protein")
    val protein: String ="",

    @ColumnInfo(name = "ingredients")
    val ingredients: List<String> = listOf<String>(),

    @ColumnInfo(name = "measures")
    val measures: List<String> = listOf<String>(),


    @ColumnInfo(name = "procedure")
    val procedure: List<String> = listOf<String>()
)
