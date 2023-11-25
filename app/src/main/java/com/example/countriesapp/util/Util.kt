package com.example.countriesapp.util

import android.content.Context
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.example.countriesapp.R
import com.squareup.picasso.Picasso

fun ImageView.setImage(url :String, contex: Context){
    Picasso.get()
        .load(url)
        .placeholder(placeHolderProgressBar(contex))
        .error(R.mipmap.ic_launcher_round)
        .resize(50, 50)
        .centerCrop()
        .into(this)
}
fun placeHolderProgressBar(contex : Context) : CircularProgressDrawable {
    return CircularProgressDrawable(contex).apply {
        strokeWidth = 8f
        centerRadius = 40f
        start()
    }
}