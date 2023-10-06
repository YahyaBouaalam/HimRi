package com.example.himri.view

import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.himri.R
import com.example.himri.model.ApiRecipe
import com.example.himri.room.entity.Recipe
import com.example.himri.ui.components.InstructionButton
import com.example.himri.ui.theme.Typography
import com.example.himri.viewmodel.APIViewModel
import com.example.himri.viewmodel.DBViewmodel
import java.text.DecimalFormat

@Composable
fun RecipePage(apiViewModel: APIViewModel, close: () -> Unit, dbViewmodel: DBViewmodel) {

    var selectedSection by remember { mutableStateOf(Section.INGREDIENTS) }
    var color = remember { mutableStateOf(Color(0xFF666666)) }
    var width by remember {
        mutableStateOf(198)
    }
    val animateWidth by animateIntAsState(
        targetValue = width,
        animationSpec = tween(durationMillis = 500))
    var width2 by remember {
        mutableStateOf(0)
    }
    val animateWidth2 by animateIntAsState(
        targetValue = width2,
        animationSpec = tween(durationMillis = 500))


    val recipe = apiViewModel.selectedRecipeState.value!!

    var number by remember { mutableStateOf(0) }
    LaunchedEffect(Unit) {
        number = dbViewmodel.recipeExists(recipe.id)
    }
    var saved by remember { mutableStateOf(false) }
    if(number==1) {
        color.value = Color(0xFFE04545)
        saved = true
        number = 0
    }
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Top) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
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
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = "",
                tint = color.value,
                modifier = Modifier
                    .width(50.dp)
                    .height(50.dp)
                    .padding(end = 14.dp, top = 8.dp)
                    .clickable {

                        if (saved) {
                            dbViewmodel.removeRecipe(recipe.id)
                            color.value = Color(0xFF666666)
                            saved = false
                        } else {
                            val ingredients = mutableListOf<String>()
                            val measures = mutableListOf<String>()
                            val procedure = mutableListOf<String>()
                            recipe.extendedIngredients.forEach {
                                ingredients.add(it.name)
                                measures.add((DecimalFormat("#.##").format(it.measures.metric.amount)).toString() + " " + it.measures.metric.unitShort)
                            }
                            recipe.analyzedInstructions.forEach {
                                it.steps.forEach {
                                    procedure.add(it.step)
                                }
                            }
                            var calories = ""
                            var protein = ""
                            var carbs = ""
                            for (nutrient in recipe.nutrition.nutrients) {
                                if (nutrient.name.contains("Calories")) {
                                    calories =
                                        (DecimalFormat("#.##").format(nutrient.amount)).toString()
                                }
                                if (nutrient.name.contains("Protein")) {
                                    protein =
                                        (DecimalFormat("#.##").format(nutrient.amount)).toString()
                                }
                                if (nutrient.name.contains("Carbohydrates")) {
                                    carbs =
                                        (DecimalFormat("#.##").format(nutrient.amount)).toString()
                                }
                            }
                            val savedrecipe = Recipe(
                                apiId = recipe.id,
                                recipeName = recipe.title,
                                ingredients = ingredients,
                                measures = measures,
                                procedure = procedure,
                                calories = calories,
                                carbs = carbs,
                                protein = protein
                            )
                            dbViewmodel.addRecipe(savedrecipe)
                            color.value = Color(0xFFE04545)
                            saved = true
                        }

                    }
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = recipe.title,
            style = MaterialTheme.typography.h5,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
        Spacer(modifier = Modifier.height(5.dp))
        Divider(
            color = Color(0xFF4079A2),
            thickness = 2.dp,
            modifier = Modifier.fillMaxWidth()
        )
        Box(Modifier.background(Color(0xFFE7E7E7))) {
                AsyncImage(
                model = recipe.image,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp)
                    .aspectRatio(16f / 9f)
//                    .graphicsLayer {
//                        clip = true
//                        shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)
//                    }
//                    .border(
//                        BorderStroke(2.dp, Color(0xFF4079A2)),
//                        RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)
//                    )
                    ,
                contentScale = ContentScale.Crop,
                placeholder = painterResource(id = R.drawable.img)
            )
        }
        Divider(
            color = Color(0xFF4079A2),
            thickness = 2.dp,
            modifier = Modifier.fillMaxWidth()
        )
        Row(modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFE7E7E7)),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(modifier = Modifier
                .weight(0.5f)
                .clickable {
                    selectedSection = Section.INGREDIENTS
                    width = 198
                    width2 = 0
                },
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF4079A2)
                        )) {
                            append("Ingredients")
                        }
                    },
                    style = MaterialTheme.typography.button,
                    modifier = Modifier.padding(16.dp)
                )
                Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.End) {
                    Divider(
                        color = Color(0xFF4079A2),
                        thickness = 2.dp,
                        modifier = Modifier.width(animateWidth.dp)
                    )
                }            }
            Column(modifier = Modifier
                .weight(0.5f)
                .clickable {
                    selectedSection = Section.COOKING_INSTRUCTIONS
                    width = 0
                    width2 = 198
                },
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF4079A2)
                        )) {
                            append("Instructions")
                        }
                    },
                    style = MaterialTheme.typography.button,
                    modifier = Modifier.padding(16.dp)
                )
                Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start) {
                    Divider(
                        color = Color(0xFF4079A2),
                        thickness = 2.dp,
                        modifier = Modifier.width(animateWidth2.dp)
                    )
                }
            }
        }




            when (selectedSection) {
                Section.INGREDIENTS -> {
                    Column(modifier = Modifier
                        .padding(start = 8.dp, bottom = 4.dp)
                        .fillMaxWidth()
                        .verticalScroll(
                            rememberScrollState()
                        ), horizontalAlignment = Alignment.Start) {
                    recipe.extendedIngredients.forEach { ingredient ->
                        Text(
                            text = buildAnnotatedString {
                                withStyle(
                                    style = SpanStyle(
                                        fontWeight = FontWeight.Bold
                                    )
                                ) {
                                    append(ingredient.name + ": ")
                                }
                                append((DecimalFormat("#.##").format(ingredient.measures.metric.amount)).toString() + " " + ingredient.measures.metric.unitShort)
                            },
                            style = MaterialTheme.typography.body1,
                            modifier = Modifier.padding(vertical = 2.dp)
                        )
                    }
                }
            }
                Section.COOKING_INSTRUCTIONS -> {
                    val steps = mutableListOf<String>()
                    var stepnum by remember { mutableStateOf(1) }
                    recipe.analyzedInstructions.forEach {
                        it.steps.forEach { step->
                            steps.add(step.step)
                        }
                    }
                    var text by remember { mutableStateOf(steps[stepnum-1]) }
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .verticalScroll(rememberScrollState())
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Step $stepnum",
                            style= Typography.h5,
                            color = Color(0xFF4079A2)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = text,
                            style= Typography.body1,
                            color = Color.Black
                        )
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                    ) {
                        Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
                            Divider(
                                color = Color(0xFF4079A2),
                                thickness = 2.dp,
                                modifier = Modifier.width(200.dp)
                            )

                            Row(modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 18.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                InstructionButton(
                                    text = "Previous",
                                    modifier = Modifier
                                        .padding(start = 20.dp)
                                        .width(124.dp),
                                    onclick = {
                                        stepnum--
                                        text = steps[stepnum-1]
                                    },
                                    icon = Icons.Default.ArrowBack,
                                    enable = (stepnum>1)
                                )
                                InstructionButton(
                                    text = "Next",
                                    modifier = Modifier
                                        .padding(end = 20.dp)
                                        .width(124.dp),
                                    onclick = {
                                        stepnum++
                                        text = steps[stepnum-1]
                                    },
                                    icon = Icons.Default.ArrowForward,
                                    enable = (stepnum<steps.size)
                                )
                            }
                        }

                    }
                }
            }


    }

}

