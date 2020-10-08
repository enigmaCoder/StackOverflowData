package com.enigmaworks.stackoverflowdata.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import com.enigmaworks.stackoverflowdata.R

class SettingsAdapter(val settingsArray: ArrayList<Pair<Pair<String,String>,Pair<InputTypes,Boolean>>>,private val context: Context,private val viewModel: MainViewModel): RecyclerView.Adapter<SettingsViewHolder>() {
    companion object{
        var currentPosition = -1
        var currentEditView: EditText? = null
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SettingsViewHolder {
        return SettingsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.search_setting,parent,false))
    }

    override fun onBindViewHolder(holder: SettingsViewHolder, position: Int) {
        holder.tagNameHolder.text = settingsArray[position].first.first
        holder.editText.setInputDataType(settingsArray[position].second.first,context)
        holder.tagCheckBox.setOnCheckedChangeListener { _, isChecked ->
            Pair(settingsArray[position].first,Pair(settingsArray[position].second.first,isChecked)).replaceOrAdd()
            settingsArray[position] = Pair(settingsArray[position].first,Pair(settingsArray[position].second.first,isChecked))
        }
        holder.tagCheckBox.isChecked = settingsArray[position].second.second
        holder.tagNameHolder.setOnClickListener {
            saveData(position)
        }
        if(currentPosition == position){
            val slideDown = AnimationUtils.loadAnimation(context,R.anim.slide_down)
            holder.editText.visibility = View.VISIBLE
            holder.editText.startAnimation(slideDown)
            holder.editText.setText(settingsArray[position].first.second)
            currentEditView = holder.editText
        }
    }

    override fun getItemCount(): Int {
        return settingsArray.size
    }

    private fun saveData(position: Int){
        val slideUp = AnimationUtils.loadAnimation(context,R.anim.slide_up)
        currentEditView?.let{
            Pair(Pair(settingsArray[currentPosition].first.first,it.text.toString()),settingsArray[currentPosition].second).replaceOrAdd()
            settingsArray[currentPosition] = Pair(Pair(settingsArray[currentPosition].first.first,it.text.toString()),settingsArray[currentPosition].second)
            it.visibility = View.GONE
            it.startAnimation(slideUp)
        }
        if(currentPosition != position){
            currentPosition = position
            callUpdate()
        }else{
            currentPosition = -1
            currentEditView = null
        }
    }

    fun callUpdate(){
        settingsArray.clear()
        if(viewModel.searchSettingText != "") {
            settingsArray.addAll(searchSettings.filter { it.first.first.contains(viewModel.searchSettingText) })
        }else{
            settingsArray.addAll(searchSettings)
        }
        notifyDataSetChanged()
    }
}