package com.example.countriesapp.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.countriesapp.model.Country
import com.example.countriesapp.service.room.CountryDatabase
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class CountryViewModel(application: Application) : AndroidViewModel(application) {

    val countryLiveData = MutableLiveData<Country>()
    private val disposable = CompositeDisposable()
    @SuppressLint("CheckResult")
    fun getDataFromRoom(uuid :Int){
        disposable.add(
        CountryDatabase(getApplication()).countryDao()
            .getCountry(uuid)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.single())
            .subscribe { country ->
                countryLiveData.postValue(country)
            })
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
        disposable.dispose()
    }
}