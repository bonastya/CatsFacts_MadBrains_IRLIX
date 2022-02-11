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
import com.example.catsfacts_madbrains_irlix.CatsAdapterFavourit
import com.example.catsfacts_madbrains_irlix.R
import com.example.catsfacts_madbrains_irlix.databinding.FragmentMainBinding
import io.realm.Realm
import io.realm.RealmConfiguration
import org.json.JSONArray

//import com.example.catsfacts_madbrains_irlix.databinding.FragmentMainBinding

/**
 * A placeholder fragment containing a simple view.
 */
class SavedFactsFragment : Fragment() {

    private lateinit var pageViewModel: PageViewModel
    //private var _binding: SavedFactsFragmentBinding? = null
    //private val binding get() = _binding!!


    private val catSavedFacts: MutableList<CatFact> = mutableListOf()
    val catSavedFactsAdapter = CatsAdapter(catSavedFacts)

    private val url = "https://cat-fact.herokuapp.com/facts"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        pageViewModel = ViewModelProvider(this).get(PageViewModel::class.java).apply {
            setIndex(arguments?.getInt(ARG_SECTION_NUMBER) ?: 1)
        }
        println("ooo я создал фрагмент saved f")


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        println("ooo я создал view saved f")
        return inflater.inflate(R.layout.fragment_saved, container, false)

       /* _binding = FragmentMainBinding.inflate(inflater, container, false)
        val root = binding.root

        val catsRecyclerView: RecyclerView = binding.catsRecyclerView
        with(catsRecyclerView) {
            this.layoutManager = LinearLayoutManager(context)
            this.adapter = catSavedFactsAdapter
            this.setHasFixedSize(true)
        }


        initRealm()*/


        /*cattest.add(CatFact("121121gg fg fg h f h fh f  g g jyg j ","https://purr.objects-us-east-1.dream.io/i/034_-_axGmO0U.gif", true))
        cattest.add(CatFact("6565656gg fg fg h f h fh f  g g jyg j ","https://purr.objects-us-east-1.dream.io/i/r9c1kru.jpg", true))
        cattest.add(CatFact("121121gg fg fg h f h fh f  g g jyg j ","https://purr.objects-us-east-1.dream.io/i/034_-_axGmO0U.gif", true))
        cattest.add(CatFact("6565656gg fg fg h f h fh f  g g jyg j ","https://purr.objects-us-east-1.dream.io/i/r9c1kru.jpg", true))
*/
        if(arguments?.getInt(ARG_SECTION_NUMBER)==1){
            //факты из интернета(загрузить с сервера)
            val queue = Volley.newRequestQueue(context)
            getCatsFromServer(queue)
        }
        else{
            //избранное(загрузить из бд)
            //catTestAdapter.notifyDataSetChanged()

            //loadFromDB()
            /*for(i in catsInFavourites)
                catFacts.add(i)*/

            /*val catsRecyclerView: RecyclerView = binding.catsRecyclerView
            //mRecyclerView.setAdapter(new MyListAdapter(mRealm.allObjects(MyBook.class)))
            with(catsRecyclerView) {
                this.layoutManager = LinearLayoutManager(context)
                this.adapter = catFactsAdapter
                this.setHasFixedSize(true)
            }*/
        }


        //listenForCats()






        //return root
    }




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showList(view)
        println("ooo функция onViewCreated завершина")
    }

    private fun loadFromDB(): List<CatFact> {
        println("ooo функция loadFromDB")
        val realm = Realm.getDefaultInstance()
        println("ooo функция loadFromDB получено" + realm.where(CatFact::class.java).findAll())
        return realm.where(CatFact::class.java).findAll()
    }

    private fun setList(facts: List<CatFact>, view: View) {
        println("ooo функция setList")
        val adapter = CatsAdapter(facts)
        val recyclerViewId: RecyclerView = view.findViewById(R.id.SavedCatsRecyclerView)
        recyclerViewId.adapter = adapter

        val layoutManager = LinearLayoutManager(super.getContext())
        recyclerViewId.layoutManager = layoutManager
        println("ooo функция setList завершина")
    }

    private fun showList(view: View) {
        println("ooo функция showList")
        val cats = loadFromDB()
        setList(cats, view)
    }








    override fun onResume() {
        super.onResume()
        //val catsRecyclerView: RecyclerView = binding.catsRecyclerView
        //catsRecyclerView.layoutManager = LinearLayoutManager(context)
        //catsRecyclerView.adapter = catTestAdapter

    }

