package com.example.matrixcalci.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.matrixcalci.viewmodel.MatrixViewModel
import com.example.matrixcalci.viewmodel.Operation

//@Composable
//fun MatrixInputScreen(vm: MatrixViewModel, onContinue: () -> Unit) {
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .verticalScroll(rememberScrollState())
//            .padding(16.dp)
//    ) {
//        // Matrix A
//        Text("Enter Matrix A", style = MaterialTheme.typography.headlineSmall)
//        MatrixWithHeaders(vm.matrixA) { i, j, v -> vm.matrixA[i][j] = v }
//
//        Spacer(Modifier.height(16.dp))
//
//        // Matrix B
//        Text("Enter Matrix B", style = MaterialTheme.typography.headlineSmall)
//        MatrixWithHeaders(vm.matrixB) { i, j, v -> vm.matrixB[i][j] = v }
//
//        Spacer(Modifier.height(16.dp))
//
//        // Determine valid operations
//        val validOps = remember(vm.aRows, vm.aCols, vm.bRows, vm.bCols) {
//            buildList<Operation> {
//                // Add & Sub require identical dims
//                if (vm.aRows == vm.bRows && vm.aCols == vm.bCols) {
//                    add(Operation.ADD)
//                    add(Operation.SUB)
//                }
//                // Mul requires A.cols == B.rows
//                if (vm.aCols == vm.bRows) {
//                    add(Operation.MUL)
//                    // Div optionally if B is square
//                    if (vm.bRows == vm.bCols) {
//                        add(Operation.DIV)
//                    }
//                }
//            }
//        }
//
//        // If current selection becomes invalid, reset to first valid
//        LaunchedEffect(validOps) {
//            if (vm.operation !in validOps && validOps.isNotEmpty()) {
//                vm.operation = validOps.first()
//            }
//        }
//
//        // Operation dropdown
//        Text("Operation", style = MaterialTheme.typography.titleMedium)
//        var expanded by remember { mutableStateOf(false) }
//        Box {
//            TextField(
//                value = vm.operation.name,
//                onValueChange = { },
//                readOnly = true,
//                enabled = validOps.isNotEmpty(),
//                trailingIcon = {
//                    IconButton(onClick = { expanded = true }, enabled = validOps.isNotEmpty()) {
//                        Icon(Icons.Filled.ArrowDropDown, contentDescription = null)
//                    }
//                },
//                modifier = Modifier.fillMaxWidth()
//            )
//            DropdownMenu(
//                expanded = expanded,
//                onDismissRequest = { expanded = false }
//            ) {
//                validOps.forEach { op ->
//                    DropdownMenuItem(
//                        text = { Text(op.name) },
//                        onClick = {
//                            vm.operation = op
//                            expanded = false
//                        }
//                    )
//                }
//            }
//        }
//
//        // Push compute button to bottom
//        Spacer(modifier = Modifier.weight(1f))
//
//        // Full‑width Compute
//        Button(
//            onClick = onContinue,
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(vertical = 8.dp)
//        ) {
//            Text("Compute")
//        }
//    }
//}
//
//@Composable
//private fun MatrixWithHeaders(
//    matrix: List<DoubleArray>,
//    onCellChange: (row: Int, col: Int, value: Double) -> Unit
//) {
//    val rowCount = matrix.size
//    val colCount = matrix.firstOrNull()?.size ?: 0
//    val scrollState = rememberScrollState()
//
//    Column {
//        // Column headers scroll in sync
//        Row(
//            horizontalArrangement = Arrangement.spacedBy(4.dp),
//            modifier = Modifier.horizontalScroll(scrollState)
//        ) {
//            Spacer(Modifier.width(40.dp))
//            for (j in 0 until colCount) {
//                Text(
//                    text = "C${j + 1}",
//                    modifier = Modifier
//                        .width(60.dp)
//                        .padding(2.dp),
//                    style = MaterialTheme.typography.bodySmall
//                )
//            }
//        }
//
//        // Data rows
//        for (i in 0 until rowCount) {
//            val row = matrix[i]
//            Row(
//                horizontalArrangement = Arrangement.spacedBy(4.dp),
//                verticalAlignment = Alignment.CenterVertically,
//                modifier = Modifier.horizontalScroll(scrollState)
//            ) {
//                // Row header
//                Text(
//                    text = "R${i + 1}",
//                    modifier = Modifier
//                        .width(40.dp)
//                        .padding(2.dp),
//                    style = MaterialTheme.typography.bodySmall
//                )
//
//                // Cells
//                for (j in 0 until colCount) {
//                    var text by remember { mutableStateOf(row[j].toInt().toString()) }
//                    var hasFocus by remember { mutableStateOf(false) }
//
//                    OutlinedTextField(
//                        value = text,
//                        onValueChange = { new ->
//                            val filtered = new.filter { it.isDigit() }
//                            val num = filtered.toIntOrNull() ?: 0
//                            if (filtered.isEmpty() || num in 0..999) {
//                                text = filtered
//                                onCellChange(i, j, num.toDouble())
//                            }
//                        },
//                        modifier = Modifier
//                            .width(60.dp)
//                            .height(60.dp)
//                            .onFocusChanged { fs ->
//                                if (fs.isFocused) {
//                                    if (!hasFocus && text == "0") text = ""
//                                    hasFocus = true
//                                } else {
//                                    if (text.isEmpty()) {
//                                        text = "0"
//                                        onCellChange(i, j, 0.0)
//                                    }
//                                    hasFocus = false
//                                }
//                            },
//                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
//                        singleLine = true
//                    )
//                }
//            }
//        }
//    }
//}

