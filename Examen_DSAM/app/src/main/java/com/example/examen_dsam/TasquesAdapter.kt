package com.example.examen_dsam

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class TasquesAdapter(
    private val onTascaClick: (Tasca) -> Unit
) : RecyclerView.Adapter<TascaViewHolder>() {

    private var tasques = listOf<Tasca>()

    fun setTasques(novesTasques: List<Tasca>) {
        tasques = novesTasques
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TascaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_tasca, parent, false)
        return TascaViewHolder(view)
    }

    override fun onBindViewHolder(holder: TascaViewHolder, position: Int) {
        val tasca = tasques[position]
        holder.nom.text = tasca.nom
        holder.categoria.text = tasca.categoria.nom
        holder.data.text = tasca.data
        holder.estat.text = tasca.estat.nom
        if (tasca.estat.nom.equals("No començada")){
            holder.estat.setTextColor(holder.estat.resources.getColor(R.color.noComencat))
        } else if (tasca.estat.nom.equals("En curs")){
            holder.estat.setTextColor(holder.estat.resources.getColor(R.color.enCurs))
        } else if (tasca.estat.nom.equals("Finalitzada")){
            holder.estat.setTextColor(holder.estat.resources.getColor(R.color.finalitzada))
        }

        holder.itemView.setOnClickListener { onTascaClick(tasca) }
    }

    override fun getItemCount(): Int {
        return tasques.size
    }

}