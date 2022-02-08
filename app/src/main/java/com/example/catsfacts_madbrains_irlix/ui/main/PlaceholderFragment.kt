package com.example.catsfacts_madbrains_irlix.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.catsfacts_madbrains_irlix.CatFact
import com.example.catsfacts_madbrains_irlix.CatsAdapter
import com.example.catsfacts_madbrains_irlix.R
import com.example.catsfacts_madbrains_irlix.databinding.FragmentMainBinding

//import com.example.catsfacts_madbrains_irlix.databinding.FragmentMainBinding

/**
 * A placeholder fragment containing a simple view.
 */
class PlaceholderFragment : Fragment() {

    private lateinit var pageViewModel: PageViewModel
    private var _binding: FragmentMainBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val catFacts: ArrayList<CatFact> = ArrayList()
    val catFactsAdapter = CatsAdapter(catFacts)

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
        listenForCats()

        //добавление адаптера к RecyclerView
        val catsRecyclerView: RecyclerView = binding.catsRecyclerView


        with(catsRecyclerView) {
            this.layoutManager = LinearLayoutManager(context)
            this.adapter = catFactsAdapter
            this.setHasFixedSize(true)
        }

        val textView: TextView = binding.sectionLabel
        pageViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
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
            catFacts.add(CatFact("121121gg fg fg h f h fh f  g g jyg j ","https://purr.objects-us-east-1.dream.io/i/034_-_axGmO0U.gif", true))
            catFacts.add(CatFact("6565656gg fg fg h f h fh f  g g jyg j ","https://purr.objects-us-east-1.dream.io/i/r9c1kru.jpg", true))
            catFacts.add(CatFact("121121gg fg fg h f h fh f  g g jyg j ","https://purr.objects-us-east-1.dream.io/i/034_-_axGmO0U.gif", true))
            catFacts.add(CatFact("6565656gg fg fg h f h fh f  g g jyg j ","https://purr.objects-us-east-1.dream.io/i/r9c1kru.jpg", true))

        }
        catFacts.add(CatFact("121121gg fg fg h f h fh f  g g jyg j ","https://purr.objects-us-east-1.dream.io/i/034_-_axGmO0U.gif", true))
        catFacts.add(CatFact("6565656gg fg fg h f h fh f  g g jyg j ","https://purr.objects-us-east-1.dream.io/i/r9c1kru.jpg", true))
    }
}