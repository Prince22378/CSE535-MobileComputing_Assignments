MatrixCalci


A fully native_powered Android matrix calculator app. You can enter two matrices of any dimension, choose an operation (add, subtract, multiply, divide), and see the result laid out in a scrollable, boxed grid. Under the hood all the heavy lifting is done in C++ via JNI and the STL’s vector, including a Gauss–Jordan inversion routine for division.


Features
- Arbitrary Dimensions — enter any row × column sizes for A and B
- Addition & Subtraction (only if dims match)
- Multiplication (A.cols == B.rows)
- Division (multiplies A by B⁻¹ when B is square and invertible)
I check these condition at the initial steps so only the preferred operation can be show to the user.
- Jetpack Compose UI with scrollable, boxed grids
- Native C++ operations using STL vector and Gauss–Jordan inversion
- Three-screen flow: dimensions → matrix entry → result
- Input validation: real-time error messages, range –99.0…999.0 (including decimals)


Getting Started
Build & Run:
Clone this repo
Open in Android Studio
Sync Gradle and ensure ndkVersion matches your installed NDK
Run the app module


Usage
* Enter dimensions for Matrix A and B. Invalid combinations prompt an alert.
* Fill in each element in the scrollable, boxed grid. Decimal input allowed, range –99.0…999.0.
* Select an operation from the dropdown (only valid ones appear).
* Tap Compute to see the result in a boxed grid. Change dims or operation anytime via buttons.


Project Structure
MainActivity.kt
viewmodel/MatrixViewModel.kt
ui/screens/DimensionScreen.kt
ui/screens/MatrixInputScreen.kt
ui/screens/ResultScreen.kt
native-lib.cpp CMakeLists.txt
AndroidManifest.xml


Native C++ Implementation
• Conversion helpers
– toVec(JNIEnv*, jobjectArray, int) → vector<vector<double>>
– toJni(JNIEnv*, vector<vector<double>>) → Java double[][]
• Operations
– add(A,B), sub(A,B)
– mul(A,B) (triple loop)
– inv(A) (Gauss–Jordan, no pivot)
– divi(A,B) = mul(A, inv(B))


• JNI exports match ViewModel’s nativeAdd, nativeSub, etc.



This readme is generated using ChatGPT.