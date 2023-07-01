package com.example.wit.utils.extension

fun Long.isZero(): Boolean {
    return this == 0L
}

fun Int.isZero(): Boolean {
    return this == 0
}

fun Any?.valueLong(): Long {
    return when (this) {
        is String? -> return try {
            val value = this as String
            if (value.isEmpty()) {
                return 0
            }

            value.toLong()
        } catch (e: Exception) {
            0
        }
        is Int? -> return try {
            val value = this as Int
            if (value.isZero()) {
                return 0
            }

            value.toLong()
        } catch (e: Exception) {
            0
        }
        else -> 0
    }
}