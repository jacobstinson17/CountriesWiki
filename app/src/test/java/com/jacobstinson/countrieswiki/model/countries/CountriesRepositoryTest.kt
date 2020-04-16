package com.jacobstinson.countrieswiki.model.countries

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.jacobstinson.countrieswiki.*
import com.jacobstinson.countrieswiki.model.CountriesAPIService
import com.jacobstinson.countrieswiki.model.countries.models.Country
import com.jacobstinson.countrieswiki.model.countries.models.CountryParameters
import com.jacobstinson.countrieswiki.model.util.RefreshData
import com.jacobstinson.countrieswiki.model.util.Resource
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.MockitoAnnotations
import java.util.*

class CountriesRepositoryTest {

    /*****************
    * Test Scaffolding
    *****************/
    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock lateinit var mockCountriesDao: CountriesDao
    @Mock lateinit var mockWebService: CountriesAPIService

    lateinit var countriesRepo: CountriesRepository

    @Before
    fun before() {
        MockitoAnnotations.initMocks(this)

        countriesRepo = CountriesRepository(mockWebService, mockCountriesDao, InstantAppExecutors())
    }



    /************
    * Test Fields
    ************/
    private val lessRecentDate = 1000L
    private val moreRecentDate = 2000L
    private val continentCode = "ID"
    private val countryCode = "US"
    private val countryCodeCountry = Country(countryCode, "United States", "1", "Washington D.C.", "USD", "NA", "North America", lessRecentDate)
    private val dbCountries = createDbCountries()
    private val dbContinentCodeCountries = createContinentCodeDbCountries()
    private val wsCountries = createWsCountries()
    private val wsContinentCodeCountries = createContinentCodeWsCountries()

    private fun createDbCountries(): LiveData<List<Country>> {
        return MutableLiveData(listOf(
            Country(continentCode, "Indonesia", "62", "Jakarta", "IDR", "FR", "Asia", lessRecentDate),
            Country("CA", "Canada",  "1", "Ottawa", "CAD", "NA", "North America", moreRecentDate),
            countryCodeCountry,
            Country("ME", "Montenegro", "382", "Podgorica", "EUR", "EU" ,"Europe", moreRecentDate)))
    }

    private fun createContinentCodeDbCountries(): LiveData<List<Country>> {
        return MutableLiveData(listOf(
            Country(continentCode, "Indonesia", "62", "Jakarta", "IDR", "FR", "Asia", lessRecentDate)
        ))
    }

    private fun createWsCountries(): LiveData<Resource<GetAllCountriesQuery.Data>> {
        return MutableLiveData(Resource.success(GetAllCountriesQuery.Data(listOf())))
    }

    private fun createContinentCodeWsCountries(): LiveData<Resource<GetCountriesByContinentQuery.Data>> {
        return MutableLiveData(Resource.success(GetCountriesByContinentQuery.Data(listOf())))
    }



    /*************
    * Test Methods
    *************/
    //getAllCountries
    @Test
    fun testGetAllCountriesFromDatabase() {
        Mockito.`when`(mockCountriesDao.loadCountries(MyMockito.any(CountryParameters::class.java), Mockito.anyLong(), ArgumentMatchers.isNull())).thenReturn(dbCountries)

        val observer = MyMockito.mock<Observer<Resource<List<Country>?>>>()
        countriesRepo.getAllCountries(CountryParameters(), false).observeForever(observer)

        Mockito.verify(mockCountriesDao, Mockito.never()).save(MyMockito.anyObject())
        Mockito.verify(mockCountriesDao, times(1)).loadCountries(MyMockito.any(CountryParameters::class.java), Mockito.anyLong(), ArgumentMatchers.isNull())
        Mockito.verify(mockWebService, Mockito.never()).getAllCountries()
        Mockito.verify(observer).onChanged(Resource.success(MyMockito.anyObject()))
    }

