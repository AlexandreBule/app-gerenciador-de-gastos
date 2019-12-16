package com.example.gastos

import android.content.Intent
import android.os.Bundle
import android.util.DisplayMetrics
import androidx.appcompat.app.AppCompatActivity
import android.view.MenuItem
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.floating_action_button
import kotlinx.android.synthetic.main.activity_main.listRecyclerView
import kotlinx.android.synthetic.main.add_gasto.view.*
import java.util.*
import kotlin.collections.ArrayList
import android.R.attr.width
import android.R.attr.button
import android.widget.LinearLayout
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T



class MainActivity : AppCompatActivity() {

    private var lists: List<Gasto>? = null
    private var month: String = ""
    private var year: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        // get device dimensions
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val height = displayMetrics.heightPixels
        // set coordinator_floating_button min height
        coordinator_floating_button.setMinimumHeight(height - 100)


        val list1: ArrayList<Gasto> = ArrayList()
        val list2: ArrayList<Gasto> = ArrayList()
        val list3: ArrayList<Gasto> = ArrayList()
        val list4: ArrayList<Gasto> = ArrayList()

        var total: Double = 0.0
        var total1: Double = 0.0
        var total2: Double = 0.0
        var total3: Double = 0.0
        var total4: Double = 0.0

        val c = Calendar.getInstance()
        month = (c.get(Calendar.MONTH) + 1).toString()
        if(savedInstanceState?.getString("month") != null) {
            month = savedInstanceState?.getString("month")!!
        }
        year = (c.get(Calendar.YEAR)).toString()
        if (savedInstanceState?.getString("year") != null) {
            year = savedInstanceState?.getString("year")!!
        }
        when(month) {
            "1" -> monthTitle.text = "Janeiro"
            "2" -> monthTitle.text = "Fevereiro"
            "3" -> monthTitle.text = "Março"
            "4" -> monthTitle.text = "Abril"
            "5" -> monthTitle.text = "Maio"
            "6" -> monthTitle.text = "Junho"
            "7" -> monthTitle.text = "Julho"
            "8" -> monthTitle.text = "Agosto"
            "9" -> monthTitle.text = "Setembro"
            "10" -> monthTitle.text = "Outubro"
            "11" -> monthTitle.text = "Novembro"
            "12" -> monthTitle.text = "Dezembro"
        }

        doAsync {
            val db = GastoDB.getDatabase(applicationContext)
            lists = db.GastoDAO().buscaGastoPeloAnoMes(month, year)

            for (i in 0..lists!!.size - 1) {
                total = Math.round((total + lists!![i].price) * 100) / 100.0
                when(lists!![i].week) {
                    "1" -> {
                        list1.add(lists!![i])
                        total1 = Math.round((total1 + lists!![i].price) * 100) / 100.0
                    }
                    "2" -> {
                        list2.add(lists!![i])
                        total2 = Math.round((total2 + lists!![i].price) * 100) / 100.0
                    }
                    "3" -> {
                        list3.add(lists!![i])
                        total3 = Math.round((total3 + lists!![i].price) * 100) / 100.0
                    }
                    "4" -> {
                        list4.add(lists!![i])
                        total4 = Math.round((total4 + lists!![i].price) * 100) / 100.0
                    }
                }
            }

            uiThread {

                val auxTotal = "R$ $total"
                totalMonth.text = auxTotal

                if (list4.size != 0) {
                    val aux = "R$ $total4"
                    totalWeek4.text = aux
                    listRecyclerView.layoutManager = LinearLayoutManager(applicationContext)
                    listRecyclerView.adapter = GastoAdapter(list4.toList(), applicationContext)
                }
                if (list3.size != 0) {
                    val aux = "R$ $total3"
                    totalWeek3.text = aux
                    listRecyclerView2.layoutManager = LinearLayoutManager(applicationContext)
                    listRecyclerView2.adapter = GastoAdapter(list3.toList(), applicationContext)
                }
                if (list2.size != 0) {
                    val aux = "R$ $total2"
                    totalWeek2.text = aux
                    listRecyclerView3.layoutManager = LinearLayoutManager(applicationContext)
                    listRecyclerView3.adapter = GastoAdapter(list2.toList(), applicationContext)
                }
                if (list1.size != 0) {
                    val aux = "R$ $total1"
                    totalWeek1.text = aux
                    listRecyclerView4.layoutManager = LinearLayoutManager(applicationContext)
                    listRecyclerView4.adapter = GastoAdapter(list1.toList(), applicationContext)
                }
            }
        }

