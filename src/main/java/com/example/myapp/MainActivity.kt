package com.example.myapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.*
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppNavigator()
        }
    }
}

// ✅ Navigation Setup (FIXED)
@Composable
fun AppNavigator() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "login") {
        composable("login") { LoginScreen(navController) }
        composable("meals") { MealSuggestionScreen() }
    }
}

fun composable(s: String) {


}

//  Login Screen
@Composable
fun LoginScreen(navController: NavController) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Login", fontSize = 24.sp)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") }
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = {
            if (username.isNotBlank() && password.isNotBlank()) {
                navController.navigate("meals") // Navigate to Meal Suggestion Screen
            }
        }) {
            Text("Login")
        }
    }
}

//  Meal Suggestion Screen
@Composable
fun MealSuggestionScreen() {
    val breakfastList = listOf("Oatmeal with Chia Seeds", "Greek Yogurt with Muesli", "Scrambled Eggs & Toast", "Green Tea with Lemon", "Smoothie with Banana & Protein Powder")
    val lunchList = listOf("Grilled Chicken & Sweet Potatoes", "Beef Stir-Fry with Vegetables", "Quinoa Salad with Lemon Dressing", "Pan-Fried Pork with Rice", "Lamb Stew with Brown Rice")
    val dinnerList = listOf("Salmon with Steamed Veggies", "Chicken Soup with Ginger", "Lentil Curry with Naan", "Baked Fish with Lemon", "Stir-Fried Tofu & Broccoli")
    val snackList = listOf("Almonds & Dark Chocolate", "Greek Yogurt with Honey", "Fruit Salad", "Popcorn with Ginseng", "Boiled Eggs")

    var breakfast by remember { mutableStateOf(breakfastList.random()) }
    var lunch by remember { mutableStateOf(lunchList.random()) }
    var dinner by remember { mutableStateOf(dinnerList.random()) }
    var snack by remember { mutableStateOf(snackList.random()) }

    var favoriteMeals by remember { mutableStateOf(listOf<String>()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Meal Suggestions", fontSize = 24.sp)

        Spacer(modifier = Modifier.height(16.dp))

        MealSuggestionItem("Breakfast", breakfast) { favoriteMeals = favoriteMeals + breakfast }
        MealSuggestionItem("Lunch", lunch) { favoriteMeals = favoriteMeals + lunch }
        MealSuggestionItem("Dinner", dinner) { favoriteMeals = favoriteMeals + dinner }
        MealSuggestionItem("Snack", snack) { favoriteMeals = favoriteMeals + snack }

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = {
            breakfast = breakfastList.random()
            lunch = lunchList.random()
            dinner = dinnerList.random()
            snack = snackList.random()
        }) {
            Text("Suggest New Meals")
        }

        Spacer(modifier = Modifier.height(20.dp))

        FavoriteMealsList(favoriteMeals)
    }
}

//  Meal Item Component
@Composable
fun MealSuggestionItem(mealType: String, meal: String, onSaveClick: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text("$mealType:", fontSize = 20.sp)
        Text(meal, fontSize = 18.sp)
        Spacer(modifier = Modifier.height(10.dp))
        Button(onClick = onSaveClick) {
            Text("Save as Favorite")
        }
        Spacer(modifier = Modifier.height(10.dp))
    }
}

//  Favorite Meals List
@Composable
fun FavoriteMealsList(favoriteMeals: List<String>) {
    if (favoriteMeals.isNotEmpty()) {
        Text("Favorite Meals:", fontSize = 22.sp)
        Spacer(modifier = Modifier.height(10.dp))
        LazyColumn {
            items(favoriteMeals) { meal ->
                Text(meal, fontSize = 18.sp, modifier = Modifier.padding(8.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLoginScreen() {
    LoginScreen(navController = rememberNavController())
}

@Preview(showBackground = true)
@Composable
fun PreviewMealSuggestionScreen() {
    MealSuggestionScreen()
}
