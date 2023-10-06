import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

@Composable
fun SelectionDialog(setShowDialog: (Boolean) -> Unit, selectedList: MutableList<String>) {
    val list = mutableListOf("Salt", "Pepper", "Flour", "Eggs", "Milk", "Butter", "Garlic", "Onion", "Olive Oil", "Lemon", "Lime", "Chicken", "Chicken Breast", "Chicken Thighs", "Chicken Wings", "Chicken Drumsticks", "Turkey", "Turkey Breast", "Ground Turkey", "Beef", "Beef Steak", "Ribeye Steak", "Sirloin Steak", "Tenderloin Steak", "Ground Beef", "Pork", "Pork Chops", "Pork Tenderloin", "Pork Ribs", "Lamb", "Lamb Chops", "Lamb Leg", "Fish", "Salmon", "Tuna", "Cod", "Halibut", "Trout", "Catfish", "Sole", "Haddock", "Mahi Mahi", "Sardines", "Anchovies", "Tilapia", "Flounder", "Snapper", "Mackerel", "Shrimp", "Crab", "Lobster", "Scallops", "Squid", "Octopus", "Clams", "Mussels", "Oysters", "Tofu", "Tempeh", "Seitan", "Quinoa", "Rice", "White Rice", "Brown Rice", "Jasmine Rice", "Basmati Rice", "Wild Rice", "Pasta", "Spaghetti", "Penne", "Fusilli", "Linguine", "Lasagna", "Bread", "White Bread", "Whole Wheat Bread", "Sourdough Bread", "Tortillas", "Corn Tortillas", "Flour Tortillas", "Tomatoes", "Roma Tomatoes", "Cherry Tomatoes", "Grape Tomatoes", "Beefsteak Tomatoes", "Cucumbers", "English Cucumbers", "Persian Cucumbers", "Carrots", "Baby Carrots", "Rainbow Carrots", "Bell Peppers", "Green Bell Peppers", "Red Bell Peppers", "Yellow Bell Peppers", "Orange Bell Peppers", "Spinach", "Baby Spinach", "Kale", "Lettuce", "Romaine Lettuce", "Iceberg Lettuce", "Butter Lettuce", "Spring Mix Lettuce", "Arugula", "Broccoli", "Cauliflower", "Zucchini", "Mushrooms", "Button Mushrooms", "Portobello Mushrooms", "Shiitake Mushrooms", "Cremini Mushrooms", "Oyster Mushrooms", "Enoki Mushrooms", "Chanterelle Mushrooms", "Potatoes", "Russet Potatoes", "Yukon Gold Potatoes", "Sweet Potatoes", "Red Potatoes", "Corn", "Sweet Corn", "Green Beans", "Asparagus", "Peas", "Avocado", "Tomatillo", "Artichoke", "Eggplant", "Cabbage", "Green Cabbage", "Red Cabbage", "Napa Cabbage", "Brussels Sprouts", "Radish", "Celery", "Ginger", "Cilantro", "Parsley", "Rosemary", "Thyme", "Basil", "Oregano", "Dill", "Mint", "Chives", "Sage", "Bay Leaves", "Turmeric", "Curry Leaves", "Lemongrass", "Saffron")
    list.sort()
    val templist = mutableListOf<String>()
    val removed = mutableListOf<String>()
    Dialog(onDismissRequest = { setShowDialog(false) }) {
        Surface(
            shape = RoundedCornerShape(28.dp),
            color = Color(0xFFfffffe),
            modifier = Modifier.padding(top = 40.dp, bottom = 80.dp)
        ) {
            Box(
                contentAlignment = Alignment.TopCenter,
                modifier = Modifier.fillMaxSize()
            ) {
                Column(modifier = Modifier.padding(20.dp), verticalArrangement = Arrangement.SpaceBetween) {
                    Column(modifier = Modifier.wrapContentSize()) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Box(modifier = Modifier.wrapContentSize()) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.End,
                                verticalAlignment = Alignment.Top
                            ) {

                                Icon(
                                    imageVector = Icons.Default.Cancel,
                                    contentDescription = "",
                                    tint = Color(0xFF666666),
                                    modifier = Modifier
                                        .width(30.dp)
                                        .height(30.dp)
                                        .clickable {
                                            selectedList.addAll(removed)
                                            templist.clear()
                                            removed.clear()
                                            setShowDialog(false)
                                        }
                                )

                            }
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Start,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Any Available ingredients?",
                                    style = TextStyle(
                                        fontSize = 24.sp,
                                        fontFamily = androidx.compose.ui.text.font.FontFamily.Default,
                                        fontWeight = FontWeight.W600
                                    )
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(20.dp))
                        Divider(color = Color(0xFF5f6c7b), thickness = 1.dp, modifier = Modifier.padding(horizontal = 2.dp))
                    }
                    Box(modifier = Modifier.wrapContentSize()) {
                        LazyColumn(modifier = Modifier.fillMaxWidth()){
                            items(list){ ingredient ->
                                val selected = remember { mutableStateOf(false) }
                                val color = remember { mutableStateOf(Color(0xFFfffffe)) }
                                val tcolor = remember { mutableStateOf(Color.Black) }
                                if(selectedList.contains(ingredient)) {
                                    selected.value = !selected.value
                                    color.value = Color(0xFF2289D3)
                                    tcolor.value = Color(0xFF2289D3)
                                }
                                Row(modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight()
                                    .clickable {
                                        if (!selected.value) {
                                            color.value = Color(0xFF2289D3)
                                            tcolor.value = Color(0xFF2289D3)
                                            templist.add(ingredient)
                                        } else {
                                            color.value = Color(0xFFfffffe)
                                            tcolor.value = Color.Black
                                            templist.remove(ingredient)
                                            if (selectedList.contains(ingredient)) {
                                                selectedList.remove(ingredient)
                                                removed.add(ingredient)
                                            }
                                        }
                                        selected.value = !selected.value
                                    },
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically) {
                                    Text(text = ingredient,
                                        color= tcolor.value,
                                        style = TextStyle(
                                            fontSize = 16.sp,
                                            fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace,
                                            fontWeight = FontWeight.W400
                                        ), modifier = Modifier.padding(start = 10.dp, top = 10.dp, bottom = 10.dp))
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
                    }
                }
            Box(
                contentAlignment = Alignment.BottomCenter,
                modifier = Modifier.fillMaxSize()
            ) {
                Column(modifier = Modifier.wrapContentHeight().fillMaxWidth().background(color = Color(0xFFfffffe)), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                    Divider(color = Color(0xFF5f6c7b), thickness = 1.dp, modifier = Modifier.padding(horizontal = 22.dp))
                    Button(modifier = Modifier.padding(vertical = 20.dp),
                        elevation = ButtonDefaults.elevation(
                            defaultElevation = 0.dp,
                        pressedElevation = 0.dp,
                        hoveredElevation = 0.dp,
                        focusedElevation = 0.dp
                    ), onClick = {
                        templist.forEach { ing ->
                            if (!selectedList.contains(ing))
                                selectedList.add(ing)
                        }
                        templist.clear()
                        removed.clear()
                        setShowDialog(false)
                    }, shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color(0xFF2289D3)
                        )) {
                        Text(text = "Done", modifier = Modifier.padding(horizontal = 28.dp, vertical =4.dp), color = Color(0xFFfffffe))
                    }
                }
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun SelectPreview() {
//    val selected = remember { mutableListOf<String>() }
//    val size = remember { mutableStateOf(selected.size) }
//    val showDialog =  remember { mutableStateOf(false) }
//    Surface(
//        modifier = Modifier.fillMaxSize(),
//        color = Color(0xFFfffffe)
//    ) {
//        Button(modifier = Modifier.wrapContentSize(), onClick = {
//            showDialog.value = true
//        }) {
//            Text(text = "(${size.value}) Ingredients Seleceted")
//        }
//        if(showDialog.value)
//            SelectionDialog(setShowDialog = {
//                showDialog.value = it
//            }, selected)
//    }
//
//}