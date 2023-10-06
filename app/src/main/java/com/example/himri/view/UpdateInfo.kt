package com.example.himri.view

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.keyframes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.himri.room.entity.User
import com.example.himri.ui.components.OnboardingButton
import com.example.himri.ui.theme.Typography
import com.example.himri.viewmodel.DBViewmodel
import kotlinx.coroutines.launch

@Composable
fun UpdateInfo(close: () -> Unit, dbViewmodel: DBViewmodel) {
    var user by remember { mutableStateOf(User()) }
    var value by remember {
        mutableStateOf(user.name)
    }

    val selectedList = remember { mutableStateListOf<String>() }

    LaunchedEffect(Unit) {
        user = dbViewmodel.getUser()
        value = user.name
        selectedList.addAll(user.allergies)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White),
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

        val list = listOf(
            "Dairy",
            "Egg",
            "Gluten",
            "Grain",
            "Peanut",
            "Seafood",
            "Sesame",
            "Shellfish",
            "Soy",
            "Sulfite",
            "Tree Nut",
            "Wheat"
        )
        val coroutineScope = rememberCoroutineScope()
        val offsetX = remember { Animatable(0f) }
        val shakeKeyframes: AnimationSpec<Float> = keyframes {
            durationMillis = 1000
            val easing = FastOutLinearInEasing

            // generate 8 keyframes
            for (i in 1..8) {
                val x = when (i % 3) {
                    0 -> 4f
                    1 -> -4f
                    else -> 0f
                }
                x at durationMillis / 10 * i with easing
            }
        }

        var err by remember {
            mutableStateOf(false)
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                "Update Personal Information:",
                style = Typography.h1,
                modifier = Modifier.padding(top = 20.dp, start = 20.dp, end = 30.dp, bottom = 30.dp)
            )

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 40.dp)
                    .offset(offsetX.value.dp, 0.dp),
                value = value,
                onValueChange = { newText ->
                    if (newText.length <= 50) {
                        value = newText
                    }
                },
               // placeholder = { Text(text = user.name) },
                label = { Text(text = "Name*") },
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color(0xFF4079A2),
                    unfocusedIndicatorColor = Color(0xFF4079A2),
                    focusedLabelColor = Color(0xFF4079A2),
                    unfocusedLabelColor = Color(0xFF4079A2),
                    backgroundColor = Color.White
                ),
                maxLines = 1,
                isError = err // Show error state when name is not entered
            )

            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterStart) {
                Text(
                    modifier = Modifier.padding(top = 20.dp, start = 40.dp, bottom = 7.dp),
                    text = "Allergies or Intolerances:"
                )
            }
            Box(
                modifier = Modifier
                    .padding(start = 40.dp, end = 40.dp, bottom = 60.dp)
                    .wrapContentSize()
                    .border(width = 1.dp, color = Color(0xFF4079A2))
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(360.dp)
                ) {
                    items(list) { item ->
                        val selected = remember { mutableStateOf(false) }
                        val color = remember { mutableStateOf(Color(0xFFfffffe)) }
                        val tcolor = remember { mutableStateOf(Color.Black) }
                        if(selectedList.contains(item)) {
                            selected.value = !selected.value
                            color.value = Color(0xFF4079A2)
                            tcolor.value = Color(0xFF4079A2)
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .clickable {
                                    if (!selected.value) {
                                        color.value = Color(0xFF4079A2)
                                        tcolor.value = Color(0xFF4079A2)
                                        selectedList.add(item)
                                    } else {
                                        color.value = Color(0xFFfffffe)
                                        tcolor.value = Color.Black
                                        selectedList.remove(item)
                                    }
                                },
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = item,
                                color = tcolor.value,
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    fontFamily = FontFamily.Monospace,
                                    fontWeight = FontWeight.W400
                                ),
                                modifier = Modifier.padding(
                                    start = 10.dp,
                                    top = 10.dp,
                                    bottom = 10.dp
                                )
                            )
                            Icon(
                                imageVector = Icons.Filled.Check,
                                contentDescription = "",
                                tint = color.value,
                                modifier = Modifier
                                    .padding(end = 10.dp)
                                    .width(25.dp)
                                    .height(25.dp)
                            )
                        }

                    }
                }
            }
            OnboardingButton("Update Information") {
                if (value.isEmpty()) {
                    err = true
                    coroutineScope.launch {
                        offsetX.animateTo(
                            targetValue = 0f,
                            animationSpec = shakeKeyframes,
                        )
                    }
                } else {
                    dbViewmodel.update(username = value, allergies = selectedList)
                    close()
                }
            }

        }
    }
}