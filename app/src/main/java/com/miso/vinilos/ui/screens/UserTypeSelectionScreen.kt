package com.miso.vinilos.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.miso.vinilos.R
import com.miso.vinilos.ui.NavigationItem
import com.miso.vinilos.ui.theme.VinilosTheme

@Composable
fun UserTypeSelectionScreen(
    navController: NavHostController,
    innerPadding: PaddingValues = PaddingValues()
) {
    VinilosTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center,
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.logo),
                    contentDescription = stringResource(R.string.vinilos_logo_descripcion),
                    modifier = Modifier.size(250.dp)
                )

                Text(
                    text = stringResource(R.string.tipo_usuario_titulo),
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(32.dp))
                Row {
                    Button(
                        onClick = {
                            navigateToAlbumTab(navController)
                        },
                        modifier = Modifier
                            .padding(start = 32.dp, end = 16.dp)
                            .weight(1f)
                            .fillMaxWidth()
                    ) {
                        Text(stringResource(R.string.tipo_usuario_visitante))
                    }
                    Button(
                        onClick = {
                            navigateToAlbumTab(navController)
                        },
                        modifier = Modifier
                            .padding(start = 16.dp, end = 32.dp)
                            .weight(1f)
                            .fillMaxWidth()
                    ) {
                        Text(stringResource(R.string.tipo_usuario_coleccionista))
                    }
                }
            }
        }
    }
}


private fun navigateToAlbumTab(navController: NavHostController) {
    navController.navigate(NavigationItem.AlbumTab.route) {
        popUpTo(navController.graph.startDestinationId)
        launchSingleTop = true
    }
}

@Preview(showBackground = true, backgroundColor = 0)
@Composable
fun UserTypeSelectionScreenPreview() {
    VinilosTheme {
        UserTypeSelectionScreen(rememberNavController())
    }
}