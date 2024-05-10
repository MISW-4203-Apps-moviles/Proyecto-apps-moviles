package com.miso.vinilos

import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
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
import com.miso.vinilos.ui.screens.AlbumDetailScreenHandler
import com.miso.vinilos.ui.screens.AlbumListScreenHandler
import com.miso.vinilos.ui.screens.ArtistaDetailScreenHandler
import com.miso.vinilos.ui.screens.ArtistaListScreenHandler
import com.miso.vinilos.ui.screens.ArtistasScreen
import com.miso.vinilos.ui.screens.ColeccionistasListScreenHandler
import com.miso.vinilos.ui.screens.ColeccionistasScreen
import com.miso.vinilos.ui.screens.UserTypeSelectionScreen
import com.miso.vinilos.ui.theme.VinylsTheme
import com.miso.vinilos.viewModels.AlbumDetailViewModel
import com.miso.vinilos.viewModels.AlbumDetailViewModelFactory
import com.miso.vinilos.viewModels.AlbumListViewModel
import com.miso.vinilos.viewModels.AlbumListViewModelFactory
import com.miso.vinilos.viewModels.ArtistaDetailViewModel
import com.miso.vinilos.viewModels.ArtistaDetailViewModelFactory
import com.miso.vinilos.viewModels.ArtistasListViewModel
import com.miso.vinilos.viewModels.ArtistasListViewModelFactory
import com.miso.vinilos.viewModels.ColeccionistasListViewModel
import com.miso.vinilos.viewModels.ColeccionistasListViewModelFactory


/**
 * enum values that represent the screens in the app
 */
enum class VinylScreen(val route: String) {
    UserTypeSelection(route = "user_type_selection"),
    AlbumList(route = "album_list"),
    AlbumDetail(route = "album_detail/{albumId}"),
    ColeccionistasList(route = "coleccionistas_list"),
    ColeccionistaDetail(route = "coleccionistas/{collectionId}"),
    ArtistasList(route = "artistas_list"),
    ArtistaDetail(route = "artistas/{performedId}"),
}

/**
 * enum values that represent the screens in the app
 */
enum class VinylTab(
    val icon: ImageVector,
    @StringRes val label: Int
) {
    AlbumTab(icon = Icons.Filled.PlayArrow, label = R.string.nav_albumes_label),
    ColeccionistaTab(icon = Icons.Filled.AccountCircle, label = R.string.nav_coleccionistas_label),
    ArtistaTab(icon = Icons.Filled.Star, label = R.string.nav_artistas_label),
}



@Composable
fun VinylAppBar(
    isVisible: Boolean = false,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AnimatedVisibility(
        visible = isVisible,
        enter = slideInVertically(initialOffsetY = { fullHeight -> -fullHeight }) + fadeIn(),
        exit = slideOutVertically(targetOffsetY = { fullHeight -> -fullHeight }) + fadeOut(),
        content = {
            //CenterAlignedTopAppBar
                TopAppBar(
                    title = {},
                    scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(
                        rememberTopAppBarState()
                    ),
                    modifier = modifier,
                    navigationIcon = {
                        IconButton(onClick = navigateUp) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = stringResource(R.string.regresar_boton_descripcion)
                            )
                        }
                    }
                )
        }
    )
}


@Composable
fun VinylApp(
    navController: NavHostController = rememberNavController()
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    // Get the name of the current screen
    //val currentScreen = backStackEntry?.destination?.route
    //Log.d("DEBUG", "currentScreen: $currentScreen")
    Scaffold(
        topBar = {
            VinylAppBar(
                isVisible = backStackEntry?.destination?.route != VinylScreen.UserTypeSelection.name,
                navigateUp = { navController.navigateUp() }
            )
        },
        bottomBar = {
            BottomNavigationBar(
                isVisible = backStackEntry?.destination?.route != VinylScreen.UserTypeSelection.name,
                navController = navController
            )
        },
        content = { innerPadding -> Navigation(navController, innerPadding) }
    )
}

@Composable
fun BottomNavigationBar(
    isVisible: Boolean = false,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    AnimatedVisibility(
        visible = isVisible,
        enter = slideInVertically(initialOffsetY = { fullHeight -> fullHeight }) + fadeIn(),
        exit = slideOutVertically(targetOffsetY = { fullHeight -> fullHeight }) + fadeOut(),
        content = {
            NavigationBar {
               val currentDestination = navController.currentDestination
                VinylTab.values().forEach { tab ->
                    //Log.d("DEBUG", "tab: ${tab.name}")
                    NavigationBarItem(
                        icon = {
                            Icon(
                                imageVector = tab.icon,
                                contentDescription = stringResource(tab.label),
                            )
                        },
                        modifier = modifier,
                        selected = currentDestination?.hierarchy?.any { it.route == tab.name } == true,
                        onClick = {
                            navController.navigate(tab.name) {
                                popUpTo(navController.graph.startDestinationId)
                                launchSingleTop = true
                            }
                        },
                        label = { Text(text = stringResource(tab.label)) }
                    )
                }
            }
        }
    )
}


