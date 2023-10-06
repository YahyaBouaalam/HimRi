package com.example.himri.view.home

import android.content.Context
import android.net.ConnectivityManager
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.getSystemService
import com.example.himri.model.ApiRecipe
import com.example.himri.room.entity.Recipe
import com.example.himri.ui.components.RecipeItem
import com.example.himri.ui.components.SavedRecipeItem
import com.example.himri.viewmodel.APIViewModel
import com.example.himri.viewmodel.DBViewmodel
import java.text.DecimalFormat

@Composable
fun Recipes(recipes: List<ApiRecipe>, apiViewModel: APIViewModel, next: () -> Unit, close: () -> Unit) {

    Column(modifier = Modifier
        .fillMaxSize()
        .background(color = Color(0xFFE7E7E7)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .background(color = Color.White),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBackIosNew,
                contentDescription = "",
                tint = Color(0xFF666666),
                modifier = Modifier
                    .width(40.dp)
                    .height(40.dp)
                    .padding(start = 14.dp, top = 8.dp)
                    .clickable {
                        close()
                        apiViewModel.response = listOf()
                    }
            )

            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = "",
                tint = Color.White,
                modifier = Modifier
                    .width(50.dp)
                    .height(50.dp)
                    .padding(end = 14.dp, top = 8.dp)
            )
        }

        when (apiViewModel.received) {

            false -> {
                Spacer(modifier = Modifier.height(240.dp))
                CircularProgressIndicator(color = Color(0xFF4079A2))
            }
            true -> {
                if (!recipes.isNullOrEmpty()) {
                        LazyColumn(Modifier.background(color = Color(0xFFE7E7E7))) {
                            itemsIndexed(items = recipes) { _, item ->
                                RecipeItem(recipe = item, apiViewModel = apiViewModel, next = next)
                            }
                        }
                } else {
                    Spacer(modifier = Modifier.height(240.dp))
                    Text(
                            text = "We couldn't find any recipes with your chosen parameters! :(\t" +
                                    "Also, please make sure that none of the ingredients conflict with your intolerances.",
                            modifier = Modifier.padding(horizontal = 24.dp)
                        )
                }
            }
        }
    }
}

@Composable
fun SavedRecipes(recipes: List<Recipe>, dbViewmodel: DBViewmodel, next: () -> Unit, close: () -> Unit) {
    Column(modifier = Modifier
        .fillMaxSize()
        .background(color = Color(0xFFE7E7E7)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top) {
        Row(
            Modifier
                .fillMaxWidth()
                .background(color = Color.White), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.ArrowBackIosNew,
                contentDescription = "",
                tint = Color(0xFF666666),
                modifier = Modifier
                    .width(40.dp)
                    .height(40.dp)
                    .padding(start = 14.dp, top = 8.dp)
                    .clickable {
                        close()
                    }
            )
            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.Black
                    )
                    ) {
                        append("Saved Recipes")
                    }
                },
                style = MaterialTheme.typography.h5,
                color = MaterialTheme.colors.onSurface
            )
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = "",
                tint = Color.White,
                modifier = Modifier
                    .width(50.dp)
                    .height(50.dp)
                    .padding(end = 14.dp, top = 8.dp)
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        LazyColumn(Modifier.background(color = Color(0xFFE7E7E7))) {
            itemsIndexed(items = recipes) { _, item ->
                SavedRecipeItem(recipe = item, dbViewmodel = dbViewmodel, next = next)
            }
        }
    }

}