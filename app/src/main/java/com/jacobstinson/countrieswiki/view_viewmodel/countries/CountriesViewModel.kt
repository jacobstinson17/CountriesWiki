package com.jacobstinson.countrieswiki.view_viewmodel.countries

import androidx.lifecycle.*
import com.jacobstinson.countrieswiki.model.countries.CountriesRepository
import com.jacobstinson.countrieswiki.model.countries.models.Country
import com.jacobstinson.countrieswiki.model.countries.models.CountryParameters
import com.jacobstinson.countrieswiki.model.util.Resource
import javax.inject.Inject

class CountriesViewModel @Inject constructor(private val countriesRepo: CountriesRepository): ViewModel() {

    var orderByField = CountryParameters.DEFAULT_ORDER_BY_FIELD
        set(value) {
            field = value.toLowerCase()
        }
    val isDescending = MutableLiveData(CountryParameters.DEFAULT_IS_DESCENDING)
    val continentCode = MutableLiveData("")
    val countries = MediatorLiveData<Resource<List<Country>?>>()
    var countriesDataSource: LiveData<Resource<List<Country>?>> = MediatorLiveData<Resource<List<Country>?>>()

    init {
        refresh(false)
    }

    fun reverseSortDirection() {
        isDescending.value = !( isDescending.value!! )
    }

    fun refresh(forceRefresh: Boolean) {
        countries.removeSource(countriesDataSource)

        countriesDataSource = getCountries(forceRefresh)

        countries.addSource(countriesDataSource) { dataSource ->
            countries.value = dataSource
        }
    }

    private fun getCountries(forceRefresh: Boolean): LiveData<Resource<List<Country>?>> {
        val countryParameters = CountryParameters(orderByField, isDescending.value!!)

        return if(continentCode.value == "") {
            countriesRepo.getAllCountries(countryParameters, forceRefresh)
        } else {
            countriesRepo.getCountriesByContinent(countryParameters, continentCode.value!!, forceRefresh)
        }
    }
}