        Previous.setOnClickListener {
            val list1: ArrayList<Gasto> = ArrayList()
            val list2: ArrayList<Gasto> = ArrayList()
            val list3: ArrayList<Gasto> = ArrayList()
            val list4: ArrayList<Gasto> = ArrayList()

            var total: Double = 0.0
            var total1: Double = 0.0
            var total2: Double = 0.0
            var total3: Double = 0.0
            var total4: Double = 0.0

            var newMonth = month.toInt() - 1
            var newYear = year.toInt()
            if (newMonth == 0) {
                newMonth = 12
                newYear--
            }
            month = newMonth.toString()
            year = newYear.toString()
            when(month.toString()) {
                "1" -> monthTitle.text = "Janeiro"
                "2" -> monthTitle.text = "Fevereiro"
                "3" -> monthTitle.text = "Março"
                "4" -> monthTitle.text = "Abril"
                "5" -> monthTitle.text = "Maio"
                "6" -> monthTitle.text = "Junho"
                "7" -> monthTitle.text = "Julho"
                "8" -> monthTitle.text = "Agosto"
                "9" -> monthTitle.text = "Setembro"
                "10" -> monthTitle.text = "Outubro"
                "11" -> monthTitle.text = "Novembro"
                "12" -> monthTitle.text = "Dezembro"
            }

            doAsync {
                val db = GastoDB.getDatabase(applicationContext)
                lists = db.GastoDAO().buscaGastoPeloAnoMes(month, year)

                for (i in 0..lists!!.size - 1) {
                    total = Math.round((total + lists!![i].price) * 100) / 100.0
                    when (lists!![i].week) {
                        "1" -> {
                            list1.add(lists!![i])
                            total1 = Math.round((total1 + lists!![i].price) * 100) / 100.0
                        }
                        "2" -> {
                            list2.add(lists!![i])
                            total2 = Math.round((total2 + lists!![i].price) * 100) / 100.0
                        }
                        "3" -> {
                            list3.add(lists!![i])
                            total3 = Math.round((total3 + lists!![i].price) * 100) / 100.0
                        }
                        "4" -> {
                            list4.add(lists!![i])
                            total4 = Math.round((total4 + lists!![i].price) * 100) / 100.0
                        }
                    }
                }

                uiThread {

                    val auxTotal = "R$ $total"
                    totalMonth.text = auxTotal

                    if (lists!!.size == 0) {
                        totalWeek4.text = "R$ 0,00"
                        totalWeek3.text = "R$ 0,00"
                        totalWeek2.text = "R$ 0,00"
                        totalWeek1.text = "R$ 0,00"
                        listRecyclerView.layoutManager = LinearLayoutManager(applicationContext)
                        listRecyclerView.adapter = GastoAdapter(lists!!, applicationContext)
                        listRecyclerView2.layoutManager = LinearLayoutManager(applicationContext)
                        listRecyclerView2.adapter = GastoAdapter(lists!!, applicationContext)
                        listRecyclerView3.layoutManager = LinearLayoutManager(applicationContext)
                        listRecyclerView3.adapter = GastoAdapter(lists!!, applicationContext)
                        listRecyclerView4.layoutManager = LinearLayoutManager(applicationContext)
                        listRecyclerView4.adapter = GastoAdapter(lists!!, applicationContext)
                    }

                    if (list4.size != 0) {
                        val aux = "R$ $total4"
                        totalWeek4.text = aux
                        listRecyclerView.layoutManager = LinearLayoutManager(applicationContext)
                        listRecyclerView.adapter = GastoAdapter(list4.toList(), applicationContext)
                    } else {
                        totalWeek1.text = "R$ 0,00"
                        listRecyclerView.layoutManager = LinearLayoutManager(applicationContext)
                        listRecyclerView.adapter = GastoAdapter(emptyList(), applicationContext)
                    }
                    if (list3.size != 0) {
                        val aux = "R$ $total3"
                        totalWeek3.text = aux
                        listRecyclerView2.layoutManager = LinearLayoutManager(applicationContext)
                        listRecyclerView2.adapter = GastoAdapter(list3.toList(), applicationContext)
                    } else {
                        totalWeek1.text = "R$ 0,00"
                        listRecyclerView2.layoutManager = LinearLayoutManager(applicationContext)
                        listRecyclerView2.adapter = GastoAdapter(emptyList(), applicationContext)
                    }
                    if (list2.size != 0) {
                        val aux = "R$ $total2"
                        totalWeek2.text = aux
                        listRecyclerView3.layoutManager = LinearLayoutManager(applicationContext)
                        listRecyclerView3.adapter = GastoAdapter(list2.toList(), applicationContext)
                    } else {
                        totalWeek1.text = "R$ 0,00"
                        listRecyclerView3.layoutManager = LinearLayoutManager(applicationContext)
                        listRecyclerView3.adapter = GastoAdapter(emptyList(), applicationContext)
                    }
                    if (list1.size != 0) {
                        val aux = "R$ $total1"
                        totalWeek1.text = aux
                        listRecyclerView4.layoutManager = LinearLayoutManager(applicationContext)
                        listRecyclerView4.adapter = GastoAdapter(list1.toList(), applicationContext)
                    } else {
                        totalWeek1.text = "R$ 0,00"
                        listRecyclerView4.layoutManager = LinearLayoutManager(applicationContext)
                        listRecyclerView4.adapter = GastoAdapter(emptyList(), applicationContext)
                    }
                }
            }
        }

