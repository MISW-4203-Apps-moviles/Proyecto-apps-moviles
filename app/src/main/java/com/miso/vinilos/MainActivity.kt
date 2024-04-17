package com.miso.vinilos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.miso.vinilos.screens.AlbumDetailScreen
import com.miso.vinilos.screens.AlbumListScreen
import com.miso.vinilos.screens.ArtistasScreen
import com.miso.vinilos.screens.ColeccionistasScreen
import com.miso.vinilos.ui.theme.VinilosTheme
import com.miso.vinilos.viewModels.AlbumDetailViewModel
import com.miso.vinilos.viewModels.AlbumDetailViewModelFactory
import com.miso.vinilos.viewModels.AlbumListViewModel
import com.miso.vinilos.viewModels.AlbumListViewModelFactory

fun replaceRoute(route: String, vararg arguments: Pair<String, String>): String {
    var newRoute = route
    arguments.forEach { (key, value) ->
        newRoute = newRoute.replace("{$key}", value)
    }
    return newRoute
}

sealed class Screen(val route: String, val icon: ImageVector, val label: String) {
    object AlbumTab :
        Screen("album_tab", Icons.Filled.PlayArrow, "Albumes")

    object AlbumList :
        Screen("album_list", Icons.Filled.PlayArrow, "Lista de álbumes")

    object AlbumDetail :
        Screen(
            "album_detail/{albumId}",
            Icons.Filled.PlayArrow,
            "Detalles del álbum",
        )

    object ColeccionistaTab :
        Screen("coleccionistas_tab", Icons.Filled.AccountCircle, "Coleccionistas")

    object Coleccionistas : Screen(
        "coleccionistas",
        Icons.Filled.AccountCircle,
        "Coleccionistas"
    )

    object ArtistaTab :
        Screen("artistas_tab", Icons.Filled.Star, "Artistas")

    object Artistas :
        Screen("artistas", Icons.Filled.Star, "Artistas")
}

class MainActivity : ComponentActivity() {
    private val albumViewModel: AlbumListViewModel by viewModels()

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
        Screen.AlbumTab, Screen.ColeccionistaTab, Screen.ArtistaTab
    )

    VinilosTheme {
        val screensPadding = PaddingValues(
            start = 4.dp,
            top = 0.dp,
            end = 0.dp,
            bottom = 0.dp
        )

        Scaffold(bottomBar = { MainNavigationBar(navController, bottomNavItems) },
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
                    startDestination = Screen.AlbumTab.route,
                ) {
                    navigation(
                        startDestination = Screen.AlbumList.route,
                        route = Screen.AlbumTab.route
                    ) {
                        composable(Screen.AlbumList.route) {
                            val albumViewModel: AlbumListViewModel =
                                viewModel(factory = AlbumListViewModelFactory())

                            AlbumListScreen(
                                navigateToAlbumDetail = { albumId ->
                                    navController.navigate(
                                        replaceRoute(
                                            Screen.AlbumDetail.route,
                                            "albumId" to albumId.toString(),
                                        )
                                    )
                                },
                                innerPadding = modifiedPadding,
                                viewModel = albumViewModel
                            )
                        }
                        composable(
                            Screen.AlbumDetail.route,
                        ) { backStackEntry ->
                            backStackEntry.arguments?.getString("albumId")?.let {
                                val viewModel: AlbumDetailViewModel =
                                    viewModel(factory = AlbumDetailViewModelFactory())

                                AlbumDetailScreen(
                                    albumId = it,
                                    innerPadding = modifiedPadding,
                                    viewModel = viewModel
                                )
                            }
                        }
                    }

                    navigation(
                        startDestination = Screen.Coleccionistas.route,
                        route = Screen.ColeccionistaTab.route
                    ) {
                        composable(Screen.Coleccionistas.route) {
                            ColeccionistasScreen(
                                modifiedPadding
                            )
                        }
                    }

                    navigation(
                        startDestination = Screen.Artistas.route,
                        route = Screen.ArtistaTab.route
                    ) {
                        composable(Screen.Artistas.route) { ArtistasScreen(modifiedPadding) }
                    }
                }
            })
    }
}

@Composable
fun MainNavigationBar(
    navController: NavHostController, items: List<Screen>
) {
    val currentRoute = remember(navController) {
        mutableStateOf(navController.currentDestination?.route ?: "")
    }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

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
            NavigationBarItem(icon = { Icon(screen.icon, contentDescription = screen.label) },
                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                onClick = { navController.navigate(screen.route) },
                label = { Text(screen.label) })
        }
    }
}

@Preview(showBackground = true)
@Composable
fun VinylsAppPreview() {
    RunVinilosApp()
}