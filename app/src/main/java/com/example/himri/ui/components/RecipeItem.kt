package com.example.himri.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.himri.R
import com.example.himri.model.ApiRecipe
import com.example.himri.room.entity.Recipe
import com.example.himri.viewmodel.APIViewModel
import com.example.himri.viewmodel.DBViewmodel
import java.text.DecimalFormat

@Composable
fun RecipeItem( recipe: ApiRecipe, next: () -> Unit, apiViewModel: APIViewModel ) {

    var calories = ""
    var protein = ""
    var carbs = ""
    for(nutrient in recipe.nutrition.nutrients) {
        if(nutrient.name.contains("Calories")) {
            calories = (DecimalFormat("#.##").format(nutrient.amount)).toString()
        }
        if(nutrient.name.contains("Protein")) {
            protein = (DecimalFormat("#.##").format(nutrient.amount)).toString()
        }
        if(nutrient.name.contains("Carbohydrates")) {
            carbs = (DecimalFormat("#.##").format(nutrient.amount)).toString()
        }
    }
    val temping = mutableListOf<String>()
    if(!recipe.extendedIngredients.isNullOrEmpty()) {
        for (ing in recipe.extendedIngredients) {
            temping.add(ing.nameClean)
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                apiViewModel.resetSelectedRecipe()
                apiViewModel.selectRecipe(recipe)
                next()
            },
        elevation = 4.dp,
        shape = RoundedCornerShape(16.dp)
    ) {
        Column {
            AsyncImage(
                model = recipe.image,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
                    .aspectRatio(16f / 9f)
                    .graphicsLayer {
                        clip = true
                        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                    },
                contentScale = ContentScale.Crop,
                placeholder = painterResource(id = R.drawable.img)
            )

            Spacer(modifier = Modifier.height(4.dp))
            Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally){
            Text(
                text = recipe.title,
                style = MaterialTheme.typography.h5,
                color = Color(0xFF2289D3),
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            }
            Spacer(modifier = Modifier.height(8.dp))

            Column(modifier = Modifier.padding(18.dp)) {
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFFFB800)
                        )
                        ) {
                            append("Calories: ")
                        }
                        append("$calories kcal")
                    },
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.onSurface
                )
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFef4565)
                        )
                        ) {
                            append("Protein: ")
                        }
                        append("$protein g")
                    },
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.onSurface
                )
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF2cb67d)
                        )
                        ) {
                            append("Carbs: ")
                        }
                        append("$carbs g")
                    },
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.onSurface
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun SavedRecipeItem(recipe: Recipe, next: () -> Unit, dbViewmodel: DBViewmodel) {

    val calories = recipe.calories
    val protein = recipe.protein
    val carbs = recipe.carbs

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                dbViewmodel.resetSelectedRecipe()
                dbViewmodel.selectRecipe(recipe)
                next()
            },
        elevation = 4.dp,
        shape = RoundedCornerShape(16.dp)
    ) {
        Column {
            Image(
                painterResource(R.drawable.placeholderecipe),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp)
                    .aspectRatio(16f / 9f)
                ,
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(4.dp))
            Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally){
                Text(
                    text = recipe.recipeName,
                    style = MaterialTheme.typography.h5,
                    color = Color(0xFF2289D3),
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))

            Column(modifier = Modifier.padding(18.dp)) {
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFFFB800)
                        )
                        ) {
                            append("Calories: ")
                        }
                        append("$calories kcal")
                    },
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.onSurface
                )
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFef4565)
                        )
                        ) {
                            append("Protein: ")
                        }
                        append("$protein g")
                    },
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.onSurface
                )
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF2cb67d)
                        )
                        ) {
                            append("Carbs: ")
                        }
                        append("$carbs g")
                    },
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.onSurface
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}