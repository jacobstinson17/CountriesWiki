package com.jacobstinson.countrieswiki.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.jacobstinson.countrieswiki.model.countries.CountriesDao
import com.jacobstinson.countrieswiki.model.countries.models.Country

@Database(entities = [Country::class], version = 1)
abstract class MyDatabase: RoomDatabase() {

    companion object {
        var INSTANCE: MyDatabase? = null

        fun getAppDatabase(context: Context): MyDatabase? {
            if(INSTANCE == null) {
                synchronized(MyDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext, MyDatabase::class.java, "myDB").build()
                }
            }
            return INSTANCE
        }

        fun destoryDatabase() {
            INSTANCE = null
        }
    }

    abstract fun countriesDao(): CountriesDao
}
