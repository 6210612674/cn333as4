package com.example.unitconverter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.unitconverter.screen.DistanceConverter
import com.example.unitconverter.screen.MassConverter
import com.example.unitconverter.screen.Screen
import com.example.unitconverter.screen.TemperatureConverter
import com.example.unitconverter.ui.theme.UnitConverterTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UnitConverter()
        }
    }
}

@Composable
fun UnitConverter() {
    val navController = rememberNavController()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.app_name))
                }
            )
        },
        bottomBar = {
            UnitCovrterBottomBar(navController)
        }
    ) {
        UnitConverterNavHost(navController)
    }
}

@Composable
fun UnitCovrterBottomBar(navController: NavController) {
    val selectedRoute = rememberSaveable { mutableStateOf(Screen.screens.first().route) }
    BottomNavigation {
        Screen.screens.forEach { screen ->
            BottomNavigationItem(
                selected = screen.route == selectedRoute.value,
                onClick = {
                    selectedRoute.value = screen.route
                    navController.navigate(screen.route) {
                        launchSingleTop = true
                        popUpTo(Screen.screens.first().route) {
                            inclusive = true
                        }
                    }
                },
                label = {
                    Text(text = stringResource(id = screen.label))
                },
                icon = {
                    Icon(
                        painter = painterResource(id = screen.icon),
                        contentDescription = stringResource(id = screen.label)
                    )
                },
                alwaysShowLabel = false
            )
        }
    }
}

@Composable
fun UnitConverterNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.screens.first().route
    ) {
        composable(Screen.screens[0].route) {
            TemperatureConverter()
        }
        composable(Screen.screens[1].route) {
            DistanceConverter()
        }
        composable(Screen.screens[2].route) {
            MassConverter()
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    UnitConverterTheme {
        UnitConverter()
    }
}