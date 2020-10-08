package com.enigmaworks.stackoverflowdata.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.enigmaworks.stackoverflowdata.R

class SearchViewAdapter(private val itemsList: ArrayList<Pair<String,Pair<String,String>>>): RecyclerView.Adapter<SearchViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        return SearchViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.search_fields,parent,false))
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.profileImage.loadImage(itemsList[position].first)
        holder.titleStr.text = itemsList[position].second.first
        holder.contentStr.text = itemsList[position].second.second
    }

    fun updateList(list: ArrayList<Pair<String,Pair<String,String>>>){
        itemsList.clear()
        itemsList.addAll(list)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }
}