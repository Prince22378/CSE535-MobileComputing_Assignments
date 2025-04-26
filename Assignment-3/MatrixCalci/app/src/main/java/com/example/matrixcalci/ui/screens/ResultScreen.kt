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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import com.example.matrixcalci.viewmodel.MatrixViewModel


@Composable
fun ResultScreen(
    vm: MatrixViewModel,
    onChangeOp: () -> Unit,
    onChangeDim: () -> Unit
) {
    val scrollH = rememberScrollState()
    val scrollV = rememberScrollState()

    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Spacer(Modifier.height(8.dp))

        Text(
            "Result (${vm.operation.name})",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(Modifier.height(8.dp))

        // dimensions
        val rows = vm.result.size
        val cols = vm.result.firstOrNull()?.size ?: 0

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Box(
                Modifier
                    .fillMaxSize()
                    .horizontalScroll(scrollH)
                    .padding(8.dp)
            ) {
                // total width = label (40dp) + cols * (cellWidth+spacing)
                val totalWidth = 40.dp + cols * (60.dp + 4.dp)
                Column(Modifier.width(totalWidth)) {
                    // header row
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Spacer(Modifier.width(40.dp))
                        repeat(cols) { j ->
                            Box(
                                modifier = Modifier
                                    .width(60.dp)
                                    .height(40.dp)
                                    .border(
                                        width = 1.dp,
                                        brush = SolidColor(MaterialTheme.colorScheme.outline),
                                        shape = MaterialTheme.shapes.small
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    "C${j + 1}",
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }
                    }

                    Spacer(Modifier.height(8.dp))

                    // data rows (vertical scroll)
                    Column(
                        Modifier
                            .height(240.dp)
                            .verticalScroll(scrollV),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        vm.result.forEachIndexed { i, row ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                // row label
                                Box(
                                    modifier = Modifier
                                        .width(40.dp)
                                        .height(60.dp)
                                        .border(
                                            1.dp,
                                            SolidColor(MaterialTheme.colorScheme.outline),
                                            MaterialTheme.shapes.small
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text("R${i + 1}", style = MaterialTheme.typography.bodySmall)
                                }

                                // cells
                                row.forEach { v ->
                                    Box(
                                        modifier = Modifier
                                            .width(60.dp)
                                            .height(60.dp)
                                            .border(
                                                1.dp,
                                                SolidColor(MaterialTheme.colorScheme.outline),
                                                MaterialTheme.shapes.small
                                            )
                                            .padding(4.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = "%.2f".format(v),
                                            style = MaterialTheme.typography.bodyLarge
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        Spacer(Modifier.weight(1f))

        // ← Moved buttons here, below the matrix
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(onClick = onChangeOp, Modifier.weight(1f))  { Text("Change Operation") }
            Button(onClick = onChangeDim, Modifier.weight(1f)) { Text("Change Dimension") }
        }
    }
}