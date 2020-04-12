package com.jacobstinson.countrieswiki.di.module

import android.app.Application
import com.jacobstinson.countrieswiki.model.MyDatabase
import com.jacobstinson.countrieswiki.model.countries.CountriesDao
import dagger.Module
import dagger.Provides

@Module
class DatabaseModule {

    @Provides
    fun providesMyDatabase(application: Application): MyDatabase {
        return MyDatabase.getAppDatabase(application)!!
    }

    //place DAOs here
    @Provides
    fun providesCountriesDao(myDatabase: MyDatabase): CountriesDao {
        return myDatabase.countriesDao()
    }
}