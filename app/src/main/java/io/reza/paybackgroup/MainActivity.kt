package io.reza.paybackgroup

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import io.reza.image_finder_domain.repository.PrefsStore
import io.reza.image_finder_presentation.image_detail_screen.ImageDetailScreen
import io.reza.image_finder_presentation.image_detail_screen.ImageDetailScreenViewModel
import io.reza.image_finder_presentation.search_screen.SearchScreen
import io.reza.image_finder_presentation.search_screen.SearchScreenViewModel
import io.reza.paybackgroup.navigation.Route
import io.reza.paybackgroup.ui.theme.PayBackGroupTheme
import javax.inject.Inject

@ExperimentalComposeUiApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var prefsStore: PrefsStore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel: MainViewModel by viewModels()
        viewModel.saveApiKey()

        setContent {
            PayBackGroupTheme {
                val navController = rememberNavController()
                val scaffoldState = rememberScaffoldState()

                Scaffold(
                    scaffoldState = scaffoldState,
                    modifier = Modifier.fillMaxSize()
                ) { paddingValues ->

                    NavHost(
                        modifier = Modifier.padding(paddingValues),
                        navController = navController,
                        startDestination = Route.SEARCH
                    ) {
                        composable(Route.SEARCH) {
                            val searchScreenViewModel: SearchScreenViewModel = hiltViewModel()
                            SearchScreen(
                                viewModel = searchScreenViewModel,
                                onNavigateToDetailScreen = {
                                    navController.navigate(Route.IMAGE_DETAIL + "/$it")
                                }
                            )
                        }
                        composable(
                            route = Route.IMAGE_DETAIL + "/{id}",
                            arguments = listOf(
                                navArgument(name = "id") {
                                    type = NavType.IntType
                                }
                            )
                        ) {
                            val imageDetailScreenViewModel: ImageDetailScreenViewModel =
                                hiltViewModel()
                            ImageDetailScreen(
                                onBackPressed = navController::popBackStack,
                                onOpenUrl = {
                                    val uri = Uri.parse(it)
                                    this@MainActivity.startActivity(
                                        Intent(Intent.ACTION_VIEW, uri)
                                    )
                                },
                                viewModel = imageDetailScreenViewModel
                            )
                        }

                    }

                }

            }
        }
    }
}