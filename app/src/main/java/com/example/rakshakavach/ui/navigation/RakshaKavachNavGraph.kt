package com.example.rakshakavach.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.rakshakavach.ui.screens.ChecklistScreen
import com.example.rakshakavach.ui.screens.HomeScreen
import com.example.rakshakavach.ui.screens.SplashScreen
import com.example.rakshakavach.ui.screens.TaskSelectorScreen

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Home : Screen("home")
    object TaskSelector : Screen("task_selector")
    object Checklist : Screen("checklist/{taskId}") {
        fun createRoute(taskId: String) = "checklist/$taskId"
    }
    object Quiz : Screen("quiz")
    object IncidentLog : Screen("incident_log")
    object Profile : Screen("profile")
    object AIAssistant : Screen("ai_assistant")
}

@Composable
fun RakshaKavachNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = Screen.Splash.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(
                onNavigateToHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
            )
        }
        composable(Screen.Home.route) {
            HomeScreen(
                onNavigateToTaskSelector = { navController.navigate(Screen.TaskSelector.route) },
                onNavigateToQuiz = { navController.navigate(Screen.Quiz.route) },
                onNavigateToIncidentLog = { navController.navigate(Screen.IncidentLog.route) },
                onNavigateToProfile = { navController.navigate(Screen.Profile.route) },
                onNavigateToAIAssistant = { navController.navigate(Screen.AIAssistant.route) }
            )
        }
        composable(Screen.TaskSelector.route) {
            TaskSelectorScreen(
                onTaskSelected = { taskId -> navController.navigate(Screen.Checklist.createRoute(taskId)) },
                onBack = { navController.popBackStack() }
            )
        }
        composable(Screen.Checklist.route) { backStackEntry ->
            val taskId = backStackEntry.arguments?.getString("taskId") ?: return@composable
            ChecklistScreen(
                taskId = taskId,
                onComplete = {
                    navController.popBackStack(Screen.Home.route, inclusive = false)
                }
            )
        }
        composable(Screen.Quiz.route) {
            com.example.rakshakavach.ui.screens.QuizScreen(
                onBack = { navController.popBackStack() }
            )
        }
        composable(Screen.IncidentLog.route) {
            com.example.rakshakavach.ui.screens.IncidentLogScreen(
                onBack = { navController.popBackStack() }
            )
        }
        composable(Screen.Profile.route) {
            com.example.rakshakavach.ui.screens.ProfileScreen(
                onBack = { navController.popBackStack() }
            )
        }
        composable(Screen.AIAssistant.route) {
            com.example.rakshakavach.ui.screens.AIAssistantScreen(
                onBack = { navController.popBackStack() }
            )
        }
    }
}
