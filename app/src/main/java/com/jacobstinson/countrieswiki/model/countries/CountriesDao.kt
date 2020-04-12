package com.jacobstinson.countrieswiki.model.countries

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.jacobstinson.countrieswiki.model.countries.models.Country
import com.jacobstinson.countrieswiki.model.util.RefreshData
import java.util.*

@Dao
interface CountriesDao {
    @Insert(onConflict = REPLACE)
    fun save(countries: List<Country>)

    @Query("SELECT * FROM country WHERE lastRefresh > :lastRefreshMax ORDER BY CASE WHEN :descending = 1 THEN :sortByField END DESC, CASE WHEN :descending = 0 THEN :sortByField END")
    fun loadAllCountries(lastRefreshMax: Date = RefreshData.getMaxRefreshTime(Date()), sortByField: String = "name", descending: Boolean = false): LiveData<List<Country>>

    @Query("SELECT * FROM country WHERE continentCode = :continentCode AND lastRefresh > :lastRefreshMax ORDER BY CASE WHEN :descending = 1 THEN :sortByField END DESC, CASE WHEN :descending = 0 THEN :sortByField END")
    fun loadContinentCountries(continentCode: String, lastRefreshMax: Date = RefreshData.getMaxRefreshTime(Date()), sortByField: String = "name", descending: Boolean = false): LiveData<List<Country>>

    @Query("SELECT * FROM country WHERE code = :code")
    fun loadCountry(code: String): LiveData<Country>
}