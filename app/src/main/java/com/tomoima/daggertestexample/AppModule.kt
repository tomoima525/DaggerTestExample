package com.tomoima.daggertestexample

import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val app: MyApplication) {
    @Provides
    @Singleton
    fun provideApp(): MyApplication = app
}