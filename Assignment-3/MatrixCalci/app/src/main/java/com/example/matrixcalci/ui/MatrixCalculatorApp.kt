////package com.example.matrixcalci.ui
////
////import androidx.compose.foundation.layout.padding
////import androidx.compose.material.icons.Icons
////import androidx.compose.material.icons.filled.ArrowBack
////import androidx.compose.material3.AlertDialog
////import androidx.compose.material3.Icon
////import androidx.compose.material3.IconButton
////import androidx.compose.material3.Scaffold
////import androidx.compose.material3.Switch
////import androidx.compose.material3.Text
////import androidx.compose.material3.TextButton
////import androidx.compose.runtime.Composable
////import androidx.compose.runtime.LaunchedEffect
////import androidx.compose.runtime.getValue
////import androidx.compose.runtime.mutableStateOf
////import androidx.compose.runtime.remember
////import androidx.compose.runtime.setValue
////import androidx.compose.ui.Modifier
////import androidx.lifecycle.viewmodel.compose.viewModel
////import androidx.navigation.compose.NavHost
////import androidx.navigation.compose.composable
////import androidx.navigation.compose.rememberNavController
////import com.example.matrixcalci.ui.screens.DimensionScreen
////import com.example.matrixcalci.ui.screens.MatrixInputScreen
////import com.example.matrixcalci.ui.screens.ResultScreen
////import com.example.matrixcalci.ui.theme.MatrixCalciTheme
////import com.example.matrixcalci.viewmodel.MatrixViewModel
////
////
//////@OptIn(ExperimentalMaterial3Api::class)
//////@Composable
//////fun MatrixCalculatorApp() {
//////    val nav = rememberNavController()
//////    val vm: MatrixViewModel = viewModel()
//////    var isDark by remember { mutableStateOf(false) }
//////    var showError by remember { mutableStateOf(false) }
//////
//////    val canPop = nav.previousBackStackEntry != null
//////    // Which route are we on?
//////    val backStack by nav.currentBackStackEntryAsState()
//////    val currentRoute = backStack?.destination?.route
//////    // Only show back on the Input screen
//////    val showBack = currentRoute == "Input"
//////
//////    Scaffold(
//////        topBar = {
//////            SmallTopAppBar(
//////                title = { Text("Matrix Calculator") },
//////                navigationIcon = {
//////                    if (showBack) {
//////                        IconButton(onClick = { nav.navigateUp() }) {
//////                            Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
//////                        }
//////                    }
//////                },
//////                actions = {
//////                    // 2) the toggle switch
//////                    Switch(
//////                        checked = isDark,
//////                        onCheckedChange = { isDark = it },
//////                        thumbContent = if (isDark) {
//////                            { Icon(Icons.Default.DarkMode, null) }
//////                        } else {
//////                            { Icon(Icons.Default.LightMode, null) }
//////                        }
//////                    )
//////                }
//////            )
//////        }
//////    ) { innerPadding ->
//////        MatrixCalciTheme(darkTheme = isDark) {
//////            NavHost(
//////                navController = nav,
//////                startDestination = "Dimensions",
//////                modifier = Modifier.padding(innerPadding)
//////            ) {
//////                composable("Dimensions") {
//////                    DimensionScreen(vm) {
//////                        vm.resizeMatrices()
//////                        nav.navigate("Input")
//////                    }
//////                }
//////                composable("Input") {
//////                    MatrixInputScreen(vm) {
//////                        if (vm.computeResult()) {
//////                            nav.navigate("Result")
//////                        } else {
//////                            showError = true
//////                        }
//////                    }
//////                }
//////                composable("Result") {
//////                    ResultScreen(
//////                        vm,
//////                        onChangeOp = { nav.navigate("Input") },
//////                        onChangeDim = { nav.navigate("Dimensions") }
//////                    )
//////                }
//////            }
//////
//////            if (showError && vm.errorMessage != null) {
//////                AlertDialog(
//////                    onDismissRequest = { showError = false },
//////                    title = { Text("Error") },
//////                    text = { Text(vm.errorMessage!!) },
//////                    confirmButton = {
//////                        TextButton(onClick = { showError = false }) {
//////                            Text("OK")
//////                        }
//////                    }
//////                )
//////            }
//////        }
//////    }
//////}
//////
//////@Composable
//////fun SmallTopAppBar(title: @Composable () -> Unit, navigationIcon: @Composable () -> Unit, actions: @Composable () -> Unit) {
//////    TODO("Not yet implemented")
//////}
////
////
//////@OptIn(ExperimentalMaterial3Api::class)
//////@Composable
//////fun MatrixCalculatorApp() {
//////    val nav = rememberNavController()
//////    val vm: MatrixViewModel = viewModel()
//////    var isDark by remember { mutableStateOf(false) }
//////    var showError by remember { mutableStateOf(false) }
//////    val canPop = nav.previousBackStackEntry != null
//////
//////    Scaffold(
//////        topBar = {
//////            SmallTopAppBar(
//////                title = { Text("Matrix Calculator") },
//////                navigationIcon = {
//////                    if (canPop) {
//////                        IconButton(onClick = { nav.navigateUp() }) {
//////                            Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
//////                        }
//////                    }
//////                },
//////                actions = {
//////                    Switch(checked = isDark, onCheckedChange = { isDark = it })
//////                }
//////            )
//////        }
//////    ) { innerPadding ->
//////        MatrixCalciTheme(darkTheme = isDark) {
//////            NavHost(
//////                navController = nav,
//////                startDestination = "Dimensions",
//////                modifier = Modifier.padding(innerPadding)
//////            ) {
//////                composable("Dimensions") {
//////                    DimensionScreen(vm) {
//////                        vm.resizeMatrices()
//////                        nav.navigate("Input")
//////                    }
//////                }
//////                composable("Input") {
//////                    LaunchedEffect(Unit) { showError = false }
//////                    MatrixInputScreen(vm) {
//////                        if (vm.computeResult()) nav.navigate("Result")
//////                        else showError = true
//////                    }
//////                    if (showError && vm.errorMessage != null) {
//////                        AlertDialog(
//////                            onDismissRequest = { showError = false },
//////                            title = { Text("Error") },
//////                            text = { Text(vm.errorMessage!!) },
//////                            confirmButton = {
//////                                TextButton(onClick = { showError = false }) {
//////                                    Text("OK")
//////                                }
//////                            }
//////                        )
//////                    }
//////                }
//////                composable("Result") {
//////                    ResultScreen(
//////                        vm,
//////                        onChangeOp = { nav.navigate("Input") },
//////                        onChangeDim = { nav.navigate("Dimensions") }
//////                    )
//////                }
//////            }
//////        }
//////    }
//////}
////
////
////@Composable
////fun MatrixCalculatorApp() {
////    val nav = rememberNavController()
////    val vm: MatrixViewModel = viewModel()
////
////    // Light/dark toggle state
////    var isDark by remember { mutableStateOf(false) }
////    // Whether to show error dialog on Input screen
////    var showError by remember { mutableStateOf(false) }
////
////    // Determine if we can navigate back
////    val canPop = nav.previousBackStackEntry != null
////
////    Scaffold(
////        topBar = {
////            SmallTopAppBar(
////                title = { Text("Matrix Calculator") },
////                navigationIcon = {
////                    if (canPop) {
////                        IconButton(onClick = { nav.navigateUp() }) {
////                            Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
////                        }
////                    }
////                },
////                actions = {
////                    Switch(
////                        checked = isDark,
////                        onCheckedChange = { isDark = it }
////                    )
////                }
////            )
////        }
////    ) { innerPadding ->
////        // Wrap your entire NavHost in the theme
////        MatrixCalciTheme(darkTheme = isDark) {
////            NavHost(
////                navController = nav,
////                startDestination = "Dimensions",
////                modifier = Modifier.padding(innerPadding)
////            ) {
////                composable("Dimensions") {
////                    DimensionScreen(vm) {
////                        vm.resizeMatrices()
////                        nav.navigate("Input")
////                    }
////                }
////                composable("Input") {
////                    // Reset dialog flag whenever we enter this screen
////                    LaunchedEffect(Unit) { showError = false }
////
////                    MatrixInputScreen(vm) {
////                        if (vm.computeResult()) {
////                            nav.navigate("Result")
////                        } else {
////                            showError = true
////                        }
////                    }
////
////                    if (showError && vm.errorMessage != null) {
////                        AlertDialog(
////                            onDismissRequest = { showError = false },
////                            title = { Text("Error") },
////                            text = { Text(vm.errorMessage!!) },
////                            confirmButton = {
////                                TextButton(onClick = { showError = false }) {
////                                    Text("OK")
////                                }
////                            }
////                        )
////                    }
////                }
////                composable("Result") {
////                    ResultScreen(
////                        vm,
////                        onChangeOp = { nav.navigate("Input") },
////                        onChangeDim = { nav.navigate("Dimensions") }
////                    )
////                }
////            }
////        }
////    }
////}
////
//////@Composable
//////fun SmallTopAppBar(title: @Composable () -> Unit, navigationIcon: @Composable () -> Unit, actions: @Composable () -> Unit) {
//////    TODO("Not yet implemented")
//////}
//
//
//
//package com.example.matrixcalci.ui
//
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.ArrowBack
//import androidx.compose.material3.AlertDialog
//import androidx.compose.material3.CenterAlignedTopAppBar
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.Icon
//import androidx.compose.material3.IconButton
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.Switch
//import androidx.compose.material3.Text
//import androidx.compose.material3.TextButton
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Modifier
//import androidx.lifecycle.viewmodel.compose.viewModel
//import androidx.navigation.compose.NavHost
//import androidx.navigation.compose.composable
//import androidx.navigation.compose.rememberNavController
//import com.example.matrixcalci.ui.screens.DimensionScreen
//import com.example.matrixcalci.ui.screens.MatrixInputScreen
//import com.example.matrixcalci.ui.screens.ResultScreen
//import com.example.matrixcalci.ui.theme.MatrixCalciTheme
//import com.example.matrixcalci.viewmodel.MatrixViewModel
//
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun MatrixCalculatorApp() {
//    val navController = rememberNavController()
//    val vm: MatrixViewModel = viewModel()
//
//    // Light/dark toggle state
//    var isDark by remember { mutableStateOf(false) }
//    // Error dialog flag
//    var showError by remember { mutableStateOf(false) }
//
//    // Can we navigate back?
//    val canPop = navController.previousBackStackEntry != null
//
//    Scaffold(
//        topBar = {
//            CenterAlignedTopAppBar(
//                title = { Text("Matrix Calculator") },
//                navigationIcon = {
//                    if (canPop) {
//                        IconButton(onClick = { navController.navigateUp() }) {
//                            Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
//                        }
//                    }
//                },
//                actions = {
//                    Switch(
//                        checked = isDark,
//                        onCheckedChange = { isDark = it }
//                    )
//                }
//            )
//        }
//    ) { innerPadding ->
//        MatrixCalciTheme(darkTheme = isDark) {
//            NavHost(
//                navController = navController,
//                startDestination = "Dimensions",
//                modifier = Modifier.padding(innerPadding)
//            ) {
//                composable("Dimensions") {
//                    DimensionScreen(vm) {
//                        vm.resizeMatrices()
//                        navController.navigate("Input")
//                    }
//                }
//                composable("Input") {
//                    LaunchedEffect(Unit) { showError = false }
//
//                    MatrixInputScreen(vm) {
//                        if (vm.computeResult()) {
//                            navController.navigate("Result")
//                        } else {
//                            showError = true
//                        }
//                    }
//
//                    if (showError && vm.errorMessage != null) {
//                        AlertDialog(
//                            onDismissRequest = { showError = false },
//                            title = { Text("Error") },
//                            text = { Text(vm.errorMessage!!) },
//                            confirmButton = {
//                                TextButton(onClick = { showError = false }) {
//                                    Text("OK")
//                                }
//                            }
//                        )
//                    }
//                }
//                composable("Result") {
//                    ResultScreen(
//                        vm,
//                        onChangeOp = { navController.navigate("Input") },
//                        onChangeDim = { navController.navigate("Dimensions") }
//                    )
//                }
//            }
//        }
//    }
//}



package com.example.matrixcalci.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.matrixcalci.ui.screens.DimensionScreen
import com.example.matrixcalci.ui.screens.MatrixInputScreen
import com.example.matrixcalci.ui.screens.ResultScreen
import com.example.matrixcalci.ui.theme.MatrixCalciTheme
import com.example.matrixcalci.viewmodel.MatrixViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MatrixCalculatorApp() {
    val navController = rememberNavController()
    val vm: MatrixViewModel = viewModel()

    // theme toggle state
    var isDark by remember { mutableStateOf(false) }
    // error‐dialog flag for Input
    var showError by remember { mutableStateOf(false) }

    // Watch the current backstack entry
    val backstackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backstackEntry?.destination?.route

    // Only show back on Input or Result
    val showBack = currentRoute == "Input" || currentRoute == "Result"

    MatrixCalciTheme(darkTheme = isDark) {
        Scaffold(
            containerColor = MaterialTheme.colorScheme.background,
            topBar = {
                CenterAlignedTopAppBar(
                    title = { Text("Matrix Calculator") },
                    navigationIcon = {
                        if (showBack) {
                            IconButton(onClick = { navController.navigateUp() }) {
                                Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                            }
                        }
                    },
                    actions = {
                        Switch(
                            checked = isDark,
                            onCheckedChange = { isDark = it }
                        )
                    }
                )
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = "Dimensions",
                modifier = Modifier.padding(innerPadding)
            ) {
                composable("Dimensions") {
                    DimensionScreen(vm) {
                        vm.resizeMatrices()
                        navController.navigate("Input")
                    }
                }
                composable("Input") {
                    LaunchedEffect(Unit) { showError = false }

                    MatrixInputScreen(vm) {
                        if (vm.computeResult()) {
                            navController.navigate("Result")
                        } else {
                            showError = true
                        }
                    }

                    if (showError && vm.errorMessage != null) {
                        AlertDialog(
                            onDismissRequest = { showError = false },
                            title = { Text("Error") },
                            text = { Text(vm.errorMessage!!) },
                            confirmButton = {
                                TextButton(onClick = { showError = false }) {
                                    Text("OK")
                                }
                            }
                        )
                    }
                }
                composable("Result") {
                    ResultScreen(
                        vm,
                        onChangeOp  = { navController.navigate("Input") },
                        onChangeDim = { navController.navigate("Dimensions") }
                    )
                }
            }
        }
    }
}