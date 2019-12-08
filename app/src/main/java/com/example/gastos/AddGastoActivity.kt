package com.example.gastos

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.add_gasto.*
import org.jetbrains.anko.doAsync
import android.widget.Toast

class AddGastoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_gasto)

        description.setOnFocusChangeListener {_, hasFocus ->
            val text = description.getText().toString()
            if (!hasFocus) {
                val tagFromText = getTag(text)
                tag.setText(tagFromText)
            }
        }

        btn_Add.setOnClickListener {
            // Get values from inputs
            val description = description.getText().toString()
            val price = if (price.getText().toString().isNullOrEmpty()) 0.0 else price.getText().toString().toDouble()
            val tag = if (tag.getText().toString().isNullOrEmpty()) "" else tag.getText().toString()
            val location = if (location.getText().toString().isNullOrEmpty()) "" else location.getText().toString()
            val date = if (date.getText().toString().isNullOrEmpty()) listOf<String>("", "", "")  else date.getText().toString().split('/')

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

    fun getTag(text: String): String {
        val tag = text.substringAfter('#').split(' ')
        print(text)
        if (tag.size > 0) {
            return tag[0]
        } else {
            return ""
        }
    }
}
