package com.fitin.preferences

import android.content.Context
import android.content.SharedPreferences

class PrefManager (context: Context) {

    private val PREFS_NAME = "FitIN.pref"
    private var sharedPref: SharedPreferences
    val editor: SharedPreferences.Editor

    init {
        sharedPref = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        editor = sharedPref.edit()
    }

    fun put(key: String, value: String) {
        editor.putString(key, value)
            .apply()
    }

    fun put(key: String, value: Int) {
        editor.putInt(key, value)
            .apply()
    }

    fun getString(key: String): String? {
        return sharedPref.getString(key, null)
    }

    fun getInt(key: String): Int {
        return sharedPref.getInt(key, 0)
    }

    fun clear() {
        editor.clear()
            .apply()
    }
}