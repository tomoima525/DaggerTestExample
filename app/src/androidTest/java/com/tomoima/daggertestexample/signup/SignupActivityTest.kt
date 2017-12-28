package com.tomoima.daggertestexample.signup

import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.whenever
import com.tomoima.daggertestexample.*
import com.tomoima.daggertestexample.api.RegisterApi
import com.tomoima.daggertestexample.api.TestApiModule
import com.tomoima.daggertestexample.data.TestPrefModule
import com.tomoima.daggertestexample.data.UserPrefs
import com.tomoima.daggertestexample.model.User
import io.reactivex.Maybe
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.MockitoAnnotations
import javax.inject.Inject


@RunWith(AndroidJUnit4::class)
class SignupActivityTest {

    @get:Rule
    val testRule: ActivityTestRule<SignupActivity>
            = ActivityTestRule(SignupActivity::class.java, false, false)

    @Inject
    lateinit var registerApi: RegisterApi

    @Inject
    lateinit var userPref: UserPrefs

    private lateinit var testAppComponent: TestAppComponent

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        val app = InstrumentationRegistry.getTargetContext().applicationContext as MyApplication
        testAppComponent = DaggerTestAppComponent.builder()
                .appModule(AppModule(app))
                .apiModule(TestApiModule())
                .prefModule(TestPrefModule())
                .build()
        app.appComponent = testAppComponent
        testAppComponent.inject(this)
        RxAndroidPlugins.initMainThreadScheduler { Schedulers.trampoline() }

    }

    @After
    fun tearDown() {
        RxAndroidPlugins.reset()
    }

    @Test
    fun userInfo_returns_no_info_by_default() {
        // given
        // nothing is stored
        whenever(userPref.hasAge()).thenReturn(false)
        whenever(userPref.hasName()).thenReturn(false)

        // when
        testRule.launchActivity(null)

        // then
        onView(withId(R.id.user_info)).check(matches(withText("No info")))
    }

    @Test
    fun userInfo_returns_stored_userInfo() {
        // given
        // nothing is stored
        whenever(userPref.hasAge()).thenReturn(true)
        whenever(userPref.hasName()).thenReturn(true)
        whenever(userPref.name).thenReturn("Mike")
        whenever(userPref.age).thenReturn(20)

        // when
        testRule.launchActivity(null)

        // then
        onView(withId(R.id.user_info)).check(matches(withText("Current info: Name Mike Age 20")))
    }

    @Test
    fun userInfo_updates_with_input() {
        // given
        // api returns posted user
        val user = User("John", 10)
        whenever(registerApi.post(user)).thenReturn(Maybe.just(user))

        // nothing is stored
        whenever(userPref.hasAge()).thenReturn(false)
        whenever(userPref.hasName()).thenReturn(false)

        // when
        testRule.launchActivity(null)

        onView(withId(R.id.name)).perform(typeText("John"), closeSoftKeyboard())
        onView(withId(R.id.age)).perform(typeText("10"), closeSoftKeyboard())
        onView(withId(R.id.register)).perform(click())

        // then
        onView(withId(R.id.user_info)).check(matches(withText("Current info: Name John Age 10")))
    }

    @Test
    fun errorMessage_shows_with_illegal_input() {
        // given
        // api returns an error
        whenever(registerApi.post(any())).thenReturn(Maybe.error(IllegalArgumentException("error")))

        // nothing is stored
        whenever(userPref.hasAge()).thenReturn(false)
        whenever(userPref.hasName()).thenReturn(false)

        // when
        testRule.launchActivity(null)

        onView(withId(R.id.name)).perform(typeText(""), closeSoftKeyboard())
        onView(withId(R.id.age)).perform(typeText("10"), closeSoftKeyboard())
        onView(withId(R.id.register)).perform(click())

        // then
        onView(withId(R.id.warning)).check(matches(withText("error")))
        onView(withId(R.id.user_info)).check(matches(withText("No info")))
    }

}
