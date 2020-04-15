package com.jacobstinson.countrieswiki.model.countries

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.RawQuery
import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.sqlite.db.SupportSQLiteQuery
import com.jacobstinson.countrieswiki.model.countries.models.Country
import com.jacobstinson.countrieswiki.model.countries.models.CountryParameters
import com.jacobstinson.countrieswiki.testing.MyMockable

@Dao
@MyMockable
abstract class CountriesDao {

    @Insert(onConflict = REPLACE)
    abstract fun save(countries: List<Country>)

    @Query("SELECT * FROM country WHERE code = :countryCode")
    abstract fun loadCountry(countryCode: String): LiveData<Country?>

    @RawQuery(observedEntities = [Country::class])
    abstract fun loadCountriesRawQuery(query: SupportSQLiteQuery): LiveData<List<Country>>

    fun loadCountries(parameters: CountryParameters, continentCode: String? = null): LiveData<List<Country>> {
        val query = createLoadCountriesQuery(parameters, continentCode)
        return loadCountriesRawQuery(query)
    }

    private fun createLoadCountriesQuery(parameters: CountryParameters, continentCode: String? = null): SimpleSQLiteQuery {
        var statement = "SELECT * FROM country WHERE lastRefreshMs > ${parameters.minLastRefreshMs}"
        continentCode?.let {
            statement += " AND continentCode = \'$it\'"
        }
        statement += " ORDER BY ${parameters.orderByField}"
        statement += if(parameters.isDescending) " DESC" else " ASC"

        return SimpleSQLiteQuery(statement)
    }
}