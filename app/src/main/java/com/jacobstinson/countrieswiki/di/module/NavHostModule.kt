package com.jacobstinson.countrieswiki.di.module

import com.jacobstinson.countrieswiki.di.InjectingNavHostFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class NavHostModule {

    @ContributesAndroidInjector(modules = [FragmentModule::class])
    abstract fun navHostFragmentInjector(): InjectingNavHostFragment
}