/*    private fun loadFromDB()*//*:List<CatFact>*//*{
        val realm = Realm.getDefaultInstance()
        println ("ggg "+realm.where(CatFact::class.java).findAll().toString())
        //return realm.where(CatFact::class.java).findAll()

        //val catsRecyclerView: RecyclerView = binding.catsRecyclerView
        //mRecyclerView.setAdapter(new MyListAdapter(mRealm.allObjects(MyBook.class)))

        catSavedFacts.addAll(realm.where(CatFact::class.java).findAll())
        catSavedFactsAdapter.notifyDataSetChanged()
       *//* with(catsRecyclerView) {
            this.layoutManager = LinearLayoutManager(context)
            this.adapter = catTestAdapter
            this.setHasFixedSize(true)
        }*//*
        *//*with(catsRecyclerView) {
            this.layoutManager = LinearLayoutManager(context)
            this.adapter = CatsAdapter(realm.where(CatFact::class.java).findAll())
            this.setHasFixedSize(true)
        }*//*

    }*/



    //получить с сервера строку с json
    private fun getCatsFromServer(queue: RequestQueue){
/*        val stringRequest = StringRequest(
            Request.Method.GET,
            url,
            { response ->
                parceResponce(response)
            },
            {
                Toast.makeText(context, "Ошибка запроса", Toast.LENGTH_SHORT).show()
                val catsRecyclerView: RecyclerView = binding.catsRecyclerView

                with(catsRecyclerView) {
                    this.layoutManager = LinearLayoutManager(context)
                    this.adapter = catFactsAdapter
                    this.setHasFixedSize(true)
                }
            }
        )
        queue.add(stringRequest)*/
    }

    //получить список объектов из json
    private fun parceResponce(responceText: String){
/*        //val cats: MutableList<CatFact> = mutableListOf()
        //catFacts.clear()
        val jsonArray = JSONArray(responceText)
        for(i in 0 until jsonArray.length()){
            val jsonObject = jsonArray.getJSONObject(i)
            val catText = jsonObject.getString("text")
            val cat = CatFact(catText, "", false)

            catFacts.add(cat)
            println("eee "+ catText)
        }
        println("eee23"+catFacts.size)
        val catsRecyclerView: RecyclerView = binding.catsRecyclerView

        with(catsRecyclerView) {
            this.layoutManager = LinearLayoutManager(context)
            this.adapter = catFactsAdapter
            this.setHasFixedSize(true)
        }*/


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
        fun newInstance(sectionNumber: Int): SavedFactsFragment {
            return SavedFactsFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        //_binding = null
    }

    private fun listenForCats() {






        if(arguments?.getInt(ARG_SECTION_NUMBER)==1){
            /*catFacts.add(CatFact("121121gg fg fg h f h fh f  g g jyg j ","https://purr.objects-us-east-1.dream.io/i/034_-_axGmO0U.gif", true))
            catFacts.add(CatFact("6565656gg fg fg h f h fh f  g g jyg j ","https://purr.objects-us-east-1.dream.io/i/r9c1kru.jpg", true))
            catFacts.add(CatFact("121121gg fg fg h f h fh f  g g jyg j ","https://purr.objects-us-east-1.dream.io/i/034_-_axGmO0U.gif", true))
            catFacts.add(CatFact("6565656gg fg fg h f h fh f  g g jyg j ","https://purr.objects-us-east-1.dream.io/i/r9c1kru.jpg", true))*/

        }
    }
}