@Composable
fun Navigation(
    navController: NavHostController,
    innerPadding: PaddingValues = PaddingValues()
) {
    val tweenSpec: FiniteAnimationSpec<IntOffset> = tween(300, 0, EaseOut)

    NavHost(
        navController = navController,
        startDestination = VinylScreen.UserTypeSelection.name,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
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
        // User type selection screen
        composable(VinylScreen.UserTypeSelection.name) {
            UserTypeSelectionScreen(navController)
        }
        // TABS navigation
        navigation(
            startDestination = VinylScreen.AlbumList.name,
            route = VinylTab.AlbumTab.name
        ) {
            composable(VinylScreen.AlbumList.name) {
                val albumViewModel: AlbumListViewModel =
                    viewModel(factory = AlbumListViewModelFactory())
                //Log.d("DEBUG", "AlbumListScreenHandler")
                //Log.d("DEBUG", VinylScreen.AlbumDetail.name)
                //Log.d("DEBUG", VinylScreen.AlbumList.route.replace("{albumId}", "1"))
                AlbumListScreenHandler(
                    vinylUiState = albumViewModel.vinylUiState,
                    retryAction = albumViewModel::fetchAlbums,
                    navigateToAlbumDetail = { albumId ->
                        navController.navigate(
                            VinylScreen.AlbumDetail.route.replace("{albumId}", albumId.toString()),
                        )
                    },
                    viewModel = albumViewModel,
                    innerPadding = innerPadding,
                )
            }
            composable(
                VinylScreen.AlbumDetail.route,
            ) { backStackEntry ->
                backStackEntry.arguments?.getString("albumId")?.let {
                    val viewModel: AlbumDetailViewModel =
                        viewModel(factory = AlbumDetailViewModelFactory())
                    AlbumDetailScreenHandler(
                        albumId = it,
                        viewModel = viewModel,
                        innerPadding = innerPadding
                    )
                }
            }
        }
        // Collections screen
        navigation(
            startDestination = VinylScreen.ColeccionistasList.name,
            route = VinylTab.ColeccionistaTab.name
        ) {
            composable(VinylScreen.ColeccionistasList.name) {
                val coleccionistasListViewModel: ColeccionistasListViewModel =
                    viewModel(factory = ColeccionistasListViewModelFactory())
                ColeccionistasListScreenHandler(
                    vinylUiState = coleccionistasListViewModel.vinylUiState,
                    retryAction = coleccionistasListViewModel::fetchCollections,
                    navigateToCollectionDetail = { collectionId ->
                        navController.navigate(
                            VinylScreen.ColeccionistaDetail.route.replace("{collectionId}", collectionId.toString()),
                        )
                    },
                    viewModel = coleccionistasListViewModel,
                    innerPadding = innerPadding,
                )
            }
            composable(
                VinylScreen.ColeccionistaDetail.route,
            ) { backStackEntry ->
                backStackEntry.arguments?.getString("collectionId")?.let {
                    val viewModel: ColeccionistasListViewModel =
                        viewModel(factory = ColeccionistasListViewModelFactory())
                    // TODO: Implement this screen
                    ColeccionistasScreen(innerPadding)
                    //AlbumDetailScreenHandler(
                    //    albumId = it,
                    //    viewModel = viewModel,
                    //    innerPadding = innerPadding
                    //)
                }
            }
        }
        // Artists screen
        navigation(
            startDestination = VinylScreen.ArtistasList.name,
            route = VinylTab.ArtistaTab.name
        ) {
            composable(VinylScreen.ArtistasList.name) {
                val artistasListViewModel: ArtistasListViewModel =
                    viewModel(factory = ArtistasListViewModelFactory())
                ArtistaListScreenHandler(
                    vinylUiState = artistasListViewModel.vinylUiState,
                    retryAction = artistasListViewModel::fetchPerformer,
                    navigateToPerformerDetail = { performedId ->
                        navController.navigate(
                            VinylScreen.ArtistaDetail.route.replace("{performedId}", performedId.toString()),
                        )
                    },
                    viewModel = artistasListViewModel,
                    innerPadding = innerPadding,
                )
            }
            composable(
                VinylScreen.ArtistaDetail.route,
            ) { backStackEntry ->
                backStackEntry.arguments?.getString("performedId")?.let {
                    val viewModel: ArtistaDetailViewModel =
                        viewModel(factory = ArtistaDetailViewModelFactory())

                    ArtistaDetailScreenHandler(
                        artistaId =  it,
                        viewModel = viewModel,
                        innerPadding = innerPadding
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun NavigationPreview() {
    VinylsTheme(darkTheme = true) {
        Navigation(rememberNavController())
    }
}