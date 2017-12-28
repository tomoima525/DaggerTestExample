package com.tomoima.daggertestexample.signup

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.jakewharton.rxbinding2.view.RxView
import com.tomoima.daggertestexample.MyApplication
import com.tomoima.daggertestexample.R
import io.reactivex.BackpressureStrategy
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SignupActivity : AppCompatActivity() {

    @Inject
    lateinit var signupViewModel: SignupViewModel

    @BindView(R.id.name)
    lateinit var name: TextView

    @BindView(R.id.age)
    lateinit var age: TextView

    @BindView(R.id.user_info)
    lateinit var userInfo: TextView

    @BindView(R.id.warning)
    lateinit var warning: TextView

    @BindView(R.id.register)
    lateinit var registerBtn: Button

    private val disposables: CompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        (application as MyApplication).appComponent
                .plus(SignupComponent.Module())
                .inject(this)

        ButterKnife.bind(this)

        val registerInput = RxView.clicks(registerBtn).toFlowable(BackpressureStrategy.LATEST)

        disposables.addAll(
                registerInput
                        .observeOn(Schedulers.io())
                        .flatMapCompletable { _ ->
                            signupViewModel.update(name.text.toString(), age.text.toString())
                        }
                        .subscribe({ }, { println(it) }),
                signupViewModel.observeUserInfo()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                {
                                    if (it.name.isEmpty()) {
                                        userInfo.text = "No info"
                                    } else {
                                        userInfo.text =
                                                "Current info: Name ${it.name} Age ${it.age}"
                                    }
                                },
                                { }
                        ),
                signupViewModel.errorMessage
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                { warning.text = it },
                                { }
                        )
        )

    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }
}
