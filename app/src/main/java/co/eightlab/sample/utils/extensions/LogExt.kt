package co.eightlab.sample.utils.extensions

import android.util.Log
import co.eightlab.sample.BuildConfig

fun v(tag: String, message: String) {
    if (BuildConfig.DEBUG)
        Log.v(tag, message)
}

fun d(tag: String, message: String) {
    if (BuildConfig.DEBUG)
        Log.d(tag, message)
}

fun i(tag: String, message: String) {
    if (BuildConfig.DEBUG)
        Log.i(tag, message)
}

fun w(tag: String, message: String) {
    if (BuildConfig.DEBUG)
        Log.w(tag, message)
}

fun e(tag: String, message: String) {
    if (BuildConfig.DEBUG)
        Log.e(tag, message)
}

fun wtf(tag: String, message: String) {
    if (BuildConfig.DEBUG)
        Log.wtf(tag, message)
}

fun Any.v(message: String) {
    if (BuildConfig.DEBUG)
        Log.v(this.javaClass.name, message)
}

fun Any.d(message: String) {
    if (BuildConfig.DEBUG)
        Log.d(this.javaClass.name, message)
}

fun Any.i(message: String) {
    if (BuildConfig.DEBUG)
        Log.i(this.javaClass.name, message)
}

fun Any.w(message: String) {
    if (BuildConfig.DEBUG)
        Log.w(this.javaClass.name, message)
}

fun Any.e(message: String) {
    if (BuildConfig.DEBUG)
        Log.e(this.javaClass.name, message)
}

fun Any.wtf(message: String) {
    if (BuildConfig.DEBUG)
        Log.wtf(this.javaClass.name, message)
}