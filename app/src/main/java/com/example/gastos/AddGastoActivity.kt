package com.example.gastos

import android.Manifest
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.location.Geocoder
import android.location.Location
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.android.synthetic.main.add_gasto.*
import org.jetbrains.anko.Android
import org.jetbrains.anko.doAsync
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.util.*


class AddGastoActivity : AppCompatActivity() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private val REQUEST_IMAGE_CAPTURE = 1
    private val IMAGE_PICK_CODE = 1000
    private val PERMISSION_CODE = 1001

    private var GASTO_IMAGE_STRING = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_gasto)

        // Ask for permissions
        val permissions = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        ActivityCompat.requestPermissions(this, permissions, 0)

        // When leaves description input, some other field may be filled
        description.setOnFocusChangeListener { _, hasFocus ->
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
            // Get location address
            fusedLocationClient.lastLocation
                .addOnSuccessListener { l: Location? ->
                    if (l != null) {
                        val latitude = l.getLatitude()
                        val longitude = l.getLongitude()
                        val geocoder = Geocoder(this, Locale.getDefault())
                        val addressesList = geocoder.getFromLocation(
                            latitude,
                            longitude,
                            1
                        )
                        val address = addressesList[0].getAddressLine(0)
                        location.setText(address)
                    }
                }

            val text = description.getText().toString()
            if (!hasFocus) {
                tag.setText(getTag(text))
                date.setText(getDate(text))
            }
        }

        camera.setOnClickListener {
            dispatchTakePictureIntent()
        }

        gallery.setOnClickListener {
            // check runtime permission
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_DENIED
                ) {
                    // permission denied
                    val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE);
                    // show popup to request runtime permission
                    requestPermissions(permissions, PERMISSION_CODE);
                } else {
                    // permission already granted
                    pickImageFromGallery();
                }
            } else {
                // system OS is < Marshmallow
                pickImageFromGallery();
            }
        }

        btn_Add.setOnClickListener {
            // Get values from inputs
            val description = description.getText().toString()
            val price =
                if (price.getText().toString().isNullOrEmpty()) 0.0 else getPrice(price.getText().toString()).toDouble()
            val tag = if (tag.getText().toString().isNullOrEmpty()) "" else tag.getText().toString()
            val location =
                if (location.getText().toString().isNullOrEmpty()) "" else location.getText().toString()
            val date = if (date.getText().toString().isNullOrEmpty()) listOf<String>(
                "",
                "",
                ""
            ) else date.getText().toString().split('/')

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
                gastoId = 0,
                description = description,
                price = Math.round(price * 100) / 100.0,
                tagGasto = tag,
                location = location,
                day = date[0],
                month = date[1],
                year = date[2],
                week = week,
                image = GASTO_IMAGE_STRING
            )

            doAsync {
                val db = GastoDB.getDatabase(applicationContext)
                db.GastoDAO().inserirGasto(gasto)
            }

            Toast.makeText(applicationContext, "Gasto adicionado!", Toast.LENGTH_SHORT).show()

            // Go back to MainActivity
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
        }
    }

    fun getTag(text: String): String {
        val regex = "#\\w+".toRegex()
        val match = regex.find(text)
        if (match != null) {
            val aux = match.value.split('#')
            return aux[1]
        } else {
            return ""
        }
    }

    fun getDate(text: String): String {
        val c = Calendar.getInstance()
        val today =
            c.get(Calendar.DATE).toString() + "/" + (c.get(Calendar.MONTH) + 1).toString() + "/" + c.get(
                Calendar.YEAR
            ).toString()
        val yesterday =
            (c.get(Calendar.DATE) - 1).toString() + "/" + (c.get(Calendar.MONTH) + 1).toString() + "/" + c.get(
                Calendar.YEAR
            ).toString()

        if (text.contains("hoje")) {
            return today
        } else if (text.contains("ontem")) {
            return yesterday
        } else {
            return ""
        }
    }

    fun getPrice(text: String) : String {
        var price = text.replace(",", ".")
        return  price
    }

    private fun pickImageFromGallery() {
        //Intent to pick image
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    // Handle requested permission result
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSION_CODE -> {
                if (grantResults.size > 0 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED
                ) {
                    //permission from popup granted
                    pickImageFromGallery()
                } else {
                    //permission from popup denied
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    // Method to save an image to internal storage
    private fun saveImageToInternalStorage(bitmap: Bitmap): Uri {
        val wrapper = ContextWrapper(applicationContext)

        // The bellow line return a directory in internal storage
        var file = wrapper.getDir("Images", Context.MODE_PRIVATE)
        if(!file.exists()){
            file.mkdirs()
        }
        // Create a file to save the image
        file = File(file, "${UUID.randomUUID()}.jpg")

        try {
            val stream: OutputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            stream.flush()
            stream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        Log.d("FILE PATH", file.absolutePath.toString())
        // Return the saved image uri
        return Uri.parse(file.absolutePath)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        // Camera
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            var imageBitmap = data?.extras!!.get("data")
            imageBitmap = imageBitmap as Bitmap
            camera.setImageBitmap(imageBitmap)

            // save Image To Internal Storage
            val uri: Uri = saveImageToInternalStorage(imageBitmap)
            GASTO_IMAGE_STRING = uri.toString()
        }

        // Gallery
        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            gallery.setImageURI(data?.data)
            GASTO_IMAGE_STRING = data?.data.toString()
        }
    }
}
