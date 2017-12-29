package com.tomoima.daggertestexample.signup

import com.tomoima.daggertestexample.model.User
import com.tomoima.daggertestexample.repository.UserRepository
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.processors.PublishProcessor


class SignupViewModel(private val userRepository: UserRepository) {

    val errorMessage = PublishProcessor.create<String>()

    /**
     * Action
     */
    fun update(name: String, age: String): Completable =
            userRepository.set(User(name, age.toInt()))
                    .doOnError{ errorMessage.onNext(it.message) }
                    .doOnComplete{ errorMessage.onNext("") }

    /**
     * Observable
     */
    fun observeUserInfo(): Flowable<User> = userRepository.observe()
}