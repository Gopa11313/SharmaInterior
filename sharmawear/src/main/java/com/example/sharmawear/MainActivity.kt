package com.example.sharmawear

import android.content.Intent
import android.os.Bundle
import android.support.wearable.activity.WearableActivity
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.sharmainteriordesign.api.ServiceBuilder
import com.example.sharmainteriordesign.repository.UserRepository
import com.example.sharmawear.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : WearableActivity() {
    private lateinit var email:EditText;
    private lateinit var password:EditText;
    private lateinit var login:Button;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        email=findViewById(R.id.email)
        password=findViewById(R.id.password)
        login=findViewById(R.id.login)
        login.setOnClickListener(){
            var user = User(email = email.text.toString(), password = password.text.toString())
            CoroutineScope(Dispatchers.IO).launch {
                val repository = UserRepository()
                val response = repository.checkUSer(user)
                if (response.success == true) {
                    ServiceBuilder.token = "Bearer ${response.token!!}"
                    ServiceBuilder.id = response.id
                    val data=response.data
                    ServiceBuilder.name=data?.get(0)?.name
                    withContext(Dispatchers.Main) {
                      startActivity(Intent(this@MainActivity,SecondActivity::class.java))

                    }
                } else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@MainActivity, "Inavalid User", Toast.LENGTH_SHORT).show()
                    }
                }

            }
        }
        // Enables Always-on
        setAmbientEnabled()
    }
}