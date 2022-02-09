package com.example.catsfacts_madbrains_irlix
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import com.squareup.picasso.Picasso

import kotlinx.coroutines.*
import org.json.JSONObject
import java.net.URL


class CatsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(cat: CatFact) {
        with(itemView) {
            val catFactTextView: TextView = findViewById<TextView>(R.id.catFactTextView)
            val catFactImg: ImageView = findViewById<ImageView>(R.id.cat_fact_image)
            val catProgressBar: ProgressBar = findViewById<ProgressBar>(R.id.catProgressBar)

            //заполнение данными
            catFactTextView.text = cat.factText
            getImg(catFactImg, catProgressBar)

        }
    }


    private val imgUrl = "https://aws.random.cat/meow"

    fun getImg(catFactImg: ImageView, catProgressBar: ProgressBar) {

        catProgressBar.visibility = View.VISIBLE
        val result: Deferred<String> = GlobalScope.async {
            getCatsImgFromServer()
        }

        GlobalScope.launch(Dispatchers.Main) {
            // get the downloaded bitmap
            val imgUrl : String = result.await()

            imgUrl?.apply {

                Picasso.get()
                    .load(imgUrl)
                    .fit().centerCrop()
                    .into(catFactImg)
            }

            catProgressBar.visibility = View.INVISIBLE
        }
    }


    private fun getCatsImgFromServer():String{
        var img = URL(imgUrl).readText()
        return parceImgResponce(img)
    }

    private fun parceImgResponce(responceText: String):String{
        val jsonObject = JSONObject(responceText)
        val catImg = jsonObject.getString("file").replace("\\", "")
        return catImg
    }


}