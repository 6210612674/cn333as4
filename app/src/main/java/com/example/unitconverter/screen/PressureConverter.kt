package com.example.unitconverter.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.unitconverter.R
import com.example.unitconverter.viewmodels.PressureViewModel

@Composable
fun PressureConverter() {
    val viewModel: PressureViewModel = viewModel()
    val strBar = stringResource(id = R.string.bar)
    val strPSI = stringResource(id = R.string.psi)
    val currentValue = viewModel.pressure.observeAsState(viewModel.pressure.value ?: "")
    val unit = viewModel.unit.observeAsState(viewModel.unit.value ?: R.string.bar)
    var result by rememberSaveable { mutableStateOf("") }

    val calc = {
        val temp = viewModel.convert()
        result = if (temp.isNaN())
            ""
        else
            "$temp${
                if (unit.value == R.string.bar)
                    strPSI
                else strBar
            }"
    }

    val enabled by remember(currentValue.value) {
        mutableStateOf(!viewModel.getPressureAsFloat().isNaN())
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
            shape = RoundedCornerShape(20.dp),
            color = colorResource(id = R.color.magnolia),
            elevation = 0.dp,
        ) {
            Column(
                modifier = Modifier.padding(15.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                PressureTextField(
                    distance = currentValue,
                    modifier = Modifier.padding(bottom = 16.dp),
                    callback = calc,
                    viewModel = viewModel
                )
                PressureScaleButtonGroup(
                    selected = unit,
                    modifier = Modifier.padding(bottom = 16.dp)
                ) { resId: Int ->
                    viewModel.setUnit(resId)
                }
                Button(
                    onClick = calc,
                    enabled = enabled
                ) {
                    Text(text = stringResource(id = R.string.convert))
                }
            }
        }
        if (result.isNotEmpty()) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp),
                shape = RoundedCornerShape(20.dp),
                color = colorResource(id = R.color.magnolia),

                ) {
                Column(
                    modifier = Modifier.padding(15.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = result,
                        style = MaterialTheme.typography.h3
                    )
                }
            }
        }
    }
}

@Composable
fun PressureTextField(
    distance: State<String>,
    modifier: Modifier = Modifier,
    callback: () -> Unit,
    viewModel: PressureViewModel
) {
    TextField(
        value = distance.value,
        onValueChange = {
            viewModel.setPressure(it)
        },
        placeholder = {
            Text(text = stringResource(id = R.string.placeholder_pressure))
        },
        modifier = modifier,
        keyboardActions = KeyboardActions(onAny = {
            callback()
        }),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ),
        singleLine = true
    )
}

@Composable
fun PressureScaleButtonGroup(
    selected: State<Int>,
    modifier: Modifier = Modifier,
    onClick: (Int) -> Unit
) {
    val sel = selected.value
    Row(modifier = modifier) {
        PressureRadioButton(
            selected = sel == R.string.bar,
            resId = R.string.bar,
            onClick = onClick
        )
        PressureRadioButton(
            selected = sel == R.string.psi,
            resId = R.string.psi,
            onClick = onClick,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}

@Composable
fun PressureRadioButton(
    selected: Boolean,
    resId: Int,
    onClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        RadioButton(
            selected = selected,
            onClick = {
                onClick(resId)
            }
        )
        Text(
            text = stringResource(resId),
            modifier = Modifier
                .padding(start = 8.dp)
        )
    }
}