package com.jacobstinson.starwarswiki.di.module

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
}