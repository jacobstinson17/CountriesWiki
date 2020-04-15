package com.jacobstinson.countrieswiki.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.*
import com.apollographql.apollo.exception.ApolloException
import com.jacobstinson.countrieswiki.GetAllCountriesQuery
import com.jacobstinson.countrieswiki.GetCountriesByContinentQuery
import com.jacobstinson.countrieswiki.model.util.Resource
import okhttp3.OkHttpClient

open class CountriesAPIService {

    /**********
    * Singleton
    **********/
    companion object {

        private val INSTANCE by lazy {
            CountriesAPIService()
        }

        fun getInstance(): CountriesAPIService {
            return INSTANCE
        }
    }



    /*******
    * Fields
    *******/
    private val baseUrl = "https://countries.trevorblades.com/"
    private val apolloClient = createApolloClient()



    /**********
    * EndPoints
    **********/
    open fun getAllCountries(): LiveData<Resource<GetAllCountriesQuery.Data>> {
        return createLiveDataQueryResponse(GetAllCountriesQuery())
    }

    open fun getCountriesByContinent(continentCode: String): LiveData<Resource<GetCountriesByContinentQuery.Data>> {
        return createLiveDataQueryResponse(GetCountriesByContinentQuery(continentCode.toInput()))
    }



    /****************
    * Private Methods
    ****************/
    private fun createApolloClient(): ApolloClient {
        val okHttp = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val original = chain.request()
                val builder = original.newBuilder().method(original.method(),
                    original.body())
                chain.proceed(builder.build())
            }
            .build()

        return ApolloClient.builder()
            .serverUrl(baseUrl)
            .okHttpClient(okHttp)
            .build()
    }

    private fun <D : Operation.Data, T, V : Operation.Variables> createLiveDataQueryResponse(query: Query<D, T, V>): LiveData<Resource<T>> {
        val liveData = MutableLiveData<Resource<T>>()
        liveData.value = Resource.loading(null)

        apolloClient.query(query).enqueue(object: ApolloCall.Callback<T>() {
            override fun onResponse(response: Response<T>) {
                liveData.postValue(Resource.success(response.data()))
            }

            override fun onFailure(e: ApolloException) {
                liveData.postValue(Resource.error(null))
            }
        })

        return liveData
    }
}