package com.example.catsfacts_madbrains_irlix

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.coroutines.*
import org.json.JSONObject
import java.net.URL

class DetailActivity : AppCompatActivity() {

    companion object {
        const val CAT_FACT_TAG = "cat_fact_tag"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        setupActionBar()
        setText()
        getImg(catImg, progressBarImg)

    }

    private fun setupActionBar(){
        supportActionBar?.apply {
            setDisplayShowHomeEnabled(true)
            setDisplayHomeAsUpEnabled(true)

            title = "Подробности"
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
    private  fun setText(){
        val text = intent?.extras?.getString(CAT_FACT_TAG)
        textView2.text = text
    }

    private  fun setImg(){
        val text = intent?.extras?.getString(CAT_FACT_TAG)
        textView2.text = text
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