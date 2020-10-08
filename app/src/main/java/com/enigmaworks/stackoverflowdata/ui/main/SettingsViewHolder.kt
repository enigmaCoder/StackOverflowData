package com.ascensia.stackoverflowdata.ui.main

import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ascensia.stackoverflowdata.R

class SettingsViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val tagNameHolder: TextView = view.findViewById(R.id.tagNameHolder)
    val tagCheckBox: CheckBox = view.findViewById(R.id.tagCheckBox)
    val editText: EditText = view.findViewById(R.id.tagTextField)
}