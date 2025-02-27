package com.android.news.util

object Constants {
    const val USER_SETTING = "userSetting"
    const val APP_ENTRY = "appEntry"

    const val BASE_URL = "https://newsapi.org/v2/"
    const val API_KEY = "fee38bf418b04948b7b372c045fdf499"
}

val Any.TAG: String
    get() = this::class.java.simpleName