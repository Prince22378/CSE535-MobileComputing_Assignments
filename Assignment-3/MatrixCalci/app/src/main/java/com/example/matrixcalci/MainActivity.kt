//package com.example.matrixcalci
//
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import android.widget.TextView
//import com.example.matrixcalci.databinding.ActivityMainBinding
//
//class MainActivity : AppCompatActivity() {
//
//    private lateinit var binding: ActivityMainBinding
//
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        // Example of a call to a native method
//        binding.sampleText.text = stringFromJNI()
//    }
//
//    /**
//     * A native method that is implemented by the 'matrixcalci' native library,
//     * which is packaged with this application.
//     */
//    private external fun stringFromJNI(): String
//
//    companion object {
//        // Used to load the 'matrixcalci' library on application startup.
//        init {
//            System.loadLibrary("matrixcalci")
//        }
//    }
//}



package com.example.matrixcalci

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.matrixcalci.ui.MatrixCalculatorApp
import com.example.matrixcalci.ui.theme.MatrixCalciTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        System.loadLibrary("matrixcalci")
        setContent {
            MatrixCalciTheme {
                MatrixCalculatorApp()
            }
//            MatrixCalculatorApp()
        }
    }
}



