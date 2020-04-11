package com.jacobstinson.countrieswiki.view_viewmodel.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.jacobstinson.countrieswiki.CountriesQuery
import com.jacobstinson.countrieswiki.model.CountriesAPIService
import com.jacobstinson.countrieswiki.model.util.Status
import okhttp3.OkHttpClient
import javax.inject.Inject

class HomeFragment @Inject constructor(private val viewModelFactory: ViewModelProvider.Factory): Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        CountriesAPIService.getInstance().getCountries().observe(this) { data ->
            when(data.status) {
                Status.SUCCESS -> {
                    val list = data.data
                }
                Status.ERROR -> {
                    val list = data.data
                }
                Status.LOADING -> {
                    val list = data.data
                }
            }
        }

        return super.onCreateView(inflater, container, savedInstanceState)
    }

}