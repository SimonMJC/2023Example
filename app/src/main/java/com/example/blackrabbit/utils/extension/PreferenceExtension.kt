package com.example.blackrabbit.utils.extension

import com.example.blackrabbit.utils.preference.PrefUtils
import com.example.blackrabbit.utils.preference.PreferenceKey
import kotlin.reflect.KClass

@Target(AnnotationTarget.PROPERTY, AnnotationTarget.TYPE)
annotation class RequirePreferenceKeys(val targetClass: KClass<out Any>)


fun Pair<@RequirePreferenceKeys(PreferenceKey::class) String, Any>.setPreference() {
    PrefUtils.set(this.first, this.second)
}


fun @RequirePreferenceKeys(PreferenceKey::class) String.getPreference(): Any = PrefUtils.get(this, Any())
