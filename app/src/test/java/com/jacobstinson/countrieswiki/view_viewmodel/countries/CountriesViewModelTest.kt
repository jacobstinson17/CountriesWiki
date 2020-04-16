package com.jacobstinson.countrieswiki.view_viewmodel.countries

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jacobstinson.countrieswiki.MyMockito
import com.jacobstinson.countrieswiki.model.countries.CountriesRepository
import com.jacobstinson.countrieswiki.model.countries.models.Country
import com.jacobstinson.countrieswiki.model.countries.models.CountryParameters
import com.jacobstinson.countrieswiki.model.util.Resource
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class CountriesViewModelTest {

    /*****************
    * Test Scaffolding
    *****************/
    @Mock lateinit var mockCountriesRepo: CountriesRepository

    lateinit var countriesViewModel: CountriesViewModel

    @Before
    fun before() {
        MockitoAnnotations.initMocks(this)

        countriesViewModel = CountriesViewModel(mockCountriesRepo)
    }



    /************
    * Test Fields
    ************/
    private val liveDataCountriesListResource = createLiveDataCountriesListResource()

    private fun createLiveDataCountriesListResource(): LiveData<Resource<List<Country>?>> {
        return MutableLiveData(Resource.success<List<Country>?>(listOf(
            Country("ID", "Indonesia", "62", "Jakarta", "IDR", "AS", "Asia", 0),
            Country("CA", "Canada",  "1", "Ottawa", "CAD", "NA", "North America", 0),
            Country("US", "United States", "1", "Washington D.C.", "USD", "NA", "North America", 0),
            Country("ME", "Montenegro", "382", "Podgorica", "EUR", "EU" ,"Europe", 0))
        ))
    }



    /*************
    * Test Methods
    *************/
    @Test
    fun testGetAllCountries() {
        Mockito.`when`(mockCountriesRepo.getAllCountries(MyMockito.any(CountryParameters::class.java), Mockito.anyBoolean()))
            .thenReturn(liveDataCountriesListResource)

        val getAllCountriesLiveData = countriesViewModel.getAllCountries(true)

        Mockito.verify(mockCountriesRepo).getAllCountries(countriesViewModel.countryParameters.value!!, true)
        Assert.assertThat(getAllCountriesLiveData, `is`(liveDataCountriesListResource))
    }

    @Test
    fun testGetCountriesByContinent() {
        Mockito.`when`(mockCountriesRepo.getCountriesByContinent(MyMockito.any(CountryParameters::class.java), Mockito.anyString(), Mockito.anyBoolean()))
            .thenReturn(liveDataCountriesListResource)

        val getCountriesByContinentLiveData = countriesViewModel.getCountriesByContinent(false)

        Mockito.verify(mockCountriesRepo).getCountriesByContinent(countriesViewModel.countryParameters.value!!, countriesViewModel.continentCode.value!!, false)
        Assert.assertThat(getCountriesByContinentLiveData, `is`(liveDataCountriesListResource))
    }
}