package com.example.himri

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.himri.model.ApiRecipe
import com.example.himri.room.entity.Recipe
import com.example.himri.room.entity.User
import com.example.himri.ui.components.Cuisines
import com.example.himri.ui.components.IngredientButton
import com.example.himri.ui.components.MyAppbar
import com.example.himri.ui.theme.HimriTheme
import com.example.himri.ui.theme.Typography
import com.example.himri.view.RecipePage
import com.example.himri.view.SavedRecipePage
import com.example.himri.view.UpdateInfo
import com.example.himri.view.home.HomeScreen
import com.example.himri.view.home.Recipes
import com.example.himri.view.home.SavedRecipes
import com.example.himri.view.home.Splash
import com.example.himri.view.onboarding.*
import com.example.himri.viewmodel.APIViewModel
import com.example.himri.viewmodel.DBViewModelAbstract
import com.example.himri.viewmodel.DBViewmodel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    val dbViewModel: DBViewmodel by viewModels()
    val apiViewModel: APIViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HimriTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
//                    Column() {
                        //val selected = mutableListOf<ApiRecipe>()
                        //IngredientButton(selected = selected, viewModel = DBViewModel)
//                        AddUserScreen(viewModel = DBViewModel)
                    //Onboarding3()
                        //RecipeList(recipes = apiViewModel.response)
                        //Cuisines()
                        //apiViewModel.getRecipes(listOf("chicken"))
                    //Sui(apiViewModel.response)
                    //apiViewModel.getRecipes(listOf("chicken", "cheese"), "0","100", "5", "100", "40", "10000", "italian")
                HimriNav(dbViewModel = dbViewModel, apiViewModel = apiViewModel)
//                    }
                }
            }
        }
    }
}

enum class Screen {
    Home,
    Onboarding,
    Recipes,
    Splash,
    RecipePage,
    SavedRecipePage,
    SavedRecipes,
    UpdateInfo
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun HimriNav(
    dbViewModel: DBViewmodel,
    apiViewModel: APIViewModel
) {
    val navController = rememberNavController()
    var number by remember { mutableStateOf(0) }
    LaunchedEffect(Unit) {
        number = dbViewModel.exists()
    }

    NavHost(
        navController = navController,
        startDestination = Screen.Splash.name,
        //if(number > 0) Screen.Home.name else Screen.Onboarding1.name,
        builder = {
            composable(Screen.Home.name) {
                val scaffoldState = rememberScaffoldState()
                val coroutineScope = rememberCoroutineScope()
                var user by remember { mutableStateOf(User()) }
                LaunchedEffect(Unit) {
                    user = dbViewModel.getUser()
                }
                coroutineScope.launch {
                    scaffoldState.drawerState.close()
                }
                Scaffold(
                    scaffoldState = scaffoldState,
                    topBar = {
                        MyAppbar {
                            coroutineScope.launch {
                                scaffoldState.drawerState.open()
                            }
                        }
                    },
                    drawerContent = {
                        Column() {
                            Box(modifier = Modifier.fillMaxWidth().height(250.dp).padding(bottom = 10.dp).background(Color(0xFFE7E7E7)), contentAlignment = Alignment.BottomStart) {
                             Text(text = "Hi, ${user.name}.",
                             style = Typography.h3,
                                modifier = Modifier.padding(bottom = 24.dp, start = 18.dp)
                             )
                            }
                            Column(modifier = Modifier) {
                                Row(modifier = Modifier
                                    .padding(24.dp)
                                    .fillMaxWidth()
                                    .clickable {
                                        navController.navigate(Screen.SavedRecipes.name)
                                    }
                                    , horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically) {
                                    Text("Saved")
                                    Icon(
                                        imageVector = Icons.Default.Bookmark,
                                        contentDescription = "",
                                        tint = Color(0xFF666666),
                                        modifier = Modifier
                                            .width(40.dp)
                                            .height(40.dp)
                                            .padding(start = 14.dp)
                                    )
                                }
                                Row(modifier = Modifier
                                    .padding(horizontal = 24.dp, vertical = 8.dp)
                                    .fillMaxWidth()
                                    .clickable {
                                        navController.navigate(Screen.UpdateInfo.name)
                                    }
                                    , horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically) {
                                    Text("Update Personal Information")
                                    Icon(
                                        imageVector = Icons.Default.Person,
                                        contentDescription = "",
                                        tint = Color(0xFF666666),
                                        modifier = Modifier
                                            .width(40.dp)
                                            .height(40.dp)
                                            .padding(start = 14.dp)
                                    )
                                }
                            }

                        }
                    },

                    ) {
                    HomeScreen(apiViewModel = apiViewModel, next = {navController.navigate(Screen.Recipes.name)}, torandom = {navController.navigate(Screen.RecipePage.name)}, dbViewmodel = dbViewModel)
                }
            }

            composable(Screen.Onboarding.name) {
                Onboarding(next = {navController.navigate(Screen.Home.name)}, dbViewmodel = dbViewModel )
            }
            composable(Screen.Recipes.name) {
                Recipes(apiViewModel.response, apiViewModel = apiViewModel, next = {navController.navigate(Screen.RecipePage.name)}, close = {navController.popBackStack()} )
            }
            composable(Screen.Splash.name) {
                Splash(dbViewModel, next = {navController.navigate(Screen.Home.name)}, next2= {navController.navigate(Screen.Onboarding.name)})
            }
            composable(Screen.RecipePage.name) {
                RecipePage(apiViewModel = apiViewModel, close = {navController.popBackStack()}, dbViewmodel = dbViewModel)
                }
            composable(Screen.UpdateInfo.name) {
                UpdateInfo(close = {navController.popBackStack()}, dbViewmodel = dbViewModel)
            }
            composable(Screen.SavedRecipePage.name) {
                SavedRecipePage(dbViewModel.selectedRecipeState.value!!, close = {navController.popBackStack()}, dbViewmodel = dbViewModel)
            }
            composable(Screen.SavedRecipes.name) {
                SavedRecipes(recipes = dbViewModel.recipeListFlow.collectAsState(initial = listOf()).value, dbViewmodel = dbViewModel ,next = {navController.navigate(Screen.SavedRecipePage.name)}, close = {navController.popBackStack()} )
            }
        })
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    HimriTheme {
        Greeting("Android")
    }
}