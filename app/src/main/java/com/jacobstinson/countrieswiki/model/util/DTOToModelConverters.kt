package com.jacobstinson.countrieswiki.model.util

import com.jacobstinson.countrieswiki.GetAllCountriesQuery
import com.jacobstinson.countrieswiki.GetCountriesByContinentQuery
import com.jacobstinson.countrieswiki.model.countries.models.Country

object DTOToModelConverters {

    fun getCountriesFromAllCountriesQuery(getAllCountriesQueryData: GetAllCountriesQuery.Data): List<Country> {
        val countries = mutableListOf<Country>()

        for(getAllCountry in getAllCountriesQueryData.countries) {
            val country = getCountryFromAllCountriesQuery(getAllCountry)
            countries.add(country)
        }

        return countries
    }

    private fun getCountryFromAllCountriesQuery(getAllCountriesCountry: GetAllCountriesQuery.Country): Country {
        return Country(getAllCountriesCountry.code, getAllCountriesCountry.name, getAllCountriesCountry.phone,
            getAllCountriesCountry.capital, getAllCountriesCountry.currency, getAllCountriesCountry.continent.code,
            getAllCountriesCountry.continent.name, System.currentTimeMillis())
    }

    fun getCountriesFromCountriesByContinentQuery(getCountriesByContinentQuery: GetCountriesByContinentQuery.Data): List<Country> {
        val countries = mutableListOf<Country>()

        for(getAllCountry in getCountriesByContinentQuery.countries) {
            val country = getCountryFromCountriesByContinentQuery(getAllCountry)
            countries.add(country)
        }

        return countries
    }

    private fun getCountryFromCountriesByContinentQuery(getCountriesByContinentQuery: GetCountriesByContinentQuery.Country): Country {
        return Country(getCountriesByContinentQuery.code, getCountriesByContinentQuery.name, getCountriesByContinentQuery.phone,
            getCountriesByContinentQuery.capital, getCountriesByContinentQuery.currency, getCountriesByContinentQuery.continent.code,
            getCountriesByContinentQuery.continent.name, System.currentTimeMillis())
    }
}
