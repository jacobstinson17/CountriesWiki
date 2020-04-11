package com.jacobstinson.countrieswiki.di

import android.content.Context
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import com.jacobstinson.countrieswiki.di.factory.MyFragmentFactory
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class InjectingNavHostFragment: NavHostFragment() {

    @Inject
    protected lateinit var daggerFragmentInjectionFactory: MyFragmentFactory

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        childFragmentManager.fragmentFactory = daggerFragmentInjectionFactory
        super.onCreate(savedInstanceState)
    }
}