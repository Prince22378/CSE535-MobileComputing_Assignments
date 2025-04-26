//////#include <jni.h>
//////#include <string>
//////
//////extern "C" JNIEXPORT jstring JNICALL
//////Java_com_example_matrixcalci_MainActivity_stringFromJNI(
//////        JNIEnv* env,
//////        jobject /* this */) {
//////    std::string hello = "Hello from C++";
//////    return env->NewStringUTF(hello.c_str());
//////}
////#include <jni.h>
////#include <vector>
////#include <cmath>
////using namespace std;
////
////// Convert a Java double[][] to C++ vector with given rows & cols
////static vector<vector<double>> toVec(JNIEnv* env, jobjectArray arr, int rows, int cols) {
////    vector<vector<double>> M(rows, vector<double>(cols));
////    for(int i = 0; i < rows; i++) {
////        jdoubleArray row = (jdoubleArray)env->GetObjectArrayElement(arr, i);
////        jdouble* elems = env->GetDoubleArrayElements(row, nullptr);
////        for(int j = 0; j < cols; j++) {
////            M[i][j] = elems[j];
////        }
////        env->ReleaseDoubleArrayElements(row, elems, 0);
////        env->DeleteLocalRef(row);
////    }
////    return M;
////}
////
////// Convert C++ vector back to Java double[][]
////static jobjectArray toJni(JNIEnv* env, const vector<vector<double>>& M) {
////    int rows = M.size();
////    int cols = rows ? M[0].size() : 0;
////    jclass dblArrCls = env->FindClass("[D");
////    jobjectArray outer = env->NewObjectArray(rows, dblArrCls, nullptr);
////    for(int i = 0; i < rows; i++) {
////        jdoubleArray inner = env->NewDoubleArray(cols);
////        env->SetDoubleArrayRegion(inner, 0, cols, M[i].data());
////        env->SetObjectArrayElement(outer, i, inner);
////        env->DeleteLocalRef(inner);
////    }
////    return outer;
////}
////
////// Addition
////static vector<vector<double>> addOp(const vector<vector<double>>& A,
////                                    const vector<vector<double>>& B) {
////    int m = A.size(), n = A[0].size();
////    vector<vector<double>> R(m, vector<double>(n));
////    for(int i = 0; i < m; i++)
////        for(int j = 0; j < n; j++)
////            R[i][j] = A[i][j] + B[i][j];
////    return R;
////}
////
////// Subtraction
////static vector<vector<double>> subOp(const vector<vector<double>>& A,
////                                    const vector<vector<double>>& B) {
////    int m = A.size(), n = A[0].size();
////    vector<vector<double>> R(m, vector<double>(n));
////    for(int i = 0; i < m; i++)
////        for(int j = 0; j < n; j++)
////            R[i][j] = A[i][j] - B[i][j];
////    return R;
////}
////
////// Multiplication (A is m×k, B is k×p => R is m×p)
////static vector<vector<double>> mulOp(const vector<vector<double>>& A,
////                                    const vector<vector<double>>& B) {
////    int m = A.size(), k = A[0].size(), p = B[0].size();
////    vector<vector<double>> R(m, vector<double>(p, 0.0));
////    for(int i = 0; i < m; i++)
////        for(int j = 0; j < p; j++)
////            for(int t = 0; t < k; t++)
////                R[i][j] += A[i][t] * B[t][j];
////    return R;
////}
////
////// Inversion with singular check
////static vector<vector<double>> inv(JNIEnv* env, const vector<vector<double>>& A) {
////    int n = A.size();
////    // Build augmented [A|I]
////    vector<vector<double>> aug(n, vector<double>(2 * n));
////    for(int i = 0; i < n; i++) {
////        for(int j = 0; j < n; j++) aug[i][j] = A[i][j];
////        aug[i][n + i] = 1.0;
////    }
////    // Gauss-Jordan
////    for(int i = 0; i < n; i++) {
////        double diag = aug[i][i];
////        if (fabs(diag) < 1e-9) {
////            // Not invertible
////            jclass ex = env->FindClass("java/lang/IllegalArgumentException");
////            env->ThrowNew(ex, "Matrix is not invertible");
////            return {};
////        }
////        for(int j = 0; j < 2 * n; j++) aug[i][j] /= diag;
////        for(int k = 0; k < n; k++) if (k != i) {
////                double f = aug[k][i];
////                for(int j = 0; j < 2 * n; j++)
////                    aug[k][j] -= f * aug[i][j];
////            }
////    }
////    // Extract inverse
////    vector<vector<double>> I(n, vector<double>(n));
////    for(int i = 0; i < n; i++)
////        for(int j = 0; j < n; j++)
////            I[i][j] = aug[i][n + j];
////    return I;
////}
////
////// JNI Exports
////extern "C" {
////
////// Add: requires A.rows==B.rows && A.cols==B.cols
////JNIEXPORT jobjectArray JNICALL
////Java_com_example_matrixcalci_viewmodel_MatrixViewModel_nativeAdd(
////        JNIEnv* env, jobject,
////        jobjectArray a, jobjectArray b,
////        jint m, jint n)
////{
////    // dimension check
////    if (env->GetArrayLength(b) != m) {
////        jclass ex = env->FindClass("java/lang/IllegalArgumentException");
////        env->ThrowNew(ex, "Addition requires same number of rows");
////        return nullptr;
////    }
////    // Check B cols
////    jdoubleArray brow0 = (jdoubleArray)env->GetObjectArrayElement(b, 0);
////    int bn = env->GetArrayLength(brow0);
////    env->DeleteLocalRef(brow0);
////    if (bn != n) {
////        jclass ex = env->FindClass("java/lang/IllegalArgumentException");
////        env->ThrowNew(ex, "Addition requires same number of columns");
////        return nullptr;
////    }
////    auto A = toVec(env, a, m, n);
////    auto B = toVec(env, b, m, n);
////    auto R = addOp(A, B);
////    return toJni(env, R);
////}
////
////// Subtract: same dimensionality requirements
////JNIEXPORT jobjectArray JNICALL
////Java_com_example_matrixcalci_viewmodel_MatrixViewModel_nativeSub(
////        JNIEnv* env, jobject,
////        jobjectArray a, jobjectArray b,
////        jint m, jint n)
////{
////    // same checks as add
////    if (env->GetArrayLength(b) != m) {
////        jclass ex = env->FindClass("java/lang/IllegalArgumentException");
////        env->ThrowNew(ex, "Subtraction requires same number of rows");
////        return nullptr;
////    }
////    jdoubleArray brow0 = (jdoubleArray)env->GetObjectArrayElement(b, 0);
////    int bn = env->GetArrayLength(brow0);
////    env->DeleteLocalRef(brow0);
////    if (bn != n) {
////        jclass ex = env->FindClass("java/lang/IllegalArgumentException");
////        env->ThrowNew(ex, "Subtraction requires same number of columns");
////        return nullptr;
////    }
////    auto A = toVec(env, a, m, n);
////    auto B = toVec(env, b, m, n);
////    auto R = subOp(A, B);
////    return toJni(env, R);
////}
////
////// Multiply: A is m×k, B is k×p => args: m,k,p
////JNIEXPORT jobjectArray JNICALL
////Java_com_example_matrixcalci_viewmodel_MatrixViewModel_nativeMul(
////        JNIEnv* env, jobject,
////        jobjectArray a, jobjectArray b,
////        jint m, jint k, jint p)
////{
////    // check B rows == k
////    if (env->GetArrayLength(b) != k) {
////        jclass ex = env->FindClass("java/lang/IllegalArgumentException");
////        env->ThrowNew(ex, "Multiplication requires A.cols == B.rows");
////        return nullptr;
////    }
////    auto A = toVec(env, a, m, k);
////    auto B = toVec(env, b, k, p);
////    auto R = mulOp(A, B);
////    return toJni(env, R);
////}
////
////// (Optional) Division: A×inv(B) - only square B, result m×p (where p=k)
////JNIEXPORT jobjectArray JNICALL
////Java_com_example_matrixcalci_viewmodel_MatrixViewModel_nativeDiv(
////        JNIEnv* env, jobject,
////        jobjectArray a, jobjectArray b,
////        jint m, jint k, jint p)
////{
////    // you can implement inv(B) and then mulOp
////    // For brevity, call mulOp on A and inv(B) (requires B square k×k)
////    // ... your inv() here ...
////    jclass ex = env->FindClass("java/lang/UnsupportedOperationException");
////    env->ThrowNew(ex, "Division not implemented for rectangular matrices");
////    return nullptr;
////}
////
////} // extern "C"
//
//
//#include <jni.h>
//#include <vector>
//#include <cmath>
//using namespace std;
//
//// Convert Java double[][] to C++ vector<vector<double>>
//static vector<vector<double>> toVec(JNIEnv* env, jobjectArray arr, int rows, int cols) {
//    vector<vector<double>> M(rows, vector<double>(cols));
//    for(int i = 0; i < rows; i++) {
//        jdoubleArray row = (jdoubleArray)env->GetObjectArrayElement(arr, i);
//        jdouble* elems = env->GetDoubleArrayElements(row, nullptr);
//        for(int j = 0; j < cols; j++) {
//            M[i][j] = elems[j];
//        }
//        env->ReleaseDoubleArrayElements(row, elems, 0);
//        env->DeleteLocalRef(row);
//    }
//    return M;
//}
//
//// Convert C++ vector<vector<double>> back to Java double[][]
//static jobjectArray toJni(JNIEnv* env, const vector<vector<double>>& M) {
//    int rows = M.size();
//    int cols = rows ? (int)M[0].size() : 0;
//    jclass dblArrCls = env->FindClass("[D");
//    jobjectArray outer = env->NewObjectArray(rows, dblArrCls, nullptr);
//    for(int i = 0; i < rows; i++) {
//        jdoubleArray inner = env->NewDoubleArray(cols);
//        env->SetDoubleArrayRegion(inner, 0, cols, M[i].data());
//        env->SetObjectArrayElement(outer, i, inner);
//        env->DeleteLocalRef(inner);
//    }
//    return outer;
//}
//
//// Addition
//static vector<vector<double>> addOp(const vector<vector<double>>& A,
//                                    const vector<vector<double>>& B) {
//    int m = A.size(), n = A[0].size();
//    vector<vector<double>> R(m, vector<double>(n));
//    for(int i = 0; i < m; i++)
//        for(int j = 0; j < n; j++)
//            R[i][j] = A[i][j] + B[i][j];
//    return R;
//}
//
//// Subtraction
//static vector<vector<double>> subOp(const vector<vector<double>>& A,
//                                    const vector<vector<double>>& B) {
//    int m = A.size(), n = A[0].size();
//    vector<vector<double>> R(m, vector<double>(n));
//    for(int i = 0; i < m; i++)
//        for(int j = 0; j < n; j++)
//            R[i][j] = A[i][j] - B[i][j];
//    return R;
//}
//
//// Multiplication (A is m×k, B is k×p => R is m×p)
//static vector<vector<double>> mulOp(const vector<vector<double>>& A,
//                                    const vector<vector<double>>& B) {
//    int m = A.size(), k = A[0].size(), p = B[0].size();
//    vector<vector<double>> R(m, vector<double>(p, 0.0));
//    for(int i = 0; i < m; i++)
//        for(int j = 0; j < p; j++)
//            for(int t = 0; t < k; t++)
//                R[i][j] += A[i][t] * B[t][j];
//    return R;
//}
//
//// Inversion with singular check
//static vector<vector<double>> inv(JNIEnv* env, const vector<vector<double>>& A) {
//    int n = A.size();
//    // Build augmented [A|I]
//    vector<vector<double>> aug(n, vector<double>(2 * n));
//    for(int i = 0; i < n; i++) {
//        for(int j = 0; j < n; j++) aug[i][j] = A[i][j];
//        aug[i][n + i] = 1.0;
//    }
//    // Gauss-Jordan
//    for(int i = 0; i < n; i++) {
//        double diag = aug[i][i];
//        if (fabs(diag) < 1e-9) {
//            // Not invertible
//            jclass ex = env->FindClass("java/lang/IllegalArgumentException");
//            env->ThrowNew(ex, "Matrix is not invertible");
//            return {};
//        }
//        for(int j = 0; j < 2 * n; j++) aug[i][j] /= diag;
//        for(int k = 0; k < n; k++) if (k != i) {
//                double f = aug[k][i];
//                for(int j = 0; j < 2 * n; j++)
//                    aug[k][j] -= f * aug[i][j];
//            }
//    }
//    // Extract inverse
//    vector<vector<double>> I(n, vector<double>(n));
//    for(int i = 0; i < n; i++)
//        for(int j = 0; j < n; j++)
//            I[i][j] = aug[i][n + j];
//    return I;
//}
//
//extern "C" {
//
//// Add: requires A.rows==B.rows && A.cols==B.cols
//JNIEXPORT jobjectArray JNICALL
//Java_com_example_matrixcalci_viewmodel_MatrixViewModel_nativeAdd(
//        JNIEnv* env, jobject,
//        jobjectArray a, jobjectArray b,
//        jint m, jint n)
//{
//    // check rows
//    if (env->GetArrayLength(b) != m) {
//        jclass ex = env->FindClass("java/lang/IllegalArgumentException");
//        env->ThrowNew(ex, "Addition requires same number of rows");
//        return nullptr;
//    }
//    // check cols
//    jdoubleArray brow0 = (jdoubleArray)env->GetObjectArrayElement(b, 0);
//    int bn = env->GetArrayLength(brow0);
//    env->DeleteLocalRef(brow0);
//    if (bn != n) {
//        jclass ex = env->FindClass("java/lang/IllegalArgumentException");
//        env->ThrowNew(ex, "Addition requires same number of columns");
//        return nullptr;
//    }
//    auto A = toVec(env, a, m, n);
//    auto B = toVec(env, b, m, n);
//    auto R = addOp(A, B);
//    return toJni(env, R);
//}
//
//// Sub: same dims
//JNIEXPORT jobjectArray JNICALL
//Java_com_example_matrixcalci_viewmodel_MatrixViewModel_nativeSub(
//        JNIEnv* env, jobject,
//        jobjectArray a, jobjectArray b,
//        jint m, jint n)
//{
//    if (env->GetArrayLength(b) != m) {
//        jclass ex = env->FindClass("java/lang/IllegalArgumentException");
//        env->ThrowNew(ex, "Subtraction requires same number of rows");
//        return nullptr;
//    }
//    jdoubleArray brow0 = (jdoubleArray)env->GetObjectArrayElement(b, 0);
//    int bn = env->GetArrayLength(brow0);
//    env->DeleteLocalRef(brow0);
//    if (bn != n) {
//        jclass ex = env->FindClass("java/lang/IllegalArgumentException");
//        env->ThrowNew(ex, "Subtraction requires same number of columns");
//        return nullptr;
//    }
//    auto A = toVec(env, a, m, n);
//    auto B = toVec(env, b, m, n);
//    auto R = subOp(A, B);
//    return toJni(env, R);
//}
//
//// Mul: A is m×k, B is k×p
//JNIEXPORT jobjectArray JNICALL
//Java_com_example_matrixcalci_viewmodel_MatrixViewModel_nativeMul(
//        JNIEnv* env, jobject,
//        jobjectArray a, jobjectArray b,
//        jint m, jint k, jint p)
//{
//    if (env->GetArrayLength(b) != k) {
//        jclass ex = env->FindClass("java/lang/IllegalArgumentException");
//        env->ThrowNew(ex, "Multiplication requires A.cols == B.rows");
//        return nullptr;
//    }
//    auto A = toVec(env, a, m, k);
//    auto B = toVec(env, b, k, p);
//    auto R = mulOp(A, B);
//    return toJni(env, R);
//}
//
//// Div: A is m×k, B must be k×k invertible => result is m×k
//JNIEXPORT jobjectArray JNICALL
//Java_com_example_matrixcalci_viewmodel_MatrixViewModel_nativeDiv(
//        JNIEnv* env, jobject,
//        jobjectArray a, jobjectArray b,
//        jint m, jint k, jint p)
//{
//    // require square B
//    if (k != p) {
//        jclass ex = env->FindClass("java/lang/IllegalArgumentException");
//        env->ThrowNew(ex, "Division requires square B (rows==cols)");
//        return nullptr;
//    }
//    auto A = toVec(env, a, m, k);
//    auto B = toVec(env, b, k, p);
//    auto Binv = inv(env, B);
//    if (env->ExceptionCheck()) return nullptr; // singular
//    auto R = mulOp(A, Binv);
//    return toJni(env, R);
//}
//
//}  // extern "C"