    @Test
    fun testGetAllCountriesFromNetworkDataNull() {
        Mockito.`when`(mockWebService.getAllCountries()).thenReturn(wsCountries)
        Mockito.`when`(mockCountriesDao.save(MyMockito.anyObject())).then { }
        Mockito.`when`(mockCountriesDao.loadCountries(MyMockito.any(CountryParameters::class.java), Mockito.anyLong(), ArgumentMatchers.isNull())).thenReturn(MutableLiveData(null))

        val observer = MyMockito.mock<Observer<Resource<List<Country>?>>>()
        countriesRepo.getAllCountries(CountryParameters(), false).observeForever(observer)

        Mockito.verify(mockCountriesDao, times(1)).save(MyMockito.anyObject())
        Mockito.verify(mockCountriesDao, times(2)).loadCountries(MyMockito.any(CountryParameters::class.java), Mockito.anyLong(), ArgumentMatchers.isNull())
        Mockito.verify(mockWebService, times(1)).getAllCountries()
        Mockito.verify(observer).onChanged(Resource.success(MyMockito.anyObject()))
    }

    @Test
    fun testGetAllCountriesFromNetworkDataEmpty() {
        Mockito.`when`(mockWebService.getAllCountries()).thenReturn(wsCountries)
        Mockito.`when`(mockCountriesDao.save(MyMockito.anyObject())).then { }
        Mockito.`when`(mockCountriesDao.loadCountries(MyMockito.any(CountryParameters::class.java), Mockito.anyLong(), ArgumentMatchers.isNull())).thenReturn(MutableLiveData(emptyList()))

        val observer = MyMockito.mock<Observer<Resource<List<Country>?>>>()
        countriesRepo.getAllCountries(CountryParameters(), false).observeForever(observer)

        Mockito.verify(mockCountriesDao, times(1)).save(MyMockito.anyObject())
        Mockito.verify(mockCountriesDao, times(2)).loadCountries(MyMockito.any(CountryParameters::class.java), Mockito.anyLong(), ArgumentMatchers.isNull())
        Mockito.verify(mockWebService, times(1)).getAllCountries()
        Mockito.verify(observer).onChanged(Resource.success(MyMockito.anyObject()))
    }

    @Test
    fun testGetAllCountriesFromNetworkForceRefresh() {
        Mockito.`when`(mockWebService.getAllCountries()).thenReturn(wsCountries)
        Mockito.`when`(mockCountriesDao.save(MyMockito.anyObject())).then { }
        Mockito.`when`(mockCountriesDao.loadCountries(MyMockito.any(CountryParameters::class.java), Mockito.anyLong(), ArgumentMatchers.isNull())).thenReturn(dbCountries)

        val observer = MyMockito.mock<Observer<Resource<List<Country>?>>>()
        countriesRepo.getAllCountries(CountryParameters(), true).observeForever(observer)

        Mockito.verify(mockCountriesDao, times(1)).save(MyMockito.anyObject())
        Mockito.verify(mockCountriesDao, times(2)).loadCountries(MyMockito.any(CountryParameters::class.java), Mockito.anyLong(), ArgumentMatchers.isNull())
        Mockito.verify(mockWebService, times(1)).getAllCountries()
        Mockito.verify(observer).onChanged(Resource.success(MyMockito.anyObject()))
    }

    //getCountriesByContinent
    @Test
    fun testGetCountriesByContinentFromDatabase() {
        Mockito.`when`(mockCountriesDao.loadCountries(MyMockito.any(CountryParameters::class.java), Mockito.anyLong(), Mockito.anyString())).thenReturn(dbContinentCodeCountries)

        val observer = MyMockito.mock<Observer<Resource<List<Country>?>>>()
        countriesRepo.getCountriesByContinent(CountryParameters(), continentCode, false).observeForever(observer)

        Mockito.verify(mockCountriesDao, Mockito.never()).save(MyMockito.anyObject())
        Mockito.verify(mockCountriesDao, times(1)).loadCountries(MyMockito.any(CountryParameters::class.java), Mockito.anyLong(), Mockito.anyString())
        Mockito.verify(mockWebService, Mockito.never()).getCountriesByContinent( Mockito.anyString())
        Mockito.verify(observer).onChanged(Resource.success(MyMockito.anyObject()))
    }