//
//@Composable
//fun MatrixInputScreen(vm: MatrixViewModel, onContinue: () -> Unit) {
//    val vertScroll = rememberScrollState()
//    val aScroll   = rememberScrollState()
//    val bScroll   = rememberScrollState()
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .verticalScroll(vertScroll)
//            .padding(16.dp),
//        verticalArrangement = Arrangement.spacedBy(24.dp)
//    ) {
//        // --- Matrix A ---
//        Text("Enter Matrix A", style = MaterialTheme.typography.headlineSmall)
//        Box(modifier = Modifier.horizontalScroll(aScroll)) {
//            Column {
//                // column headers
//                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
//                    Spacer(Modifier.width(40.dp))
//                    repeat(vm.aCols) { j ->
//                        Text(
//                            "C${j+1}",
//                            modifier = Modifier.width(60.dp).padding(2.dp),
//                            style = MaterialTheme.typography.bodySmall
//                        )
//                    }
//                }
//                Spacer(Modifier.height(8.dp))
//                // cells
//                repeat(vm.aRows) { i ->
//                    Row(
//                        verticalAlignment = Alignment.CenterVertically,
//                        horizontalArrangement = Arrangement.spacedBy(4.dp),
//                        modifier = Modifier.fillMaxWidth()
//                    ) {
//                        Text(
//                            "R${i+1}",
//                            modifier = Modifier.width(40.dp).padding(2.dp),
//                            style = MaterialTheme.typography.bodySmall
//                        )
//                        repeat(vm.aCols) { j ->
//                            MatrixCell(
//                                value = vm.matrixA[i][j].toInt(),
//                                onValueChange = { vm.matrixA[i][j] = it.toDouble() }
//                            )
//                        }
//                    }
//                    Spacer(Modifier.height(4.dp))
//                }
//            }
//        }
//
//        // --- Matrix B ---
//        Text("Enter Matrix B", style = MaterialTheme.typography.headlineSmall)
//        Box(modifier = Modifier.horizontalScroll(bScroll)) {
//            Column {
//                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
//                    Spacer(Modifier.width(40.dp))
//                    repeat(vm.bCols) { j ->
//                        Text(
//                            "C${j+1}",
//                            modifier = Modifier.width(60.dp).padding(2.dp),
//                            style = MaterialTheme.typography.bodySmall
//                        )
//                    }
//                }
//                Spacer(Modifier.height(8.dp))
//                repeat(vm.bRows) { i ->
//                    Row(
//                        verticalAlignment = Alignment.CenterVertically,
//                        horizontalArrangement = Arrangement.spacedBy(4.dp),
//                        modifier = Modifier.fillMaxWidth()
//                    ) {
//                        Text(
//                            "R${i+1}",
//                            modifier = Modifier.width(40.dp).padding(2.dp),
//                            style = MaterialTheme.typography.bodySmall
//                        )
//                        repeat(vm.bCols) { j ->
//                            MatrixCell(
//                                value = vm.matrixB[i][j].toInt(),
//                                onValueChange = { vm.matrixB[i][j] = it.toDouble() }
//                            )
//                        }
//                    }
//                    Spacer(Modifier.height(4.dp))
//                }
//            }
//        }
//
//        // --- Operation dropdown ---
//        Text("Operation", style = MaterialTheme.typography.titleMedium)
//        OperationDropdown(vm)
//
//        // --- Compute button ---
//        Spacer(Modifier.height(16.dp))
//        Button(
//            onClick = onContinue,
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(48.dp)
//        ) {
//            Text("Compute")
//        }
//    }
//}
//
//@Composable
//private fun MatrixCell(value: Int, onValueChange: (Int) -> Unit) {
//    var text by remember { mutableStateOf(value.toString()) }
//    var hasFocus by remember { mutableStateOf(false) }
//
//    OutlinedTextField(
//        value = text,
//        onValueChange = { raw ->
//            val filtered = raw.filter { it.isDigit() }
//            val num = filtered.toIntOrNull() ?: 0
//            if (filtered.isEmpty() || num in 0..999) {
//                text = filtered
//                onValueChange(num)
//            }
//        },
//        singleLine = true,
//        modifier = Modifier
//            .width(60.dp)
//            .height(60.dp)
//            .onFocusChanged { fs ->
//                if (fs.isFocused && !hasFocus && text == "0") text = ""
//                if (!fs.isFocused && text.isEmpty()) {
//                    text = "0"
//                    onValueChange(0)
//                }
//                hasFocus = fs.isFocused
//            },
//        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
//    )
//}
//
//@Composable
//private fun OperationDropdown(vm: MatrixViewModel) {
//    val validOps = remember(vm.aRows, vm.aCols, vm.bRows, vm.bCols) {
//        buildList {
//            if (vm.aRows == vm.bRows && vm.aCols == vm.bCols) {
//                add(Operation.ADD); add(Operation.SUB)
//            }
//            if (vm.aCols == vm.bRows) {
//                add(Operation.MUL)
//                if (vm.bRows == vm.bCols) add(Operation.DIV)
//            }
//        }
//    }
//
//    LaunchedEffect(validOps) {
//        if (vm.operation !in validOps && validOps.isNotEmpty()) {
//            vm.operation = validOps.first()
//        }
//    }
//
//    var expanded by remember { mutableStateOf(false) }
//    Box {
//        TextField(
//            value = vm.operation.name,
//            onValueChange = {},
//            readOnly = true,
//            enabled = validOps.isNotEmpty(),
//            modifier = Modifier.fillMaxWidth(),
//            trailingIcon = {
//                IconButton(onClick = { expanded = true }, enabled = validOps.isNotEmpty()) {
//                    Icon(Icons.Filled.ArrowDropDown, contentDescription = "Select")
//                }
//            }
//        )
//        DropdownMenu(
//            expanded = expanded,
//            onDismissRequest = { expanded = false }
//        ) {
//            validOps.forEach { op ->
//                DropdownMenuItem(
//                    text = { Text(op.name) },
//                    onClick = {
//                        vm.operation = op
//                        expanded = false
//                    }
//                )
//            }
//        }
//    }
//}

