package com.example.gastos

import android.content.Intent
import android.graphics.Bitmap
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.android.synthetic.main.add_gasto.*
import org.jetbrains.anko.doAsync
import java.util.*


class AddGastoActivity : AppCompatActivity() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_gasto)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        // Get location address
        fusedLocationClient.lastLocation
            .addOnSuccessListener { l : Location? ->
                if (l != null) {
                    val latitude = l.getLatitude()
                    val longitude = l.getLongitude()
                    val geocoder = Geocoder(this, Locale.getDefault())
                    val addressesList = geocoder.getFromLocation(
                        latitude,
                        longitude,
                        1)
                    val address = addressesList[0].getAddressLine(0)
                    location.setText(address)
                }
            }

        // When leaves description input, some other field may be filled
        description.setOnFocusChangeListener {_, hasFocus ->
            val text = description.getText().toString()
            if (!hasFocus) {
                tag.setText(getTag(text))
                date.setText(getDate(text))
            }
        }

        camera.setOnClickListener {
            Log.d("tag", "clicou")
            dispatchTakePictureIntent()
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
                tagGasto = tag,
                location = location,
                day = date[0],
                month = date[1],
                year = date[2],
                image = GASTO_IMAGE_STRING
            )

            doAsync {
                val db = GastoDB.getDatabase(applicationContext)
                db.GastoDAO().inserirGasto(gasto)
                val g = db.GastoDAO().buscaGastoPelaDescricao("gasto 2")
                Log.e("TAG", g.image)
            }

            // Go back to MainActivity
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
        }
    }

    fun getTag(text: String) : String {
        val regex = "#\\w+".toRegex()
        val match = regex.find(text)
        if (match != null) {
            val aux = match.value.split('#')
            return aux[1]
        } else {
            return ""
        }
    }

    fun getDate(text: String) : String {
        val c = Calendar.getInstance()
        val today = c.get(Calendar.DATE).toString() + "/" + (c.get(Calendar.MONTH) + 1).toString() +  "/" +  c.get(Calendar.YEAR).toString()
        val yesterday = (c.get(Calendar.DATE) - 1).toString() + "/" + (c.get(Calendar.MONTH) + 1).toString() +  "/" +  c.get(Calendar.YEAR).toString()

        if (text.contains("hoje")) {
            return today
        } else if(text.contains("ontem")) {
            return yesterday
        } else {
            return ""
        }
    }

    val REQUEST_IMAGE_CAPTURE = 1
    var GASTO_IMAGE_STRING = ""

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    override fun onActivityResult(requestCode:Int, resultCode:Int, data:Intent?) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            if (data != null) {
                var imageBitmap = data.extras!!.get("data")
                GASTO_IMAGE_STRING = imageBitmap.toString()
                imageBitmap = imageBitmap as Bitmap
                image.setImageBitmap(imageBitmap)
            }
        }
    }
}
