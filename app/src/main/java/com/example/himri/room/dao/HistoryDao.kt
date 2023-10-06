package com.example.himri.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.himri.room.entity.Recipe
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addRecipe(recipe: Recipe)

    @Query("SELECT * FROM history_table")
    fun getHistory(): Flow<List<Recipe>>

    @Query("SELECT Count(*) FROM history_table WHERE apiId  = :recipeID")
    suspend fun recipeExists(recipeID: Int?): Int

    @Query("DELETE FROM history_table WHERE apiId  = :recipeID")
    suspend fun removeRecipe(recipeID: Int?)
}