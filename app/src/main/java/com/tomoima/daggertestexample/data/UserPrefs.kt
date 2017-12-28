package com.tomoima.daggertestexample.data

import android.app.Application
import com.tomoima.daggertestexample.annotations.DebugOpenClass

@DebugOpenClass
class UserPrefs(application: Application) : BasePreferences() {
    init {
        super.init(application, "User")
    }

    var name: String
    get() = getString("name", "")
    set(name) = putString("name", name)

    fun hasName(): Boolean {
        return has("name")
    }

    var age: Int
    get() = getInt("age", 0)
    set(age) = putInt("age", age)

    fun hasAge(): Boolean {
        return has("age")
    }

}