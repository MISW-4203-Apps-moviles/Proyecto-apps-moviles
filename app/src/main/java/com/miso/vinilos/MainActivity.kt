package com.miso.vinilos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.miso.vinilos.ui.BottomNavigationBar
import com.miso.vinilos.ui.Navigation
import com.miso.vinilos.ui.TopNavigationBar
import com.miso.vinilos.ui.theme.VinilosTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            VinilosTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen(navController: NavHostController = rememberNavController()) {
  //  val navController = rememberNavController()
    //val navController = navController

    Scaffold(
        topBar = {
            TopNavigationBar(navController)
        },
        bottomBar = {
            BottomNavigationBar(navController)
        },
        content = { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)

            ) {
                Navigation(navController, innerPadding)
            }

        })
}
@Preview(showBackground = true)
@Composable
fun VinylsAppPreview() {
    MainScreen()
}