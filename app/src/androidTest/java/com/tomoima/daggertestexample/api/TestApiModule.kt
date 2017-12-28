package com.tomoima.daggertestexample.api

import org.mockito.Mockito


class TestApiModule : ApiModule() {

    override fun provideRegisterApi(): RegisterApi = Mockito.mock(RegisterApi::class.java)

}