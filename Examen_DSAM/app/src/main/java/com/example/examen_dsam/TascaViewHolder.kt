package com.example.examen_dsam

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TascaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val nom = itemView.findViewById<TextView>(R.id.tvNom)
    val categoria = itemView.findViewById<TextView>(R.id.tvCategoria)
    val data = itemView.findViewById<TextView>(R.id.tvData)
    val estat = itemView.findViewById<TextView>(R.id.tvEstat)
}