package com.example.unitconverter.screen

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.unitconverter.R

sealed class Screen(
    val route: String,
    @StringRes val label: Int,
    @DrawableRes val icon: Int
) {
    companion object {
        val screens = listOf(Temperature, Distances, Mass, Pressure)
    }

    private object Temperature: Screen(
        "temperature",
        R.string.temperature,
        R.drawable.outline_thermostat_24
    )

    private object Distances: Screen(
        "distances",
        R.string.distances,
        R.drawable.outline_square_foot_24
    )

    private object Mass: Screen(
        "Mass",
        R.string.mass,
        R.drawable.outline_monitor_weight_24
    )

    private object Pressure: Screen(
        "Pressure",
        R.string.pressure,
        R.drawable.outline_speed_24
    )
}
