package com.example.countriesapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.countriesapp.R
import com.example.countriesapp.databinding.FragmentCountryBinding
import com.example.countriesapp.databinding.ItemCountryBinding
import com.example.countriesapp.util.setImage
import com.example.countriesapp.viewmodel.CountryViewModel


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
        binding= DataBindingUtil.inflate<FragmentCountryBinding>(inflater, R.layout.fragment_country,container,false)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(CountryViewModel::class.java)
        arguments?.let{
            countryUuid = CountryFragmentArgs.fromBundle(it).countryUuid
        }
        viewModel.getDataFromRoom(countryUuid)
        observeLiveData()
    }
    private fun observeLiveData(){
        viewModel.countryLiveData.observe(viewLifecycleOwner, Observer {

            it?.let{
                binding.selectedCountry = it
                /*binding.nameOfCountry.text = it.countryName
                binding.capitalOfCountry.text = it.countryCapital
                binding.currencyOfCountry.text = it.countryCurrency
                binding.languageOfCountry.text = it.countryLanguage
                binding.regionOfCountry.text = it.countryRegion
                binding.countryImage.setImage(it.countryImageUrl!!,requireContext())*/

            }

        })
    }
}