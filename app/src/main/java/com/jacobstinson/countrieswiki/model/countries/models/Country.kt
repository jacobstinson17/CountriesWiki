package com.jacobstinson.countrieswiki.model.countries.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jacobstinson.countrieswiki.GetAllCountriesQuery
import com.jacobstinson.countrieswiki.GetCountriesByContinentQuery
import com.jacobstinson.countrieswiki.model.util.RefreshData
import java.util.*

@Entity
data class Country(@PrimaryKey val code: String, val name: String, val phone: String, val capital: String?,
              val currency: String?, val continentCode: String, val continentName: String, var lastRefresh: Date) {

    companion object {
        fun getCountriesFromAllCountriesQuery(getAllCountriesQueryData: GetAllCountriesQuery.Data): List<Country> {
            val countries = mutableListOf<Country>()

            for(getAllCountry in getAllCountriesQueryData.countries) {
                countries.add(getCountryFromAllCountriesQuery(getAllCountry))
            }

            return countries
        }

        private fun getCountryFromAllCountriesQuery(getAllCountriesCountry: GetAllCountriesQuery.Country): Country {
            return Country(getAllCountriesCountry.code, getAllCountriesCountry.name, getAllCountriesCountry.phone, getAllCountriesCountry.capital,
                getAllCountriesCountry.currency, getAllCountriesCountry.continent.code, getAllCountriesCountry.continent.name, Date())
        }

        fun getCountriesFromCountriesByContinentQuery(getCountriesByContinentQuery: GetCountriesByContinentQuery.Data): List<Country> {
            val countries = mutableListOf<Country>()

            for(getAllCountry in getCountriesByContinentQuery.countries) {
                countries.add(getCountryFromCountriesByContinentQuery(getAllCountry))
            }

            return countries
        }

        private fun getCountryFromCountriesByContinentQuery(getCountriesByContinentQuery: GetCountriesByContinentQuery.Country): Country {
            return Country(getCountriesByContinentQuery.code, getCountriesByContinentQuery.name, getCountriesByContinentQuery.phone, getCountriesByContinentQuery.capital,
                getCountriesByContinentQuery.currency, getCountriesByContinentQuery.continent.code, getCountriesByContinentQuery.continent.name, Date())
        }
    }
}

fun List<Country>.hasOutdatedData(minRefreshTime: Long): Boolean {
    return this.filter { country -> country.lastRefresh.time < minRefreshTime }.isNotEmpty()
}