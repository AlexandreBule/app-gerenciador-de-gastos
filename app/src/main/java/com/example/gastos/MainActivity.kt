package com.example.gastos

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.MenuItem
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        floating_action_button.setOnClickListener {
            val gasto = Gasto("Um gasto", 23.4, "Comida", "Recife", "2", "10", "2019", "asdasd")
            doAsync {
                val db = GastoDB.getDatabase(applicationContext)
                db.GastoDAO().inserirGasto(gasto)
                uiThread { finish() }
            }
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
