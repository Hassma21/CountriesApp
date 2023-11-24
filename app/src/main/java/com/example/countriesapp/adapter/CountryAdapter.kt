package com.example.countriesapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.countriesapp.databinding.ItemCountryBinding
import com.example.countriesapp.model.Country
import com.example.countriesapp.view.FeedFragementDirections

class CountryAdapter(val countryList :ArrayList<Country>) : RecyclerView.Adapter<CountryAdapter.CountryViewHolder>() {
     class CountryViewHolder(val binding : ItemCountryBinding) : ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val binding = ItemCountryBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CountryViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return countryList.size
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.binding.countryName.text = countryList[position].countryName
        holder.binding.countryRegion.text = countryList[position].countryRegion

        holder.binding.root.setOnClickListener {
            val action = FeedFragementDirections.actionFeedFragementToCountryFragment()
            Navigation.findNavController(it).navigate(action)
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    fun updateCountryList(newCountryList : List<Country>){
        countryList.clear()
        countryList.addAll(newCountryList)
        notifyDataSetChanged()
    }
}