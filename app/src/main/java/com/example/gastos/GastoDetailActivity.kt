package com.example.gastos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_gasto_detail.*
import org.jetbrains.anko.doAsync

class GastoDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gasto_detail)

        tagGasto.text = intent.getStringExtra("tagGasto")
        description.setText(intent.getStringExtra("description"))
        price.setText(intent.getStringExtra("price"))
        tag.setText(intent.getStringExtra("tagGasto"))
        location.setText(intent.getStringExtra("location"))
        date.setText(intent.getStringExtra("date"))

        val id = intent.getStringExtra("id")

        btn_Update.setOnClickListener {
            // Get values from inputs
            val description = description.getText().toString()
            val price = if (price.getText().toString().isNullOrEmpty()) 0.0 else price.getText().toString().toDouble()
            val tag = if (tag.getText().toString().isNullOrEmpty()) "" else tag.getText().toString()
            val location = if (location.getText().toString().isNullOrEmpty()) "" else location.getText().toString()
            val date = if (date.getText().toString().isNullOrEmpty()) listOf<String>("", "", "")  else date.getText().toString().split('/')

            //Add spent week
            var week: String
            var day = date[0].toInt()
            if (day <= 7) {
                week = "1"
            } else if (day <= 14) {
                week = "2"
            } else if (day <=21) {
                week = "3"
            } else {
                week = "4"
            }

            val gasto = Gasto(
                gastoId = id!!.toInt(),
                description = description,
                price = Math.round(price * 100) / 100.0,
                tagGasto = tag,
                location = location,
                day = date[0],
                month = date[1],
                year = date[2],
                week = week,
                image = ""
            )

            doAsync {
                val db = GastoDB.getDatabase(applicationContext)
                db.GastoDAO().atualizarGasto(gasto)
            }

            Toast.makeText(applicationContext, "Gasto atualizado!", Toast.LENGTH_SHORT).show()

            // Go back to MainActivity
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
        }

        btn_Delete.setOnClickListener {
            doAsync {
                val db = GastoDB.getDatabase(applicationContext)
                db.GastoDAO().deleteById(id!!.toInt())
            }

            Toast.makeText(applicationContext, "Gasto removido!", Toast.LENGTH_SHORT).show()

            // Go back to MainActivity
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
