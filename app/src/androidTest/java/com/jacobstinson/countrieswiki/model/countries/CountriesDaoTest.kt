package com.jacobstinson.countrieswiki.model.countries

import androidx.test.InstrumentationRegistry
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.runner.AndroidJUnit4
import com.jacobstinson.countrieswiki.getLiveDataValue
import com.jacobstinson.countrieswiki.model.MyDatabase
import com.jacobstinson.countrieswiki.model.countries.models.Country
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
        Assert.assertTrue(getLiveDataValue(countriesDao.loadCountries()).isEmpty())

        countriesDao.save(dbCountries)

        Assert.assertTrue(getLiveDataValue(countriesDao.loadCountries()).isNotEmpty())
    }

    @Test
    fun testSaveCountriesEmptyList() {
        Assert.assertTrue(getLiveDataValue(countriesDao.loadCountries()).isEmpty())

        countriesDao.save(emptyList())

        Assert.assertTrue(getLiveDataValue(countriesDao.loadCountries()).isEmpty())
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

    //loadAllCountries
    @Test
    fun testLoadCountriesEmptyDb() {
        val allCountries = countriesDao.loadCountries()

        Assert.assertTrue(getLiveDataValue(allCountries).isEmpty())
    }

    @Test
    fun testLoadCountriesSortedByNameAscending() {
        val allCountries = getLiveDataValue(countriesDao.loadCountries())

        Assert.assertThat(dbCountries.sortedBy { country -> country.name }, `is`(allCountries))
    }

    @Test
    fun testLoadCountriesSortedByCapitalDescending() {
        val allCountries = getLiveDataValue(countriesDao.loadCountries(orderByField = "capital", descending = true))

        Assert.assertThat(dbCountries.sortedByDescending { country -> country.capital }, `is`(allCountries))
    }

    @Test
    fun testLoadCountriesExcludeLessRecentDates() {
        val moreRecentCountries = getLiveDataValue(countriesDao.loadCountries(lastRefreshMin = Date(1001)))

        Assert.assertThat(dbCountries.filter { country -> country.lastRefresh == moreRecentDate }, `is`(moreRecentCountries))
    }

    @Test
    fun testLoadCountriesExcludeLessAndMoreRecentDates() {
        val noCountries = getLiveDataValue(countriesDao.loadCountries(lastRefreshMin = Date(2001)))

        Assert.assertTrue(noCountries.isEmpty())
    }

    @Test
    fun testLoadCountriesByValidContinentCode() {
        val validContinentCountries = getLiveDataValue(countriesDao.loadCountries(validContinentCode))

        Assert.assertThat(listOf(validContinentCodeCountry), `is`(validContinentCountries))
    }

    @Test
    fun testLoadCountriesByInvalidContinentCode() {
        val invalidContinentCountries = getLiveDataValue(countriesDao.loadCountries(invalidContinentCode))

        Assert.assertTrue(invalidContinentCountries.isEmpty())
    }
































    /*@Test
    fun testGetCountry() {
        val saveCountries = listOf(
            Country("CA", "Canada",  "1", "Ottawa", "CAD", "NA", "North America", Date()))
        myDatabase.countriesDao().save(saveCountries)

        val getCountry = myDatabase.countriesDao().loadAll("CA")
        assert(Utils.getLiveDataValue(getCountry) == saveCountries[0])
    }

    @Test
    fun testSaveAndGetCountries() {
        val saveCountries = listOf(
            Country("CA", "Canada",  "1", "ZOttawa", "CAD", "NA", "North America", Date()),
            Country("US", "United States", "1", "Washington D.C.", "USD", "NA", "North America", Date()))
        myDatabase.countriesDao().save(saveCountries)

        val loadCountriesSortByCapital = myDatabase.countriesDao().loadAll(sortByField = "capital")
        assert(Utils.getLiveDataValue(loadCountriesSortByCapital)[0].capital == saveCountries[1].capital)
        val loadCountriesSortByCode = myDatabase.countriesDao().loadAll(sortByField = "code", descending = true)
        assert(Utils.getLiveDataValue(loadCountriesSortByCode)[1].code == saveCountries[0].code)
    }

    @Test
    fun testUpdateCountries() {
        var saveCountries = listOf(
            Country("CA", "Canada",  "1", "Ottawa", "CAD", "NA", "North America", Date()))
        myDatabase.countriesDao().save(saveCountries)

        val getCountryPreUpdate = myDatabase.countriesDao().loadAll("CA")
        assert(Utils.getLiveDataValue(getCountryPreUpdate).name == saveCountries[0].name)

        saveCountries = listOf(
            Country("CA", "UpdatedCanada",  "1", "Ottawa", "CAD", "NA", "North America", Date()))
        myDatabase.countriesDao().save(saveCountries)

        val getCountryPostUpdate = myDatabase.countriesDao().loadAll("CA")
        assert(Utils.getLiveDataValue(getCountryPostUpdate).name == saveCountries[0].name)
    }*/
}