package com.jacobstinson.countrieswiki.view_viewmodel.countries

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.jacobstinson.countrieswiki.model.CountriesAPIService
import com.jacobstinson.countrieswiki.model.util.Status
import javax.inject.Inject

class CountriesFragment @Inject constructor(private val viewModelFactory: ViewModelProvider.Factory): Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return super.onCreateView(inflater, container, savedInstanceState)
    }

}