package com.bpjstk.deviceid.session

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.preference.PreferenceManager

object  RegisPreferences {
    private const val PREF_KEY = "com.bpjstk.deviceid.session.shared"

    fun saveString(context: Context, key: String, value: String) {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        sharedPreferences.edit().putString(PREF_KEY + key, value).apply()
    }

    fun getString(context: Context, key: String, defaultValue: String): String {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        return sharedPreferences.getString(PREF_KEY + key, defaultValue) ?: defaultValue
    }
    fun clearData(context: Context) {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        sharedPreferences.edit().clear().apply()
    }

}