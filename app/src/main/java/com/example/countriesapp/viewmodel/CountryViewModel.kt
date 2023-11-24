package com.example.countriesapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.countriesapp.model.Country

class CountryViewModel :ViewModel(){

    val countryLiveData = MutableLiveData<Country>()
    fun getDataFromRoom(){
        val country = Country("Turkey","Asia","Ankara","TL","Turkish","www.turkey.com")
        countryLiveData.value = country
    }
}