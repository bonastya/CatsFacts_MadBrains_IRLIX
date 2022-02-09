package com.example.catsfacts_madbrains_irlix.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.catsfacts_madbrains_irlix.CatFact
import com.example.catsfacts_madbrains_irlix.CatsAdapter
import com.example.catsfacts_madbrains_irlix.R
import com.example.catsfacts_madbrains_irlix.databinding.FragmentMainBinding
import kotlinx.coroutines.Deferred
import org.json.JSONArray
import org.json.JSONObject



/**
 * A placeholder fragment containing a simple view.
 */
class PlaceholderFragment : Fragment() {

    private lateinit var pageViewModel: PageViewModel
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!


    private val catFacts: ArrayList<CatFact> = ArrayList()
    private val catImages: ArrayList<String> = ArrayList()
    val catFactsAdapter = CatsAdapter(catFacts)

    private val url = "https://cat-fact.herokuapp.com/facts"
    private val imgUrl = "https://aws.random.cat/meow  "

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pageViewModel = ViewModelProvider(this).get(PageViewModel::class.java).apply {
            setIndex(arguments?.getInt(ARG_SECTION_NUMBER) ?: 1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMainBinding.inflate(inflater, container, false)
        val root = binding.root


        val queue = Volley.newRequestQueue(context)
        getCatsFromServer(queue)

        listenForCats()


        //добавление адаптера к RecyclerView
        val catsRecyclerView: RecyclerView = binding.catsRecyclerView

        with(catsRecyclerView) {
            this.layoutManager = LinearLayoutManager(context)
            this.adapter = catFactsAdapter
            this.setHasFixedSize(true)
        }

        return root
    }

    //получить с сервера строку с json
    private fun getCatsFromServer(queue: RequestQueue){
        val stringRequest = StringRequest(
            Request.Method.GET,
            url,
            { response ->
                parceResponce(response)
            },
            {
                Toast.makeText(context, "Ошибка запроса", Toast.LENGTH_SHORT).show()
            }
        )
        queue.add(stringRequest)
    }

    //получить список объектов из json
    private fun parceResponce(responceText: String){

        val jsonArray = JSONArray("[{"+responceText)
        for(i in 0 until jsonArray.length()){
            val jsonObject = jsonArray.getJSONObject(i)
            val catText = jsonObject.getString("text")
            val cat = CatFact(catText, "", false)

            catFacts.add(cat)
        }


    }




    companion object {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private const val ARG_SECTION_NUMBER = "section_number"

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        @JvmStatic
        fun newInstance(sectionNumber: Int): PlaceholderFragment {
            return PlaceholderFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun listenForCats() {






        if(arguments?.getInt(ARG_SECTION_NUMBER)==1){

        }
        catFacts.add(CatFact("121121gg fg fg h f h fh f  g g jyg j ","https://purr.objects-us-east-1.dream.io/i/034_-_axGmO0U.gif", true))
        //catFacts.add(CatFact("6565656gg fg fg h f h fh f  g g jyg j ","https://purr.objects-us-east-1.dream.io/i/r9c1kru.jpg", true))
    }
}