package com.jacobstinson.countrieswiki.view_viewmodel.home

import androidx.lifecycle.ViewModel
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.jacobstinson.countrieswiki.CountriesQuery
import okhttp3.OkHttpClient
import javax.inject.Inject

class HomeViewModel @Inject constructor(): ViewModel() {

    init {
        getCountries()
    }

    fun getCountries() {
        var client = setupApollo()
        client.query(CountriesQuery()).enqueue(object: ApolloCall.Callback<CountriesQuery.Data>() {
            override fun onResponse(response: Response<CountriesQuery.Data>) {
                var temp = "temp"
            }

            override fun onFailure(e: ApolloException) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })
    }

    private fun setupApollo(): ApolloClient {
        val okHttp = OkHttpClient
            .Builder()
            .addInterceptor({ chain ->
                val original = chain.request()
                val builder = original.newBuilder().method(original.method(),
                    original.body())
                chain.proceed(builder.build())
            })
            .build()
        return ApolloClient.builder()
            .serverUrl("https://countries.trevorblades.com/")
            .okHttpClient(okHttp)
            .build()
    }
}