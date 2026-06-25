package com.appsworld.marketwatch.auth.data

import android.content.Context
import android.util.Base64
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.crypto.tink.Aead
import com.google.crypto.tink.KeyTemplates
import com.google.crypto.tink.RegistryConfiguration
import com.google.crypto.tink.aead.AeadConfig
import com.google.crypto.tink.integration.android.AndroidKeysetManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private val Context.tokenDataStore: DataStore<Preferences> by preferencesDataStore(name = "kite_token_store")

private val ACCESS_TOKEN = stringPreferencesKey("access_token")

private const val KEYSET_NAME = "kite_token_keyset"
private const val KEYSET_PREFS = "kite_token_keyset_prefs"
private const val MASTER_KEY_URI = "android-keystore://kite_token_master_key"

/**
 * Encrypted storage for the single Kite access token.
 *
 * The token bytes are encrypted with a Tink [Aead] primitive whose data-encryption keyset is wrapped
 * by a non-exportable master key in the Android Keystore. Ciphertext (Base64) is persisted in a
 * Preferences DataStore.
 */
class SecureTokenStore(context: Context) {

    private val dataStore = context.tokenDataStore

    private val aead: Aead by lazy {
        AeadConfig.register()
        AndroidKeysetManager.Builder()
            .withSharedPref(context, KEYSET_NAME, KEYSET_PREFS)
            .withKeyTemplate(KeyTemplates.get("AES256_GCM"))
            .withMasterKeyUri(MASTER_KEY_URI)
            .build()
            .keysetHandle
            .getPrimitive(RegistryConfiguration.get(), Aead::class.java)
    }

    // TODO: make this flow resilient — catch IOException from dataStore.data (emit emptyPreferences)
    //  and treat decrypt() failures (invalidated keyset / Keystore reset / restored app data) as a
    //  null token instead of letting the exception propagate to collectors.
    val token: Flow<String?> = dataStore.data.map { prefs ->
        prefs[ACCESS_TOKEN]?.let(::decrypt)
    }

    suspend fun saveToken(value: String) {
        val encrypted = encrypt(value)
        dataStore.edit { it[ACCESS_TOKEN] = encrypted }
    }

    suspend fun clear() {
        dataStore.edit { it.remove(ACCESS_TOKEN) }
    }

    private fun encrypt(plaintext: String): String {
        val cipher = aead.encrypt(plaintext.toByteArray(Charsets.UTF_8), ByteArray(0))
        return Base64.encodeToString(cipher, Base64.NO_WRAP)
    }

    private fun decrypt(stored: String): String {
        val cipher = Base64.decode(stored, Base64.NO_WRAP)
        return String(aead.decrypt(cipher, ByteArray(0)), Charsets.UTF_8)
    }
}
