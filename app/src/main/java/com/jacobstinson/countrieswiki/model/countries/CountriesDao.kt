package com.jacobstinson.countrieswiki.model.countries

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.jacobstinson.countrieswiki.model.countries.models.Country
import java.util.*

@Dao
interface CountriesDao {
    @Insert(onConflict = REPLACE)
    fun save(countries: List<Country>)

    @Query("SELECT * FROM country WHERE lastRefresh > :lastRefreshMax ORDER BY CASE WHEN :descending = 1 THEN :sortByField END DESC, CASE WHEN :descending = 0 THEN :sortByField END")
    fun load(lastRefreshMax: Date, sortByField: String = "name", descending: Boolean): LiveData<List<Country>>

}