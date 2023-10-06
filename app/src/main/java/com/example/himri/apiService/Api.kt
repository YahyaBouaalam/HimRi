package com.example.himri.apiService

import com.example.himri.model.*
import com.example.himri.room.entity.Recipe
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private const val apiKey: String = "e138182bba2f4606955d9c69278d5f3d"

interface Api {

    @GET("complexSearch?apiKey=$apiKey&addRecipeInformation=true&number=100&fillIngredients=true")
    suspend fun getAllRecipes(@Query("includeIngredients") ings: String,
                              @Query("minCarbs") minCarbs: String,
                              @Query("maxCarbs") maxCarbs: String,
                              @Query("minProtein") minProtein: String,
                              @Query("maxProtein") maxProtein: String,
                              @Query("minCalories") minCalories: String,
                              @Query("maxCalories") maxCalories: String,
                              @Query("intolerances") intolerances: String) : Response

    @GET("{{id}/nutritionWidget.json?apiKey=$apiKey")
    suspend fun getNutrition(@Path("id") recipeId: Int) : List<Nutrition>

    @GET("random?number=1&apiKey=$apiKey")
    suspend fun getRandom() : RandomResponse


    companion object {

        private var retrofitService: Api? = null
        fun getInstance() : Api {

            if (retrofitService == null) {
                retrofitService = Retrofit.Builder()
                    .baseUrl("https://api.spoonacular.com/recipes/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(Api::class.java)
            }
            return retrofitService!!
        }
    }
}