#include <jni.h>
#include <vector>
#include <cmath>
using namespace std;

// Convert Java double[][] → C++ vector<vector<double>>
static vector<vector<double>> toVec(JNIEnv* env, jobjectArray arr, int rows, int cols) {
    vector<vector<double>> M(rows, vector<double>(cols));
    for(int i = 0; i < rows; i++) {
        jdoubleArray row = (jdoubleArray)env->GetObjectArrayElement(arr, i);
        jdouble* elems = env->GetDoubleArrayElements(row, nullptr);
        for(int j = 0; j < cols; j++) {
            M[i][j] = elems[j];
        }
        env->ReleaseDoubleArrayElements(row, elems, 0);
        env->DeleteLocalRef(row);
    }
    return M;
}

// Convert C++ vector<vector<double>> → Java double[][]
static jobjectArray toJni(JNIEnv* env, const vector<vector<double>>& M) {
    int rows = M.size();
    int cols = rows ? (int)M[0].size() : 0;
    jclass dblArrCls = env->FindClass("[D");
    jobjectArray outer = env->NewObjectArray(rows, dblArrCls, nullptr);
    for(int i = 0; i < rows; i++) {
        jdoubleArray inner = env->NewDoubleArray(cols);
        env->SetDoubleArrayRegion(inner, 0, cols, M[i].data());
        env->SetObjectArrayElement(outer, i, inner);
        env->DeleteLocalRef(inner);
    }
    return outer;
}

