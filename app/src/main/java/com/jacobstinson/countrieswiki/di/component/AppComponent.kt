package com.jacobstinson.countrieswiki.di.component

import com.jacobstinson.countrieswiki.CountriesWikiApp
import com.jacobstinson.countrieswiki.di.module.*
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidSupportInjectionModule::class, AppModule::class, DatabaseModule::class,
    FragmentModule::class, NavHostModule::class, ViewModelModule::class, WebServiceModule::class])
interface AppComponent: AndroidInjector<CountriesWikiApp> {

    @Component.Factory
    abstract class Factory: AndroidInjector.Factory<CountriesWikiApp>
}