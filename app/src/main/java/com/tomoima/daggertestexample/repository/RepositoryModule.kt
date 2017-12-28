package com.tomoima.daggertestexample.repository

import com.tomoima.daggertestexample.api.RegisterApi
import com.tomoima.daggertestexample.data.UserPrefs
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideUserRepository(api: RegisterApi, userPrefs: UserPrefs): UserRepository
            = UserRepository(api, userPrefs)
}