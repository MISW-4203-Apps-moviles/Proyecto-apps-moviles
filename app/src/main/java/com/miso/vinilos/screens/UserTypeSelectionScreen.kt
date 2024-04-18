package com.miso.vinilos.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.miso.vinilos.R
import com.miso.vinilos.Screen
import com.miso.vinilos.ui.theme.VinilosTheme

@Composable
fun UserTypeSelectionScreen(navController: NavHostController, innerPadding: PaddingValues = PaddingValues()) {
    VinilosTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.logo),
                    contentDescription = "Vinilos logo",
                    modifier = Modifier.size(250.dp)
                )

                Text(
                    text = "Seleccione el tipo de usuario",
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(32.dp))
                Row{
                    Button(
                        onClick = {
                            navController.navigate(Screen.AlbumTab.route) {
                                popUpTo(navController.graph.startDestinationId)
                                launchSingleTop = true
                            }
                        },
                        modifier = Modifier.padding(start = 32.dp, end = 16.dp).weight(1f).fillMaxWidth()
                    ) {
                        Text("Visitante")
                    }
                    Button(
                        onClick = {
                            navController.navigate(Screen.AlbumTab.route) {
                                popUpTo(navController.graph.startDestinationId)
                                launchSingleTop = true
                            }
                        },
                        modifier = Modifier.padding(start = 16.dp, end = 32.dp).weight(1f).fillMaxWidth()
                    ) {
                        Text("Coleccionista")
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun InitialScreenPreview() {
    VinilosTheme {
        UserTypeSelectionScreen(rememberNavController())
    }
}