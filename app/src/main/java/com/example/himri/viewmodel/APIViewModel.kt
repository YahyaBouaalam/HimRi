package com.example.himri.viewmodel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.himri.apiService.Api
import com.example.himri.model.ApiRecipe
import com.example.himri.model.Nutrition
import com.example.himri.room.entity.Recipe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class APIViewModel : ViewModel() {
    var received : Boolean by mutableStateOf(false)
    var response : List<ApiRecipe> by mutableStateOf(listOf())
    var errorMessage : String by mutableStateOf("")

    private val _selectedRecipeState: MutableState<ApiRecipe?> = mutableStateOf(null)
    val selectedRecipeState: State<ApiRecipe?>
        get() = _selectedRecipeState

    fun selectRecipe(recipe: ApiRecipe) {
        _selectedRecipeState.value = recipe
    }

    fun resetSelectedRecipe() {
        _selectedRecipeState.value = null
    }

    fun getRecipes(ingredients: List<String>,
                   minCarbs: String,
                   maxCarbs: String,
                   minProtein: String,
                   maxProtein: String,
                   minCalories: String,
                   maxCalories: String,
                   intolerances : List<String>) {
        viewModelScope.launch {
            val service = withContext(Dispatchers.IO){
            Api.getInstance()
            }
            var ings : String = ingredients[0]
            for(index in 1 until ingredients.size) {
                ings+=",+${ingredients[index]}"
            }

            val allergies = intolerances.joinToString(",+")

            try {
                response = service.getAllRecipes(ings, minCarbs, maxCarbs, minProtein, maxProtein, minCalories, maxCalories, allergies).results
                received = true
            } catch (e:Exception) {
                errorMessage = e.message.toString()
            }
        }
    }

    fun getRandom() {
        viewModelScope.launch {
            val service = withContext(Dispatchers.IO){
                Api.getInstance()
            }
            try {
                val r1 = service.getRandom()
                val random = r1.recipes[0]
                val recipe = ApiRecipe(
                        extendedIngredients = random.extendedIngredients,
                        id = random.id,
                        title = random.title,
                        image = random.image,
                        imageType = random.imageType,
                        cuisines = random.cuisines,
                        analyzedInstructions = random.analyzedInstructions,
                        nutrition = Nutrition(listOf())
                    )
                selectRecipe(recipe)
            } catch (e:Exception) {
                errorMessage = e.message.toString()
            }
        }
    }
}