package com.jacobstinson.starwarswiki.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.jacobstinson.starwarswiki.model.models.people.People

@Database(entities = [People::class], version = 1)
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

    /*abstract fun jokesDao(): JokesDao*/
}
