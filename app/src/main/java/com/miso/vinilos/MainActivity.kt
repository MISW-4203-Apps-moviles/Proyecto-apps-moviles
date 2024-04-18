package com.miso.vinilos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
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
import com.miso.vinilos.screens.InitialScreen
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
    data object AlbumTab :
        Screen("album_tab", Icons.Filled.PlayArrow, "Albumes")

    data object AlbumList :
        Screen("album_list", Icons.Filled.PlayArrow, "Lista de álbumes")

    data object AlbumDetail :
        Screen(
            "album_detail/{albumId}",
            Icons.Filled.PlayArrow,
            "Detalles del álbum",
        )

    data object ColeccionistaTab :
        Screen("coleccionistas_tab", Icons.Filled.AccountCircle, "Coleccionistas")

    data object Coleccionistas : Screen(
        "coleccionistas",
        Icons.Filled.AccountCircle,
        "Coleccionistas"
    )

    data object ArtistaTab :
        Screen("artistas_tab", Icons.Filled.Star, "Artistas")

    data object Artistas :
        Screen("artistas", Icons.Filled.Star, "Artistas")
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var showScaffold by remember { mutableStateOf(false) }

            if (showScaffold) {
                RunVinilosApp()
            } else {
                InitialScreen(onStartAppClicked = { showScaffold = true })
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RunVinilosApp() {
    val navController = rememberNavController()
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    val bottomNavItems = listOf(
        Screen.AlbumTab, Screen.ColeccionistaTab, Screen.ArtistaTab
    )

    val screensPadding = PaddingValues(
        start = 4.dp,
        top = 0.dp,
        end = 0.dp,
        bottom = 0.dp
    )

    VinilosTheme {
        Scaffold(
            topBar = {TopNavigationBar(navController, scrollBehavior)},
            bottomBar = { BottomNavigationBar(navController, bottomNavItems) },
            content = { innerPadding ->
                val tweenSpec: FiniteAnimationSpec<IntOffset> = tween(300, 0, EaseOut)

                NavHost(
                    navController,
                    startDestination = Screen.AlbumTab.route,
                    enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Start, tweenSpec) },
                    exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Start, tweenSpec) },
                    popEnterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.End, tweenSpec) },
                    popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.End, tweenSpec) }
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
                            ColeccionistasScreen()
                        }
                    }

                    navigation(
                        startDestination = Screen.Artistas.route,
                        route = Screen.ArtistaTab.route
                    ) {
                        composable(Screen.Artistas.route) { ArtistasScreen() }
                    }
                }
            })
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopNavigationBar(navController: NavHostController, scrollBehavior: TopAppBarScrollBehavior) {

    CenterAlignedTopAppBar(
        title = {},
        navigationIcon = {
            IconButton(onClick = {
                if (!navController.navigateUp()){
                    navController.navigate(Screen.AlbumTab.route)
                }
            }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Localized description"
                )
            }
        },
        scrollBehavior = scrollBehavior
    )
}

@Composable
fun BottomNavigationBar(
    navController: NavHostController, items: List<Screen>
) {
    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        items.forEach { screen ->
            NavigationBarItem(
                icon = { Icon(screen.icon, contentDescription = screen.label) },
                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                onClick = { navController.navigate(screen.route){
                    popUpTo(navController.graph.startDestinationId)
                    launchSingleTop = true
                } },
                label = { Text(screen.label) })
        }
    }
}

@Preview(showBackground = true)
@Composable
fun VinylsAppPreview() {
    RunVinilosApp()
}