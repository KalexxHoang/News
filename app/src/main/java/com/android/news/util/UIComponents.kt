package com.android.news.util

sealed class UIComponents {
    data class Toast(val message: String): UIComponents()

    data class Dialog(val title: String, val message: String): UIComponents()

    data class None(val message: String = "None Effect"): UIComponents()
}