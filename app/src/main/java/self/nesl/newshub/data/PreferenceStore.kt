package self.nesl.newshub.data

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton


private val Context.dataStore by preferencesDataStore("preference_store")

@Singleton
class PreferenceStore @Inject constructor(
    @ApplicationContext appContext: Context
)  {
    private object Keys {
        val KEY_ID_TOKEN = stringPreferencesKey("KEY_ID_TOKEN")
    }

    private val dataStore = appContext.dataStore

    suspend fun saveToken(
        idToken: String,
    ) {
        dataStore.edit { preferences ->
            preferences[Keys.KEY_ID_TOKEN] = idToken
        }
    }

    val observeTokens = dataStore.data
        .catch { exception ->
            // dataStore.data throws an IOException when an error is encountered when reading data
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            mapTokensPreferences(preferences)
        }

    private fun mapTokensPreferences(preferences: Preferences): Tokens {
        return Tokens(
            preferences[Keys.KEY_ID_TOKEN] ?: "",
        )
    }

    data class Tokens(
        val idToken: String,
    )
}
