package com.sinamtd.sixappcompose.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sinamtd.sixappcompose.presentation.screen.ChartScreen
import com.sinamtd.sixappcompose.presentation.screen.DetailScreen
import com.sinamtd.sixappcompose.presentation.screen.WeatherScreen

@Composable
fun SetUpNavigation(){
    val navController=rememberNavController()
    NavHost(navController=navController,startDestination= Screens.Main.rout) {


        composable(route = Screens.Main.rout) { WeatherScreen(navController = navController) }

        composable(route = Screens.Details.rout) { DetailScreen(navController = navController) }

        composable(route = Screens.Charts.rout) { ChartScreen(navController = navController) }





    }

}