package com.alexxx_hoang.news.domain.usecases.appentry

import com.alexxx_hoang.news.domain.manager.LocalUserManager
import javax.inject.Inject

class ReadAppEntry @Inject constructor(
    private val localUserManager: LocalUserManager
) {
    operator fun invoke() = localUserManager.readAppEntry()
}