// Add
static vector<vector<double>> addOp(const vector<vector<double>>& A,
                                    const vector<vector<double>>& B) {
    int m = A.size(), n = A[0].size();
    vector<vector<double>> R(m, vector<double>(n));
    for(int i = 0; i < m; i++)
        for(int j = 0; j < n; j++)
            R[i][j] = A[i][j] + B[i][j];
    return R;
}

// Subtract
static vector<vector<double>> subOp(const vector<vector<double>>& A,
                                    const vector<vector<double>>& B) {
    int m = A.size(), n = A[0].size();
    vector<vector<double>> R(m, vector<double>(n));
    for(int i = 0; i < m; i++)
        for(int j = 0; j < n; j++)
            R[i][j] = A[i][j] - B[i][j];
    return R;
}

// Multiply (m×k) × (k×p) = (m×p)
static vector<vector<double>> mulOp(const vector<vector<double>>& A,
                                    const vector<vector<double>>& B) {
    int m = A.size(), k = A[0].size(), p = B[0].size();
    vector<vector<double>> R(m, vector<double>(p, 0.0));
    for(int i = 0; i < m; i++)
        for(int j = 0; j < p; j++)
            for(int t = 0; t < k; t++)
                R[i][j] += A[i][t] * B[t][j];
    return R;
}