    @Test
    fun testGetCountriesByContinentFromNetworkDataNull() {
        Mockito.`when`(mockWebService.getCountriesByContinent(Mockito.anyString())).thenReturn(wsContinentCodeCountries)
        Mockito.`when`(mockCountriesDao.save(MyMockito.anyObject())).then { }
        Mockito.`when`(mockCountriesDao.loadCountries(MyMockito.any(CountryParameters::class.java), Mockito.anyLong(), Mockito.anyString())).thenReturn(MutableLiveData(null))

        val observer = MyMockito.mock<Observer<Resource<List<Country>?>>>()
        countriesRepo.getCountriesByContinent(CountryParameters(), continentCode, false).observeForever(observer)

        Mockito.verify(mockCountriesDao, times(1)).save(MyMockito.anyObject())
        Mockito.verify(mockCountriesDao, times(2)).loadCountries(MyMockito.any(CountryParameters::class.java), Mockito.anyLong(), Mockito.anyString())
        Mockito.verify(mockWebService, times(1)).getCountriesByContinent(Mockito.anyString())
        Mockito.verify(observer).onChanged(Resource.success(MyMockito.anyObject()))
    }

    @Test
    fun testGetCountriesByContinentFromNetworkDataEmpty() {
        Mockito.`when`(mockWebService.getCountriesByContinent(Mockito.anyString())).thenReturn(wsContinentCodeCountries)
        Mockito.`when`(mockCountriesDao.save(MyMockito.anyObject())).then { }
        Mockito.`when`(mockCountriesDao.loadCountries(MyMockito.any(CountryParameters::class.java), Mockito.anyLong(), Mockito.anyString())).thenReturn(MutableLiveData(emptyList()))

        val observer = MyMockito.mock<Observer<Resource<List<Country>?>>>()
        countriesRepo.getCountriesByContinent(CountryParameters(), continentCode, false).observeForever(observer)

        Mockito.verify(mockCountriesDao, times(1)).save(MyMockito.anyObject())
        Mockito.verify(mockCountriesDao, times(2)).loadCountries(MyMockito.any(CountryParameters::class.java), Mockito.anyLong(), Mockito.anyString())
        Mockito.verify(mockWebService, times(1)).getCountriesByContinent(Mockito.anyString())
        Mockito.verify(observer).onChanged(Resource.success(MyMockito.anyObject()))
    }

    @Test
    fun testGetCountriesByContinentFromNetworkForceRefresh() {
        Mockito.`when`(mockWebService.getCountriesByContinent(Mockito.anyString())).thenReturn(wsContinentCodeCountries)
        Mockito.`when`(mockCountriesDao.save(MyMockito.anyObject())).then { }
        Mockito.`when`(mockCountriesDao.loadCountries(MyMockito.any(CountryParameters::class.java), Mockito.anyLong(), Mockito.anyString())).thenReturn(dbContinentCodeCountries)

        val observer = MyMockito.mock<Observer<Resource<List<Country>?>>>()
        countriesRepo.getCountriesByContinent(CountryParameters(), continentCode, true).observeForever(observer)

        Mockito.verify(mockCountriesDao, times(1)).save(MyMockito.anyObject())
        Mockito.verify(mockCountriesDao, times(2)).loadCountries(MyMockito.any(CountryParameters::class.java), Mockito.anyLong(), Mockito.anyString())
        Mockito.verify(mockWebService, times(1)).getCountriesByContinent(Mockito.anyString())
        Mockito.verify(observer).onChanged(Resource.success(MyMockito.anyObject()))
    }

    //getCountry
    @Test
    fun testGetCountry() {
        Mockito.`when`(mockCountriesDao.loadCountry(Mockito.anyString())).thenReturn(MutableLiveData(countryCodeCountry))

        val observer = MyMockito.mock<Observer<Country?>>()
        countriesRepo.getCountry(countryCode).observeForever(observer)

        Mockito.verify(mockCountriesDao, times(1)).loadCountry(Mockito.anyString())
        Mockito.verify(observer).onChanged(MyMockito.anyObject())
    }
}