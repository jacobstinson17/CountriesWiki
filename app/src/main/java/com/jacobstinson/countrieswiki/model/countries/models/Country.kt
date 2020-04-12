package com.jacobstinson.countrieswiki.model.countries.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jacobstinson.countrieswiki.GetAllCountriesQuery
import com.jacobstinson.countrieswiki.GetCountriesQuery
import java.util.*

@Entity
class Country(@PrimaryKey val code: String, val name: String, val phone: String, val capital: String?,
              val currency: String?, val continentCode: String, val continentName: String, var lastRefresh: Date) {

    companion object {
        fun getCountries(getAllCountriesQueryData: GetAllCountriesQuery.Data): List<Country> {
            val countries = mutableListOf<Country>()

            for(getAllCountry in getAllCountriesQueryData.countries) {
                countries.add(getCountry(getAllCountry))
            }

            return countries
        }

        fun getCountries(getCountriesQuery: GetCountriesQuery.Data): List<Country> {
            val countries = mutableListOf<Country>()

            for(getAllCountry in getCountriesQuery.countries) {
                countries.add(getCountry(getAllCountry))
            }

            return countries
        }

        private fun getCountry(getAllCountriesCountry: GetAllCountriesQuery.Country): Country {
            return Country(getAllCountriesCountry.code, getAllCountriesCountry.name, getAllCountriesCountry.phone, getAllCountriesCountry.capital,
                getAllCountriesCountry.currency, getAllCountriesCountry.continent.code, getAllCountriesCountry.continent.name, Date())
        }

        private fun getCountry(getCountriesQuery: GetCountriesQuery.Country): Country {
            return Country(getCountriesQuery.code, getCountriesQuery.name, getCountriesQuery.phone, getCountriesQuery.capital,
                getCountriesQuery.currency, getCountriesQuery.continent.code, getCountriesQuery.continent.name, Date())
        }
    }
}