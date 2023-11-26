package com.example.countriesapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.countriesapp.R
import com.example.countriesapp.databinding.ItemCountryBinding
import com.example.countriesapp.model.Country
import com.example.countriesapp.util.setImage
import com.example.countriesapp.view.FeedFragementDirections

class CountryAdapter(val countryList :ArrayList<Country>) : RecyclerView.Adapter<CountryAdapter.CountryViewHolder>(), CountryClickListener {
    private lateinit var binding: ItemCountryBinding
     class CountryViewHolder(val binding : ItemCountryBinding) : ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        //val binding = ItemCountryBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        binding = DataBindingUtil.inflate<ItemCountryBinding>(LayoutInflater.from(parent.context), R.layout.item_country,parent,false)
        //you can also coll  ItemCountryBinding with DataBindingUtil
        return CountryViewHolder(binding)
    }

     override fun getItemCount(): Int {
        return countryList.size
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {

        holder.binding.country = countryList[position]
        holder.binding.listener = this

        /*holder.binding.countryName.text = countryList[position].countryName
        holder.binding.countryRegion.text = countryList[position].countryRegion
        holder.binding.countryImageView.setImage(countryList[position].countryImageUrl!!,holder.binding.root.context)
        holder.binding.root.setOnClickListener {
            val action = FeedFragementDirections.actionFeedFragementToCountryFragment().setCountryUuid(countryList[position].uuid)
            Navigation.findNavController(it).navigate(action)
        }*/
    }
    @SuppressLint("NotifyDataSetChanged")
    fun updateCountryList(newCountryList : List<Country>){
        countryList.clear()
        countryList.addAll(newCountryList)
        notifyDataSetChanged()
    }

    override fun onCountryClicked(view: View) {
        val action = FeedFragementDirections.actionFeedFragementToCountryFragment().setCountryUuid(binding.countryuuID.text.toString().toInt())
        Navigation.findNavController(view).navigate(action)
    }
}