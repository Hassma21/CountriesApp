package com.example.countriesapp.service.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.countriesapp.model.Country
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single

@Dao
interface CountryDao {
    //Data Access Object

    @Insert
    fun insertAll(vararg countries : Country) : Single<List<Long>>

    @Query("SELECT * FROM country")
    fun getAllCountries() : Flowable<List<Country>>

    @Query("SELECT * FROM country WHERE uuid = :countryId")
    fun getCountry(countryId :Int) : Single<Country>

    @Query("DELETE FROM country")
    fun deleteAllCountries() : Completable


}