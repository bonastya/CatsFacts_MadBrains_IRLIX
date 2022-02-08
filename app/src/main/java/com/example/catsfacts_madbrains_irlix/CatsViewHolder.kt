package com.example.catsfacts_madbrains_irlix
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

import com.squareup.picasso.Picasso
import java.lang.reflect.Array.get
import android.graphics.BitmapFactory

import android.graphics.Bitmap
import android.icu.number.NumberFormatter.with
import android.icu.number.NumberRangeFormatter.with

import java.io.IOException
import java.io.InputStream
import java.net.MalformedURLException
import java.net.URL


class CatsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(cat: CatFact) {
        with(itemView) {
            val catFactTextView: TextView = findViewById<TextView>(R.id.catFactTextView)
            val catFactImg: ImageView = findViewById<ImageView>(R.id.cat_fact_image)

            //заполнение данными
            catFactTextView.text = cat.factText


/*

            try {
                val i: ImageView = findViewById<ImageView>(R.id.cat_fact_image)
                val bitmap = BitmapFactory.decodeStream(URL("https://i.imgur.com/DvpvklR.png").getContent() as InputStream)
                i.setImageBitmap(bitmap)
            } catch (e: MalformedURLException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }
*/



            if (cat.iconUrl != "") {
                Picasso.get()
                    .load(cat.iconUrl)
                    .fit().centerCrop()
                    .into(catFactImg)
            }




        }
    }
}