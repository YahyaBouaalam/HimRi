package com.example.himri.view.home

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.keyframes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.himri.room.entity.Recipe
import com.example.himri.room.entity.User
import com.example.himri.ui.components.*
import com.example.himri.ui.theme.Typography
import com.example.himri.viewmodel.APIViewModel
import com.example.himri.viewmodel.DBViewmodel
import kotlinx.coroutines.launch
import java.text.DecimalFormat

@Composable
fun HomeScreen(apiViewModel: APIViewModel, next: () -> Unit, torandom: () -> Unit, dbViewmodel: DBViewmodel) {
    val selectedIngredients = remember { mutableStateListOf<String>() }
    val carbs = remember  { mutableStateOf (0f..100f) }
    val protein = remember  { mutableStateOf (0f..200f) }
    val calories = remember  { mutableStateOf (0f..1000f) }
    val offsetX = remember { Animatable(0f) }
    val coroutineScope = rememberCoroutineScope()

    val shakeKeyframes: AnimationSpec<Float> = keyframes {
        durationMillis = 1000
        val easing = FastOutLinearInEasing

        for (i in 1..8) {
            val x = when (i % 3) {
                0 -> 4f
                1 -> -4f
                else -> 0f
            }
            x at durationMillis / 10 * i with easing
        }
    }

    var user by remember { mutableStateOf(User()) }
    LaunchedEffect(Unit) {
        user = dbViewmodel.getUser()
    }

    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Spacer(modifier = Modifier.height(20.dp))
        IngredientButton(selected = selectedIngredients, modifier = Modifier.offset(offsetX.value.dp, 0.dp))
        Spacer(modifier = Modifier.height(36.dp))
        Text(buildAnnotatedString {
            withStyle(style = SpanStyle(
                fontWeight = FontWeight.Bold
            )
            ) {
                append("Calories")
            }
        })
        MySlider(range = 0f..1000f, values = calories, unit = "kcal", modifier = Modifier.padding(horizontal = 35.dp), color = Color(0xFFFFB800))
        Text(buildAnnotatedString {
            withStyle(style = SpanStyle(
                fontWeight = FontWeight.Bold
            )
            ) {
                append("Protein")
            }
        })
        MySlider(range = 0f..200f, values = protein, unit = "g", modifier = Modifier.padding(horizontal = 35.dp), color = Color(0xFFef4565))
        Text(buildAnnotatedString {
            withStyle(style = SpanStyle(
                fontWeight = FontWeight.Bold
            )
            ) {
                append("Carbohydrates")
            }
        })
        MySlider(range = 0f..100f, values = carbs, unit = "g", modifier = Modifier.padding(horizontal = 35.dp), color = Color(0xFF2cb67d))
        Spacer(modifier = Modifier.height(24.dp))
        FindRecipe() {
            if(selectedIngredients.isNullOrEmpty()) {
                coroutineScope.launch {
                    offsetX.animateTo(
                        targetValue = 0f,
                        animationSpec = shakeKeyframes,
                    )
                }
            } else {
                apiViewModel.getRecipes(
                    selectedIngredients,
                    carbs.value.start.toString(), carbs.value.endInclusive.toString(),
                    protein.value.start.toString(), protein.value.endInclusive.toString(),
                    calories.value.start.toString(), calories.value.endInclusive.toString(),
                    user.allergies
                )
                next()
            }
        }
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 36.dp), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
            Divider(color = Color(0xFF5f6c7b), thickness = 1.dp, modifier = Modifier.width(120.dp))
            Text("or", style = Typography.body1, color = Color(0xFF5f6c7b), modifier = Modifier.padding(horizontal = 12.dp))
            Divider(color = Color(0xFF5f6c7b), thickness = 1.dp, modifier = Modifier.width(120.dp))

        }
        RandomButton() {
            apiViewModel.getRandom()
            torandom()
        }
    }

}





@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {

}