package com.dev.cura.util

import android.content.Context
import android.content.SharedPreferences

class SharedPrefsHelper(context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)

    fun getUserName(): String? {
        return prefs.getString("name", null)
    }

    fun getUserToken(): String? {
        return prefs.getString("token", null)
    }

    fun getUserEmail(): String? {
        return prefs.getString("email", null)
    }

    fun getUserPhoto(): String? {
        return prefs.getString("photo", null)
    }

    fun getHasUserCompletedSurvey(): Boolean {
        return prefs.getBoolean("hasCompletedSurvey", false)
    }

}
