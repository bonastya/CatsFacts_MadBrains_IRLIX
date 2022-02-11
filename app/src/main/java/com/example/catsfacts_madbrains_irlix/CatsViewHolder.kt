package com.example.catsfacts_madbrains_irlix
import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.catsfacts_madbrains_irlix.DetailActivity.Companion.CAT_FACT_TAG

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
            //getImg(catFactImg, catProgressBar)
            val queue = Volley.newRequestQueue(context)
            getImgFromServer(catFactImg, queue)

            this.setOnClickListener{
                openDetailActivity(this.context, cat)
            }

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

    private fun getImgFromServer(catFactImg: ImageView, queue: RequestQueue) {
        val stringRequest = StringRequest(
            Request.Method.GET,
            imgUrl,
            { response ->
                val jsonObject = JSONObject(response)
                val catImg = jsonObject.getString("file").replace("\\", "")

                Picasso.get()
                    .load(catImg)
                    .fit().centerCrop()
                    .into(catFactImg)

            },
            {
                //Toast.makeText(this, "Ошибка запроса", Toast.LENGTH_SHORT).show()
            }
        )

        queue.add(stringRequest)
    }


    private fun getCatsImgFromServer():String{
        println("ooo функция getCatsImgFromServer")
        var img = URL(imgUrl).readText()
        println("ooo функция getCatsImgFromServer рез "+img)
        return parceImgResponce(img)
    }

    private fun parceImgResponce(responceText: String):String{
        val jsonObject = JSONObject(responceText)
        val catImg = jsonObject.getString("file").replace("\\", "")
        return catImg
    }

    private fun openDetailActivity(context: Context, cat: CatFact){
        val intent = Intent(context, DetailActivity::class.java)
        intent.putExtra(CAT_FACT_TAG, cat.factText)
        context.startActivity(intent)
    }


}