        Next.setOnClickListener {
            val list1: ArrayList<Gasto> = ArrayList()
            val list2: ArrayList<Gasto> = ArrayList()
            val list3: ArrayList<Gasto> = ArrayList()
            val list4: ArrayList<Gasto> = ArrayList()

            var total: Double = 0.0
            var total1: Double = 0.0
            var total2: Double = 0.0
            var total3: Double = 0.0
            var total4: Double = 0.0

            var newMonth = month.toInt() + 1
            var newYear = year.toInt()
            if (newMonth == 13) {
                newMonth = 1
                newYear++
            }
            month = newMonth.toString()
            year = newYear.toString()
            when(month.toString()) {
                "1" -> monthTitle.text = "Janeiro"
                "2" -> monthTitle.text = "Fevereiro"
                "3" -> monthTitle.text = "Março"
                "4" -> monthTitle.text = "Abril"
                "5" -> monthTitle.text = "Maio"
                "6" -> monthTitle.text = "Junho"
                "7" -> monthTitle.text = "Julho"
                "8" -> monthTitle.text = "Agosto"
                "9" -> monthTitle.text = "Setembro"
                "10" -> monthTitle.text = "Outubro"
                "11" -> monthTitle.text = "Novembro"
                "12" -> monthTitle.text = "Dezembro"
            }

            doAsync {
                val db = GastoDB.getDatabase(applicationContext)
                lists = db.GastoDAO().buscaGastoPeloAnoMes(month, year)

                for (i in 0..lists!!.size - 1) {
                    total = Math.round((total + lists!![i].price) * 100) / 100.0
                    when (lists!![i].week) {
                        "1" -> {
                            list1.add(lists!![i])
                            total1 = Math.round((total1 + lists!![i].price) * 100) / 100.0
                        }
                        "2" -> {
                            list2.add(lists!![i])
                            total2 = Math.round((total2 + lists!![i].price) * 100) / 100.0
                        }
                        "3" -> {
                            list3.add(lists!![i])
                            total3 = Math.round((total3 + lists!![i].price) * 100) / 100.0
                        }
                        "4" -> {
                            list4.add(lists!![i])
                            total4 = Math.round((total4 + lists!![i].price) * 100) / 100.0
                        }
                    }
                }

                uiThread {

                    val auxTotal = "R$ $total"
                    totalMonth.text = auxTotal

                    if (lists!!.size == 0) {
                        totalWeek4.text = "R$ 0,00"
                        totalWeek3.text = "R$ 0,00"
                        totalWeek2.text = "R$ 0,00"
                        totalWeek1.text = "R$ 0,00"
                        listRecyclerView.layoutManager = LinearLayoutManager(applicationContext)
                        listRecyclerView.adapter = GastoAdapter(lists!!, applicationContext)
                        listRecyclerView2.layoutManager = LinearLayoutManager(applicationContext)
                        listRecyclerView2.adapter = GastoAdapter(lists!!, applicationContext)
                        listRecyclerView3.layoutManager = LinearLayoutManager(applicationContext)
                        listRecyclerView3.adapter = GastoAdapter(lists!!, applicationContext)
                        listRecyclerView4.layoutManager = LinearLayoutManager(applicationContext)
                        listRecyclerView4.adapter = GastoAdapter(lists!!, applicationContext)
                    }

                    if (list4.size != 0) {
                        val aux = "R$ $total4"
                        totalWeek4.text = aux
                        listRecyclerView.layoutManager = LinearLayoutManager(applicationContext)
                        listRecyclerView.adapter = GastoAdapter(list4.toList(), applicationContext)
                    } else {
                        totalWeek1.text = "R$ 0,00"
                        listRecyclerView.layoutManager = LinearLayoutManager(applicationContext)
                        listRecyclerView.adapter = GastoAdapter(emptyList(), applicationContext)
                    }
                    if (list3.size != 0) {
                        val aux = "R$ $total3"
                        totalWeek3.text = aux
                        listRecyclerView2.layoutManager = LinearLayoutManager(applicationContext)
                        listRecyclerView2.adapter = GastoAdapter(list3.toList(), applicationContext)
                    } else {
                        totalWeek1.text = "R$ 0,00"
                        listRecyclerView2.layoutManager = LinearLayoutManager(applicationContext)
                        listRecyclerView2.adapter = GastoAdapter(emptyList(), applicationContext)
                    }
                    if (list2.size != 0) {
                        val aux = "R$ $total2"
                        totalWeek2.text = aux
                        listRecyclerView3.layoutManager = LinearLayoutManager(applicationContext)
                        listRecyclerView3.adapter = GastoAdapter(list2.toList(), applicationContext)
                    } else {
                        totalWeek1.text = "R$ 0,00"
                        listRecyclerView3.layoutManager = LinearLayoutManager(applicationContext)
                        listRecyclerView3.adapter = GastoAdapter(emptyList(), applicationContext)
                    }
                    if (list1.size != 0) {
                        val aux = "R$ $total1"
                        totalWeek1.text = aux
                        listRecyclerView4.layoutManager = LinearLayoutManager(applicationContext)
                        listRecyclerView4.adapter = GastoAdapter(list1.toList(), applicationContext)
                    } else {
                        totalWeek1.text = "R$ 0,00"
                        listRecyclerView4.layoutManager = LinearLayoutManager(applicationContext)
                        listRecyclerView4.adapter = GastoAdapter(emptyList(), applicationContext)
                    }
                }
            }
        }

        floating_action_button.setOnClickListener {
            // Open AddGastoActivity
            val intent = Intent(applicationContext, AddGastoActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString("month",month)
        outState.putString("year",year)
        super.onSaveInstanceState(outState)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
