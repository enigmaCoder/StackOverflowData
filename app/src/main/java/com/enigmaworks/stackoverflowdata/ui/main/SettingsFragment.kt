package com.ascensia.stackoverflowdata.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ascensia.stackoverflowdata.R
import kotlinx.android.synthetic.main.recyler_fragment.*

class SettingsFragment: Fragment() {

    companion object {
        fun newInstance() = SettingsFragment()
        var settingsAdapter: SettingsAdapter? = null
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.recyler_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val settingsArray: ArrayList<Pair<Pair<String,String>,Pair<InputTypes,Boolean>>> = arrayListOf()
        settingsArray.addAll(searchSettings)
        val slideDown = AnimationUtils.loadAnimation(context,R.anim.slide_down)
        val slideUp = AnimationUtils.loadAnimation(context,R.anim.slide_up)
        val recylerView = fragRecyclerView as RecyclerView
        viewModel = ViewModelProviders.of(this.requireActivity()).get(MainViewModel::class.java)
        context?.let { contextData ->
            settingsAdapter = SettingsAdapter(settingsArray,contextData,viewModel)
            recylerView.adapter = settingsAdapter
        }
        recylerView.visibility = View.GONE
        searchView.queryHint = "Query"
        searchView.setQuery(viewModel.searchSettingText as CharSequence,true)
        searchView.setIconifiedByDefault(true)
        searchView.setOnQueryTextFocusChangeListener { _, hasFocus ->
            if(!hasFocus && SettingsAdapter.currentPosition==-1){
                recylerView.visibility = View.GONE
                recylerView.startAnimation(slideUp)
                settingsAdapter?.apply {
                    SettingsAdapter.currentEditView = null
                    SettingsAdapter.currentPosition = -1
                    notifyDataSetChanged()
                }
            }else{
                if(!recylerView.isShown && !recylerView.isAnimating) {
                    recylerView.visibility = View.VISIBLE
                    recylerView.startAnimation(slideDown)
                }
            }
        }
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
        query?.let { queryParam ->
            viewModel.searchSettingText = queryParam
            settingsAdapter?.callUpdate()
        }
        return true
    }
}