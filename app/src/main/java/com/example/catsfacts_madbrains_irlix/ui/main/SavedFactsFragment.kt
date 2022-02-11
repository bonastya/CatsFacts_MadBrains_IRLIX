package com.example.catsfacts_madbrains_irlix.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.catsfacts_madbrains_irlix.CatFact
import com.example.catsfacts_madbrains_irlix.CatsAdapter
import com.example.catsfacts_madbrains_irlix.R
import io.realm.Realm


class SavedFactsFragment : Fragment() {

    private lateinit var pageViewModel: PageViewModel

    private val catSavedFacts: MutableList<CatFact> = mutableListOf()
    val catSavedFactsAdapter = CatsAdapter(catSavedFacts)

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
        return inflater.inflate(R.layout.fragment_saved, container, false)


    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showList(view)

    }

    private fun loadFromDB(): List<CatFact> {
        val realm = Realm.getDefaultInstance()
        return realm.where(CatFact::class.java).findAll()
    }

    private fun setList(facts: List<CatFact>, view: View) {
        val adapter = CatsAdapter(facts)
        val recyclerViewId: RecyclerView = view.findViewById(R.id.SavedCatsRecyclerView)
        recyclerViewId.adapter = adapter

        val layoutManager = LinearLayoutManager(super.getContext())
        recyclerViewId.layoutManager = layoutManager
    }

    private fun showList(view: View) {
        val cats = loadFromDB()
        setList(cats, view)
    }


    companion object {

        private const val ARG_SECTION_NUMBER = "section_number"
        @JvmStatic
        fun newInstance(sectionNumber: Int): SavedFactsFragment {
            return SavedFactsFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }

}