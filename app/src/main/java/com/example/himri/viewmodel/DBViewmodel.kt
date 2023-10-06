package com.example.himri.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.himri.room.entity.Recipe
import com.example.himri.room.entity.User
import com.example.himri.room.repo.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface DBViewModelAbstract {
    val selectedRecipeState: State<Recipe?>
    suspend fun getUser(): User
    fun add(user: User)
    val recipeListFlow: Flow<List<Recipe>>
    fun addRecipe(recipe: Recipe)
    fun removeRecipe(recipeID: Int?)
    fun update(username: String, allergies: List<String>)
    fun selectRecipe(recipe: Recipe)
    fun resetSelectedRecipe()
    suspend fun exists() : Int
    suspend fun recipeExists(recipeID: Int?): Int
}

@HiltViewModel
class DBViewmodel
@Inject constructor(
    private val repo: Repository,
): ViewModel(), DBViewModelAbstract {

    private val ioScope = CoroutineScope(Dispatchers.IO)


    private val _selectedRecipeState: MutableState<Recipe?> = mutableStateOf(null)
    override val selectedRecipeState: State<Recipe?>
        get() = _selectedRecipeState

    override val recipeListFlow: Flow<List<Recipe>> = repo.getHistory()

    override suspend fun getUser(): User {
        return withContext(Dispatchers.IO) {
            repo.getUser()
        }
    }

    override suspend fun exists(): Int {
        return withContext(Dispatchers.IO) {
            repo.exists()
        }
    }

    override suspend fun recipeExists(recipeID: Int?): Int {
        return withContext(Dispatchers.IO) {
            repo.recipeExists(recipeID)
        }
    }

    override fun addRecipe(recipe: Recipe) {
        ioScope.launch {
            if(repo.recipeExists(recipe.apiId)!=1) {
                repo.addRecipe(recipe = recipe)
            }
        }
    }

    override fun removeRecipe(recipeID: Int?) {
        ioScope.launch {
            repo.removeRecipe(recipeID= recipeID)
        }
    }

    override fun add(user: User) {
        ioScope.launch {
            if(exists()==0) {
                repo.addUser(user = user)
            }
            else {
                repo.updateUser(username = user.name)
                repo.updateIntolerances(intolerances = user.allergies)
            }
        }
    }

    override fun update(username: String, allergies: List<String>) {
        ioScope.launch {
            repo.updateUser(username = username)
            repo.updateIntolerances(intolerances = allergies)
        }
    }

    override fun selectRecipe(recipe: Recipe) {
        _selectedRecipeState.value = recipe
    }

    override fun resetSelectedRecipe() {
        _selectedRecipeState.value = null
    }

}