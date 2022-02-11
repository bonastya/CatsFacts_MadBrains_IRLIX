package com.example.catsfacts_madbrains_irlix.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.catsfacts_madbrains_irlix.CatFact
import com.example.catsfacts_madbrains_irlix.CatsAdapter
import com.example.catsfacts_madbrains_irlix.R
import org.json.JSONArray


class AllFactsFragment : Fragment() {

    private lateinit var pageViewModel: PageViewModel
    private val url = "https://cat-fact.herokuapp.com/facts"

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
        return inflater.inflate(R.layout.fragment_main, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val queue = Volley.newRequestQueue(context)
        getCatsFromServer(queue, view)
    }


    //получить с сервера строку с json
    private fun getCatsFromServer(queue: RequestQueue, view: View){
        val stringRequest = StringRequest(
            Request.Method.GET,
            url,
            { response ->
                val catFacts = parceResponce(response)
                setList(catFacts, view)
            },
            {
                Toast.makeText(context, "Ошибка запроса", Toast.LENGTH_SHORT).show()

            }
        )
        queue.add(stringRequest)
    }

    //получить список объектов из json
    private fun parceResponce(responceText: String):List<CatFact>{
        val cats: MutableList<CatFact> = mutableListOf()
        val jsonArray = JSONArray(responceText)
        for(i in 0 until jsonArray.length()){
            val jsonObject = jsonArray.getJSONObject(i)
            val catText = jsonObject.getString("text")
            val cat = CatFact(catText, "", false)

            cats.add(cat)

        }
        return  cats


    }

    private fun setList(facts: List<CatFact>, view: View) {
        val adapter = CatsAdapter(facts)
        val recyclerViewId: RecyclerView = view.findViewById(R.id.catsRecyclerView)
        recyclerViewId.adapter = adapter

        val layoutManager = LinearLayoutManager(super.getContext())
        recyclerViewId.layoutManager = layoutManager
    }


    companion object {
        private const val ARG_SECTION_NUMBER = "section_number"
        @JvmStatic
        fun newInstance(sectionNumber: Int): AllFactsFragment {
            return AllFactsFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }


}