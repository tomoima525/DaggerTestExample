package com.tomoima.daggertestexample.data

import com.tomoima.daggertestexample.MyApplication
import org.mockito.Mockito


class TestPrefModule: PrefModule() {

    override fun provideUserPref(application: MyApplication): UserPrefs {
        return Mockito.mock(UserPrefs::class.java)
    }
}