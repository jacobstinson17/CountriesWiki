package com.jacobstinson.starwarswiki.di.module

import android.app.Application
import com.jacobstinson.starwarswiki.StarWarsWikiApp
import com.jacobstinson.starwarswiki.view_viewmodel.main.MainActivity
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class AppModule {

    @Binds
    abstract fun bindApp(app: StarWarsWikiApp): Application

    @ContributesAndroidInjector(modules = [NavHostModule::class])
    abstract fun mainActivityInjector(): MainActivity
}