package com.example.gastos

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.add_gasto.*
import org.jetbrains.anko.doAsync


class AddGastoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_gasto)

        btn_Add.setOnClickListener {
            // Get values from inputs
            val description = description.getEditText()!!.getText().toString()
            val price = if (price.getEditText()!!.getText().toString().isNullOrEmpty()) 0.0 else price.getEditText()!!.getText().toString().toDouble()
            val tag = if (tag.getEditText()!!.getText().toString().isNullOrEmpty()) "" else tag.getEditText()!!.getText().toString()
            val location = if (location.getEditText()!!.getText().toString().isNullOrEmpty()) "" else location.getEditText()!!.getText().toString()
            val date = if (date.getEditText()!!.getText().toString().isNullOrEmpty()) listOf<String>("", "", "")  else date.getEditText()!!.getText().toString().split('/')

            val gasto = Gasto(
                description = description,
                price = price,
                tag = tag,
                location = location,
                day = date[0],
                month = date[1],
                year = date[2],
                image = ""
            )

            doAsync {
                val db = GastoDB.getDatabase(applicationContext)
                db.GastoDAO().inserirGasto(gasto)
                /*val g = db.GastoDAO().buscaGastoPelaDescricao("gasto 2")
                Log.e("TAG", g.toString())*/
            }

            // Go back to MainActivity
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
