package com.jacobstinson.countrieswiki.di.module

import com.apollographql.apollo.ApolloClient
import com.jacobstinson.countrieswiki.model.CountriesAPIService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import java.util.concurrent.Executor
import java.util.concurrent.Executors

@Module
class WebServiceModule {

    private val baseUrl = "https://countries.trevorblades.com/"

    @Provides
    fun providesExecutor(): Executor {
        return Executors.newFixedThreadPool(5)
    }

    @Provides
    fun providesCountriesAPIService(apolloClient: ApolloClient): CountriesAPIService {
        return CountriesAPIService(apolloClient)
    }

    @Provides
    fun provideApolloClient(): ApolloClient {
        val okHttp = OkHttpClient.Builder().addInterceptor { chain ->
                val original = chain.request()
                val builder = original.newBuilder().method(original.method(), original.body())

                chain.proceed(builder.build())
            }.build()

        return ApolloClient.builder()
            .serverUrl(baseUrl)
            .okHttpClient(okHttp)
            .build()
    }
}