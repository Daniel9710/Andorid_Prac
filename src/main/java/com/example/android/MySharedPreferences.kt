package com.example.android

import android.content.Context
import android.content.SharedPreferences

class MySharedPreferences(context: Context) {

    val PREFS_FILENAME = "prefs"
    val prefs: SharedPreferences = context.getSharedPreferences(PREFS_FILENAME, 0)

    val PREF_KEY_SET_TEXT = "setText"
    val PREF_KEY_SET_COLOR = "setColor"

    var setText: Boolean
        get() = prefs.getBoolean(PREF_KEY_SET_TEXT,false)
        set(value) = prefs.edit().putBoolean(PREF_KEY_SET_TEXT, value).apply()
    var setColor: Boolean
        get() = prefs.getBoolean(PREF_KEY_SET_COLOR,false)
        set(value) = prefs.edit().putBoolean(PREF_KEY_SET_COLOR, value).apply()
}