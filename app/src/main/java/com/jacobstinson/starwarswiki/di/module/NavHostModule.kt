package com.jacobstinson.starwarswiki.di.module

import com.jacobstinson.starwarswiki.di.InjectingNavHostFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class NavHostModule {

    @ContributesAndroidInjector(modules = [FragmentModule::class])
    abstract fun navHostFragmentInjector(): InjectingNavHostFragment
}