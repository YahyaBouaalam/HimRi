package com.example.himri.ui.components

import SelectionDialog
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.NavigateNext
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.himri.R
import com.example.himri.ui.theme.Typography
import java.text.DecimalFormat

@Composable
fun RandomButton(next : () -> Unit) {
    Button(onClick = {next()},
        modifier = Modifier
            .height(60.dp)
            .width(300.dp),
        elevation = ButtonDefaults.elevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp,
            hoveredElevation = 0.dp,
            focusedElevation = 0.dp
        ),
        shape = RoundedCornerShape(28.dp),
        contentPadding = PaddingValues(15.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color(0xFFfffffe),
            contentColor = Color(0xFF094067)
        ),
        border = BorderStroke(2.dp, Color(0xFF5f6c7b))
    ) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {

            Text(
                text = "I'm feeling lucky!",
                color = Color(0xFF4079A2),
                modifier =  Modifier.padding(start =  30.dp),
                style = Typography.button
            )
            Spacer(modifier = Modifier.width(20.dp))
            Image(
                painterResource(R.drawable.dice),
                contentDescription = "",
                modifier =  Modifier.padding(end =  0.dp)
            )
        }
    }
}

@Composable
fun FindRecipe(next : () -> Unit) {
    Button(onClick = {next()},
        modifier = Modifier
            .height(60.dp)
            .width(300.dp),
        elevation = ButtonDefaults.elevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp,
            hoveredElevation = 0.dp,
            focusedElevation = 0.dp
        ),
        shape = RoundedCornerShape(28.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color(0xFF4079A2))) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {

            Text(
                text = "Find me a Recipe",
                color = Color(0xFFfffffe),
                modifier =  Modifier.padding(start =  30.dp),
                style = Typography.button
            )
        }
    }
}

@Composable
fun OnboardingButton(text: String = "Next", next : () -> Unit) {
    Button(onClick = {next()},
        modifier = Modifier
            .height(75.dp)
            .width(175.dp),
        elevation = ButtonDefaults.elevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp,
            hoveredElevation = 0.dp,
            focusedElevation = 0.dp
        ),
        shape = RoundedCornerShape(15.dp),
        contentPadding = PaddingValues(15.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF4079A2)),
    ) {
        Text(text = text, color = Color(0xFFfffffe), style = Typography.button)
    }
}

@Composable
fun IngredientButton(selected: MutableList<String>, modifier: Modifier) {
    val showDialog =  remember { mutableStateOf(false) }
    Button(modifier = modifier
        .height(60.dp)
        .fillMaxWidth()
        .padding(horizontal = 60.dp), onClick = {
        showDialog.value = true
    },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color(0xFF4079A2)
        )) {
        Text(text = "(${selected.size}) Ingredients Seleceted",
            color = Color(0xFFfffffe),
            style = TextStyle(
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.W700,
                fontSize = 14.sp
            )
        )
    }
    if(showDialog.value)
        SelectionDialog(setShowDialog = {
            showDialog.value = it
        }, selected)
}

@Composable
fun InstructionButton(text: String, modifier: Modifier, onclick: () -> Unit, icon: ImageVector, enable : Boolean) {
    Button(modifier = modifier
        .height(60.dp), onClick = onclick,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color(0xFF4079A2)
        ),
        enabled = enable) {
        Text(
                text = text,
                color = Color(0xFFfffffe),
                style = TextStyle(
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight.W700,
                    fontSize = 14.sp
                )
            )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Cuisines(selected: MutableState<String>) {
    var ISexpanded = remember { mutableStateOf(false) }
    val cuisines = mutableListOf<String>("-None-", "African", "Asian", "American", "British", "Cajun", "Caribbean", "Chinese", "Eastern European", "European", "French", "German", "Greek", "Indian", "Irish", "Italian", "Japanese", "Jewish", "Korean", "Latin American", "Mediterranean", "Mexican", "Middle Eastern", "Nordic", "Southern", "Spanish", "Thai", "Vietnamese"
    )

    Icons.Filled.KeyboardArrowDown
    Column(Modifier.padding(20.dp)) {

        ExposedDropdownMenuBox(expanded = ISexpanded.value, onExpandedChange = {
            ISexpanded.value = !ISexpanded.value
        }) {
            TextField(
                modifier = Modifier
                .width(250.dp),
                value = selected.value,
                onValueChange = {},
                readOnly = true,
                label = { Text(text = " Type of Cuisine (optional)") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = ISexpanded.value
                    )
                },
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color(0xFF2289D3),
                unfocusedIndicatorColor = Color(0xFF2289D3),
                focusedLabelColor = Color(0xFF2289D3),
                unfocusedLabelColor = Color(0xFF2289D3)
            ))
            ExposedDropdownMenu(
                modifier = Modifier.height(200.dp),
                expanded = ISexpanded.value,
                onDismissRequest = { ISexpanded.value = false }
            ) {
                // this is a column scope
                // all the items are added vertically
                cuisines.forEach { selectedOption ->
                    // menu item
                    DropdownMenuItem(onClick = {
                        selected.value = selectedOption
                        ISexpanded.value = false
                    }) {
                        Text(text = selectedOption)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable

fun MySlider(range : ClosedFloatingPointRange<Float>,values : MutableState<ClosedFloatingPointRange<Float>>, unit: String, modifier: Modifier, color: Color) {
    val decimalFormat = DecimalFormat("0")
    var sliderPosition by remember { mutableStateOf(range) }
    Column(modifier = modifier) {
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            Text("${decimalFormat.format(sliderPosition.start)} $unit")
            Text("${decimalFormat.format(sliderPosition.endInclusive)} $unit")
        }
        RangeSlider(
            values = sliderPosition,
            onValueChange = { sliderPosition = it },
            valueRange = range,
            onValueChangeFinished = {values.value = sliderPosition},
            colors = SliderDefaults.colors(
                thumbColor = color,
                activeTrackColor = color,
                inactiveTrackColor = Color(0xFF5f6c7b)
            )
        )
    }

}

@Preview(showBackground = true)
@Composable
fun ButtonPreview() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        val se = remember  { mutableStateOf (0f..100f) }
        val selected = remember { mutableListOf<String>() }
        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            var sliderPosition by remember { mutableStateOf(0f..100f) }
            Text(se.value.endInclusive.toString())
            Text(se.value.start.toString())
   //             MySlider(0f..100f, se,"Kcal", Modifier.padding(horizontal = 20.dp))
            }
        }
    }