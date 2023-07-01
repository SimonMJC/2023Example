package com.example.wit.utils.preference

import android.content.Context
import android.content.SharedPreferences
import com.example.wit.di.KoinApplication
import com.example.wit.R

object PrefUtils {
    val preferences: SharedPreferences =
        KoinApplication.appContext.getSharedPreferences(
            KoinApplication.string(R.string.app_name),
            Context.MODE_PRIVATE
        )

    fun set(key: String, value: Any?) {
        if (value == null) {
            return
        }

        val edit = preferences.edit()
        when (value) {
            is String -> edit.putString(key, value)
            is Long -> edit.putLong(key, value)
            is Int -> edit.putInt(key, value)
            is Boolean -> edit.putBoolean(key, value)
            is Float -> edit.putFloat(key, value)
        }

        edit.apply()
    }

    /**
     * 제거 필요 대상 set(key, value)와 동일 역할 함수
     */
    fun setCommit(key: String, value: Any?) {
        if (value == null) {
            return
        }

        val edit = preferences.edit()
        when (value) {
            is String -> edit.putString(key, value)
            is Long -> edit.putLong(key, value)
            is Int -> edit.putInt(key, value)
            is Boolean -> edit.putBoolean(key, value)
            is Float -> edit.putFloat(key, value)
        }

        edit.commit()
    }

    fun set(key: String, value: Any, edit: SharedPreferences.Editor) {
        when (value) {
            is String -> edit.putString(key, value)
            is Long -> edit.putLong(key, value)
            is Int -> edit.putInt(key, value)
            is Boolean -> edit.putBoolean(key, value)
            is Float -> edit.putFloat(key, value)
        }
    }

    //inline + reified : 런타임 타입 검사(실체화한 타입 파라미터)
    inline fun <reified T : Any> get(key: String, defaultValue: T): T = when (T::class) {
        String::class -> preferences.getString(key, defaultValue as String) as T
        Long::class -> preferences.getLong(key, defaultValue as Long) as T
        Int::class -> preferences.getInt(key, defaultValue as Int) as T
        Boolean::class -> preferences.getBoolean(key, defaultValue as Boolean) as T
        Float::class -> preferences.getFloat(key, defaultValue as Float) as T
        else -> defaultValue
    }

    fun clear() {
        // 지워지는 시점이 동기적이야해서 일부러 commit() 으로 했습니다.
        preferences.edit().clear().commit()
    }

    fun remove(key: String, edit: SharedPreferences.Editor) {
        edit.remove(key)
    }

    //사용예 - PrefUtils.open().set("test1", "aaa").set("test2", "bbb").set("test3", "ccc").close()
    class open {
        private val edit: SharedPreferences.Editor = preferences.edit()
        fun set(key: String, value: Any?): open {
            if (value != null) {
                set(key, value, edit)
            }

            return this
        }

        fun remove(key: String): open {
            edit.remove(key)
            return this
        }

        fun close() {
            edit.apply()
        }
    }
}