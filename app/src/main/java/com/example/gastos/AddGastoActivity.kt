package com.example.gastos

import android.app.PendingIntent.getActivity
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.os.Parcelable
import android.provider.ContactsContract.Directory.PACKAGE_NAME
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

        fusedLocationClient.lastLocation
            .addOnSuccessListener { local : Location? ->
                Log.d("HERE", "HEERE")

                val MyLat = local!!.getLatitude()
                val MyLong = local!!.getLongitude()
                Log.d("local", MyLat.toString())

                val geocoder = Geocoder(this, Locale.getDefault())
                var addresses: List<Address> = emptyList()
                addresses = geocoder.getFromLocation(
                    MyLat,
                    MyLong,
                    // In this sample, we get just a single address.
                    1)

                val cityName = addresses[0].getAddressLine(0)
                val stateName = addresses[0].getAddressLine(1)
                val countryName = addresses[0].getAddressLine(2)
                Log.d("HEAAAAAAAAAAAAAAAAAARE", cityName)
                location.setText(cityName)
            }

        description.setOnFocusChangeListener {_, hasFocus ->
            val text = description.getText().toString()
            if (!hasFocus) {
                tag.setText(getTag(text))
                date.setText(getDate(text))

            /*    val local = intent.getParcelableExtra("${PACKAGE_NAME}.LOCATION_DATA_EXTRA") as Parcelable
                val geocoder = Geocoder(this, Locale.getDefault())
                var addresses: List<Address> = emptyList()
                addresses = geocoder.getFromLocation(
                    local.latitude,
                    local.longitude,
                    // In this sample, we get just a single address.
                    1)

                val cityName = addresses[0].getAddressLine(0)
                val stateName = addresses[0].getAddressLine(1)
                val countryName = addresses[0].getAddressLine(2)*/
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

    fun getTag(text: String) : String {
        val tag = text.substringAfter('#').split(' ')
        if (tag.size > 1) {
            return tag[0]
        } else {
            return ""
        }
    }

    fun getDate(text: String) : String {
        val c = Calendar.getInstance()
        val today = c.get(Calendar.DATE).toString() + "/" + (c.get(Calendar.MONTH).toString() + 1) +  "/" +  c.get(Calendar.YEAR).toString()
        val yesterday = (c.get(Calendar.DATE) - 1).toString() + "/" + (c.get(Calendar.MONTH).toString() + 1) +  "/" +  c.get(Calendar.YEAR).toString()

        if (text.contains("hoje")) {
            return today
        } else if(text.contains("ontem")) {
            return yesterday
        } else {
            return ""
        }
    }
}
