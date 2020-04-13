package com.jacobstinson.countrieswiki.model.countries

import androidx.lifecycle.LiveData
import com.jacobstinson.countrieswiki.GetAllCountriesQuery
import com.jacobstinson.countrieswiki.GetCountriesQuery
import com.jacobstinson.countrieswiki.model.CountriesAPIService
import com.jacobstinson.countrieswiki.model.countries.models.Country
import com.jacobstinson.countrieswiki.model.util.AppExecutors
import com.jacobstinson.countrieswiki.model.util.NetworkBoundResource
import com.jacobstinson.countrieswiki.model.util.Resource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class CountriesRepository @Inject constructor(val webService: CountriesAPIService, val countriesDao: CountriesDao, val appExecutors: AppExecutors) {

    open fun getAllCountries(forceRefresh: Boolean): LiveData<Resource<List<Country>?>> = object: NetworkBoundResource<List<Country>, GetAllCountriesQuery.Data>(appExecutors) {
        override fun saveCallResult(item: GetAllCountriesQuery.Data) {
            countriesDao.save(Country.getCountries(item))
        }
        override fun shouldFetch(data: List<Country>?): Boolean {
            return data == null || data.isEmpty() || forceRefresh
        }
        override fun loadFromDb(): LiveData<List<Country>> {
            return countriesDao.loadCountries()
        }
        override fun createCall(): LiveData<Resource<GetAllCountriesQuery.Data>> {
            return webService.getAllCountries()
        }
    }.asLiveData()

    open fun getCountries(continentCode: String, forceRefresh: Boolean): LiveData<Resource<List<Country>?>> = object: NetworkBoundResource<List<Country>, GetCountriesQuery.Data>(appExecutors) {
        override fun saveCallResult(item: GetCountriesQuery.Data) {
            countriesDao.save(Country.getCountries(item))
        }
        override fun shouldFetch(data: List<Country>?): Boolean {
            return data == null || data.isEmpty() || forceRefresh
        }
        override fun loadFromDb(): LiveData<List<Country>> {
            return countriesDao.loadCountries(continentCode = continentCode)
        }
        override fun createCall(): LiveData<Resource<GetCountriesQuery.Data>> {
            return webService.getCountries(continentCode)
        }
    }.asLiveData()

//    open fun getCountry(countryCode: String): LiveData<Country> {
//        return countriesDao.loadCountry(countryCode)
//    }
}