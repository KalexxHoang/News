package com.android.news.domain.usecases

import com.android.news.domain.manager.LocalUserManager
import javax.inject.Inject

class SaveAppEntry @Inject constructor(
    private val localUserManager: LocalUserManager
) {
    suspend fun saveAppEntry() {
        localUserManager.saveAppEntry()
    }
}