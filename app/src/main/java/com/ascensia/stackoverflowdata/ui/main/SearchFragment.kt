package com.ascensia.stackoverflowdata.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ascensia.stackoverflowdata.R
import kotlinx.android.synthetic.main.recyler_fragment.*

class SearchFragment: Fragment() {

    companion object {
        fun newInstance() = SearchFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.recyler_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recylerView = fragRecyclerView as RecyclerView
        val settingsAdapter = SearchViewAdapter(arrayListOf())
        recylerView.adapter = settingsAdapter
        viewModel = ViewModelProviders.of(this.requireActivity()).get(MainViewModel::class.java)
        viewModel.subscribeForStackResponse {
            val dataList = it.items.map { itemData -> Pair(itemData.owner.profile_image,Pair(itemData.title,itemData.tags.toString())) }
            settingsAdapter.updateList(ArrayList(dataList))
        }
        searchView.queryHint = "Filter Text..."
        searchView.setIconifiedByDefault(true)
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return searchCall(query)
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return searchCall(newText)
            }

        })
        recylerView.layoutManager = LinearLayoutManager(activity)
    }

    private fun searchCall(query: String?): Boolean{

        query?.let {
            searchSettings.callSearchQuery(viewModel.searchSettingText, query)
        }
        return true
    }
}