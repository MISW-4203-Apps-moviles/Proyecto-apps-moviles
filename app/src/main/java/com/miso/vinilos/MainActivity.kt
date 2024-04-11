package com.miso.vinilos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.miso.vinilos.screens.AlbumesScreen
import com.miso.vinilos.screens.ArtistasScreen
import com.miso.vinilos.screens.ColeccionistasScreen
import com.miso.vinilos.ui.theme.VinilosTheme

sealed class Screen(val route: String, val icon: ImageVector, val label: String) {
    object Albumes : Screen("albumes", Icons.Filled.PlayArrow, "Albumes")
    object Coleccionistas : Screen("coleccionistas", Icons.Filled.AccountCircle, "Coleccionistas")
    object Artistas : Screen("artistas", Icons.Filled.Star, "Artistas")
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RunVinilosApp()
        }
    }
}

@Composable
fun RunVinilosApp() {
    val navController = rememberNavController()
    val bottomNavItems = listOf(
        Screen.Albumes,
        Screen.Coleccionistas,
        Screen.Artistas
    )

    VinilosTheme {
        val screensPadding = PaddingValues(
            start = 16.dp,
            top = 50.dp,
            end = 0.dp,
            bottom = 0.dp
        )

        Scaffold(
            bottomBar = { MainNavigationBar(navController, bottomNavItems) },
            content = { innerPadding ->

                val modifiedPadding = PaddingValues(
                    start = innerPadding.calculateStartPadding(LayoutDirection.Ltr) + screensPadding.calculateStartPadding(
                        LayoutDirection.Ltr
                    ),
                    top = innerPadding.calculateTopPadding() + screensPadding.calculateTopPadding(),
                    end = innerPadding.calculateEndPadding(LayoutDirection.Ltr) + screensPadding.calculateEndPadding(
                        LayoutDirection.Ltr
                    ),
                    bottom = innerPadding.calculateBottomPadding() + screensPadding.calculateBottomPadding()
                )

                NavHost(
                    navController,
                    startDestination = Screen.Albumes.route
                ) {
                    composable(Screen.Albumes.route) { AlbumesScreen(modifiedPadding) }
                    composable(Screen.Coleccionistas.route) { ColeccionistasScreen(modifiedPadding) }
                    composable(Screen.Artistas.route) { ArtistasScreen(modifiedPadding) }
                }
            }
        )
    }
}

@Composable
fun MainNavigationBar(
    navController: NavHostController,
    items: List<Screen>
) {
    val currentRoute = remember(navController) {
        mutableStateOf(navController.currentDestination?.route ?: "")
    }

    DisposableEffect(navController) {
        val callback = NavController.OnDestinationChangedListener { _, destination, _ ->
            currentRoute.value = destination.route ?: ""
        }
        navController.addOnDestinationChangedListener(callback)
        onDispose {
            navController.removeOnDestinationChangedListener(callback)
        }
    }

    NavigationBar {
        items.forEach { screen ->
            NavigationBarItem(
                icon = { Icon(screen.icon, contentDescription = screen.label) },
                selected = currentRoute.value == screen.route,
                onClick = { navController.navigate(screen.route) },
                label = { Text(screen.label) }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun VinylsAppPreview() {
    RunVinilosApp()
}