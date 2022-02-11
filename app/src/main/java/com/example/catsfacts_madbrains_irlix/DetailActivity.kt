package com.example.catsfacts_madbrains_irlix

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso
import io.realm.Realm
import io.realm.RealmConfiguration
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

        val queue = Volley.newRequestQueue(this)
        getImgFromServer(catImg,queue)

        //getImg(catImg, progressBarImg)

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
                Toast.makeText(this, "Ошибка запроса", Toast.LENGTH_SHORT).show()
            }
        )

        queue.add(stringRequest)
    }


    fun addToFavorit(view: android.view.View) {
        val text = intent?.extras?.getString(CAT_FACT_TAG)

        val realm = Realm.getDefaultInstance()
        realm.beginTransaction()
        val cat: CatFact =realm.createObject(CatFact::class.java)
        cat.factText=text!!
        cat.isFavorit=true
        realm.commitTransaction()
    }

    private fun initRealm(){
        Realm.init(this)
        val config = RealmConfiguration.Builder()
            .deleteRealmIfMigrationNeeded()
            .build()
        Realm.setDefaultConfiguration(config)
    }

}