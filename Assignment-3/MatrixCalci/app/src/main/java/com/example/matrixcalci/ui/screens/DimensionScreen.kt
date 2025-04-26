package com.example.matrixcalci.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.matrixcalci.viewmodel.MatrixViewModel
import com.example.matrixcalci.viewmodel.Operation


//@Composable
//fun DimensionScreen(vm: MatrixViewModel, onContinue: ()->Unit) {
//    // Local text‐field state
//    var aR by remember { mutableStateOf(vm.aRows.toString()) }
//    var aC by remember { mutableStateOf(vm.aCols.toString()) }
//    var bR by remember { mutableStateOf(vm.bRows.toString()) }
//    var bC by remember { mutableStateOf(vm.bCols.toString()) }
//
//    // Holds the attempted dims when invalid
//    var invalidDims by remember { mutableStateOf<Quad?>(null) }
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .verticalScroll(rememberScrollState())
//            .padding(16.dp),
//        verticalArrangement = Arrangement.spacedBy(12.dp)
//    ) {
//        Text("Enter dimensions", style = MaterialTheme.typography.headlineSmall)
//
//        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
//            OutlinedTextField(
//                value = aR, onValueChange = { aR = it },
//                label = { Text("A rows") },
//                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
//                modifier = Modifier.weight(1f)
//            )
//            OutlinedTextField(
//                value = aC, onValueChange = { aC = it },
//                label = { Text("A cols") },
//                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
//                modifier = Modifier.weight(1f)
//            )
//        }
//
//        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
//            OutlinedTextField(
//                value = bR, onValueChange = { bR = it },
//                label = { Text("B rows") },
//                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
//                modifier = Modifier.weight(1f)
//            )
//            OutlinedTextField(
//                value = bC, onValueChange = { bC = it },
//                label = { Text("B cols") },
//                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
//                modifier = Modifier.weight(1f)
//            )
//        }
//
//        Spacer(Modifier.height(16.dp))
//
//        Button(
//            onClick = {
//                // parse user inputs
//                val ar = aR.toIntOrNull() ?: 0
//                val ac = aC.toIntOrNull() ?: 0
//                val br = bR.toIntOrNull() ?: 0
//                val bc = bC.toIntOrNull() ?: 0
//
//                // 1) Reject zero or negative dims immediately
//                if (ar <= 0 || ac <= 0 || br <= 0 || bc <= 0) {
//                    invalidDims = Quad(ar, ac, br, bc)
//                    return@Button
//                }
//
//                // determine which ops would be valid on these dims
//                val prospective = buildList<Operation> {
//                    if (ar == br && ac == bc) {
//                        add(Operation.ADD); add(Operation.SUB)
//                    }
//                    if (ac == br) {
//                        add(Operation.MUL)
//                        if (br == bc) add(Operation.DIV)
//                    }
//                }
//
//                if (prospective.isNotEmpty()) {
//                    // commit dims and go next
//                    vm.aRows = ar; vm.aCols = ac
//                    vm.bRows = br; vm.bCols = bc
//                    invalidDims = null
//                    onContinue()
//                } else {
//                    // show invalid alert using these attempted dims
//                    invalidDims = Quad(ar, ac, br, bc)
//                }
//            },
//            modifier = Modifier.align(Alignment.End)
//        ) {
//            Text("Continue")
//        }
//    }
//
//    // Alert for invalid dims
//    // Alert for invalid dims (including zero/negative)
//    invalidDims?.let { (ar, ac, br, bc) ->
//        AlertDialog(
//            onDismissRequest = { invalidDims = null },
//            title = { Text("Invalid Dimensions") },
//            text = {
//                Text(
//                    when {
//                        ar <= 0 || ac <= 0 || br <= 0 || bc <= 0 ->
//                            "All dimensions must be at least 1 (you entered A: ${ar}×${ac}, B: ${br}×${bc})."
//                        else ->
//                            "No valid operation can be performed on A (${ar}×${ac}) and B (${br}×${bc})."
//                    }
//                )
//            },
//            confirmButton = {
//                TextButton(onClick = { invalidDims = null }) {
//                    Text("OK")
//                }
//            }
//        )
//    }
//}
//
//// Simple data class to hold four ints
//private data class Quad(val aR: Int, val aC: Int, val bR: Int, val bC: Int)

