package com.example.himri.model


data class RandomResponse(
    val recipes: List<RandomRecipe>
)

data class Response(
    val results: List<ApiRecipe>,
    val offset: Int,
    val number: Int,
    val totalResults: Int
)

data class RandomRecipe(
    val extendedIngredients: List<ExtendedIngredient>,
    val id: Int,
    val title: String,
    val image: String,
    val imageType: String,
    val cuisines: List<String>,
    val analyzedInstructions: List<Instruction>
)

data class ApiRecipe(
    val extendedIngredients: List<ExtendedIngredient>,
    val id: Int,
    val title: String,
    val image: String,
    val imageType: String,
    val cuisines: List<String>,
    val analyzedInstructions: List<Instruction>,
    val nutrition: Nutrition
)

data class Instruction(
    val name: String,
    val steps: List<Step>
)

data class Step(
    val number: Int,
    val step: String,
    val ingredients: List<StepIngredient>,
    val equipment: List<StepEquipment>,
    val length: StepLength?
)

data class StepIngredient(
    val id: Int,
    val name: String,
    val localizedName: String,
    val image: String
)

data class StepEquipment(
    val id: Int,
    val name: String,
    val localizedName: String,
    val image: String
)

data class StepLength(
    val number: Int,
    val unit: String
)

data class ExtendedIngredient(
    val id: Int,
    val aisle: String,
    val image: String,
    val consistency: String,
    val name: String,
    val nameClean: String,
    val original: String,
    val originalName: String,
    val amount: Double,
    val unit: String,
    val meta: List<String>,
    val measures: Measures
)

data class Nutrition(
    val nutrients: List<Nutrient>
)

data class Nutrient(
    val name: String,
    val amount: Double,
    val unit: String
)


data class Measures(
    val us: Measure,
    val metric: Measure
)

data class Measure(
    val amount: Double,
    val unitShort: String
)

