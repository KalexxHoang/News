package com.android.news.util

object Constants {
    const val USER_SETTING = "userSetting"
    const val APP_ENTRY = "appEntry"
}

val Any.TAG: String
    get() = this::class.java.simpleName