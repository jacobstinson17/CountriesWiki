package com.jacobstinson.countrieswiki.view_viewmodel.countries

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jacobstinson.countrieswiki.model.countries.CountriesRepository
import com.jacobstinson.countrieswiki.model.countries.models.Country
import com.jacobstinson.countrieswiki.model.countries.models.CountryParameters
import com.jacobstinson.countrieswiki.model.util.Resource
import javax.inject.Inject

class CountriesViewModel @Inject constructor(private val countriesRepo: CountriesRepository): ViewModel() {

    val countryParameters: LiveData<CountryParameters> = MutableLiveData(CountryParameters())
    val continentCode: LiveData<String> = MutableLiveData<String>("")


    fun getAllCountries(forceRefresh: Boolean): LiveData<Resource<List<Country>?>> {
        return countriesRepo.getAllCountries(countryParameters.value!!, forceRefresh)
    }

    fun getCountriesByContinent(forceRefresh: Boolean): LiveData<Resource<List<Country>?>> {
        return countriesRepo.getCountriesByContinent(countryParameters.value!!, continentCode.value!!, forceRefresh)
    }
}