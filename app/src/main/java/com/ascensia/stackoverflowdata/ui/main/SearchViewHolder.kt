package com.ascensia.stackoverflowdata.ui.main

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ascensia.stackoverflowdata.R

class SearchViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val profileImage: ImageView = view.findViewById(R.id.profileImage)
    val titleStr: TextView = view.findViewById(R.id.titleStr)
    val contentStr: TextView = view.findViewById(R.id.contentStr)
}