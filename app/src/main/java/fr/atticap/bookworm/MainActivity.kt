package fr.atticap.bookworm

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import fr.atticap.bookworm.ui.features.bookshelf.BookshelvesRoute
import fr.atticap.bookworm.ui.features.bookshelf.BookshelvesScreen
import fr.atticap.bookworm.ui.features.bookshelf.BookshelvesWallRoute
import fr.atticap.bookworm.ui.features.bookshelf.BookshelvesWallScreen
import fr.atticap.bookworm.ui.features.volume.VolumeRoute
import fr.atticap.bookworm.ui.features.volume.VolumeScreen
import fr.atticap.bookworm.ui.theme.BookwormTheme
import org.koin.androidx.compose.KoinAndroidContext

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            val navController: NavHostController = rememberNavController()
            KoinAndroidContext {
                BookwormTheme {
                    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                        NavHost(
                            modifier = Modifier
                                .padding(innerPadding)
                                .fillMaxSize(),
                            navController = navController,
                            startDestination = BookshelvesRoute
                        ) {
                            composable<BookshelvesRoute>(
                                enterTransition = {
                                    when (initialState.destination.route) {
                                        BookshelvesWallRoute.javaClass.canonicalName -> scaleIn()
                                        else -> fadeIn()
                                    }
                                },
                                exitTransition = {
                                    when (targetState.destination.route) {
                                        BookshelvesWallRoute.javaClass.canonicalName -> scaleOut()
                                        else -> fadeOut()
                                    }
                                }
                            ) {
                                BookshelvesScreen(
                                    onNavigateVolume = {
                                        navController.navigate(VolumeRoute(it.id.toString()))
                                    },
                                    onZoomOut = {
                                        navController.navigate(BookshelvesWallRoute)
                                    }
                                )
                            }

                            composable<BookshelvesWallRoute> {
                                BookshelvesWallScreen(
                                    onZoomIn = {
                                        navController.navigate(BookshelvesRoute)
                                    }
                                )
                            }

                            composable<VolumeRoute> {
                                VolumeScreen()
                            }
                        }
                    }
                }
            }

        }
    }
}
