package com.example.catsfacts_madbrains_irlix

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso
import io.realm.Realm
import io.realm.RealmResults
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.coroutines.*
import org.json.JSONObject

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

        val addButton: Button = findViewById(R.id.addButton)

        if (getFactFromDB().isEmpty()) {
            addButton.text = "Добавить в избранное"
        } else {
            addButton.text = "Удалить из избранного"
        }


    }

    private fun setupActionBar(){
        supportActionBar?.apply {
            setDisplayShowHomeEnabled(true)
            setDisplayHomeAsUpEnabled(true)

            title = "Подробности"
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        startMainActivity(this)
        finish()
        return true
    }
    private  fun setText(){
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

        if (getFactFromDB().isEmpty()) {
            val cat: CatFact =realm.createObject(CatFact::class.java)
            cat.factText=text!!
            cat.isFavorit=true
        }else{
            val catFacts: RealmResults<CatFact> = getFactFromDB()
            if (!catFacts.isEmpty()) {
                for (i in catFacts.size - 1 downTo 0) {
                    catFacts[i]?.deleteFromRealm()
                }
            }

        }

        realm.commitTransaction()
        startMainActivity(this)

    }

    private fun getFactFromDB(): RealmResults<CatFact> {
        val realm = Realm.getDefaultInstance()

        return realm.where(CatFact::class.java)
            .equalTo("factText", intent?.extras?.getString(CAT_FACT_TAG))
            .findAll()
    }

    private fun startMainActivity(context: Context) {
        val intent = Intent(context, MainActivity::class.java)
        context.startActivity(intent)
        overridePendingTransition(R.anim.slide_left, R.anim.slide_right)
    }



}