package com.example.countriesapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.countriesapp.R
import com.example.countriesapp.adapter.CountryAdapter
import com.example.countriesapp.databinding.FragmentFeedFragementBinding
import com.example.countriesapp.viewmodel.FeedViewModel

class FeedFragement : Fragment() {

    private lateinit var viewModel: FeedViewModel
    private val countryAdapter = CountryAdapter(ArrayList())
    private lateinit var binding: FragmentFeedFragementBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feed_fragement, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= FragmentFeedFragementBinding.bind(view)
        viewModel = ViewModelProvider(this).get(FeedViewModel::class.java)
        viewModel.refreshData()
        binding.rcwCountryList.layoutManager = LinearLayoutManager(context)
        binding.rcwCountryList.adapter = countryAdapter

        binding.swiperefresh.setOnRefreshListener {
            binding.rcwCountryList.visibility = View.GONE
            binding.countryError.visibility = View.GONE
            binding.countryLoading.visibility = View.VISIBLE
            viewModel.refreshFromAPI()
            binding.swiperefresh.isRefreshing = false
        }

        observeLiveData()

    }
    private fun observeLiveData(){
        viewModel.countries.observe(viewLifecycleOwner){ countries ->//Truth usage is viewLifecycleOwner
            countries?.let{
                binding.rcwCountryList.visibility = View.VISIBLE
                countryAdapter.updateCountryList(countries)
            }
        }
        viewModel.countryError.observe(viewLifecycleOwner){
            it?.let{
                if(it){
                    binding.countryError.visibility = View.VISIBLE
                }
                else{
                    binding.countryError.visibility = View.GONE
                }
            }
        }
        viewModel.countryLoading.observe(viewLifecycleOwner){ loading ->
            loading?.let {
                if(loading){
                    binding.countryLoading.visibility = View.VISIBLE
                    binding.rcwCountryList.visibility = View.GONE
                    binding.countryError.visibility = View.GONE
                }else{
                    binding.countryLoading.visibility = View.GONE
                }
            }

        }
    }
}