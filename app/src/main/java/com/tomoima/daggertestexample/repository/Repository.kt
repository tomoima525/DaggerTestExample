package com.tomoima.daggertestexample.repository

import io.reactivex.Completable
import io.reactivex.Flowable


abstract class Repository<T> {
    abstract fun get(): T
    abstract fun observe(): Flowable<T>
    abstract fun set(value :T): Completable
}