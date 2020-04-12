package com.jacobstinson.countrieswiki.model.countries

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.jacobstinson.countrieswiki.*
import com.jacobstinson.countrieswiki.model.CountriesAPIService
import com.jacobstinson.countrieswiki.model.countries.models.Country
import com.jacobstinson.countrieswiki.model.util.Resource
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.util.*

class CountriesRepositoryTest {

    val dbCountries = listOf(
        Country("US", "United States", "1", "Washington D.C.", "USD", "NA", "North America", Date()),
        Country("CA", "Canada",  "1", "Ottawa", "CAD", "NA", "North America", Date()))
    val wsGetAllCountries = GetAllCountriesQuery.Data(listOf(
        GetAllCountriesQuery.Country("Country", "US", "United States", "United States", "1",
            GetAllCountriesQuery.Continent("Continent", "NA", "North America"), "Washington D.C.", "USD", listOf(), listOf()),
        GetAllCountriesQuery.Country("Country", "CA", "Canada", "Canada", "1",
            GetAllCountriesQuery.Continent("Continent", "NA", "North America"), "Ottawa", "CAD", listOf(), listOf())))
    val wsGetCountries = GetCountriesQuery.Data(listOf(
        GetCountriesQuery.Country("Country", "US", "United States", "United States", "1",
            GetCountriesQuery.Continent("Continent", "NA", "North America"), "Washington D.C.", "USD", listOf(), listOf()),
        GetCountriesQuery.Country("Country", "CA", "Canada", "Canada", "1",
            GetCountriesQuery.Continent("Continent", "NA", "North America"), "Ottawa", "CAD", listOf(), listOf())))

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var mockCountriesDao: CountriesDao
    @Mock
    lateinit var mockWebService: CountriesAPIService

    lateinit var countriesRepo: CountriesRepository

    @Before
    fun before() {
        MockitoAnnotations.initMocks(this)

        countriesRepo = CountriesRepository(mockWebService, mockCountriesDao, InstantAppExecutors())
    }

    @Test
    fun loadAllCountriesDontGoToNetwork() {
        //set up mock data
        val dbData = MutableLiveData<List<Country>>()
        dbData.value = dbCountries
        Mockito.`when`(mockCountriesDao.load()).thenReturn(dbData)

        //call FUT
        val observer = MyMockito.mock<Observer<Resource<List<Country>?>>>()
        countriesRepo.getAllCountries(false).observeForever(observer)

        //verify
        Mockito.verify(mockWebService, Mockito.never()).getCountries(Mockito.anyString())
        Mockito.verify(observer).onChanged(Resource.success(dbCountries))
    }

    @Test
    fun loadAllCountriesGoToNetwork() {
        //set up mock db return value
        val dbData = MutableLiveData<List<Country>>()
        dbData.value = null
        Mockito.`when`(mockCountriesDao.load()).thenReturn(dbData)
        Mockito.`when`(mockCountriesDao.save(MyMockito.anyObject())).then {  }

        //set up webservice return value
        val call = WebServiceUtil.successCall(wsGetAllCountries)
        Mockito.`when`(mockWebService.getAllCountries()).thenReturn(call)

        //call FUT
        val observer = MyMockito.mock<Observer<Resource<List<Country>?>>>()
        countriesRepo.getAllCountries(true).observeForever(observer)

        //verify
        Mockito.verify(mockWebService).getAllCountries()
        Mockito.verify(mockCountriesDao).save(dbCountries)
    }

    @Test
    fun loadCountriesDontGoToNetwork() {
        //set up mock data
        val dbData = MutableLiveData<List<Country>>()
        dbData.value = dbCountries
        Mockito.`when`(mockCountriesDao.load()).thenReturn(dbData)

        //call FUT
        val observer = MyMockito.mock<Observer<Resource<List<Country>?>>>()
        countriesRepo.getCountries("NA", false).observeForever(observer)

        //verify
        Mockito.verify(mockWebService, Mockito.never()).getCountries(Mockito.anyString())
        Mockito.verify(observer).onChanged(Resource.success(dbCountries))
    }

    @Test
    fun loadCountriesGoToNetwork() {
        //set up mock db return value
        val dbData = MutableLiveData<List<Country>>()
        dbData.value = null
        Mockito.`when`(mockCountriesDao.load()).thenReturn(dbData)
        Mockito.`when`(mockCountriesDao.save(MyMockito.anyObject())).then {  }

        //set up webservice return value
        val call = WebServiceUtil.successCall(wsGetCountries)
        Mockito.`when`(mockWebService.getCountries(Mockito.anyString())).thenReturn(call)

        //call FUT
        val observer = MyMockito.mock<Observer<Resource<List<Country>?>>>()
        countriesRepo.getCountries("NA", true).observeForever(observer)

        //verify
        Mockito.verify(mockWebService).getAllCountries()
        Mockito.verify(mockCountriesDao).save(dbCountries)
    }

    @Test
    fun loadCountryFromDatabase() {
        //set up mock data
        val dbData = MutableLiveData<Country>()
        dbData.value = dbCountries[0]
        Mockito.`when`(mockCountriesDao.load(code = Mockito.anyString())).thenReturn(dbData)

        //call FUT
        val observer = MyMockito.mock<Observer<Resource<Country>>>()
        countriesRepo.getCountry("US").observeForever(observer)

        //verify
        Mockito.verify(mockWebService, Mockito.never()).getCountries(Mockito.anyString())
        Mockito.verify(observer).onChanged(Resource.success(dbCountries[0]))
    }
}