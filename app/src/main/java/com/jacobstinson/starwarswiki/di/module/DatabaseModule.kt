package com.jacobstinson.starwarswiki.di.module

import android.app.Application
import com.jacobstinson.starwarswiki.model.MyDatabase
import dagger.Module
import dagger.Provides

@Module
class DatabaseModule {

    @Provides
    fun providesMyDatabase(application: Application): MyDatabase {
        return MyDatabase.getAppDatabase(application)!!
    }

    //place DAOs here
    /*@Provides
    fun providesJokesDao(myDatabase: MyDatabase): JokesDao {
        return myDatabase.jokesDao()
    }*/
}