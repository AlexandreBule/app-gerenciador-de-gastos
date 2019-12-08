package com.example.gastos

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.MenuItem
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.floating_action_button
import kotlinx.android.synthetic.main.activity_main.listRecyclerView
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val c = Calendar.getInstance()
        val month = (c.get(Calendar.MONTH) + 1).toString()
        Log.e("MÊSSS", month)

        doAsync {
            val db = GastoDB.getDatabase(applicationContext)
            var lists = db.GastoDAO().buscaGastoPeloMes("12")

            uiThread {
                if (lists.size != 0) {
                    listRecyclerView.layoutManager = LinearLayoutManager(applicationContext)
                    listRecyclerView.adapter = GastoAdapter(lists, applicationContext)
                    Log.e("Descrição do Gasto", lists[0].description)
                }
            }
        }


        floating_action_button.setOnClickListener {
            val gasto = Gasto("Um gasto NOVO 3", 50.0, "Entretenimento", "Recife", "2", "12", "2019", "asdasd")
            doAsync {
                val db = GastoDB.getDatabase(applicationContext)
                db.GastoDAO().inserirGasto(gasto)
//                uiThread { finish() }
            }

            Log.e("Clique no botão", "Botão Clicado")
//          startActivity(Intent(applicationContext,RecyclerViewActivity::class.java))
        }
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
