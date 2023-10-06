package com.example.himri.view.home

import androidx.compose.runtime.*
import com.example.himri.viewmodel.DBViewmodel

@Composable
fun Splash(dbViewmodel: DBViewmodel, next: () -> Unit, next2: () -> Unit) {

    var number by remember { mutableStateOf(0) }
    LaunchedEffect(Unit) {
        number = dbViewmodel.exists()
    }


    LaunchedEffect(number) {
        if(number==1) {
            next()
        }
        else if(number==0) {
            next2()
        }
    }


}