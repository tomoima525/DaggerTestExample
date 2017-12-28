package com.tomoima.daggertestexample.data

import android.content.Context
import android.content.SharedPreferences


abstract class BasePreferences {
    private lateinit var prefs: SharedPreferences

    protected fun init(context: Context, tableName: String) {
        this.prefs = context.getSharedPreferences(tableName, Context.MODE_PRIVATE)
    }

    protected fun getString(key: String, value: String): String = prefs.getString(key, value)

    protected fun putString(key: String, value: String) {
        prefs.edit().putString(key, value).apply()
    }

    protected fun has(key: String): Boolean {
        return prefs.contains(key)
    }

    protected fun getInt(key: String, defValue: Int): Int = prefs.getInt(key, defValue)

    protected fun putInt(key: String, value: Int) {
        prefs.edit().putInt(key, value).apply()
    }

}