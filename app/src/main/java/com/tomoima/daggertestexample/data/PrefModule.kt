package com.tomoima.daggertestexample.data

import com.tomoima.daggertestexample.MyApplication
import com.tomoima.daggertestexample.annotations.DebugOpenClass
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@DebugOpenClass
@Module
class PrefModule {
    @Provides
    @Singleton
    fun provideUserPref(application: MyApplication): UserPrefs = UserPrefs(application)

}