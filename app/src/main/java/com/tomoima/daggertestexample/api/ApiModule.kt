package com.tomoima.daggertestexample.api

import com.tomoima.daggertestexample.annotations.DebugOpenClass
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@DebugOpenClass
@Module
class ApiModule {
    @Provides
    @Singleton
    fun provideRegisterApi() = RegisterApi()
}