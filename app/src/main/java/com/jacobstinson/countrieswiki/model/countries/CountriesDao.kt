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
import java.util.*

@Dao
abstract class CountriesDao {

    @Insert(onConflict = REPLACE)
    abstract fun save(countries: List<Country>)

    @Query("SELECT * FROM country WHERE code = :countryCode")
    abstract fun loadCountry(countryCode: String): LiveData<Country?>

    @RawQuery(observedEntities = [Country::class])
    abstract fun loadAllCountriesRawQuery(query: SupportSQLiteQuery): LiveData<List<Country>>

    fun loadCountries(continentCode: String? = null, orderByField: String = "name", descending: Boolean = false, lastRefreshMin: Date = Date(0)): LiveData<List<Country>> {
        var statement = "SELECT * FROM country WHERE lastRefresh > ${lastRefreshMin.time}"
        continentCode?.let {
            statement += " AND continentCode = \'$it\'"
        }
        statement += " ORDER BY $orderByField"
        statement += if(descending) " DESC" else " ASC"

        val query = SimpleSQLiteQuery(statement)

        return loadAllCountriesRawQuery(query)
    }
}