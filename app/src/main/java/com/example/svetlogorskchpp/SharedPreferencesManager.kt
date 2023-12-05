package com.example.svetlogorskchpp

import android.content.Context
import android.content.SharedPreferences

object SharedPreferencesManager {

    private const val PREF_NAME = "MySharedPrefs"
    private lateinit var _sharedPreferences: SharedPreferences
    val sharedPreferences: SharedPreferences get() = _sharedPreferences!!

    fun init(context: Context) {
        _sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun saveString(key: String, value: String) {
        _sharedPreferences.edit().putString(key, value).apply()
    }

    fun getString(key: String, defaultValue: String): String {
        return _sharedPreferences.getString(key, defaultValue) ?: defaultValue
    }

    fun saveLong(key: String, value: Long) {
        _sharedPreferences.edit().putLong(key, value).apply ()
    }

    fun getLong(key: String, defaultValue: Long): Long {
        return _sharedPreferences.getLong(key, defaultValue) ?: defaultValue
    }

}