//package com.example.gastos
//
//import android.app.Activity
//import android.os.Bundle
//import android.util.Log
//import androidx.recyclerview.widget.LinearLayoutManager
//import kotlinx.android.synthetic.main.activity_recycler_view.*
//import org.jetbrains.anko.doAsync
//import org.jetbrains.anko.uiThread
//
//class RecyclerViewActivity : Activity() {
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_recycler_view)
//
//
//        Log.e("TAG", "RECYCLERRRRRRRR")
//
//        doAsync {
//            val db = GastoDB.getDatabase(applicationContext)
//            var lists = db.GastoDAO().buscaGastoPeloMes("Outubro")
//            listRecyclerView.layoutManager = LinearLayoutManager(this@RecyclerViewActivity)
//            listRecyclerView.adapter = GastoAdapter(lists, this@RecyclerViewActivity)
//            Log.e("KKKKKKKKKK", lists[0].description)
//            uiThread { finish() }
//        }
//    }
//}
