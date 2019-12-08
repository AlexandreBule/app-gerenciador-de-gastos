package com.example.gastos

import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_gasto.view.*

class GastoAdapter (private val gastos: List<Gasto>, private val c : Context) : RecyclerView.Adapter<GastoAdapter.ViewHolder>() {

    override fun getItemCount(): Int = gastos.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(c).inflate(R.layout.item_gasto, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val g = gastos[position]
        holder.price.text = g.price.toString()
        holder.tag.text = g.tagGasto
        Log.e("TAG", "Entrando no Adapter")
    }

    class ViewHolder (item : View) : RecyclerView.ViewHolder(item) {
        val price = item.price
        var tag = item.tagGasto

//        init {
//            Log.e("TAG3",item.toString())
//            item.setOnClickListener(this)
//        }
//
//        override fun onClick(v: View) {
//            val position = adapterPosition
//            Toast.makeText(v.context, "Clicou no item da posição: $position", Toast.LENGTH_SHORT).show()
//        }
    }
}