//@Composable
//fun MatrixInputScreen(vm: MatrixViewModel, onContinue: () -> Unit) {
//    // scroll state for the entire screen
//    val screenScroll = rememberScrollState()
//    // independent horizontal scrolls
//    val aScroll = rememberScrollState()
//    val bScroll = rememberScrollState()
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .verticalScroll(screenScroll)
//            .padding(16.dp),
//        verticalArrangement = Arrangement.spacedBy(24.dp)
//    ) {
//        // --- Matrix A ---
//        Text("Enter Matrix A", style = MaterialTheme.typography.headlineSmall)
//        Card(
//            shape = RoundedCornerShape(8.dp),
//            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(280.dp)
//        ) {
//            Column(Modifier.fillMaxSize().padding(8.dp)) {
//                // Column headers (plain horizontal scroll)
//                Row(
//                    Modifier
//                        .wrapContentWidth()
//                        .horizontalScroll(aScroll),
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    Spacer(Modifier.width(40.dp))
//                    repeat(vm.aCols) { j ->
//                        Text(
//                            "C${j + 1}",
//                            modifier = Modifier.width(60.dp).padding(2.dp),
//                            style = MaterialTheme.typography.bodySmall
//                        )
//                    }
//                }
//
//                Spacer(Modifier.height(8.dp))
//
//                // Rows (lazy vertical)
//                LazyColumn(
//                    modifier = Modifier.fillMaxSize(),
//                    verticalArrangement = Arrangement.spacedBy(4.dp)
//                ) {
//                    items(vm.aRows) { i ->
//                        Row(
//                            Modifier
//                                .fillMaxWidth()
//                                .horizontalScroll(aScroll),
//                            verticalAlignment = Alignment.CenterVertically,
//                            horizontalArrangement = Arrangement.spacedBy(4.dp)
//                        ) {
//                            Text(
//                                "R${i + 1}",
//                                modifier = Modifier.width(40.dp).padding(2.dp),
//                                style = MaterialTheme.typography.bodySmall
//                            )
//                            repeat(vm.aCols) { j ->
//                                MatrixCell(
//                                    value = vm.matrixA[i][j].toInt(),
//                                    onValueChange = { vm.matrixA[i][j] = it.toDouble() }
//                                )
//                            }
//                        }
//                    }
//                }
//            }
//        }
//
//        // --- Matrix B ---
//        Text("Enter Matrix B", style = MaterialTheme.typography.headlineSmall)
//        Card(
//            shape = RoundedCornerShape(8.dp),
//            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(280.dp)
//        ) {
//            Column(Modifier.fillMaxSize().padding(8.dp)) {
//                Row(
//                    Modifier
//                        .wrapContentWidth()
//                        .horizontalScroll(bScroll),
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    Spacer(Modifier.width(40.dp))
//                    repeat(vm.bCols) { j ->
//                        Text(
//                            "C${j + 1}",
//                            modifier = Modifier.width(60.dp).padding(2.dp),
//                            style = MaterialTheme.typography.bodySmall
//                        )
//                    }
//                }
//
//                Spacer(Modifier.height(8.dp))
//
//                LazyColumn(
//                    modifier = Modifier.fillMaxSize(),
//                    verticalArrangement = Arrangement.spacedBy(4.dp)
//                ) {
//                    items(vm.bRows) { i ->
//                        Row(
//                            Modifier
//                                .fillMaxWidth()
//                                .horizontalScroll(bScroll),
//                            verticalAlignment = Alignment.CenterVertically,
//                            horizontalArrangement = Arrangement.spacedBy(4.dp)
//                        ) {
//                            Text(
//                                "R${i + 1}",
//                                modifier = Modifier.width(40.dp).padding(2.dp),
//                                style = MaterialTheme.typography.bodySmall
//                            )
//                            repeat(vm.bCols) { j ->
//                                MatrixCell(
//                                    value = vm.matrixB[i][j].toInt(),
//                                    onValueChange = { vm.matrixB[i][j] = it.toDouble() }
//                                )
//                            }
//                        }
//                    }
//                }
//            }
//        }
//
//        // --- Operation & Compute ---
//        Text("Operation", style = MaterialTheme.typography.titleMedium)
//        OperationDropdown(vm)
//
//        Spacer(Modifier.height(16.dp))
//        Button(
//            onClick = onContinue,
//            modifier = Modifier.fillMaxWidth().height(48.dp)
//        ) {
//            Text("Compute")
//        }
//    }
//}
//
//@Composable
//private fun MatrixCell(
//    value: Int,
//    onValueChange: (Int) -> Unit
//) {
//    var text by remember { mutableStateOf(value.toString()) }
//    var hasFocus by remember { mutableStateOf(false) }
//
//    OutlinedTextField(
//        value = text,
//        onValueChange = { raw ->
//            val digits = raw.filter { it.isDigit() }
//            val num = digits.toIntOrNull() ?: 0
//            if (digits.isEmpty() || num in 0..999) {
//                text = digits
//                onValueChange(num)
//            }
//        },
//        singleLine = true,
//        modifier = Modifier
//            .width(60.dp)
//            .height(60.dp)
//            .onFocusChanged { fs ->
//                if (fs.isFocused && !hasFocus && text == "0") text = ""
//                if (!fs.isFocused && text.isEmpty()) {
//                    text = "0"; onValueChange(0)
//                }
//                hasFocus = fs.isFocused
//            },
//        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
//    )
//}
//
//@Composable
//private fun OperationDropdown(vm: MatrixViewModel) {
//    val validOps = remember(vm.aRows, vm.aCols, vm.bRows, vm.bCols) {
//        buildList {
//            if (vm.aRows == vm.bRows && vm.aCols == vm.bCols) {
//                add(Operation.ADD); add(Operation.SUB)
//            }
//            if (vm.aCols == vm.bRows) {
//                add(Operation.MUL)
//                if (vm.bRows == vm.bCols) add(Operation.DIV)
//            }
//        }
//    }
//
//    LaunchedEffect(validOps) {
//        if (vm.operation !in validOps && validOps.isNotEmpty()) {
//            vm.operation = validOps.first()
//        }
//    }
//
//    var expanded by remember { mutableStateOf(false) }
//    Box {
//        TextField(
//            value = vm.operation.name,
//            onValueChange = {},
//            readOnly = true,
//            enabled = validOps.isNotEmpty(),
//            modifier = Modifier.fillMaxWidth(),
//            trailingIcon = {
//                IconButton(onClick = { expanded = true }, enabled = validOps.isNotEmpty()) {
//                    Icon(Icons.Filled.ArrowDropDown, contentDescription = "Select")
//                }
//            }
//        )
//        DropdownMenu(
//            expanded = expanded,
//            onDismissRequest = { expanded = false }
//        ) {
//            validOps.forEach { op ->
//                DropdownMenuItem(
//                    text = { Text(op.name) },
//                    onClick = {
//                        vm.operation = op
//                        expanded = false
//                    }
//                )
//            }
//        }
//    }
//}

