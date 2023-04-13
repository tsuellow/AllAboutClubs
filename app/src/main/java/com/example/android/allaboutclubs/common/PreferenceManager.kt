package com.example.android.allaboutclubs.common

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferenceManager @Inject constructor(@ApplicationContext val context: Context) {

    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("preferencesStore")
        val LAST_UPDATED = stringPreferencesKey("last_update")
    }

    suspend fun saveDateTag(lastUpdated: String) {
        context.dataStore.edit { preferences ->
            preferences[LAST_UPDATED] = lastUpdated
        }
    }

    suspend fun getLastUpdated(): String {
        val flow = context.dataStore.data.map { preferences ->
                preferences[LAST_UPDATED] ?: ""
        }
        return flow.first()
    }
}