@Composable
fun SavedRecipePage(recipe: Recipe, close: () -> Unit, dbViewmodel: DBViewmodel) {
    var selectedSection by remember { mutableStateOf(Section.INGREDIENTS) }
    var color = remember { mutableStateOf(Color(0xFF666666)) }
    var width by remember {
        mutableStateOf(198)
    }
    val animateWidth by animateIntAsState(
        targetValue = width,
        animationSpec = tween(durationMillis = 500))
    var width2 by remember {
        mutableStateOf(0)
    }
    val animateWidth2 by animateIntAsState(
        targetValue = width2,
        animationSpec = tween(durationMillis = 500))

    var number by remember { mutableStateOf(0) }
    LaunchedEffect(Unit) {
        number = dbViewmodel.recipeExists(recipe.apiId)
    }
    var saved by remember { mutableStateOf(false) }
    if(number==1) {
        color.value = Color(0xFFE04545)
        saved = true
        number = 0
    }
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Top) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
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
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = "",
                tint = color.value,
                modifier = Modifier
                    .width(50.dp)
                    .height(50.dp)
                    .padding(end = 14.dp, top = 8.dp)
                    .clickable {

                        if (saved) {
                            dbViewmodel.removeRecipe(recipe.apiId)
                            color.value = Color(0xFF666666)
                            saved = false
                        } else {
                            dbViewmodel.addRecipe(recipe)
                            color.value = Color(0xFFE04545)
                            saved = true
                        }
                    }
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = recipe.recipeName,
            style = MaterialTheme.typography.h5,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
        Spacer(modifier = Modifier.height(5.dp))
        Divider(
            color = Color(0xFF4079A2),
            thickness = 2.dp,
            modifier = Modifier.fillMaxWidth()
        )
        Box(Modifier.background(Color(0xFFE7E7E7))) {
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
        }
        Divider(
            color = Color(0xFF4079A2),
            thickness = 2.dp,
            modifier = Modifier.fillMaxWidth()
        )
        Row(modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFE7E7E7)),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(modifier = Modifier
                .weight(0.5f)
                .clickable {
                    selectedSection = Section.INGREDIENTS
                    width = 198
                    width2 = 0
                },
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF4079A2)
                        )) {
                            append("Ingredients")
                        }
                    },
                    style = MaterialTheme.typography.button,
                    modifier = Modifier.padding(16.dp)
                )
                Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.End) {
                    Divider(
                        color = Color(0xFF4079A2),
                        thickness = 2.dp,
                        modifier = Modifier.width(animateWidth.dp)
                    )
                }            }
            Column(modifier = Modifier
                .weight(0.5f)
                .clickable {
                    selectedSection = Section.COOKING_INSTRUCTIONS
                    width = 0
                    width2 = 198
                },
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF4079A2)
                        )) {
                            append("Instructions")
                        }
                    },
                    style = MaterialTheme.typography.button,
                    modifier = Modifier.padding(16.dp)
                )
                Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start) {
                    Divider(
                        color = Color(0xFF4079A2),
                        thickness = 2.dp,
                        modifier = Modifier.width(animateWidth2.dp)
                    )
                }
            }
        }




        when (selectedSection) {
            Section.INGREDIENTS -> {
                Column(modifier = Modifier
                    .padding(start = 8.dp, bottom = 4.dp)
                    .fillMaxWidth()
                    .verticalScroll(
                        rememberScrollState()
                    ), horizontalAlignment = Alignment.Start) {
                    recipe.ingredients.forEachIndexed { index, ingredient ->
                        Text(
                            text = buildAnnotatedString {
                                withStyle(
                                    style = SpanStyle(
                                        fontWeight = FontWeight.Bold
                                    )
                                ) {
                                    append(ingredient + ": ")
                                }
                                append(recipe.measures[index])
                            },
                            style = MaterialTheme.typography.body1,
                            modifier = Modifier.padding(vertical = 2.dp)
                        )
                    }
                }
            }
            Section.COOKING_INSTRUCTIONS -> {
                val steps = mutableListOf<String>()
                steps.addAll(recipe.procedure)
                var stepnum by remember { mutableStateOf(1) }

                var text by remember { mutableStateOf(steps[stepnum-1]) }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Step $stepnum",
                        style= Typography.h5,
                        color = Color(0xFF4079A2)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = text,
                        style= Typography.body1,
                        color = Color.Black
                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                ) {
                    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
                        Divider(
                            color = Color(0xFF4079A2),
                            thickness = 2.dp,
                            modifier = Modifier.width(200.dp)
                        )

                        Row(modifier = Modifier.fillMaxWidth().padding(vertical = 18.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            InstructionButton(
                                text = "Previous",
                                modifier = Modifier.padding(start = 20.dp).width(124.dp),
                                onclick = {
                                    stepnum--
                                    text = steps[stepnum-1]
                                },
                                icon = Icons.Default.ArrowBack,
                                enable = (stepnum>1)
                            )
                            InstructionButton(
                                text = "Next",
                                modifier = Modifier.padding(end = 20.dp).width(124.dp),
                                onclick = {
                                    stepnum++
                                    text = steps[stepnum-1]
                                },
                                icon = Icons.Default.ArrowForward,
                                enable = (stepnum<steps.size)
                            )
                        }
                    }

                }
            }
        }


    }

}

enum class Section(val title: String) {
    INGREDIENTS("Ingredients"),
    COOKING_INSTRUCTIONS("Cooking Instructions")
}