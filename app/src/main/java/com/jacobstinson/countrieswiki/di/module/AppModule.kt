package com.jacobstinson.countrieswiki.di.module

import android.app.Application
import com.jacobstinson.countrieswiki.CountriesWikiApp
import com.jacobstinson.countrieswiki.view_viewmodel.main.MainActivity
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class AppModule {

    @Binds
    abstract fun bindApp(app: CountriesWikiApp): Application

    @ContributesAndroidInjector(modules = [NavHostModule::class])
    abstract fun mainActivityInjector(): MainActivity
}