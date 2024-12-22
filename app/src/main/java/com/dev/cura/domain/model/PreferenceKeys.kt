package com.dev.cura.domain.model

import androidx.datastore.preferences.core.stringPreferencesKey

object PreferencesKeys {
    val NAME = stringPreferencesKey("name")
    val EMAIL = stringPreferencesKey("email")
    val PHOTO = stringPreferencesKey("photo")
    val TOKEN = stringPreferencesKey("token")
}