package com.alexxx_hoang.news.data.manager

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.alexxx_hoang.news.domain.manager.LocalUserManager
import com.alexxx_hoang.news.util.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import com.alexxx_hoang.news.util.TAG

class LocalUserManagerImpl @Inject constructor (
    private val application: Application
): LocalUserManager {
    override suspend fun saveAppEntry() {
        Log.d(TAG, "Save app")
        application.dataStore.edit { setting ->
            setting[PreferenceKeys.APP_ENTRY] = true
        }
    }

    override fun readAppEntry(): Flow<Boolean> {
        Log.d(TAG, "Read app")
        return application.dataStore.data.map { preference ->
            preference[PreferenceKeys.APP_ENTRY] ?: false
        }
    }
}

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = Constants.USER_SETTING)

private object PreferenceKeys {
    val APP_ENTRY = booleanPreferencesKey(name = Constants.APP_ENTRY)
}