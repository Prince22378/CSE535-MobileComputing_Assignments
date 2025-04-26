//package com.example.matrixcalci.viewmodel
//
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateListOf
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.setValue
//import androidx.lifecycle.ViewModel
//
//enum class Operation { ADD, SUB, MUL, DIV }
//
//class MatrixViewModel : ViewModel() {
//    var aRows by mutableStateOf(2)
//    var aCols by mutableStateOf(2)
//    var bRows by mutableStateOf(2)
//    var bCols by mutableStateOf(2)
//
//    val matrixA = mutableStateListOf<DoubleArray>().apply {
//        repeat(aRows) { add(DoubleArray(aCols)) }
//    }
//    val matrixB = mutableStateListOf<DoubleArray>().apply {
//        repeat(bRows) { add(DoubleArray(bCols)) }
//    }
//
//    var operation by mutableStateOf(Operation.ADD)
//    val result = mutableStateListOf<DoubleArray>()
//
//    // Will hold validation or JNI errors
//    var errorMessage by mutableStateOf<String?>(null)
//
//    fun resizeMatrices() {
//        matrixA.clear(); repeat(aRows) { matrixA.add(DoubleArray(aCols)) }
//        matrixB.clear(); repeat(bRows) { matrixB.add(DoubleArray(bCols)) }
//    }
//
//    /**
//     * Returns true if the operation was valid and the native call succeeded.
//     * Otherwise sets errorMessage and returns false.
//     */
//    fun computeResult(): Boolean {
//        errorMessage = null
//
//        // 1) Dimension‐vs‐operation validation
//        when (operation) {
//            Operation.ADD, Operation.SUB -> {
//                if (aRows != bRows || aCols != bCols) {
//                    errorMessage = "Add/Sub requires same dimensions (A is ${aRows}×${aCols}, B is ${bRows}×${bCols})"
//                    return false
//                }
//            }
//            Operation.MUL -> {
//                if (aCols != bRows) {
//                    errorMessage = "Multiplication requires A.cols == B.rows (${aCols} != ${bRows})"
//                    return false
//                }
//            }
//            Operation.DIV -> {
//                if (aCols != bRows) {
//                    errorMessage = "Division requires A.cols == B.rows (${aCols} != ${bRows})"
//                    return false
//                }
//                if (bRows != bCols) {
//                    errorMessage = "Division requires B to be square (${bRows}×${bCols})"
//                    return false
//                }
//            }
//        }
//
//        // 2) Call into native and catch inversion errors
//        return try {
//            val m = aRows
//            val n = aCols
//            val p = bCols
//            val resArr: Array<DoubleArray> = when (operation) {
//                Operation.ADD -> nativeAdd(matrixA.toTypedArray(), matrixB.toTypedArray(), m, n)
//                Operation.SUB -> nativeSub(matrixA.toTypedArray(), matrixB.toTypedArray(), m, n)
//                Operation.MUL -> nativeMul(matrixA.toTypedArray(), matrixB.toTypedArray(), m, n, p)
//                Operation.DIV -> nativeDiv(matrixA.toTypedArray(), matrixB.toTypedArray(), m, n, p)
//            }
//            result.clear()
//            resArr.forEach { result.add(it) }
//            true
//        } catch (e: IllegalArgumentException) {
//            errorMessage = e.message
//            false
//        }
//    }
//
//    // JNI bridges
//    private external fun nativeAdd(a: Array<DoubleArray>, b: Array<DoubleArray>, m: Int, n: Int): Array<DoubleArray>
//    private external fun nativeSub(a: Array<DoubleArray>, b: Array<DoubleArray>, m: Int, n: Int): Array<DoubleArray>
//    private external fun nativeMul(a: Array<DoubleArray>, b: Array<DoubleArray>, m: Int, k: Int, p: Int): Array<DoubleArray>
//    private external fun nativeDiv(a: Array<DoubleArray>, b: Array<DoubleArray>, m: Int, k: Int, p: Int): Array<DoubleArray>
//
//    companion object {
//        init { System.loadLibrary("matrixcalci") }
//    }
//}


