package ru.xmagi.cpuminer

class NativeLib {

    /**
     * A native method that is implemented by the 'cpuminer' native library,
     * which is packaged with this application.
     */
    external fun stringFromJNI(): String

    companion object {
        // Used to load the 'cpuminer' library on application startup.
        init {
            System.loadLibrary("cpuminer")
        }
    }
}