package com.miso.vinilos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.miso.vinilos.ui.VinylApp
import com.miso.vinilos.ui.theme.VinylsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Save the instance of the MainActivity
        context = this

        enableEdgeToEdge()

        setContent {
            VinylsTheme {
                VinylApp()
            }
        }
    }

    companion object {
        lateinit var context: MainActivity
            private set
    }
}


@Preview(showBackground = true)
@Composable
fun VinylsAppPreview() {
    VinylsTheme(darkTheme = true) {
        VinylApp()
    }
}