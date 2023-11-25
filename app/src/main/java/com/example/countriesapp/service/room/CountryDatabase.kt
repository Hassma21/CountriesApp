package com.example.countriesapp.service.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.countriesapp.model.Country

@Database(entities = [Country::class], version = 1)//each any change on Database ,have to increase version number
abstract class CountryDatabase :RoomDatabase() {

    abstract fun countryDao() : CountryDao

    companion object{
        @Volatile private var instance : CountryDatabase? = null //Voaltile means every thread see this instance

        private val lock = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(lock){
            instance ?: makeDatabase(context).also {
                instance = it
            }
        }

        private fun makeDatabase(context : Context) = Room.databaseBuilder(
            context.applicationContext,CountryDatabase::class.java,"countrydatabase"
        ).build()
    }

}