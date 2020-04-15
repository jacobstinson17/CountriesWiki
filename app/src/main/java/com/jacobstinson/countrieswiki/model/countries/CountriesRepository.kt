package com.jacobstinson.countrieswiki.model.countries

import androidx.lifecycle.LiveData
import com.jacobstinson.countrieswiki.GetAllCountriesQuery
import com.jacobstinson.countrieswiki.GetCountriesByContinentQuery
import com.jacobstinson.countrieswiki.model.CountriesAPIService
import com.jacobstinson.countrieswiki.model.countries.models.Country
import com.jacobstinson.countrieswiki.model.countries.models.CountryParameters
import com.jacobstinson.countrieswiki.model.countries.models.hasOutdatedData
import com.jacobstinson.countrieswiki.model.util.AppExecutors
import com.jacobstinson.countrieswiki.model.util.NetworkBoundResource
import com.jacobstinson.countrieswiki.model.util.Resource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class CountriesRepository @Inject constructor(val webService: CountriesAPIService, val countriesDao: CountriesDao, val appExecutors: AppExecutors) {

    open fun getAllCountries(parameters: CountryParameters, forceRefresh: Boolean): LiveData<Resource<List<Country>?>>
            = object: NetworkBoundResource<List<Country>, GetAllCountriesQuery.Data>(appExecutors) {
        override fun saveCallResult(item: GetAllCountriesQuery.Data) {
            countriesDao.save(Country.getCountriesFromAllCountriesQuery(item))
        }
        override fun shouldFetch(data: List<Country>?): Boolean {
            return data == null || data.isEmpty() || forceRefresh || data.hasOutdatedData(parameters.minLastRefreshMs)
        }
        override fun loadFromDb(): LiveData<List<Country>> {
            return countriesDao.loadCountries(parameters, null)
        }
        override fun createCall(): LiveData<Resource<GetAllCountriesQuery.Data>> {
            return webService.getAllCountries()
        }
    }.asLiveData()

    open fun getCountriesByContinent(parameters: CountryParameters, continentCode: String, forceRefresh: Boolean): LiveData<Resource<List<Country>?>>
            = object: NetworkBoundResource<List<Country>, GetCountriesByContinentQuery.Data>(appExecutors) {
        override fun saveCallResult(item: GetCountriesByContinentQuery.Data) {
            countriesDao.save(Country.getCountriesFromCountriesByContinentQuery(item))
        }
        override fun shouldFetch(data: List<Country>?): Boolean {
            return data == null || data.isEmpty() || forceRefresh || data.hasOutdatedData(parameters.minLastRefreshMs)
        }
        override fun loadFromDb(): LiveData<List<Country>> {
            return countriesDao.loadCountries(parameters, continentCode)
        }
        override fun createCall(): LiveData<Resource<GetCountriesByContinentQuery.Data>> {
            return webService.getCountriesByContinent(continentCode)
        }
    }.asLiveData()

    open fun getCountry(countryCode: String): LiveData<Country?> {
        return countriesDao.loadCountry(countryCode)
    }
}