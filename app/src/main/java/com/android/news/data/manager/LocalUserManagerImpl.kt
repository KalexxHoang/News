package com.android.news.data.manager

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.android.news.domain.manager.LocalUserManager
import com.android.news.util.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalUserManagerImpl @Inject constructor (
    private val application: Application
): LocalUserManager {
    override suspend fun saveAppEntry() {
        application.dataStore.edit { setting ->
            setting[PreferenceKeys.APP_ENTRY] = true
        }
    }

    override fun readAppEntry(): Flow<Boolean> {
        return application.dataStore.data.map { preference ->
            preference[PreferenceKeys.APP_ENTRY] ?: false
        }
    }
}

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = Constants.USER_SETTING)

private object PreferenceKeys {
    val APP_ENTRY = booleanPreferencesKey(name = Constants.APP_ENTRY)
}