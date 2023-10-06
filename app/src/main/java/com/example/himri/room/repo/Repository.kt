package com.example.himri.room.repo

import com.example.himri.room.dao.HistoryDao
import com.example.himri.room.dao.UserDao
import com.example.himri.room.entity.Recipe
import com.example.himri.room.entity.User
import kotlinx.coroutines.flow.Flow


class Repository(
    private val userDAO: UserDao,
    private val historyDAO: HistoryDao
) {
    suspend fun addRecipe(recipe: Recipe) = historyDAO.addRecipe(recipe)
    fun getHistory(): Flow<List<Recipe>> = historyDAO.getHistory()
    suspend fun removeRecipe(recipeID: Int?) = historyDAO.removeRecipe(recipeID)

    suspend fun addUser(user: User) = userDAO.addUser(user)
    suspend fun updateUser(username: String) = userDAO.updateUser(username)
    suspend fun updateIntolerances(intolerances: List<String>) = userDAO.updateIntolerances(intolerances)
    suspend fun getUser(): User = userDAO.getUser()
    suspend fun exists(): Int  = userDAO.exists()
    suspend fun recipeExists(recipeID: Int?): Int  = historyDAO.recipeExists(recipeID)
}
