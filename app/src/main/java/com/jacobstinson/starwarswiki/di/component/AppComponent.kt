package com.jacobstinson.starwarswiki.di.component

import com.jacobstinson.starwarswiki.StarWarsWikiApp
import com.jacobstinson.starwarswiki.di.module.*
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Component(modules = [AndroidSupportInjectionModule::class, AppModule::class, DatabaseModule::class, FragmentModule::class,
    NavHostModule::class, ViewModelModule::class, WebServiceModule::class])
@Singleton
interface AppComponent: AndroidInjector<StarWarsWikiApp> {

    @Component.Factory
    abstract class Factory: AndroidInjector.Factory<StarWarsWikiApp>
}