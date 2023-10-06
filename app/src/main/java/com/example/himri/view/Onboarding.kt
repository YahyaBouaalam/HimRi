package com.example.himri.view.onboarding

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.*
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.himri.R
import com.example.himri.room.entity.User
import com.example.himri.ui.components.OnboardingButton
import com.example.himri.ui.theme.Typography
import com.example.himri.viewmodel.DBViewmodel
import kotlinx.coroutines.launch
import okhttp3.internal.wait
import java.lang.Thread.sleep

@Composable
fun Onboarding(dbViewmodel : DBViewmodel, next: () -> Unit) {

    var currentPage by remember { mutableStateOf(0) }
    val progress by animateFloatAsState(
        targetValue = (currentPage) / 5f,
        animationSpec = tween(800)
    )

    var visible by remember {
        mutableStateOf(true)
    }

    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Column( modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom) {


            when(currentPage) {
                0 -> {
                    Text(
                        "Cooking Can Sometimes be Confusing",
                        style = Typography.h1,
                        modifier = Modifier.padding(top = 140.dp, start = 20.dp, end = 30.dp)
                    )
                    Spacer(modifier = Modifier.height(30.dp))
                    Image(
                        painter = painterResource(id = R.drawable.cookconfused),
                        contentDescription = "",
                        modifier = Modifier.size(300.dp)
                    )
                    Spacer(modifier = Modifier.height(70.dp))
                    OnboardingButton(next = {
                        visible = false
                        currentPage++
                        visible = true
                    })
                    Spacer(modifier = Modifier.height(60.dp))
                }
                1 -> {
                    Text(
                        "Do not worry, We can help!",
                        style = Typography.h1,
                        modifier = Modifier.padding(top = 140.dp, start = 20.dp, end = 30.dp)
                    )
                    Spacer(modifier = Modifier.height(30.dp))
                    Image(painter = painterResource(id = R.drawable.cookhelp), contentDescription = "", modifier = Modifier.size(300.dp))
                    Spacer(modifier = Modifier.height(70.dp))
                    OnboardingButton(next= {
                        visible = false
                        currentPage++
                        visible = true                    })
                    Spacer(modifier = Modifier.height(60.dp))
                }
                2 -> {
                    Text(
                        "Explore a vast selection of Recipes with one click",
                        style = Typography.h1,
                        modifier = Modifier.padding(top = 140.dp, start = 20.dp, end = 30.dp)
                    )
                    Spacer(modifier = Modifier.height(30.dp))
                    Image(painter = painterResource(id = R.drawable.space), contentDescription = "", modifier = Modifier.size(300.dp))
                    Spacer(modifier = Modifier.height(70.dp))
                    OnboardingButton(next= {
                        visible = false
                        currentPage++
                        visible = true                    })
                    Spacer(modifier = Modifier.height(60.dp))
                }
                3 -> {
                    Text(
                        "Fully Customize Your Request to Find The perfect meal",
                        style = Typography.h1,
                        modifier = Modifier.padding(top = 140.dp, start = 20.dp, end = 30.dp)
                    )
                    Spacer(modifier = Modifier.height(30.dp))
                    Image(painter = painterResource(id = R.drawable.settings), contentDescription = "", modifier = Modifier.size(300.dp))
                    Spacer(modifier = Modifier.height(70.dp))
                    OnboardingButton( next= {
                        visible = false
                        currentPage++
                        visible = true                    })
                    Spacer(modifier = Modifier.height(60.dp))
                }
                4-> {
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
                    val selectedList = mutableListOf<String>()
                    var value by remember {
                        mutableStateOf("")
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
                            "But First, Tell us About Yourself!",
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
                                text = "Any Allergies?",
                                style = TextStyle(
                                    fontSize = 24.sp,
                                    fontFamily = FontFamily.Default,
                                    fontWeight = FontWeight.W600
                                )
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
                                                selected.value = !selected.value
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
                        OnboardingButton("start cooking") {
                            if (value.isEmpty()) {
                                err = true
                                coroutineScope.launch {
                                    offsetX.animateTo(
                                        targetValue = 0f,
                                        animationSpec = shakeKeyframes,
                                    )
                                }
                            } else {
                                visible = false
                                currentPage++
                                dbViewmodel.add(User(name = value, allergies = selectedList))
                                next()
                            }
                        }

                    }
                }
            }

            LinearProgressIndicator(
                progress = progress,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp),
                color = Color(0xFF4079A2),
                backgroundColor = Color.White
            )

        }
    }

}