package com.tomoima.daggertestexample.repository

import com.tomoima.daggertestexample.annotations.DebugOpenClass
import com.tomoima.daggertestexample.api.RegisterApi
import com.tomoima.daggertestexample.data.UserPrefs
import com.tomoima.daggertestexample.model.User
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.processors.BehaviorProcessor

@DebugOpenClass
class UserRepository(
        private val api: RegisterApi,
        private val userPref: UserPrefs) : Repository<User>() {
    private val processor = BehaviorProcessor.createDefault(User("", 0))

    init {
        initialization()
    }

    private fun initialization() {
        if (userPref.hasAge() && userPref.hasName()) {
            processor.onNext(User(userPref.name, userPref.age))
        }
    }

    override fun get(): User = processor.value

    override fun observe(): Flowable<User> {
        return processor
    }

    override fun set(value: User): Completable =
            api.post(value)
                    .doOnSuccess {
                        userPref.name = it.name
                        userPref.age = it.age
                        processor.onNext(it)
                    }
                    .ignoreElement()

}