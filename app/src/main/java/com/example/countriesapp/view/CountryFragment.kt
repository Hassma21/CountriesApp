package com.example.countriesapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.countriesapp.R
import com.example.countriesapp.databinding.FragmentCountryBinding
import com.example.countriesapp.databinding.FragmentFeedFragementBinding
import com.example.countriesapp.viewmodel.CountryViewModel
import com.example.countriesapp.viewmodel.FeedViewModel

class CountryFragment : Fragment() {

    private lateinit var viewModel : CountryViewModel
    private var countryUuid = 0
    private lateinit var binding: FragmentCountryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_country, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= FragmentCountryBinding.bind(view)
        viewModel = ViewModelProvider(this).get(CountryViewModel::class.java)
        viewModel.getDataFromRoom()
        arguments?.let{
            countryUuid = CountryFragmentArgs.fromBundle(it).countryUuid
        }
        observeLiveData()
    }
    private fun observeLiveData(){
        viewModel.countryLiveData.observe(viewLifecycleOwner, Observer {

            it?.let{
                binding.nameOfCountry.text = it.countryName
                binding.capitalOfCountry.text = it.countryCapital
                binding.currencyOfCountry.text = it.countryCurrency
                binding.languageOfCountry.text = it.countryLanguage
                binding.regionOfCountry.text = it.countryRegion

            }

        })
    }
}