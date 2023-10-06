package com.example.himri.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.example.himri.room.entity.Recipe
import java.text.DecimalFormat

@Composable
fun MyAppbar(menu: () -> Unit) {

    TopAppBar(
        backgroundColor = MaterialTheme.colors.primary,
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "",
                tint = Color.White,
                modifier = Modifier
                    .width(40.dp)
                    .height(40.dp)
                    .padding(start = 18.dp)
                    .clickable {
                        menu()
                    }
            )
            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    ) {
                        append("HIM")
                    }
                    withStyle(style = SpanStyle(
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                            ) {

                        append("Ri")
                    }
                },
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.onSurface,
                modifier = Modifier.padding(end = 18.dp)
            )

        }
    }
    
}

@Composable
fun RecipesTopBar(close: () -> Unit) {

    TopAppBar(
        backgroundColor = MaterialTheme.colors.primary,
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
    }

}