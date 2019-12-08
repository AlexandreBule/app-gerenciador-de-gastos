package com.example.gastos

import android.app.Activity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_recycler_view.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class RecyclerViewActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view)

        var lists: ArrayList<Gasto> = []

        doAsync {
            val db = GastoDB.getDatabase(applicationContext)
            lists = db.GastoDAO().buscaGastoPeloMes("Outubro")
            uiThread { finish() }
        }

        listRecyclerView.layoutManager = LinearLayoutManager(this)
        listRecyclerView.adapter = GastoAdapter(lists.toArray(), this)
    }
}
