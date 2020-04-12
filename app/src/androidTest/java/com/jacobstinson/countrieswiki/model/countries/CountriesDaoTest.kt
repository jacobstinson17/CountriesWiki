package com.jacobstinson.countrieswiki.model.countries

import androidx.test.InstrumentationRegistry
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.runner.AndroidJUnit4
import com.jacobstinson.countrieswiki.Utils
import com.jacobstinson.countrieswiki.model.MyDatabase
import com.jacobstinson.countrieswiki.model.countries.models.Country
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

@RunWith(AndroidJUnit4::class)
class CountriesDaoTest {

    /*****************
    * Test Scaffolding
    *****************/
    @Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var myDatabase: MyDatabase

    @Before
    fun initDb() {
        myDatabase = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getContext(),
            MyDatabase::class.java)
            .allowMainThreadQueries()
            .build()
    }

    @After
    fun closeDb() {
        myDatabase.close()
    }



    /*********************
    * Single Country Tests
    *********************/
    @Test
    fun testGetCountry() {
        val saveCountries = listOf(
            Country("CA", "Canada",  "1", "Ottawa", "CAD", "NA", "North America", Date()))
        myDatabase.countriesDao().save(saveCountries)

        val getCountry = myDatabase.countriesDao().load("CA")
        assert(Utils.getValue(getCountry) == saveCountries[0])
    }


    /************************
    * List of Countries Tests
    ************************/
    @Test
    fun testSaveAndGetCountries() {
        val saveCountries = listOf(
            Country("CA", "Canada",  "1", "ZOttawa", "CAD", "NA", "North America", Date()),
            Country("US", "United States", "1", "Washington D.C.", "USD", "NA", "North America", Date()))
        myDatabase.countriesDao().save(saveCountries)

        val loadCountriesSortByCapital = myDatabase.countriesDao().load(sortByField = "capital")
        assert(Utils.getValue(loadCountriesSortByCapital)[0].capital == saveCountries[1].capital)
        val loadCountriesSortByCode = myDatabase.countriesDao().load(sortByField = "code", descending = true)
        assert(Utils.getValue(loadCountriesSortByCode)[1].code == saveCountries[0].code)
    }

    @Test
    fun testUpdateCountries() {
        var saveCountries = listOf(
            Country("CA", "Canada",  "1", "Ottawa", "CAD", "NA", "North America", Date()))
        myDatabase.countriesDao().save(saveCountries)

        val getCountryPreUpdate = myDatabase.countriesDao().load("CA")
        assert(Utils.getValue(getCountryPreUpdate).name == saveCountries[0].name)

        saveCountries = listOf(
            Country("CA", "UpdatedCanada",  "1", "Ottawa", "CAD", "NA", "North America", Date()))
        myDatabase.countriesDao().save(saveCountries)

        val getCountryPostUpdate = myDatabase.countriesDao().load("CA")
        assert(Utils.getValue(getCountryPostUpdate).name == saveCountries[0].name)
    }
}