@Composable
fun MatrixInputScreen(
    vm: MatrixViewModel,
    onContinue: () -> Unit
) {
    val screenScroll = rememberScrollState()
    val hScroll       = rememberScrollState()

    // sizes & spacing
    val labelW  = 40.dp
    val cellW   = 60.dp
    val cellH   = 60.dp
    val spacing = 4.dp

    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(screenScroll)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        // Matrix A
        Text("Enter Matrix A", style = MaterialTheme.typography.headlineSmall)
        Card(
            Modifier
                .fillMaxWidth()
                .height(cellH * 4 + spacing * 3 + 16.dp),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Column {
                // headers
                Row(
                    Modifier
                        .horizontalScroll(hScroll)
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(spacing),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(Modifier.width(labelW))
                    repeat(vm.aCols) { j ->
                        Box(
                            Modifier
                                .width(cellW)
                                .height(cellH)
                                .border(1.dp, SolidColor(MaterialTheme.colorScheme.outline), MaterialTheme.shapes.small),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("C${j+1}", style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
                // data rows
                LazyColumn(
                    Modifier
                        .fillMaxWidth()
                        .height(cellH * 3 + spacing * 2),
                    verticalArrangement = Arrangement.spacedBy(spacing)
                ) {
                    itemsIndexed(vm.aRows.let { List(it){ idx->idx } }) { i, _ ->
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .horizontalScroll(hScroll),
                            horizontalArrangement = Arrangement.spacedBy(spacing),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                Modifier
                                    .width(labelW)
                                    .height(cellH)
                                    .border(1.dp, SolidColor(MaterialTheme.colorScheme.outline), MaterialTheme.shapes.small),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("R${i+1}", style = MaterialTheme.typography.bodySmall)
                            }
                            repeat(vm.aCols) { j ->
                                MatrixCell(
                                    initial = vm.matrixA[i][j],
                                    onValidValue = { vm.matrixA[i][j] = it }
                                )
                            }
                        }
                    }
                }
            }
        }

        // Matrix B
        Text("Enter Matrix B", style = MaterialTheme.typography.headlineSmall)
        Card(
            Modifier
                .fillMaxWidth()
                .height(cellH * 4 + spacing * 3 + 16.dp),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Column {
                Row(
                    Modifier
                        .horizontalScroll(hScroll)
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(spacing),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(Modifier.width(labelW))
                    repeat(vm.bCols) { j ->
                        Box(
                            Modifier
                                .width(cellW)
                                .height(cellH)
                                .border(1.dp, SolidColor(MaterialTheme.colorScheme.outline), MaterialTheme.shapes.small),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("C${j+1}", style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
                LazyColumn(
                    Modifier
                        .fillMaxWidth()
                        .height(cellH * 3 + spacing * 2),
                    verticalArrangement = Arrangement.spacedBy(spacing)
                ) {
                    itemsIndexed(vm.bRows.let { List(it){ idx->idx } }) { i, _ ->
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .horizontalScroll(hScroll),
                            horizontalArrangement = Arrangement.spacedBy(spacing),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                Modifier
                                    .width(labelW)
                                    .height(cellH)
                                    .border(1.dp, SolidColor(MaterialTheme.colorScheme.outline), MaterialTheme.shapes.small),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("R${i+1}", style = MaterialTheme.typography.bodySmall)
                            }
                            repeat(vm.bCols) { j ->
                                MatrixCell(
                                    initial = vm.matrixB[i][j],
                                    onValidValue = { vm.matrixB[i][j] = it }
                                )
                            }
                        }
                    }
                }
            }
        }

        // Operation & Compute
        Text("Operation", style = MaterialTheme.typography.titleMedium)
        OperationDropdown(vm)

        Spacer(Modifier.height(16.dp))
        Button(
            onClick = onContinue,
            Modifier.fillMaxWidth().height(48.dp)
        ) {
            Text("Compute")
        }
    }
}

@Composable
private fun MatrixCell(
    initial: Double,
    onValidValue: (Double) -> Unit
) {
    var text by remember { mutableStateOf(initial.toString()) }
    var error by remember { mutableStateOf<String?>(null) }
    var hasFocus by remember { mutableStateOf(false) }

    Column {
        OutlinedTextField(
            value = text,
            onValueChange = { raw ->
                // 1) allow blank or lone "-" as intermediate
                if (raw.isEmpty() || raw == "-") {
                    text = raw
                    error = null
                    return@OutlinedTextField
                }
                // 2) require at least one digit before optional decimal dot
                val pattern = Regex("^-?\\d+(\\.\\d*)?$")
                if (!pattern.matches(raw)) {
                    error = "Invalid input"
                    text = raw
                    return@OutlinedTextField
                }
                // 3) parse and enforce range
                val num = raw.toDoubleOrNull() ?: run {
                    error = "Invalid input"
                    text = raw
                    return@OutlinedTextField
                }
                if (num < -99.0 || num > 999.0) {
                    error = "Limit –99…999"
                    text = raw
                    return@OutlinedTextField
                }
                // 4) good!
                error = null
                text = raw
                onValidValue(num)
            },
            singleLine = true,
            isError = error != null,
            modifier = Modifier
                .width(60.dp)
                .height(60.dp)
                .onFocusChanged { fs ->
                    if (fs.isFocused && !hasFocus && text == "0.0") text = ""
                    if (!fs.isFocused && text.isEmpty()) {
                        text = "0.0"
                        onValidValue(0.0)
                    }
                    hasFocus = fs.isFocused
                },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        error?.let { msg ->
            Text(
                text = msg,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 4.dp, top = 2.dp)
            )
        }
    }
}


@Composable
private fun OperationDropdown(vm: MatrixViewModel) {
    val validOps by remember(vm.aRows, vm.aCols, vm.bRows, vm.bCols) {
        mutableStateOf(buildList {
            if (vm.aRows == vm.bRows && vm.aCols == vm.bCols) {
                add(Operation.ADD); add(Operation.SUB)
            }
            if (vm.aCols == vm.bRows) {
                add(Operation.MUL)
                if (vm.bRows == vm.bCols) add(Operation.DIV)
            }
        })
    }

    LaunchedEffect(validOps) {
        if (vm.operation !in validOps && validOps.isNotEmpty()) {
            vm.operation = validOps.first()
        }
    }

    var expanded by remember { mutableStateOf(false) }
    Box {
        TextField(
            value = vm.operation.name,
            onValueChange = {},
            readOnly = true,
            enabled = validOps.isNotEmpty(),
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                IconButton(onClick = { expanded = true }, enabled = validOps.isNotEmpty()) {
                    Icon(Icons.Filled.ArrowDropDown, contentDescription = "Select")
                }
            }
        )
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            validOps.forEach { op ->
                DropdownMenuItem(
                    text = { Text(op.name) },
                    onClick = {
                        vm.operation = op
                        expanded = false
                    }
                )
            }
        }
    }
}