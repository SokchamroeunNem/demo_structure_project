package com.example.demo.graphs
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.demo.presentation.ScreenContent
import com.example.demo.presentation.components.utils.BottomBarScreen
import com.example.demo.presentation.postscreen.PostScreen

@Composable
fun MainNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        route = Graph.MAIN_SCREEN_PAGE,
        startDestination = BottomBarScreen.Home.route
    ) {
        composable(route = BottomBarScreen.Home.route) {
            PostScreen(navController = navController)
        }
        composable(route = BottomBarScreen.Profile.route) {
            ScreenContent(
                name = BottomBarScreen.Profile.route,
                onClick = {
                    navController.navigate(Graph.DETAILS)
                }
            )
        }
        composable(route = BottomBarScreen.Settings.route) {
            ScreenContent(
                name = BottomBarScreen.Settings.route,
                onClick = {
                    navController.navigate(Graph.DETAILS)
                }
            )
        }
        detailsNavGraph(navController = navController)

        // Authentication Navigation Graph
        authNavGraph(navController = navController)
    }
}

fun NavGraphBuilder.detailsNavGraph(navController: NavHostController) {
    navigation(
        route = Graph.DETAILS,
        startDestination = DetailsScreen.BTM_DETAIL_PAGE.route
    ) {
        composable(route = DetailsScreen.BTM_DETAIL_PAGE.route) {
            ScreenContent(name = "Detail Page") {
                navController.navigate(DetailsScreen.BTM_SUB_DETAILS_PAGE.route)
            }
        }
        composable(route = DetailsScreen.BTM_SUB_DETAILS_PAGE.route) {
            ScreenContent(name = "Sub Detail Page") {}
        }
    }
}

sealed class DetailsScreen(val route: String) {
    object BTM_DETAIL_PAGE : DetailsScreen(route = "DETAIL_PAGE_")
    object BTM_SUB_DETAILS_PAGE : DetailsScreen(route = "DETAIL_PAGE_SUB")
}