// Inversion with singularity check
static vector<vector<double>> inv(JNIEnv* env, const vector<vector<double>>& A) {
    int n = A.size();
    vector<vector<double>> aug(n, vector<double>(2 * n));
    // [A | I]
    for(int i = 0; i < n; i++) {
        for(int j = 0; j < n; j++) aug[i][j] = A[i][j];
        aug[i][n + i] = 1.0;
    }
    // Gauss–Jordan
    for(int i = 0; i < n; i++) {
        double diag = aug[i][i];
        if (fabs(diag) < 1e-9) {
            jclass ex = env->FindClass("java/lang/IllegalArgumentException");
            env->ThrowNew(ex, "Matrix is not invertible");
            return {};
        }
        for(int j = 0; j < 2 * n; j++) aug[i][j] /= diag;
        for(int k = 0; k < n; k++) if(k != i) {
                double f = aug[k][i];
                for(int j = 0; j < 2 * n; j++)
                    aug[k][j] -= f * aug[i][j];
            }
    }
    // Extract inverse
    vector<vector<double>> I(n, vector<double>(n));
    for(int i = 0; i < n; i++)
        for(int j = 0; j < n; j++)
            I[i][j] = aug[i][n + j];
    return I;
}

extern "C" {

// ADD
JNIEXPORT jobjectArray JNICALL
Java_com_example_matrixcalci_viewmodel_MatrixViewModel_nativeAdd(
        JNIEnv* env, jobject,
        jobjectArray a, jobjectArray b,
        jint m, jint n)
{
    // rows
    if (env->GetArrayLength(b) != m) {
        jclass ex = env->FindClass("java/lang/IllegalArgumentException");
        env->ThrowNew(ex, "Addition requires same number of rows");
        return nullptr;
    }
    // cols
    jdoubleArray brow0 = (jdoubleArray)env->GetObjectArrayElement(b, 0);
    int bn = env->GetArrayLength(brow0);
    env->DeleteLocalRef(brow0);
    if (bn != n) {
        jclass ex = env->FindClass("java/lang/IllegalArgumentException");
        env->ThrowNew(ex, "Addition requires same number of columns");
        return nullptr;
    }
    auto A = toVec(env, a, m, n);
    auto B = toVec(env, b, m, n);
    return toJni(env, addOp(A, B));
}

// SUB
JNIEXPORT jobjectArray JNICALL
Java_com_example_matrixcalci_viewmodel_MatrixViewModel_nativeSub(
        JNIEnv* env, jobject,
        jobjectArray a, jobjectArray b,
        jint m, jint n)
{
    if (env->GetArrayLength(b) != m) {
        jclass ex = env->FindClass("java/lang/IllegalArgumentException");
        env->ThrowNew(ex, "Subtraction requires same number of rows");
        return nullptr;
    }
    jdoubleArray brow0 = (jdoubleArray)env->GetObjectArrayElement(b, 0);
    int bn = env->GetArrayLength(brow0);
    env->DeleteLocalRef(brow0);
    if (bn != n) {
        jclass ex = env->FindClass("java/lang/IllegalArgumentException");
        env->ThrowNew(ex, "Subtraction requires same number of columns");
        return nullptr;
    }
    auto A = toVec(env, a, m, n);
    auto B = toVec(env, b, m, n);
    return toJni(env, subOp(A, B));
}

// MUL
JNIEXPORT jobjectArray JNICALL
Java_com_example_matrixcalci_viewmodel_MatrixViewModel_nativeMul(
        JNIEnv* env, jobject,
        jobjectArray a, jobjectArray b,
        jint m, jint k, jint p)
{
    if (env->GetArrayLength(b) != k) {
        jclass ex = env->FindClass("java/lang/IllegalArgumentException");
        env->ThrowNew(ex, "Multiplication requires A.cols == B.rows");
        return nullptr;
    }
    auto A = toVec(env, a, m, k);
    auto B = toVec(env, b, k, p);
    return toJni(env, mulOp(A, B));
}

// DIV
JNIEXPORT jobjectArray JNICALL
Java_com_example_matrixcalci_viewmodel_MatrixViewModel_nativeDiv(
        JNIEnv* env, jobject,
        jobjectArray a, jobjectArray b,
        jint m, jint k, jint p)
{
    if (k != p) {
        jclass ex = env->FindClass("java/lang/IllegalArgumentException");
        env->ThrowNew(ex, "Division requires B to be square");
        return nullptr;
    }
    auto A = toVec(env, a, m, k);
    auto B = toVec(env, b, k, p);
    auto Binv = inv(env, B);
    if (env->ExceptionCheck()) return nullptr;
    return toJni(env, mulOp(A, Binv));
}

}  // extern "C"
