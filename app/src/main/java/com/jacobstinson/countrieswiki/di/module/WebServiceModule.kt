package com.jacobstinson.countrieswiki.di.module

import com.jacobstinson.countrieswiki.model.CountriesAPIService
import dagger.Module
import dagger.Provides
import java.util.concurrent.Executor
import java.util.concurrent.Executors

@Module
class WebServiceModule {

    @Provides
    fun providesExecutor(): Executor {
        return Executors.newFixedThreadPool(5)
    }

    @Provides
    fun providesCountriesAPIService(): CountriesAPIService {
        return CountriesAPIService.getInstance()
    }
}