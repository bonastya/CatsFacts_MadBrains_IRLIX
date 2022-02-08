package com.example.catsfacts_madbrains_irlix

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.catsfacts_madbrains_irlix.R
import com.example.catsfacts_madbrains_irlix.CatFact

class CatsAdapter( var catsList: MutableList<CatFact>) :
    RecyclerView.Adapter<CatsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatsViewHolder {
        val itemView: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_cat, parent, false)
        return CatsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CatsViewHolder, position: Int) {
        holder.bind(catsList[position])
    }

    override fun getItemCount() = catsList.size

    fun add(cat: CatFact){
        catsList.add(cat)
        notifyDataSetChanged()
    }



}