@Composable
fun DimensionScreen(
    vm: MatrixViewModel,
    onContinue: () -> Unit
) {
    // Backing state for each field + its error
    var aR by remember { mutableStateOf(vm.aRows.toString()) }
    var aC by remember { mutableStateOf(vm.aCols.toString()) }
    var bR by remember { mutableStateOf(vm.bRows.toString()) }
    var bC by remember { mutableStateOf(vm.bCols.toString()) }

    var aRError by remember { mutableStateOf<String?>(null) }
    var aCError by remember { mutableStateOf<String?>(null) }
    var bRError by remember { mutableStateOf<String?>(null) }
    var bCError by remember { mutableStateOf<String?>(null) }

    // Dialog for “no-op” dims
    var invalidDims by remember { mutableStateOf<Quad?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            "Enter Matrix Dimensions",
            style = MaterialTheme.typography.headlineMedium
        )

        // Card for Matrix A dims
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text("Matrix A", style = MaterialTheme.typography.titleMedium)
                DimensionRow(
                    label1 = "Rows", value1 = aR, error1 = aRError, onValueChange1 = { new ->
                        aR = new
                        aRError = validateDim(new)
                    },
                    label2 = "Cols", value2 = aC, error2 = aCError, onValueChange2 = { new ->
                        aC = new
                        aCError = validateDim(new)
                    }
                )
            }
        }

        // Card for Matrix B dims
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text("Matrix B", style = MaterialTheme.typography.titleMedium)
                DimensionRow(
                    label1 = "Rows", value1 = bR, error1 = bRError, onValueChange1 = { new ->
                        bR = new
                        bRError = validateDim(new)
                    },
                    label2 = "Cols", value2 = bC, error2 = bCError, onValueChange2 = { new ->
                        bC = new
                        bCError = validateDim(new)
                    }
                )
            }
        }

        Spacer(Modifier.height(8.dp))
        val hasFieldErrors = listOf(aRError, aCError, bRError, bCError).any { it != null }
        Button(
            onClick = {
                // if any field errors, do nothing
                if (listOf(aRError, aCError, bRError, bCError).any { it != null }) return@Button

                // parse
                val ar = aR.toIntOrNull() ?: 0
                val ac = aC.toIntOrNull() ?: 0
                val br = bR.toIntOrNull() ?: 0
                val bc = bC.toIntOrNull() ?: 0

                // zero check
                if (ar <= 0 || ac <= 0 || br <= 0 || bc <= 0) {
                    invalidDims = Quad(ar, ac, br, bc)
                    return@Button
                }

                // prospective ops
                val ops = buildList {
                    if (ar == br && ac == bc) add(Operation.ADD).also { add(Operation.SUB) }
                    if (ac == br) {
                        add(Operation.MUL)
                        if (br == bc) add(Operation.DIV)
                    }
                }
                if (ops.isEmpty()) {
                    invalidDims = Quad(ar, ac, br, bc)
                } else {
                    vm.aRows = ar; vm.aCols = ac
                    vm.bRows = br; vm.bCols = bc
                    invalidDims = null
                    onContinue()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            enabled = !hasFieldErrors
        ) {
            Text("Continue")
        }
    }

    // Invalid‐dims dialog
    invalidDims?.let { (ar, ac, br, bc) ->
        AlertDialog(
            onDismissRequest = { invalidDims = null },
            title = { Text("Invalid Dimensions") },
            text = {
                Text(
                    if (ar <= 0 || ac <= 0 || br <= 0 || bc <= 0)
                        "All dims must be ≥1 (you entered A ${ar}×${ac}, B ${br}×${bc})."
                    else
                        "No operation possible for A ${ar}×${ac} and B ${br}×${bc}."
                )
            },
            confirmButton = {
                TextButton(onClick = { invalidDims = null }) {
                    Text("OK")
                }
            }
        )
    }
}

@Composable
private fun DimensionRow(
    label1: String, value1: String, error1: String?, onValueChange1: (String) -> Unit,
    label2: String, value2: String, error2: String?, onValueChange2: (String) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        DimensionField(
            label = label1,
            value = value1,
            error = error1,
            onValueChange = onValueChange1,
            modifier = Modifier.weight(1f)
        )
        DimensionField(
            label = label2,
            value = value2,
            error = error2,
            onValueChange = onValueChange2,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun DimensionField(
    label: String,
    value: String,
    error: String?,
    onValueChange: (String) -> Unit,
    modifier: Modifier
) {
    Column(modifier = modifier) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(label) },
            isError = error != null,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        error?.let {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 8.dp, top = 4.dp)
            )
        }
    }
}

private fun validateDim(input: String): String? {
    if (input.any { !it.isDigit() }) return "Invalid input"
    val n = input.toIntOrNull() ?: return null
    return if (n > 999) "Too big (max 999)" else null
}

private data class Quad(val aR: Int, val aC: Int, val bR: Int, val bC: Int)







