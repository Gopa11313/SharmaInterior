package com.example.sharmainteriordesign.ui

import android.app.Activity
import android.app.UiModeManager.MODE_NIGHT_NO
import android.app.UiModeManager.MODE_NIGHT_YES
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.sharmainteriordesign.Notificationchannel
import com.example.sharmainteriordesign.R
import com.example.sharmainteriordesign.api.ServiceBuilder
import com.example.sharmainteriordesign.repository.ProductRepository
import com.example.sharmainteriordesign.repository.UserRepository
import com.example.sharmainteriordesign.ui.model.Product
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

class AddProductActivity : AppCompatActivity(),SensorEventListener {
    private lateinit var area: EditText
    private lateinit var price:EditText
    private lateinit var location:EditText
    private lateinit var profilepic:ImageView
    private lateinit var phNo:EditText
    private lateinit var submit:Button
    private val REQUEST_GALLERY_CODE=0;
    private val REQUEST_CAMERA_CODE=1;
    private var imageUrl:String?=null;
    private lateinit var sensorManager: SensorManager
    private var sensor: Sensor? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_product)
        area=findViewById(  R.id.area)
        price=findViewById(R.id.price)
        location=findViewById(R.id.location)
        profilepic=findViewById(R.id.profilepic)
        phNo=findViewById(R.id.phNo)
        submit=findViewById(R.id.submit)
        submit.setOnClickListener(){
            val product= Product(area = area.text.toString(),price = price.text.toString(),location = location.text.toString(),phNo = phNo.text.toString(),userId = ServiceBuilder.id!!)
            CoroutineScope(Dispatchers.IO).launch {
                val repository= ProductRepository()
                val response=repository.insertProduct(product)
                if(response.success==true){
                    val id=response.id
                    showHighPriorityNotification()
                    if(imageUrl != null){
                        uploadImage(id!!)
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@AddProductActivity, "Student Added Successfully", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                else{
                    withContext(Main){
                        Toast.makeText(this@AddProductActivity, "${response.msg}", Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }
        sensorManager = getSystemService(AppCompatActivity.SENSOR_SERVICE) as SensorManager
        if (!checkSensor())
            return
        else {
            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
        }

        profilepic.setOnClickListener(){
            popup()
        }
    }
    private fun showHighPriorityNotification(){
        val notificationManager= NotificationManagerCompat.from(this)

        val notificationChannels= Notificationchannel(this)
        notificationChannels.createNotificationChannels()

        val notification= NotificationCompat.Builder(this,notificationChannels.CHANNEL_1)
                .setSmallIcon(R.drawable.ic_baseline_sms_24)
                .setContentTitle(ServiceBuilder.name)
                .setContentText("Uploaded ${location.text.toString()} note.")
                .setColor(Color.BLUE)
                .build()
        notificationManager.notify(1,notification)
    }

    private fun uploadImage(studentId: String) {
        if (imageUrl != null) {
            val file = File(imageUrl!!)
            val reqFile =
                    RequestBody.create(MediaType.parse("image/${file.extension}"), file)
            val body =
                    MultipartBody.Part.createFormData("file", file.name, reqFile)
            CoroutineScope(Dispatchers.IO).launch {
                    val studentRepository = ProductRepository()
                    val response = studentRepository.uploadImage(studentId, body)
                    if (response.success == true) {
                        withContext(Main) {
                            Toast.makeText(this@AddProductActivity, "Uploaded", Toast.LENGTH_SHORT)
                                    .show()
                        }
                    } else {
                        withContext(Main) {
                            Toast.makeText(this@AddProductActivity, "image no uploaded", Toast.LENGTH_SHORT).show()
                        }
                    }
//                }
//                catch (ex: Exception) {
//                    withContext(Main) {
//                        Toast.makeText(this@AddProductActivity, "$body", Toast.LENGTH_SHORT).show()
//                        Log.d("Error uploading image ", ex.toString())
//                        Toast.makeText(
//                                this@AddProductActivity,
//                                ex.localizedMessage,
//                                Toast.LENGTH_SHORT
//                        ).show()
//                    }
               // }
            }
        }
    }
    private fun clear(){
        area.setText("")
        price.setText("")
        location.setText("")
        phNo.setText("")
    }
    private fun openGallery(){
        val intent= Intent(Intent.ACTION_PICK)
        intent.type="image/*"
        startActivityForResult(intent,REQUEST_GALLERY_CODE)
    }
    private fun openCamera(){
        val cameraIntent= Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent,REQUEST_CAMERA_CODE)
    }
    private fun bitmapToFile(
            bitmap: Bitmap,
            fileNameToSave: String
    ): File? {
        var file: File? = null
        return try {
            file = File(
                    getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                            .toString() + File.separator + fileNameToSave
            )
            file.createNewFile()
            //Convert bitmap to byte array
            val bos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, bos) // YOU can also save it in JPEG
            val bitMapData = bos.toByteArray()
            //write the bytes in file
            val fos = FileOutputStream(file)
            fos.write(bitMapData)
            fos.flush()
            fos.close()
            file
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            file // it will return null
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode== Activity.RESULT_OK){
            if(requestCode == REQUEST_GALLERY_CODE && data != null) {
                val selectedImage = data.data
                val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
                val contentResolver = contentResolver
                val cursor =
                        contentResolver.query(selectedImage!!, filePathColumn, null, null, null)
                cursor!!.moveToFirst()
                val columnIndex = cursor.getColumnIndex(filePathColumn[0])
                imageUrl = cursor.getString(columnIndex)
                profilepic.setImageBitmap(BitmapFactory.decodeFile(imageUrl))
                cursor.close()
            }
            else if (requestCode == REQUEST_CAMERA_CODE && data != null) {
                val imageBitmap = data.extras?.get("data") as Bitmap
                val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
                val file = bitmapToFile(imageBitmap, "$timeStamp.jpg")
                imageUrl = file!!.absolutePath
                profilepic.setImageBitmap(BitmapFactory.decodeFile(imageUrl))
            }
        }
    }
    private fun popup(){
        val popupMenu= PopupMenu(this,profilepic)
        popupMenu.menuInflater.inflate(R.menu.pmenu,popupMenu.menu)
        popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.gallery -> {
                    openGallery()
                    true
                }
                R.id.camera -> {
                    openCamera()
                    true
                }
                else -> false
            }
        })
        popupMenu.show()
    }
    private fun checkSensor(): Boolean {
        var flag = true
        if (sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT) == null) {
            flag = false
        }
        return flag
    }
    override fun onSensorChanged(event: SensorEvent?) {
//        val values = event!!.values[0]
//        if (values.toInt()>20000)
//            delegate.localNightMode=AppCompatDelegate.MODE_NIGHT_NO
//        else if (values > 0) {
//            delegate.localNightMode=AppCompatDelegate.MODE_NIGHT_YES
//        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }
}