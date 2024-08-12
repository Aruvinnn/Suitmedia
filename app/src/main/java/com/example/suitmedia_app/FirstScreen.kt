package com.example.suitmedia_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "first_screen") {
                composable("first_screen") { FirstScreen(navController) }
                composable("second_screen/{name}") { backStackEntry ->
                    SecondScreen(
                        navController,
                        backStackEntry.arguments?.getString("name") ?: ""
                    )
                }
                composable("third_screen") { ThirdScreen(navController) }
            }
        }
    }
}

@Composable
fun FirstScreen(navController: NavHostController) {
    var name by remember { mutableStateOf(TextFieldValue("")) }
    var sentence by remember { mutableStateOf(TextFieldValue("")) }
    var showDialog by remember { mutableStateOf(false) }
    var isPalindrome by remember { mutableStateOf(false) }

    if (showDialog) {
        Dialog(onDismissRequest = { showDialog = false }) {
            Surface {
                Text(
                    text = if (isPalindrome) "Palindrome" else "Not Palindrome",
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = sentence,
            onValueChange = { sentence = it },
            label = { Text("Palindrome") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            isPalindrome = sentence.text == sentence.text.reversed()
            showDialog = true
        }) {
            Text("Check")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            navController.navigate("second_screen/${name.text}")
        }) {
            Text("Next")
        }
    }
}