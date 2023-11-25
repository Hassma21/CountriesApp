package com.example.countriesapp.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.countriesapp.model.Country
import com.example.countriesapp.service.retrofit.CountryAPIService
import com.example.countriesapp.service.room.CountryDatabase
import com.example.countriesapp.util.CustomSharedPreferences

import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers


class FeedViewModel(application: Application) : AndroidViewModel(application)  {

    private val countryApiService = CountryAPIService()
    private val disposable = CompositeDisposable() //internetten veri indirkçe inen bilgiler ram'de yer kaplamasın diye sürekli bu dispoasble nesne içine atarız
    // böylece fragmet'la işimiz bitince dispoasble'dan kurtuluruz.aynı anda birden fazla call'da verileri buraya atarız
    private var customPreferences : CustomSharedPreferences = CustomSharedPreferences(getApplication())
    private var refreshTime = 10 * 60 * 1000 * 1000 * 1000L

    val countries = MutableLiveData<List<Country>>()
    val countryError = MutableLiveData<Boolean>()
    val countryLoading = MutableLiveData<Boolean>()

    fun refreshData(){
        val updateTime = customPreferences.getTime()
        if(updateTime != null && updateTime != 0L && System.nanoTime() -updateTime < refreshTime){
            getDataFromSQLite()
        }else{
            getDataFromAPI()
        }
    }
    fun refreshFromAPI(){
        getDataFromAPI()
    }

    private fun getDataFromSQLite() {
        countryLoading.value = true
        disposable.add(
            CountryDatabase(getApplication()).countryDao().getAllCountries()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe { countries ->
                    showCountries(countries)
                }
        )
        Toast.makeText(getApplication(),"Countries From SQLite",Toast.LENGTH_LONG).show()
    }

    fun getDataFromAPI(){
        countryLoading.value = true
        disposable.add(
            countryApiService.getData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.newThread())
                .subscribeWith(object : DisposableSingleObserver<List<Country>>(){
                    override fun onSuccess(t: List<Country>) {
                        storeInSQLite(t)
                    }

                    override fun onError(e: Throwable) {
                        countryError.postValue(true)
                        countryLoading.postValue(false)
                        e.printStackTrace()
                    }

                })

        )
        Toast.makeText(getApplication(),"Countries From API",Toast.LENGTH_LONG).show()
    }

    @SuppressLint("CheckResult")
    private fun storeInSQLite(countryList: List<Country>) {
        val dao = CountryDatabase(getApplication()).countryDao()
        disposable.add(
            dao.deleteAllCountries()
                .andThen(dao.insertAll(*countryList.toTypedArray()))
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.single())
                .subscribe { insertedIds ->
                    var i = 0
                    while(i < countryList.size){
                        countryList[i].uuid = insertedIds[i].toInt()
                        i += 1
                    }
                }
        )
        showCountries(countryList)
        customPreferences.saveTime(System.nanoTime())
    }

    private fun showCountries(countryList : List<Country>){
        countries.postValue(countryList)
        countryError.postValue(false)
        countryLoading.postValue(false)
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
        disposable.dispose()
    }
}