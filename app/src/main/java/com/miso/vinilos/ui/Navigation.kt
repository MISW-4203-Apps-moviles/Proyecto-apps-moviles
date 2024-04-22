package com.miso.vinilos.ui

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.miso.vinilos.ui.screens.AlbumDetailRoute
import com.miso.vinilos.ui.screens.AlbumListRoute
import com.miso.vinilos.ui.screens.ArtistasScreen
import com.miso.vinilos.ui.screens.ColeccionistasScreen
import com.miso.vinilos.ui.screens.UserTypeSelectionScreen
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


sealed class NavigationItem(
    val route: String,
    val icon: ImageVector? = null,
    val label: String = ""
) {

    data object UserTypeSelection :
        NavigationItem("user_type_selection")

    data object AlbumTab :
        NavigationItem("album_tab", Icons.Filled.PlayArrow, "Albumes")

    data object AlbumList :
        NavigationItem("album_list")

    data object AlbumDetail :
        NavigationItem("album_detail/{albumId}")

    data object ColeccionistaTab :
        NavigationItem("coleccionistas_tab", Icons.Filled.AccountCircle, "Coleccionistas")

    data object Coleccionistas :
        NavigationItem("coleccionistas")

    data object ArtistaTab :
        NavigationItem("artistas_tab", Icons.Filled.Star, "Artistas")

    data object Artistas :
        NavigationItem("artistas")
}

@Composable
fun Navigation(navController: NavHostController, innerPadding: PaddingValues = PaddingValues()) {

    val tweenSpec: FiniteAnimationSpec<IntOffset> = tween(300, 0, EaseOut)

    NavHost(
        navController,
        startDestination = NavigationItem.UserTypeSelection.route,
        enterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Start,
                tweenSpec
            )
        },
        exitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Start,
                tweenSpec
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.End,
                tweenSpec
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.End,
                tweenSpec
            )
        }
    ) {
        composable(NavigationItem.UserTypeSelection.route) { UserTypeSelectionScreen(navController) }

        navigation(
            startDestination = NavigationItem.AlbumList.route,
            route = NavigationItem.AlbumTab.route
        ) {
            composable(NavigationItem.AlbumList.route) {
                val albumViewModel: AlbumListViewModel =
                    viewModel(factory = AlbumListViewModelFactory())

                AlbumListRoute(
                    navigateToAlbumDetail = { albumId ->
                        navController.navigate(
                            replaceRoute(
                                NavigationItem.AlbumDetail.route,
                                "albumId" to albumId.toString(),
                            )
                        )
                    },
                    viewModel = albumViewModel,
                    innerPadding = innerPadding
                )
            }
            composable(
                NavigationItem.AlbumDetail.route,
            ) { backStackEntry ->
                backStackEntry.arguments?.getString("albumId")?.let {
                    val viewModel: AlbumDetailViewModel =
                        viewModel(factory = AlbumDetailViewModelFactory())

                    AlbumDetailRoute(
                        albumId = it,
                        viewModel = viewModel,
                        innerPadding = innerPadding
                    )
                }
            }
        }

        navigation(
            startDestination = NavigationItem.Coleccionistas.route,
            route = NavigationItem.ColeccionistaTab.route
        ) {
            composable(NavigationItem.Coleccionistas.route) {
                ColeccionistasScreen(innerPadding)
            }
        }

        navigation(
            startDestination = NavigationItem.Artistas.route,
            route = NavigationItem.ArtistaTab.route
        ) {
            composable(NavigationItem.Artistas.route) { ArtistasScreen(innerPadding) }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopNavigationBar(navController: NavHostController) {
    val currentDestination by navController.currentBackStackEntryAsState()
    var isVisible by remember { mutableStateOf(true) }

    isVisible = currentDestination?.destination?.route != NavigationItem.UserTypeSelection.route

    AnimatedVisibility(
        visible = isVisible,
        enter = slideInVertically(initialOffsetY = { fullHeight -> -fullHeight }) + fadeIn(),
        exit = slideOutVertically(targetOffsetY = { fullHeight -> -fullHeight }) + fadeOut(),
        content = {
            CenterAlignedTopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigateUp()
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Regresar"
                        )
                    }
                },
                scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(
                    rememberTopAppBarState()
                )
            )
        }
    )
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val currentNavBackStackEntry by navController.currentBackStackEntryAsState()

    var isVisible by remember { mutableStateOf(true) }

    isVisible =
        currentNavBackStackEntry?.destination?.route != NavigationItem.UserTypeSelection.route

    AnimatedVisibility(
        visible = isVisible,
        enter = slideInVertically(initialOffsetY = { fullHeight -> fullHeight }) + fadeIn(),
        exit = slideOutVertically(targetOffsetY = { fullHeight -> fullHeight }) + fadeOut(),
        content = {
            NavigationBar {
                val currentDestination = navController.currentDestination
                val items = listOf(
                    NavigationItem.AlbumTab,
                    NavigationItem.ColeccionistaTab,
                    NavigationItem.ArtistaTab
                )

                items.forEach { navigationItem ->
                    NavigationBarItem(
                        icon = {
                            navigationItem.icon?.let { icon ->
                                Icon(
                                    imageVector = icon,
                                    contentDescription = navigationItem.label
                                )
                            }
                        },
                        selected = currentDestination?.hierarchy?.any { it.route == navigationItem.route } == true,
                        onClick = {
                            navController.navigate(navigationItem.route) {
                                popUpTo(navController.graph.startDestinationId)
                                launchSingleTop = true
                            }
                        },
                        label = { Text(navigationItem.label) }
                    )
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun NavigationPreview() {
    Navigation(rememberNavController())
}