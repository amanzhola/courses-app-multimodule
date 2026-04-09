package com.example.coursesapp.core.ui

import android.content.Context
import androidx.annotation.StringRes

sealed interface UiText {
    data class DynamicString(val value: String) : UiText
    data class StringResource(@StringRes val resId: Int) : UiText
}

fun UiText.asString(context: Context): String {
    return when (this) {
        is UiText.DynamicString -> value
        is UiText.StringResource -> context.getString(resId)
    }
}