package com.tomoima.daggertestexample.api

import com.tomoima.daggertestexample.annotations.DebugOpenClass
import com.tomoima.daggertestexample.model.User
import io.reactivex.Maybe

@DebugOpenClass
class RegisterApi {

    fun post(user: User): Maybe<User> = Maybe.create<User> {
        if (user.name.isEmpty()) {
            it.onError(IllegalArgumentException("Name invalid"))
        }
        it.onSuccess(user)
    }

}