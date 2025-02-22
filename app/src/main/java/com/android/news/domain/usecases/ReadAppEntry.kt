package com.android.news.domain.usecases

import com.android.news.domain.manager.LocalUserManager
import javax.inject.Inject

class ReadAppEntry @Inject constructor(
    private val localUserManager: LocalUserManager
) {
    fun readAppEntry() {
        localUserManager.readAppEntry()
    }
}