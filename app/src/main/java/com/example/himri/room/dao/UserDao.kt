package com.example.himri.room.dao

import androidx.room.*
import com.example.himri.room.entity.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUser(user: User)

    @Query("SELECT * FROM user_table")
    suspend fun getUser(): User

    @Query("SELECT Count(*) FROM user_table")
    suspend fun exists(): Int

    @Query("UPDATE user_table SET username = :newusername")
    suspend fun updateUser(newusername: String)

    @Query("UPDATE user_table SET allergies = :intolerances")
    suspend fun updateIntolerances(intolerances: List<String>)
}