package com.example.matrixcalci.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

enum class Operation { ADD, SUB, MUL, DIV }


class MatrixViewModel : ViewModel() {
    var aRows by mutableStateOf(2)
    var aCols by mutableStateOf(2)
    var bRows by mutableStateOf(2)
    var bCols by mutableStateOf(2)

    val matrixA = mutableStateListOf<DoubleArray>().apply {
        repeat(aRows){add(DoubleArray(aCols))}
    }
    val matrixB = mutableStateListOf<DoubleArray>().apply {
        repeat(bRows){add(DoubleArray(bCols))}
    }

    var operation by mutableStateOf(Operation.ADD)
    val result = mutableStateListOf<DoubleArray>()
    var errorMessage by mutableStateOf<String?>(null)

    val validOps: List<Operation>
        get() = buildList {
            // Add/Sub require same dimensions
            if (aRows == bRows && aCols == bCols) {
                add(Operation.ADD)
                add(Operation.SUB)
            }
            // Mul requires A.cols == B.rows
            if (aCols == bRows) {
                add(Operation.MUL)
                // Div further requires B to be square
                if (bRows == bCols) add(Operation.DIV)
            }
        }
    fun resizeMatrices(){
        matrixA.clear();repeat(aRows){matrixA.add(DoubleArray(aCols))}
        matrixB.clear();repeat(bRows){matrixB.add(DoubleArray(bCols))}
    }

    fun computeResult():Boolean {
        errorMessage=null
        when (operation) {
            Operation.ADD, Operation.SUB -> {
                if (aRows != bRows || aCols != bCols) {
                    errorMessage = "Add/Sub requires identical dims (A ${aRows}×${aCols}, B ${bRows}×${bCols})"
                    return false
                }
            }
            Operation.MUL -> {
                if (aCols != bRows) {
                    errorMessage = "Mul requires A.cols == B.rows (${aCols} != ${bRows})"
                    return false
                }
            }
            Operation.DIV -> {
                if (aCols != bRows) {
                    errorMessage = "Div requires A.cols == B.rows (${aCols} != ${bRows})"
                    return false
                }
                if (bRows != bCols) {
                    errorMessage = "Div requires B square (${bRows}×${bCols})"
                    return false
                }
            }
        }
        return try {
            val m = aRows
            val n = aCols
            val p = bCols
            val arr = when (operation) {
                Operation.ADD -> nativeAdd(matrixA.toTypedArray(), matrixB.toTypedArray(), m, n)
                Operation.SUB -> nativeSub(matrixA.toTypedArray(), matrixB.toTypedArray(), m, n)
                Operation.MUL -> nativeMul(matrixA.toTypedArray(), matrixB.toTypedArray(), m, n, p)
                Operation.DIV -> nativeDiv(matrixA.toTypedArray(), matrixB.toTypedArray(), m, n, p)
            }
            result.clear(); arr.forEach { result.add(it) }
            true
        } catch (e: IllegalArgumentException) {
            errorMessage = e.message
            false
        }
    }

    private external fun nativeAdd(a:Array<DoubleArray>,b:Array<DoubleArray>,m:Int,n:Int):Array<DoubleArray>
    private external fun nativeSub(a:Array<DoubleArray>,b:Array<DoubleArray>,m:Int,n:Int):Array<DoubleArray>
    private external fun nativeMul(a:Array<DoubleArray>,b:Array<DoubleArray>,m:Int,k:Int,p:Int):Array<DoubleArray>
    private external fun nativeDiv(a:Array<DoubleArray>,b:Array<DoubleArray>,m:Int,k:Int,p:Int):Array<DoubleArray>

    companion object{init{System.loadLibrary("matrixcalci")}}
}