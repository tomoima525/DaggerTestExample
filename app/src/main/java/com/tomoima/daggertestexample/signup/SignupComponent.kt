package com.tomoima.daggertestexample.signup

import com.tomoima.daggertestexample.di.ActivityScope
import com.tomoima.daggertestexample.repository.UserRepository
import dagger.Provides
import dagger.Subcomponent


@ActivityScope
@Subcomponent(modules = arrayOf(SignupComponent.Module::class))
interface SignupComponent {

    fun inject(activity: SignupActivity)

    @dagger.Module
    class Module {
        @Provides
        fun provideSignupViewModel(userRepository: UserRepository): SignupViewModel {
            return SignupViewModel(userRepository)
        }
    }
}
