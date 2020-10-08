package com.enigmaworks.stackoverflowdata.ui.main

import android.app.DatePickerDialog
import android.content.Context
import android.text.InputType
import android.widget.EditText
import android.widget.ImageView
import com.enigmaworks.stackoverflowdata.R
import com.enigmaworks.stackoverflowdata.network.FilterParameters
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import io.reactivex.subjects.PublishSubject
import java.text.SimpleDateFormat
import java.util.*

enum class InputTypes{
    DATE,
    NUMBER,
    TEXT
}

val querySearchObservable  = PublishSubject.create<Map<String,String>>()

object searchSettings :ArrayList<Pair<Pair<String,String>,Pair<InputTypes,Boolean>>>(){
    init {
        FilterParameters.values().forEach {
            add(Pair(Pair(it.filterKeyValue,""),Pair(it.inputTypes,false)))
        }
    }
}

fun ArrayList<Pair<Pair<String,String>,Pair<InputTypes,Boolean>>>.callSearchQuery(key: String,value: String){
    val mapPosi = this.indexOfFirst { it.first.first == key }
    if(mapPosi!=-1) {
        this[mapPosi] = Pair(Pair(this[mapPosi].first.first, value), this[mapPosi].second)
        querySearchObservable.onNext(this.filter { it.second.second }
            .associate { it.first.first to it.first.second })
    }
}

fun Pair<Pair<String,String>,Pair<InputTypes,Boolean>>.replaceOrAdd(){
    val indexVal = searchSettings.indexOfFirst { it.first.first == this.first.first}
    if(indexVal!=-1){
        searchSettings[indexVal] = this
    }else{
        searchSettings.add(this)
    }
}

fun ImageView.loadImage(url: String?){
    val options = RequestOptions()
        .error(R.mipmap.ic_launcher_round)
    Glide.with(this.context)
        .setDefaultRequestOptions(options)
        .load(url)
        .into(this)
}

fun EditText.setInputDataType(inputTypes: InputTypes, context: Context){
    when(inputTypes){
        InputTypes.DATE -> {
            this.inputType = InputType.TYPE_DATETIME_VARIATION_DATE
            this.setOnFocusChangeListener { _, hasFocus ->
                if(hasFocus) {
                    val myCalendar = Calendar.getInstance()
                    DatePickerDialog(
                        context, { _, year, monthOfYear, dayOfMonth ->
                            // TODO Auto-generated method stub
                            myCalendar.set(Calendar.YEAR, year)
                            myCalendar.set(Calendar.MONTH, monthOfYear)
                            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                            this.setText(
                                SimpleDateFormat("YYYY-MM-dd", Locale.ENGLISH).format(
                                    myCalendar.time
                                )
                            )
                        }, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)
                    ).show()
                }
            }
        }
        InputTypes.NUMBER -> {
            this.inputType = InputType.TYPE_CLASS_NUMBER
        }
        InputTypes.TEXT -> {
            this.inputType = InputType.TYPE_CLASS_TEXT
        }
    }
}