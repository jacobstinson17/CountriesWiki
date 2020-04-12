package com.jacobstinson.countrieswiki.model.countries

import android.support.test.InstrumentationRegistry
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.runner.AndroidJUnit4
import com.jacobstinson.countrieswiki.model.MyDatabase
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

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
    fun testSaveCountry() {

    }

    @Test
    fun testGetCountry() {

    }

    @Test
    fun testUpdateCountry() {

    }



    /************************
    * List of Countries Tests
    ************************/
    @Test
    fun testSaveCountries() {

    }

    @Test
    fun testGetCountries() {

    }

    @Test
    fun testUpdateCountries() {

    }
}