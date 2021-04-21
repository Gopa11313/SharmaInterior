package com.example.sharmainteriordesign.ui

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.noteasap.RoomDatabase.db.Db
import com.example.sharmainteriordesign.R
import com.example.sharmainteriordesign.api.ServiceBuilder
import com.example.sharmainteriordesign.repository.UserRepository
import com.example.sharmainteriordesign.roomdatabase.entity.User
import com.example.sharmainteriordesign.utils.saveSharedPref
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity() {
    private lateinit var gotoSingup:TextView;
    private lateinit var login:Button;
    private lateinit var email:EditText
    private lateinit var password:EditText
    private val permissions = arrayOf(
            android.Manifest.permission.CAMERA,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
    )
    var flag=false;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        if (!hasPermission()) {
            requestPermission()
        }
        gotoSingup = findViewById(R.id.gotoSingup)
        login = findViewById(R.id.button)
        email = findViewById(R.id.email)
        password = findViewById(R.id.password)
        gotoSingup.setOnClickListener() {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
        login.setOnClickListener() {
            var user = User(email = email.text.toString(), password = password.text.toString())
            CoroutineScope(Dispatchers.IO).launch {
                val repository = UserRepository()
                val response = repository.checkUSer(user)
                if (response.success == true) {
                    Db.getInstance(this@LoginActivity).getUserDao().logout()
                    val sharedPref =getSharedPreferences("MyPref", AppCompatActivity.MODE_PRIVATE)
                    val editor=sharedPref.edit()
                    editor.remove("email")
                    editor.remove("password")
                            .apply()

                    Db.getInstance(this@LoginActivity).getUserDao().AddUSer(user)
                    saveSharedPref(_id = response.id!!, email = email.text.toString(), password =  password.text.toString())
                    ServiceBuilder.token = "Bearer ${response.token!!}"
                    ServiceBuilder.id = response.id
                    val data=response.data
                    ServiceBuilder.name=data?.get(0)?.name
                    withContext(Main) {
                        Toast.makeText(this@LoginActivity, "${ServiceBuilder.id}", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@LoginActivity, HomeActivity::class.java))

                    }
                } else {
                    withContext(Main) {
                        Toast.makeText(this@LoginActivity, "Inavalid User", Toast.LENGTH_SHORT).show()
                    }
                }

            }
        }

    }
    fun requestPermission() {
        ActivityCompat.requestPermissions(
                this@LoginActivity,
                permissions, 876
        )
    }
    fun hasPermission(): Boolean {
        var hasPermission = true
        for (permission in permissions) {
            if (ActivityCompat.checkSelfPermission(
                            this,
                            permission
                    ) != PackageManager.PERMISSION_GRANTED
            ) {
                hasPermission = false
            }
        }
        return hasPermission
    }
}
