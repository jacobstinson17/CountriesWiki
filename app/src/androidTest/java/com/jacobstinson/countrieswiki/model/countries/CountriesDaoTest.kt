package com.jacobstinson.countrieswiki.model.countries

import androidx.test.InstrumentationRegistry
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.runner.AndroidJUnit4
import com.jacobstinson.countrieswiki.getLiveDataValue
import com.jacobstinson.countrieswiki.model.MyDatabase
import com.jacobstinson.countrieswiki.model.countries.models.Country
import com.jacobstinson.countrieswiki.model.countries.models.CountryParameters
import org.junit.*
import org.junit.rules.TestName
import org.junit.runner.RunWith
import org.hamcrest.CoreMatchers.*
import org.mockito.internal.matchers.apachecommons.ReflectionEquals
import java.util.*

@RunWith(AndroidJUnit4::class)
class CountriesDaoTest {

    /*****************
    * Test Scaffolding
    *****************/
    @Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Rule
    @JvmField
    var testName = TestName()

    private lateinit var myDatabase: MyDatabase
    private lateinit var countriesDao: CountriesDao

    @Before
    fun initDb() {
        myDatabase = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(), MyDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        countriesDao = myDatabase.countriesDao()

        val testingSaveMethod = testName.methodName == this::testSaveCountriesNonEmptyList.name || testName.methodName == this::testSaveCountriesEmptyList.name
        val testingLoadEmptyDb = testName.methodName == this::testLoadCountriesEmptyDb.name
        if( !(testingSaveMethod || testingLoadEmptyDb) ) {
            countriesDao.save(dbCountries)
        }
    }

    @After
    fun closeDb() {
        myDatabase.close()
    }



    /************
    * Test Fields
    ************/
    private val lessRecentDate = Date(1000)
    private val moreRecentDate = Date(2000)
    private val validCountryCode = "CA"
    private val invalidCountryCode = "FR"
    private val validCountryCodeCountry = Country(validCountryCode, "Canada",  "1", "Ottawa", "CAD", "NA", "North America", moreRecentDate)
    private val validContinentCode = "AS"
    private val invalidContinentCode = "UR"
    private val validContinentCodeCountry = Country("ID", "Indonesia", "62", "Jakarta", "IDR", validContinentCode, "Asia", lessRecentDate)
    private val dbCountries = createDbCountries()
    private val countryParameters = CountryParameters()

    /***********
    * Test Utils
    ***********/
    private fun createDbCountries(): List<Country> {
        return listOf(
            validContinentCodeCountry,
            validCountryCodeCountry,
            Country("US", "United States", "1", "Washington D.C.", "USD", "NA", "North America", lessRecentDate),
            Country("ME", "Montenegro", "382", "Podgorica", "EUR", "EU" ,"Europe", moreRecentDate))
    }



    /*************
    * Test Methods
    *************/
    //save
    @Test
    fun testSaveCountriesNonEmptyList() {
        Assert.assertTrue(getLiveDataValue(countriesDao.loadCountries(countryParameters)).isEmpty())

        countriesDao.save(dbCountries)

        Assert.assertTrue(getLiveDataValue(countriesDao.loadCountries(countryParameters)).isNotEmpty())
    }

    @Test
    fun testSaveCountriesEmptyList() {
        Assert.assertTrue(getLiveDataValue(countriesDao.loadCountries(countryParameters)).isEmpty())

        countriesDao.save(emptyList())

        Assert.assertTrue(getLiveDataValue(countriesDao.loadCountries(countryParameters)).isEmpty())
    }

    //loadCountry
    @Test
    fun testLoadCountryWithValidCountryCode() {
        val validCountryCodeCountry = countriesDao.loadCountry(validCountryCode)

        Assert.assertTrue(ReflectionEquals(this.validCountryCodeCountry).matches(getLiveDataValue(validCountryCodeCountry)))
    }

    @Test
    fun testLoadCountryWithInvalidCountryCode() {
        val invalidCountryCodeCountry = countriesDao.loadCountry(invalidCountryCode)

        Assert.assertNull(getLiveDataValue(invalidCountryCodeCountry))
    }

    //loadCountries
    @Test
    fun testLoadCountriesEmptyDb() {
        val allCountries = countriesDao.loadCountries(countryParameters)

        Assert.assertTrue(getLiveDataValue(allCountries).isEmpty())
    }

    @Test
    fun testLoadCountriesSortedByNameAscending() {
        val allCountries = getLiveDataValue(countriesDao.loadCountries(countryParameters))

        Assert.assertThat(dbCountries.sortedBy { country -> country.name }, `is`(allCountries))
    }

    @Test
    fun testLoadCountriesSortedByCapitalDescending() {
        countryParameters.orderByField = "capital"
        countryParameters.isDescending = true
        val allCountries = getLiveDataValue(countriesDao.loadCountries(countryParameters))

        Assert.assertThat(dbCountries.sortedByDescending { country -> country.capital }, `is`(allCountries))
    }

    @Test
    fun testLoadCountriesExcludeLessRecentDates() {
        countryParameters.minLastRefreshMs = 1001
        val moreRecentCountries = getLiveDataValue(countriesDao.loadCountries(countryParameters))

        Assert.assertThat(dbCountries.filter { country -> country.lastRefresh == moreRecentDate }, `is`(moreRecentCountries))
    }

    @Test
    fun testLoadCountriesExcludeLessAndMoreRecentDates() {
        countryParameters.minLastRefreshMs = 2001
        val noCountries = getLiveDataValue(countriesDao.loadCountries(countryParameters))

        Assert.assertTrue(noCountries.isEmpty())
    }

    @Test
    fun testLoadCountriesByValidContinentCode() {
        val validContinentCountries = getLiveDataValue(countriesDao.loadCountries(countryParameters, validContinentCode))

        Assert.assertThat(listOf(validContinentCodeCountry), `is`(validContinentCountries))
    }

    @Test
    fun testLoadCountriesByInvalidContinentCode() {
        val invalidContinentCountries = getLiveDataValue(countriesDao.loadCountries(countryParameters, invalidContinentCode))

        Assert.assertTrue(invalidContinentCountries.isEmpty())
    }
}