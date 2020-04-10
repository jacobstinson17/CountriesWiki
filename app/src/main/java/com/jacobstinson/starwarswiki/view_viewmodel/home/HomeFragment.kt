package com.jacobstinson.starwarswiki.view_viewmodel.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.jacobstinson.starwarswiki.CountriesQuery
import okhttp3.OkHttpClient
import javax.inject.Inject

class HomeFragment @Inject constructor(private val viewModelFactory: ViewModelProvider.Factory): Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        getCountries()

        return super.onCreateView(inflater, container, savedInstanceState)
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