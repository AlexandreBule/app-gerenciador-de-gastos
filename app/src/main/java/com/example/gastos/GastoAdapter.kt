package com.example.gastos

import android.content.Context
import android.content.Intent
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

        holder.price.setOnClickListener{
            val date = "${g.day}/${g.month}/${g.year}"
            val intent = Intent(c, GastoDetailActivity::class.java)
            intent.putExtra("tagGasto",g.tagGasto)
            intent.putExtra("description",g.description)
            intent.putExtra("price",g.price.toString())
            intent.putExtra("location",g.location)
            intent.putExtra("date",date)
            intent.putExtra("id",g.gastoId.toString())
            c.startActivity(intent)
        }

        holder.tag.setOnClickListener{
            val date = "${g.day}/${g.month}/${g.year}"
            val intent = Intent(c, GastoDetailActivity::class.java)
            intent.putExtra("tagGasto",g.tagGasto)
            intent.putExtra("description",g.description)
            intent.putExtra("price",g.price.toString())
            intent.putExtra("location",g.location)
            intent.putExtra("date",date)
            intent.putExtra("id",g.gastoId.toString())
            c.startActivity(intent)
        }
    }

    class ViewHolder (item : View) : RecyclerView.ViewHolder(item) {
        val price = item.price
        var tag = item.tagGasto
    }
}